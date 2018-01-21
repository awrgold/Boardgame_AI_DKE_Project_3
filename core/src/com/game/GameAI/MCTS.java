package com.game.GameAI;

import com.game.Components.GameConstants.Color;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.Tools.Link;
import com.game.TreeStructure.MCTSNode;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

import java.util.*;

public class MCTS implements Strategy {

    private GameState state;
    static Random r = new Random();
    static int nActions = 5;
    static double epsilon = 1e-6;
    private double weight;
    private double nVisits;
    private double score;
    private Action actionUsed;
    private MCTS parent;
    private ArrayList<MCTS> children;

    public MCTS(){    }


    public Action decideMove(GameState currentState){
        this.state = currentState;
        selectAction();
        return actionUsed;
    }

    public GameState getState(){
        return state;
    }

    public void selectAction() {

        List<MCTS> visited = new LinkedList<MCTS>();
        MCTS cur = this;
        visited.add(this);

        while (!cur.isLeaf()) {
            cur = cur.select();
            visited.add(cur);
        }

        cur.expand(cur);
        MCTS newNode = cur.select();
        visited.add(newNode);

        double score = rollOut(newNode);

        for (MCTS node : visited) {
            // would need extra logic for n-player game
            node.updateStats(score);
        }
    }

    public void expand(MCTS toExpandFrom) {
        children = new ArrayList<>();
        for (int i=0; i<nActions; i++) {

            ArrayList<Color> colors = toExpandFrom.getState().getGamingPlayer().lowestColors();
            Hand hand = toExpandFrom.getState().getGamingPlayer().getHand();
            HexagonalGrid grid = toExpandFrom.getState().getCurrentBoard().getGrid();
            HashMap<Tile, Color> tiles = bestTilesToPlace(colors, hand);
            ArrayList<Action> bestMoves = new ArrayList<>();

            // add all the best moves to a list
            for (Tile tile : tiles.keySet()) {
                bestMoves.add(bestPlacementForTile(possibleTilePlacements(tile, grid, tiles.get(tile)), grid));
            }

            MCTS child = setChild(bestMoves.get(0));
            children.add(child);
        }
    }

    private MCTS select() {
        MCTS selected = null;
        double bestValue = Double.MIN_VALUE;

        for (MCTS c : children) {
            double uctValue = c.score / (c.nVisits + epsilon) +
                    Math.sqrt(Math.log(nVisits+1) / (c.nVisits + epsilon)) +
                    r.nextDouble() * epsilon; // small random number to break ties randomly in unexpanded nodes


            if (uctValue > bestValue) {
                selected = c;
                bestValue = uctValue;
            }
        }
        return selected;
    }

    public boolean isLeaf() {
        return children == null;
    }

    public double rollOut(MCTS tn) {
        // ultimately a roll out will end in some value
        // assume for now that it ends in a win or a loss
        // and just return this at random
        return r.nextInt(2);
    }

    public void updateStats(double value) {
        nVisits++;
        score += value;
    }

    public int arity() {
        return children == null ? 0 : children.size();
    }
    public double getScore(){
        return score;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public double getWeight(){
        return weight;
    }

    public void setActionUsed(Action actionUsed) {
        this.actionUsed = actionUsed;
    }

    public Action getActionUsed(){
        return actionUsed;
    }

    public MCTS getParent(){
        return parent;
    }

    public void setParent(MCTS parent){
        this.parent = parent;
    }

    public ArrayList<MCTS> getChildren() {
        return children;
    }

    public void setState(GameState state){
        this.state = state;
    }

    public MCTS setChild(Action action) {
        //System.out.println(action.toString());

        double gain = action.actionGain(state.getCurrentBoard().getGrid());
        //System.out.println("GAIN: " + gain);

        GameState nextState = state.cloneGameState();
        //System.out.println("Gaming Player " + nextState.getGamingPlayer().getPlayerNo());
        Action modifiedAction = action.translateAction(nextState);

        nextState = nextState.applyAction(modifiedAction);
        //System.out.println(modifiedAction.toString());
        MCTS child = new MCTS();
        child.setState(nextState);

        child.setActionUsed(modifiedAction);
        child.setWeight(gain);

        child.setParent(this);
        System.out.println("creating node: " + child.getWeight());

        return child;

    }

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
    private Action bestPlacementForTile(ArrayList<Action> all, HexagonalGrid grid){
        double bestGain = 0;
        Action bestPlacement = null;
        for (int i = 0; i < nActions; i++){
            double gain = all.get(i).actionGain(grid);
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
}