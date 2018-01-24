package com.game.GameAI;

import com.game.Components.GameConstants.Color;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.GameAssets.HexagonActor;
import com.game.Components.Tools.Link;
import com.game.TreeStructure.*;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

import java.util.*;

/*
This MCTS is adapted by Andrew Gold/Raimondo Grova from Simon Lucas' "random" MCTS example code found at:
http://mcts.ai/code/java.html

- This version of MCTS utilizes UCT to balance exploitation vs exploration
- Instead of playing randomly until gameOver(), we assume the opponent will play greedily
- To reduce branching factor, we only consider the opponents most immediately promising moves

        1) * From the current node, expand the root into a set of children
        2) * Select the best child (from some metric - greedy?) and expand that child
        3) * Continue doing this until you reach a leaf node
        4) At the leaf, do a random vs random playout, and return the result to the leaf
        5) Update all generations of parents with the result
        6) Go to the parent, and choose another child and backpropagate the result upwards
        7) Once all children are explored, go up one level and choose another child as in step 6
        8) Continue this pattern until all children of the root are fully expanded, and choose the best path
        9) Update the root, and begin again


- TODO: Integrate OpponentProbabilities class to inform MCTS further
- TODO: Integrate ExpectiMax to help prune MCTS?
- TODO: How to rollout? Clone players with new strategies?

 */

