package com.game.GameAI;

import com.game.Components.GameConstants.Color;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.Tools.Link;
import com.game.TreeStructure.*;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

import java.util.*;

/*
This MCTS is adapted by Andrew Gold & Raimondo Grova from Simon Lucas' "random" MCTS example code found at:
http://mcts.ai/code/java.html

- This version of MCTS utilizes UCT to balance exploitation vs exploration
- Instead of playing randomly until gameOver(), we assume the opponent will play greedily
- To reduce branching factor, we only consider the opponents most immediately promising moves

        STEPS:
        Selection:
        - Select a node (at first the root). If no children, expand. Otherwise, go deeper and increment visits.
        - Prioritize nodes with fewer visits, and stop when all children reach minVisits.
        - If children exist already, choose most promising (score/visit ratio). +1 for win, 0 for loss/draw.
        Expansion:
        - Expand this node and create children of possible options
        Rollout:
        - From the children, select one (randomly?) play to the end and update the win/loss score.
        Backpropagation:
        - Backpropagate the result to the parent of the node you just played out from, updating each parent until the root is reached

        UCT:
        - T = maxVisits
        - C = exploration function that weighs on whether to trust results of explored nodes vs. trying to increase visits
        - NOTE: By also ordering moves based on point gain, we can argue that this technique increases the INFORMATION GAIN of its children's results. (HOW TO CALCULATE??)
        - The downside is that over time, exploring all moves eventually outscores exploring immediately rewarding moves, and that inflection point should be evaluated.


- TODO: Integrate OpponentProbabilities class to inform MCTS further?
- TODO: Integrate ExpectiMax to help prune MCTS?
- TODO: How to rollout? Clone players with new strategies?

 */

public class MCTSSearch implements Strategy {

    static Random r = new Random();
    static int nChildren = 6;
    static int minVisits = 20;
    static double epsilon = 1e-6;
    static List knownChildren;
    private int numNodes = 0;
    private double nVisits;
    private double totValue;
    private long runTime;
    private MCTSTree tree;
    private MCTSNode best;
    private boolean DEBUG = true;



    public MCTSSearch(){
    }

    public MCTSSearch(GameState state){
        //tree.setRoot(new MCTSNode(state));
    }

    public void selectAction(GameState currentState) {

        tree = new MCTSTree();
        // Construct a list of children to explore
        List<MCTSNode> visited = new LinkedList<>();
        // Create the root node
        MCTSNode cur = new MCTSNode(currentState); //clone the state!!!!!!!!!!!!!!!!!!!
        tree.setRoot(cur);
        // Add the root to the number of visited nodes
        visited.add(cur);

        // If a node is not leaf (never happens on first root state visit)
        while (!cur.isLeaf() && cur.getNumVisits() < 20) {
            if (DEBUG){
                System.out.println("Not a leaf, using UCT to choose child");
            }

            best = getBestNode(cur);
            // select the next node based on some selection criterion (UCT, etc)
            cur = select(cur);
            // add that next node to the list of visited nodes
            visited.add(cur);
            System.out.println("visiting node with score: " + cur.getScore());

        }

        /*
        At this point in selectAction, this means we have reached a leaf
        */
        // if a leaf, expand that node to get new children:
        expand(cur);
        // Select a new node of that newly expanded leaf to rollout from:
        MCTSNode newNode = select(cur);
        // Add this new node to the list of visited nodes:
        visited.add(newNode);
        // rollout and return the result:
        double value = rollOut(newNode);

        best = getBestNode(cur);
        cur = tree.getRoot();

        for (MCTSNode node : visited) {
            // Updating stats increments the visit counter, updates the score:
            node.updateStats(value);
            if (DEBUG){
                System.out.println("Rollout result: " + value);
            }
        }

    }

