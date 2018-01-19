package com.game.GameAI;

import com.game.Components.GameConstants.Color;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.Tools.HexagonActor;
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
- TODO: Integrate OpponentProbabilities class to inform MCTS further
- TODO: Integrate ExpectiMax to help prune MCTS

 */

public class MCTSSearch implements Strategy {

    static Random r = new Random();
    static int nActions = 6;
    static double epsilon = 1e-6;
    private MCTSTree tree;
    private List<Edge> children;
    private double nVisits;
    private double totValue;

    public MCTSSearch(){}

    public MCTSSearch(MCTSTree treeToSearch){
        this.tree = treeToSearch;
    }

    public Action selectAction() {
        List<MCTSNode> visited = new LinkedList<>();
        MCTSNode cur = this.tree.getRoot();
        visited.add(cur);

        while (!cur.isLeaf()) {
            cur = select();
            visited.add(cur);
        }

        expand(cur);
        MCTSNode newNode = select();
        Action newAction = decideMove(cur.getState());
        visited.add(newNode);

        double value = rollOut(newNode);
        for (MCTSNode node : visited) {
            // would need extra logic for n-player game
            node.updateStats(value);
        }

        return
    }

    public void expand(MCTSNode toExpandFrom) {
        children = new LinkedList<>();
        for (int i = 0; i < nActions; i++) {
            // This just adds all children, need to prioritize best children to reduce branching factor, but how??
            children.add(i, toExpandFrom.getChildrenEdges().get(i));
        }
    }

    private MCTSNode select() {
        MCTSNode selected = null;
        double bestValue = Double.MIN_VALUE;
        double nodeWeight;

        for (Edge c : children) {

            nodeWeight = c.getAction().actionGain(c.getParentNode().getState().getCurrentBoard().getGrid());

            double uctValue = c.getParentNode().getWeight() / (c.getParentNode().getNumVisits() + epsilon) +
                    (nodeWeight)*(Math.sqrt(Math.log(nVisits+1) / (c.getParentNode().getNumVisits() + epsilon))) +
                    r.nextDouble() * epsilon;  // small random number to break ties randomly in unexpanded nodes

            if (uctValue > bestValue) {
                selected = c.getChildNode();
                bestValue = uctValue;
            }
        }

        return selected;
    }

    public double rollOut(MCTSNode tn) {

        RandomStrategy rs = new RandomStrategy();
        GreedyStrategy gs;
        ExpectimaxStrategy es;



        // return r.nextInt(2);
    }

    public int arity() {
        return children == null ? 0 : children.size();
    }

    // Get a list of the best tiles to place.
    private HashMap<Tile, Color> bestTilesToPlace(ArrayList<Color> colors, Hand hand){
        HashMap<Tile, Color> pieces = new HashMap<>();

        for(Color color : colors){
            for(Tile t : hand.getPieces()){
                if (t.getActors()[0].getHexColor().getColor().equals(color) && t.getActors()[1].getHexColor().getColor().equals(color)){
                    pieces.entrySet().removeIf(entry -> entry.getValue().equals(color));
                    pieces.put(t, color);
                    System.out.println("Found a double to place: " + color + " - " + color);
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
            System.out.print(piece.getActors()[0].getHexColor().getColor().toString() + "-" + piece.getActors()[1].getHexColor().toString() + "  ");
        }
        System.out.print(" <--- pieces to play \n");

        return pieces;

    }

    // Search the board for all possible places to place a given tile
    private ArrayList<Action> possibleTilePlacements(Tile tile, HexagonalGrid grid, Color color) {
        ArrayList<Action> possibleActions = new ArrayList<>();
        System.out.println("Searching for best placements");

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

    // Find the most promising placement for a list of actions
    private Action bestPlacementForTile(ArrayList<Action> all, HexagonalGrid grid){
        double bestGain = 0;
        Action bestPlacement = null;
        for (Action a : all){
            double gain = a.actionGain(grid);
            if (gain >= bestGain) {
                bestGain = gain;
                bestPlacement = a;
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

        ArrayList<Color> colors = currentState.getGamingPlayer().lowestColors();
        Hand hand = currentState.getGamingPlayer().getHand();
        HexagonalGrid grid = currentState.getCurrentBoard().getGrid();

        HashMap<Tile, Color> tiles = bestTilesToPlace(colors, hand);
        ArrayList<Action> bestMoves = new ArrayList<>();

        for (Tile tile : tiles.keySet()){
            bestMoves.add(bestPlacementForTile(possibleTilePlacements(tile, grid, tiles.get(tile)), grid));

        }

        //System.out.println(bestMoves.size());
        double bestGain = 0;
        Action bestAction = null;
        for (Action a : bestMoves){
            if (a != null){
                double gain = a.actionGain(grid);
                if (gain >= bestGain) {
                    bestGain = gain;
                    bestAction = a;
                }
            }
        }

        if (bestAction == null){
            System.out.println("best action not found");
        }

        return bestAction;
    }



}