public class MCTSSearch implements Strategy {
    @Override
    public Action decideMove(GameState currentState) {
        return null;
    }


//    static Random r = new Random();
//    static int nChildren = 6;
//    static int ply = 3;
//    static double epsilon = 1e-6;
//    static List knownChildren;
//    private MCTSTree tree;
//    private double nVisits;
//    private double totValue;
//    private GameState startingState;
//
//    public MCTSSearch(){
//        selectAction();
//    }
//
//    public MCTSSearch(MCTSTree treeToSearch){
//        this.tree = treeToSearch;
//        tree.setRoot(new MCTSNode(startingState));
//    }
//
//    public void selectAction() {
//
//
//        // Construct a list of children to explore
//        List<MCTSNode> visited = new LinkedList<>();
//        MCTSNode cur = this.tree.getRoot();
//        visited.add(cur);
//
//        //TODO: THIS DOES NOT STOP, how do we find leaf?
//        while (!isLeaf()) {
//            cur = select(cur);
//            visited.add(cur);
//            System.out.println("visiting node with score: " + cur.getScore());
//        }
//
//        expand(cur);
//        MCTSNode newNode = select(cur);
////        Action newAction = decideMove(cur.getState());
//        visited.add(newNode);
//
//        double value = rollOut(newNode);
//
//        for (MCTSNode node : visited) {
//            // Updating stats increments the visit counter, updates the score, and the weight.
//            node.updateStats(value);
//            System.out.println("Rollout result: " + value);
//        }
//
//    }
//
//    public boolean isLeaf(){
//        return knownChildren.isEmpty();
//    }
//
//    public void expand(MCTSNode toExpandFrom) {
//        knownChildren = new LinkedList<MCTSEdge>();
//
//            for (int j = 0; j < nChildren; j++) {
//            /*
//            - Find the lowest color of the hand of the gaming player
//            - Find all possible placements of that color on the current board
//            - From that, choose the top 3-6-10?? and create a list of children from these placements.
//             */
//                Player currentPlayer = toExpandFrom.getState().getGamingPlayer();
//                // Add new edges, we need parent, child, action
//                // From these actions we create the child
//
//                // Depending on strategy, construct children according to strategy
//                if (currentPlayer.getStrategy().equals("MCTS")) {
//
//                    // Get the available moves to gamingPlayer
//                    ArrayList<Color> colors = currentPlayer.lowestColors();
//                    ArrayList<Tile> hand = currentPlayer.getHand();
//                    HexagonalGrid grid = toExpandFrom.getState().getCurrentBoard().getGrid();
//                    HashMap<Tile, Color> tiles = bestTilesToPlace(colors, hand);
//                    ArrayList<Action> bestMoves = new ArrayList<>();
//
//                    // add all the best moves to a list
////                    for (Tile tile : tiles.keySet()) {
////                        bestMoves.add(bestPlacementForTile(possibleTilePlacements(tile, grid, tiles.get(tile)), state));
////                    }
//
//                    // create children nodes for each possible state resulting from the best actions
//                    for (Action a : bestMoves) {
//
//                    /*
//                    GameState newState = toExpandFrom.getState().cloneGameState();
//                    newState = newState.applyAction(a);
//                    toExpandFrom.getChildrenEdges().add(new MCTSEdge(toExpandFrom, new MCTSNode(newState), a));
//                    */
//
//                        toExpandFrom.setChild(a);
//                        knownChildren.add(toExpandFrom.getChildren());
//                    }
//
//                } else {
//                    // Construct children greedily/expectimax/randomly, etc
//                    if (currentPlayer.getStrategy().equals("Greedy")) {
//                        GreedyStrategy greedy = new GreedyStrategy();
//
//                        // Get the available moves to gamingPlayer
//                        ArrayList<Color> colors = currentPlayer.lowestColors();
//                        ArrayList<Tile> hand = currentPlayer.getHand();
//                        HexagonalGrid grid = toExpandFrom.getState().getCurrentBoard().getGrid();
//                        HashMap<Tile, Color> tiles = bestTilesToPlace(colors, hand);
//                        ArrayList<Action> bestMoves = new ArrayList<>();
//
//                        // add all the best moves to a list
////                        for (Tile tile : tiles.keySet()) {
////                            bestMoves.add(bestPlacementForTile(possibleTilePlacements(tile, grid, tiles.get(tile)), grid));
////                        }
//
//                        // create children nodes for each possible state resulting from the best actions
//                        for (Action a : bestMoves) {
//                    /*
//                    GameState newState = toExpandFrom.getState().cloneGameState();
//                    newState = newState.applyAction(a);
//                    toExpandFrom.getChildrenEdges().add(new MCTSEdge(toExpandFrom, new MCTSNode(newState), a));
//                    */
//
//                            toExpandFrom.setChild(a);
//                        }
//
//                    }
//
//                    if (currentPlayer.getStrategy().equals("Random")) {
//                        RandomStrategy random = new RandomStrategy();
//                        random.decideMove(toExpandFrom.getState());
//                    }
//                    if (currentPlayer.getStrategy().equals("ExpectiMax")) {
//                        ExpectimaxStrategy expecti = new ExpectimaxStrategy();
//                    }
//                }
//            }
//
//    }
//
//
//    private MCTSNode select(MCTSNode current) {
//        MCTSNode selected = null;
//        double bestValue = Double.MIN_VALUE;
//        double nodeWeight;
//
//        // Not the top 6, choosing all atm
//        for (MCTSNode c : current.getChildren()) {
//
//            // Here we need to determine if the weight is being chosen from the child or the parent, and which we want.
//            nodeWeight = c.getActionUsed().actionGain(c.getParent().getState());
//
//            double uctValue = c.getParent().getWeight() / (c.getParent().getNumVisits() + epsilon) +
//                    (nodeWeight)*(Math.sqrt(Math.log(nVisits+1) / (c.getParent().getNumVisits() + epsilon))) +
//                    r.nextDouble() * epsilon;  // small random number to break ties randomly in unexpanded nodes
//
//            if (uctValue > bestValue) {
//                selected = c;
//                bestValue = uctValue;
//            }
//        }
//        selected.updateStats(bestValue);
//        return selected;
//    }
//
//    public int rollOut(MCTSNode tn) {
//
//        RandomStrategy rs = new RandomStrategy();
//        GreedyStrategy gs = new GreedyStrategy();
//        // ExpectimaxStrategy es;
//
//        // ... do a playout with some strategy
//
//        return r.nextInt(2);
//
////        if (getWinner() == currentPlayer){
////            return 1;
////        }
////        return 0;
//    }
//
//    // Get a list of the best tiles to place.
//    private HashMap<Tile, Color> bestTilesToPlace(ArrayList<Color> colors, ArrayList<Tile> hand){
//        HashMap<Tile, Color> pieces = new HashMap<>();
//
//        for(Color color : colors){
//            for(Tile t : hand){
//                if (t.getActors()[0].getHexColor().getColor().equals(color) && t.getActors()[1].getHexColor().getColor().equals(color)){
//                    pieces.entrySet().removeIf(entry -> entry.getValue().equals(color));
//                    pieces.put(t, color);
//                    System.out.println("Found a double to place: " + color + " - " + color);
//                    break;
//                } if (t.getActors()[0].getHexColor().getColor().equals(color) || t.getActors()[1].getHexColor().getColor().equals(color)){
//                    pieces.put(t, color);
//                }
//            }
//        }
//
//        if (pieces.keySet().size() == 0){
//            pieces.put(hand.get(0), hand.get(0).getActors()[0].getHexColor().getColor());
//        }
//
//        for(Tile piece : pieces.keySet()){
//            System.out.print(piece.getActors()[0].getHexColor().getColor().toString() + "-" + piece.getActors()[1].getHexColor().toString() + "  ");
//        }
//        System.out.print(" <--- pieces to play \n");
//
//        return pieces;
//    }
//
//    // Search the board for all possible places to place a given tile
//    private ArrayList<Action> possibleTilePlacements(Tile tile, HexagonalGrid grid, Color color) {
//        ArrayList<Action> possibleActions = new ArrayList<>();
//        //System.out.println("Searching for best placements");
//
//        //ITERATE ALL OVER THE CURRENT BOARD
//        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
//            @Override
//            public void call(Hexagon hexagon) {
//
//                //FOR EACH HEXAGON
//                if (hexagon.getSatelliteData().isPresent()) {
//                    Link hexLink = (Link) hexagon.getSatelliteData().get();
//                    HexagonActor currentHexActor = hexLink.getActor();
//
//                    //IF THE RELATED HEXACTOR'S COLOR IS EQUAL TO ONE IN THE TILE
//                    if (currentHexActor.getHexColor().getColor().equals(color)) {
//
//                        if (color.equals(tile.getActors()[0].getHexColor().getColor())) {
//                            tile.setFirst(tile.getActors()[0]);
//                        } else {
//                            tile.setFirst(tile.getActors()[1]);
//                        }
//
//
//                        //FIND ALL POSSIBLE PLACEMENTS AROUND THAT TILE
//                        Hexagon[][] possiblePlacements = new Hexagon[7][6];
//                        int c = 0;
//                        for (Object hex : grid.getNeighborsOf(hexagon)) {
//                            if (hex instanceof Hexagon) {
//                                Hexagon currentNeighbor = (Hexagon) hex;
//
//                                if (currentNeighbor.getSatelliteData().isPresent()) {
//                                    Link neighLink = (Link) currentNeighbor.getSatelliteData().get();
//                                    HexagonActor neighHexActor = neighLink.getActor();
//
//                                    //THE FIRST ONE IS THE FIRST PLACEMENT
//                                    if (neighHexActor.getHexColor().getColor().equals(Color.EMPTY)) {
//                                        possiblePlacements[0][c] = currentNeighbor;
//                                        int g = 1;
//
//                                        //LOOKING FOR FREE NEIGHBORS
//                                        for (Object hex2 : grid.getNeighborsOf(currentNeighbor)) {
//                                            if (hex2 instanceof Hexagon) {
//                                                Hexagon currentNeighbor2 = (Hexagon) hex2;
//
//                                                if (currentNeighbor2.getSatelliteData().isPresent()) {
//                                                    Link neighLink2 = (Link) currentNeighbor2.getSatelliteData().get();
//                                                    HexagonActor neighHexActor2 = neighLink2.getActor();
//
//                                                    if (neighHexActor2.getHexColor().getColor().equals(Color.EMPTY)) {
//                                                        possiblePlacements[g][c] = currentNeighbor2;
//                                                        g++;
//                                                    }
//                                                }
//                                            }
//                                        }
//                                        c++;
//                                    }
//                                }
//                            }
//                        }
//                        // ADD TO THE LIST OF POSSIBLE ACTIONS
//                        for (int i = 0; i < 6; i++){
//                            if (possiblePlacements[0][i] != null){
//                                for (int j = 1; j < 7; j++){
//                                    if (possiblePlacements[j][i] != null){
//                                        possibleActions.add(new Action(possiblePlacements[0][i], possiblePlacements[j][i], tile));
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        });
//
//        // IF NO POSSIBLE ACTIONS - DO SOMETHING RANDOM
//        if (possibleActions.size() == 0){
//            possibleActions.add(randomAction(tile, grid));
//        }
//
//        return possibleActions;
//    }
//
//    // Find the most promising placement for a list of actions and ORDERS THEM
//    private Action bestPlacementForTile(ArrayList<Action> all, GameState state){
//        double bestGain = 0;
//        Action bestPlacement = null;
//        for (int i = 0; i < nChildren; i++){
//            double gain = all.get(i).actionGain(state);
//            if (gain >= bestGain) {
//                bestGain = gain;
//                bestPlacement = all.get(i);
//            }
//        }
//        return bestPlacement;
//    }
//
//    // Do something random (worst-case scenario)
//    private Action randomAction(Tile tile, HexagonalGrid grid) {
//        System.out.println("No good moves, doing random action");
//        Action randomAction = new Action();
//        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
//            @Override
//            public void call(Hexagon hexagon) {
//                //FOR EACH HEXAGON
//                if (hexagon.getSatelliteData().isPresent()) {
//                    Link hexLink = (Link) hexagon.getSatelliteData().get();
//                    HexagonActor currentHexActor = hexLink.getActor();
//
//                    if (currentHexActor.getHexColor().getColor().equals(Color.EMPTY)) {
//                        for (Object hex : grid.getNeighborsOf(hexagon)) {
//                            if (hex instanceof Hexagon) {
//                                Hexagon currentNeighbor = (Hexagon) hex;
//
//                                if (currentNeighbor.getSatelliteData().isPresent()) {
//                                    Link neighLink = (Link) currentNeighbor.getSatelliteData().get();
//                                    HexagonActor neighHexActor = neighLink.getActor();
//
//                                    //THE FIRST ONE IS THE FIRST PLACEMENT
//                                    if (neighHexActor.getHexColor().getColor().equals(Color.EMPTY)) {
//                                        randomAction.setH1(hexagon);
//                                        randomAction.setH2(currentNeighbor);
//                                        randomAction.setTile(tile);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        });
//
//        return randomAction;
//    }
//
//    // Decide which action to go with when creating the next GameState
//
//    public Action decideMove(GameState currentState){
//
//        this.startingState = currentState;
//
//        ArrayList<Color> colors = currentState.getGamingPlayer().lowestColors();
//        ArrayList<Tile> hand = currentState.getGamingPlayer().getHand();
//        HexagonalGrid grid = currentState.getCurrentBoard().getGrid();
//
//        HashMap<Tile, Color> tiles = bestTilesToPlace(colors, hand);
//        ArrayList<Action> bestMoves = new ArrayList<>();
//
////        for (Tile tile : tiles.keySet()){
////            bestMoves.add(bestPlacementForTile(possibleTilePlacements(tile, grid, tiles.get(tile)), grid));
////
////        }
//
//        //System.out.println(bestMoves.size());
//        double bestGain = 0;
//        Action bestAction = null;
//        for (Action a : bestMoves){
//            if (a != null){
//                double gain = a.actionGain(currentState);
//                if (gain >= bestGain) {
//                    bestGain = gain;
//                    bestAction = a;
//                }
//            }
//        }
//
//        if (bestAction == null){
//            System.out.println("best action not found");
//        }
//
//        return bestAction;
//    }



}