    public void expand(MCTSNode toExpandFrom) {

        if (DEBUG){
            System.out.println("---- START EXPANSION ----");

            System.out.println("Expanding");
        }
        knownChildren = new LinkedList<MCTSNode>();

        /*
        MCTSNode[] children = new MCTSNode[nChildren];
        for (int i = 0; i < nChildren; i++) {
            children[i] = toExpandFrom.setChild(a);
        }
        */
        Player currentPlayer = toExpandFrom.getState().getGamingPlayer();

        // If current player is MCTS, construct children from best moves from hand
        if (currentPlayer.getStrategy().equals("MCTS")) {

            // Get the available moves to gamingPlayer
            ArrayList<Color> colors = currentPlayer.lowestColors();
            Hand hand = currentPlayer.getHand();
            HexagonalGrid grid = toExpandFrom.getState().getCurrentBoard().getGrid();
            HashMap<Tile, Color> tiles = bestTilesToPlace(colors, hand);
            ArrayList<Action> promisingMoves = new ArrayList<>();
            ArrayList<Action> bestMoves = new ArrayList<>();


            // add all the best moves to an ordered list
            for (Tile tile : tiles.keySet()) {
                promisingMoves.add(bestPlacementForTile(possibleTilePlacements(tile, grid, tiles.get(tile)), grid, currentPlayer));
            }

            for (int i = 0; i < promisingMoves.size(); i++) {
                if (i < 6){
                    bestMoves.add(promisingMoves.get(i));
                }
            }
            if (DEBUG) {
                System.out.println("BestMoves size: " + bestMoves.size());
            }

            for (int i = 0; i < bestMoves.size(); i++) {

                // create children nodes for each possible state resulting from the best actions
                toExpandFrom.setChild(bestMoves.get(i));
                knownChildren.add(toExpandFrom.getChildren());
                numNodes++;

                if (DEBUG) {
                    System.out.println("Adding children to MCTSNode #: " + numNodes + ". Children size = " + knownChildren.size());
                }
            }

            // if current player is NOT MCTS, construct children based on most likely moves assuming lowest color is played
        }else {

            // Construct children greedily/expectimax/randomly, etc
            if (currentPlayer.getStrategy().equals("Greedy")) {
                GreedyStrategy greedy = new GreedyStrategy();

                // Get the available moves to gamingPlayer
                ArrayList<Color> colors = currentPlayer.lowestColors();
                Hand hand = currentPlayer.getHand();
                HexagonalGrid grid = toExpandFrom.getState().getCurrentBoard().getGrid();
                HashMap<Tile, Color> tiles = bestTilesToPlace(colors, hand);
                ArrayList<Action> bestMoves = new ArrayList<>();

                // add all the best moves to a list
                for (Tile tile : tiles.keySet()) {
                    bestMoves.add(bestPlacementForTile(possibleTilePlacements(tile, grid, tiles.get(tile)), grid, currentPlayer));
                }

                // create children nodes for each possible state resulting from the best actions
                for (Action a : bestMoves) {
                    /*
                    GameState newState = toExpandFrom.getState().cloneGameState();
                    newState = newState.applyAction(a);
                    toExpandFrom.getChildrenEdges().add(new MCTSEdge(toExpandFrom, new MCTSNode(newState), a));
                    */

                    toExpandFrom.setChild(a);
                    knownChildren.add(toExpandFrom.getChildren());
                    if (DEBUG){
                        System.out.println("Adding children to Greedy");
                    }
                }
                greedy.decideMove(toExpandFrom.getState());
            }

            if (currentPlayer.getStrategy().equals("Random")) {
                RandomStrategy random = new RandomStrategy();
                random.decideMove(toExpandFrom.getState());
            }
            if (currentPlayer.getStrategy().equals("ExpectiMax")) {
                ExpectimaxStrategy expecti = new ExpectimaxStrategy();
                expecti.decideMove(toExpandFrom.getState());
            }
        }

        if (DEBUG){
            System.out.println("---END EXPANSION---");
        }
    }


    private MCTSNode select(MCTSNode current) {

        if (DEBUG){
            System.out.println("---- START SELECTION ----");

            System.out.println("Selecting a child");
        }
        MCTSNode selected = null;
        double bestValue = Double.MIN_VALUE;
        double nodeWeight;

        // For each child in the list of children, get the weight
        for (MCTSNode c : current.getChildren()) {

            // Exploration parameter: get the total point gain from the action that resulted in this child, and set as weight
            // DO WE USE THE POINT GAIN OR Sqrt(2) AS EXPLORATION PARAMETER/WEIGHT?
            nodeWeight = c.getActionUsed().actionGain(c.getParent().getState().getCurrentBoard().getGrid(), c.getParent().getState().getGamingPlayer());

            // Determine the UCT value to influence the next choice when selecting
            double uctValue = c.getParent().getScore() / (c.getParent().getNumVisits() + epsilon) +
                    (nodeWeight)*(Math.sqrt(Math.log(nVisits+1) / (c.getParent().getNumVisits() + epsilon))) +
                    r.nextDouble() * epsilon;  // small random number to break ties randomly in unexpanded nodes

            if (uctValue > bestValue) {
                selected = c;
                bestValue = uctValue;

            }
        }
        if (DEBUG){
            System.out.println("UCT Value: " + bestValue);
        }

        selected.updateStats(bestValue);
        System.out.println("---- END EXPANSION ----");

        return selected;
    }

