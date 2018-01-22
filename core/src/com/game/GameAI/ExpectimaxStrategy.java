package com.game.GameAI;

import com.badlogic.gdx.Game;
import com.game.Components.PlayerAssets.Hand;
import com.game.TreeStructure.Edge;
import com.game.TreeStructure.Node;
import com.game.TreeStructure.Tree;
import com.game.Components.GameConstants.Color;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.Tools.Link;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.backport.Optional;
import rx.functions.Action1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpectimaxStrategy implements Strategy {

    public static final int INF = 1000;

    /*
    1) create a new tree with the current state as root
    2) build the first tree level with possible moves for the gaming player...
        -for each tile find the placement with highest gain and create a new node (gain is node weigth) with that placement


    */
/*
    private Action bestTilePlacement(Tile tile, GameState currentState, Player player) {
        ArrayList<Action> possibleActions = new ArrayList<>();
        HexagonalGrid grid = currentState.getCurrentBoard().getGrid();
        Color color;
        if (player.lowestColors().contains(tile.getActors()[0].getHexColor())){
            color = tile.getActors()[0].getHexColor();
        } else if (player.lowestColors().contains(tile.getActors()[1].getHexColor())) {
            color = tile.getActors()[1].getHexColor();
        } else {
            color = tile.getActors()[0].getHexColor();
        }

        //ITERATE ALL OVER THE CURRENT BOARD
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                //FOR EACH HEXAGON
                if (hexagon.getSatelliteData().isPresent()) {
                    Link hexLink = (Link) hexagon.getSatelliteData().get();
                    HexagonActor currentHexActor = hexLink.getActor();
                    //IF THE RELATED HEXACTOR'S COLOR IS EQAUL TO ONE IN THE TILE
                    if (currentHexActor.getHexColor().equals(color)) {
                        if (color.equals(tile.getActors()[0].getHexColor())) {
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
                                    if (neighHexActor.getHexColor().equals(Color.EMPTY)) {
                                        possiblePlacements[0][c] = currentNeighbor;
                                        int g = 1;
                                        //LOOKING FOR FREE NEIGHBORS
                                        for (Object hex2 : grid.getNeighborsOf(currentNeighbor)) {
                                            if (hex2 instanceof Hexagon) {
                                                Hexagon currentNeighbor2 = (Hexagon) hex2;

                                                if (currentNeighbor2.getSatelliteData().isPresent()) {
                                                    Link neighLink2 = (Link) currentNeighbor2.getSatelliteData().get();
                                                    HexagonActor neighHexActor2 = neighLink2.getActor();

                                                    if (neighHexActor2.getHexColor().equals(Color.EMPTY)) {
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

        if (possibleActions.size() == 0){
            possibleActions.add(randomAction(tile, grid));

        }

        double bestGain = 0;
        Action bestPlacement = null;
        for (Action a : possibleActions){
            double gain = a.actionGain(grid);
            if (gain >= bestGain) {
                bestGain = gain;
                bestPlacement = a;
            }
        }
        return bestPlacement;
    }

    private Action randomAction(Tile tile, HexagonalGrid grid) {
        //System.out.println("No good moves, doing random action");
        Action randomAction = new Action();
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                //FOR EACH HEXAGON
                if (hexagon.getSatelliteData().isPresent()) {
                    Link hexLink = (Link) hexagon.getSatelliteData().get();
                    HexagonActor currentHexActor = hexLink.getActor();

                    if (currentHexActor.getHexColor().equals(Color.EMPTY)) {
                        for (Object hex : grid.getNeighborsOf(hexagon)) {
                            if (hex instanceof Hexagon) {
                                Hexagon currentNeighbor = (Hexagon) hex;

                                if (currentNeighbor.getSatelliteData().isPresent()) {
                                    Link neighLink = (Link) currentNeighbor.getSatelliteData().get();
                                    HexagonActor neighHexActor = neighLink.getActor();

                                    //THE FIRST ONE IS THE FIRST PLACEMENT
                                    if (neighHexActor.getHexColor().equals(Color.EMPTY)) {

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
    public ArrayList<Action> possibleNextActions(GameState currentState, Player player){

        ArrayList<Action> possibleActions = new ArrayList<>();
        for (Tile t : player.getHand().getPieces()){
            Action move = bestTilePlacement(t, currentState, player);
            possibleActions.add(move);

        }

        return possibleActions;


    }*/

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
        //System.out.println(possibleActions.size());

        return possibleActions;
    }

    private Action bestPlacementForTile(ArrayList<Action> all, HexagonalGrid grid, Player player){
        double bestGain = 0;
        Action bestPlacement = null;
        for (Action a : all){
            double gain = a.actionGain(grid, player);
            if (gain >= bestGain) {
                bestGain = gain;
                bestPlacement = a;
            }
        }
        return bestPlacement;
    }


    private HashMap<Tile, Color> bestTilesToPlace(ArrayList<Color> colors, Hand hand){
        HashMap<Tile, Color> pieces = new HashMap<>();

        for(Color color : colors){
            for(Tile t : hand.getPieces()){
                if (t.getActors()[0].getHexColor().getColor().equals(color) && t.getActors()[1].getHexColor().getColor().equals(color)){
                    pieces.entrySet().removeIf(entry -> entry.getValue().equals(color));
                    pieces.put(t, color);
                    //System.out.println("Found a double to place: " + color + " - " + color);
                    break;
                } if (t.getActors()[0].getHexColor().getColor().equals(color) || t.getActors()[1].getHexColor().getColor().equals(color)){
                    pieces.put(t, color);
                }
            }
        }

        if (pieces.keySet().size() == 0){
            pieces.put(hand.getPieces().get(0), hand.getPieces().get(0).getActors()[0].getHexColor().getColor());
        }

        //for(Tile piece : pieces.keySet()){
        //System.out.print(piece.getActors()[0].getHexColor().getColor().toString() + "-" + piece.getActors()[1].getHexColor().toString() + "  ");
        //}
        //System.out.print(" <--- pieces to play \n");

        return pieces;

    }


    private Action randomAction(Tile tile, HexagonalGrid grid) {
        //System.out.println("No good moves, doing random action");
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

    public ArrayList<Action> possibleNextActions(GameState currentState){
        ArrayList<Color> colors = currentState.getGamingPlayer().lowestColors();
        Hand hand = currentState.getGamingPlayer().getHand();
        HexagonalGrid grid = currentState.getCurrentBoard().getGrid();

        HashMap<Tile, Color> tiles = bestTilesToPlace(colors, hand);
        ArrayList<Action> bestMoves = new ArrayList<>();

        for (Tile tile : tiles.keySet()){
            bestMoves.add(bestPlacementForTile(possibleTilePlacements(tile, grid, tiles.get(tile)), grid, currentState.getGamingPlayer()));

        }
        return bestMoves;
    }


    public Node expectiminimax(Node node, int depth) {

        double a;
        if(depth == 0) {
            //System.out.println("Depth 0, returning node of weight: " + node.getWeight());
            return node;
        }

        Node next;
        Action nextMove = null;

        if(depth % 2 == 0) {
            if (node.getState().getCurrentBoard().gameOver()){
                return node;
            }
            //System.out.println("    My move");
            a = -INF;
            ArrayList<Action> possibleActions = possibleNextActions(node.getState());
            //System.out.println("Found " + possibleActions.size() + " possible actions to play");
            for(int i = 0; i < possibleActions.size(); i++) {
                next = expectiminimax(node.setChild(possibleActions.get(i)), depth - 1);
                //System.out.println("    Possible action: " + possibleActions.get(i) + " || Gain: " + next.getWeight());
                if(a < next.getWeight()) {
                    a = next.getWeight();

                    //System.out.println("    Better Action: " + possibleActions.get(i) + " || Gain: " + next.getWeight());
                    nextMove = possibleActions.get(i);
                }
            }
            return node.setChild(nextMove);
        }
        else {

            if (node.getState().getCurrentBoard().gameOver()){
                return node;
            }
            //System.out.println("        Chance node");
            a = 0;
            //create an HashMap that contains Tiles with its probabilities of being in the opponent's hand
            //for each tile create an action (bestTilePlacement)
            //expectimax(setChild(newAction))

            HashMap<Tile, Double> possibilities = node.getState().tilesExpectations(node.getState().getGamingPlayer().lowestColors());
            for (Tile t : possibilities.keySet()){
                Action bestAction = bestPlacementForTile(possibleTilePlacements(t, node.getState().getCurrentBoard().getGrid(), t.getFirst().getHexColor()),
                        node.getState().getCurrentBoard().getGrid(), node.getState().getGamingPlayer());
                double b = possibilities.get(t);
                next = expectiminimax(node.setChild(bestAction), depth - 1);
                a = a + b * next.getWeight();
                //nextMove = next.getActionUsed();
                //System.out.println("            Predicted Action: " + bestAction.toString() + " || Gain: " + next.getWeight());
            }
            node.setWeight(a * node.getWeight());
            return node;


        }
        //System.out.println(nextMove.toString());
        //System.out.println("Weight: " + a);
        //Node nextNode = node.setChild(nextMove);
        //nextNode.setWeight(a * nextNode.getWeight());

        //System.out.println("Setting weight: " + nextNode.getWeight());

        //return nextNode;
    }

    public Action decideMove(GameState currentState) {

        Node root = new Node(currentState.cloneGameState());

        Node bestNode = expectiminimax(root, 2);

        //System.out.println(bestNode.getActionUsed().toString());

        return bestNode.getActionUsed().translateAction(currentState);
    }
}