    public int rollOut(MCTSNode leaf) {

        RandomStrategy rs = new RandomStrategy();
        GreedyStrategy gs = new GreedyStrategy();
        // ExpectimaxStrategy es;

        // ... do a playout with some strategy
        if (DEBUG){
            System.out.println("---- START ROLLOUT ----");

            System.out.println("Rolling out");
        }

        System.out.println("---- END EXPANSION ----");

        return r.nextInt(2);

        /*
        if (leaf.getState().getCurrentBoard().gameOver() && leaf.getState().getWinner().getPlayerNo() == leaf.getState().getGamingPlayer().getPlayerNo()){
            return 1;
        }
        return 0;
        */
    }

    public MCTSNode getBestNode(MCTSNode toFind){

        MCTSNode bestNode = null;
        double bestGain = Double.MIN_VALUE;
        for (MCTSNode n : toFind.getChildren()){
            if (n.getActionUsed().actionGain(toFind.getState().getCurrentBoard().getGrid(), toFind.getState().getGamingPlayer()) > bestGain && !toFind.getChildren().isEmpty()){
                bestNode = n;
                bestGain = n.getActionUsed().actionGain(toFind.getState().getCurrentBoard().getGrid(), toFind.getState().getGamingPlayer());
            }
            if (toFind.getChildren().isEmpty()){
                System.out.println("No children");
            }
        }
        if (DEBUG){
            System.out.println("getBestNode() called");
        }
        return bestNode;
    }

    // Get a list of the best tiles to place.
    private HashMap<Tile, Color> bestTilesToPlace(ArrayList<Color> colors, Hand hand){
        HashMap<Tile, Color> pieces = new HashMap<>();

        for(Color color : colors){
            for(Tile t : hand.getPieces()){
                if (t.getActors()[0].getHexColor().getColor().equals(color) && t.getActors()[1].getHexColor().getColor().equals(color)){
                    pieces.entrySet().removeIf(entry -> entry.getValue().equals(color));
                    pieces.put(t, color);
                    if (DEBUG){
                        System.out.println("Found a double to place: " + color + " - " + color);
                    }
                    break;
                } if (t.getActors()[0].getHexColor().getColor().equals(color) || t.getActors()[1].getHexColor().getColor().equals(color)){
                    pieces.put(t, color);
                }
            }
        }

        if (pieces.keySet().size() == 0){
            pieces.put(hand.getPieces().get(0), hand.getPieces().get(0).getActors()[0].getHexColor().getColor());
        }

        for(Tile piece : pieces.keySet()){
            if (DEBUG){
                System.out.print("KeySet piece: " + piece.getActors()[0].getHexColor().getColor().toString() + "-" + piece.getActors()[1].getHexColor().toString() + "  ");
                System.out.print(" <--- pieces to play \n");
            }
        }

        return pieces;
    }

    // Search the board for all possible places to place a given tile
    private ArrayList<Action> possibleTilePlacements(Tile tile, HexagonalGrid grid, Color color) {
        ArrayList<Action> possibleActions = new ArrayList<>();
        //System.out.println("Searching for best placements");

        //ITERATE ALL OVER THE CURRENT BOARD
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {

                //FOR EACH HEXAGON
                if (hexagon.getSatelliteData().isPresent()) {
                    Link hexLink = (Link) hexagon.getSatelliteData().get();
                    HexagonActor currentHexActor = hexLink.getActor();

                    //IF THE RELATED HEXACTOR'S COLOR IS EQUAL TO ONE IN THE TILE
                    if (currentHexActor.getHexColor().getColor().equals(color)) {

                        if (color.equals(tile.getActors()[0].getHexColor().getColor())) {
                            tile.setFirst(tile.getActors()[0]);
                        } else {
                            tile.setFirst(tile.getActors()[1]);
                        }


                        //FIND ALL POSSIBLE PLACEMENTS AROUND THAT TILE
                        Hexagon[][] possiblePlacements = new Hexagon[7][6];
                        int c = 0;
                        for (Object hex : grid.getNeighborsOf(hexagon)) {
                            if (hex instanceof Hexagon) {
                                Hexagon currentNeighbor = (Hexagon) hex;

                                if (currentNeighbor.getSatelliteData().isPresent()) {
                                    Link neighLink = (Link) currentNeighbor.getSatelliteData().get();
                                    HexagonActor neighHexActor = neighLink.getActor();

                                    //THE FIRST ONE IS THE FIRST PLACEMENT
                                    if (neighHexActor.getHexColor().getColor().equals(Color.EMPTY)) {
                                        possiblePlacements[0][c] = currentNeighbor;
                                        int g = 1;

                                        //LOOKING FOR FREE NEIGHBORS
                                        for (Object hex2 : grid.getNeighborsOf(currentNeighbor)) {
                                            if (hex2 instanceof Hexagon) {
                                                Hexagon currentNeighbor2 = (Hexagon) hex2;

                                                if (currentNeighbor2.getSatelliteData().isPresent()) {
                                                    Link neighLink2 = (Link) currentNeighbor2.getSatelliteData().get();
                                                    HexagonActor neighHexActor2 = neighLink2.getActor();

                                                    if (neighHexActor2.getHexColor().getColor().equals(Color.EMPTY)) {
                                                        possiblePlacements[g][c] = currentNeighbor2;
                                                        g++;
                                                    }
                                                }
                                            }
                                        }
                                        c++;
                                    }
                                }
                            }
                        }
                        // ADD TO THE LIST OF POSSIBLE ACTIONS
                        for (int i = 0; i < 6; i++){
                            if (possiblePlacements[0][i] != null){
                                for (int j = 1; j < 7; j++){
                                    if (possiblePlacements[j][i] != null){
                                        possibleActions.add(new Action(possiblePlacements[0][i], possiblePlacements[j][i], tile));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        // IF NO POSSIBLE ACTIONS - DO SOMETHING RANDOM
        if (possibleActions.size() == 0){
            possibleActions.add(randomAction(tile, grid));
        }

        return possibleActions;
    }

    // Find the most promising placement for a list of actions and ORDERS THEM
    private Action bestPlacementForTile(ArrayList<Action> all, HexagonalGrid grid, Player player){
        double bestGain = 0;
        Action bestPlacement = null;
        for (int i = 0; i < all.size(); i++){
            double gain = all.get(i).actionGain(grid, player);
            if (gain >= bestGain) {
                bestGain = gain;
                bestPlacement = all.get(i);
            }
        }
        return bestPlacement;
    }

    // Do something random (worst-case scenario)
    private Action randomAction(Tile tile, HexagonalGrid grid) {
        System.out.println("No good moves, doing random action");
        Action randomAction = new Action();
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                //FOR EACH HEXAGON
                if (hexagon.getSatelliteData().isPresent()) {
                    Link hexLink = (Link) hexagon.getSatelliteData().get();
                    HexagonActor currentHexActor = hexLink.getActor();

                    if (currentHexActor.getHexColor().getColor().equals(Color.EMPTY)) {
                        for (Object hex : grid.getNeighborsOf(hexagon)) {
                            if (hex instanceof Hexagon) {
                                Hexagon currentNeighbor = (Hexagon) hex;

                                if (currentNeighbor.getSatelliteData().isPresent()) {
                                    Link neighLink = (Link) currentNeighbor.getSatelliteData().get();
                                    HexagonActor neighHexActor = neighLink.getActor();

                                    //THE FIRST ONE IS THE FIRST PLACEMENT
                                    if (neighHexActor.getHexColor().getColor().equals(Color.EMPTY)) {
                                        randomAction.setH1(hexagon);
                                        randomAction.setH2(currentNeighbor);
                                        randomAction.setTile(tile);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        return randomAction;
    }

    // Decide which action to go with when creating the next GameState

    public Action decideMove(GameState currentState){

        long runTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - runTime < 100){
            selectAction(currentState);
        }

        return getBestNode(tree.getRoot()).getActionUsed();
//        if (DEBUG){
//            System.out.println("No good moves, doing something random");
//        }
//        return randomAction(currentState.getGamingPlayer().getHand().getPieces().get(0), currentState.getCurrentBoard().getGrid());
    }


}