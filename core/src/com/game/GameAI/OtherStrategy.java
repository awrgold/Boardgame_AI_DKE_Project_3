package com.game.GameAI;

import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.Tools.Link;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

import java.util.ArrayList;
import java.util.HashMap;

public class OtherStrategy implements Strategy {
    private ArrayList<Action> Moves;

    //A candidate set, from which a solution is created (the hand)
    //A selection function, which chooses the best candidate to be added to the solution (bestTilesToPlace())
    //A feasibility function, that is used to determine if a candidate can be used to contribute to a solution (possibleTilePlacements())
    //An objective function, which assigns a value to a solution, or a partial solution, (bestPlacementForTile())
    //A solution function, which will indicate when we have discovered a complete solution (decideMove())

    //  PICK FROM HAND TILES THAT CONTAIN THAT COLORS (IF THERE'S A DOUBLE IS THE BEST ONE)
    private HashMap<Tile, String> bestTilesToPlace(ArrayList<String> colors, Hand hand){
        HashMap<Tile, String> pieces = new HashMap<>();

        for(String color : colors){
            for(Tile t : hand.getPieces()){
                if (t.getActors()[0].getHexColor().equals(color) && t.getActors()[1].getHexColor().equals(color)){
                    pieces.entrySet().removeIf(entry -> entry.getValue().equals(color));
                    pieces.put(t, color);
                    //System.out.println("Found a double to place: " + color + " - " + color);
                    break;
                } if (t.getActors()[0].getHexColor().equals(color) || t.getActors()[1].getHexColor().equals(color)){
                    pieces.put(t, color);

                }
            }
        }

        if (pieces.keySet().size() == 0){
            pieces.put(hand.getPieces().get(0), hand.getPieces().get(0).getActors()[0].getHexColor());
            //System.out.println("totally random");
        }

        //for(Tile piece : pieces.keySet()){
        //System.out.print(piece.getActors()[0].getHexColor() + "-" + piece.getActors()[1].getHexColor() + "  ");
        //}
        //System.out.println(pieces.entrySet().size() + " <--- pieces to play");

        return pieces;

    }

    private ArrayList<Action> possibleTilePlacements(Tile tile, HexagonalGrid grid, String color) {
        ArrayList<Action> possibleActions = new ArrayList<>();

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
                                    if (neighHexActor.getHexColor().equals("EMPTY")) {
                                        possiblePlacements[0][c] = currentNeighbor;
                                        int g = 1;
                                        //LOOKING FOR FREE NEIGHBORS
                                        for (Object hex2 : grid.getNeighborsOf(currentNeighbor)) {
                                            if (hex2 instanceof Hexagon) {
                                                Hexagon currentNeighbor2 = (Hexagon) hex2;

                                                if (currentNeighbor2.getSatelliteData().isPresent()) {
                                                    Link neighLink2 = (Link) currentNeighbor2.getSatelliteData().get();
                                                    HexagonActor neighHexActor2 = neighLink2.getActor();

                                                    if (neighHexActor2.getHexColor().equals("EMPTY")) {
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

        return possibleActions;
    }

    private Action bestPlacementForTile(ArrayList<Action> all, HexagonalGrid grid){
        int bestGain = 0;
        Action bestPlacement = null;
        for (Action a : all){
            int gain = a.actionGain(grid);
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

                    if (currentHexActor.getHexColor().equals("EMPTY")) {
                        for (Object hex : grid.getNeighborsOf(hexagon)) {
                            if (hex instanceof Hexagon) {
                                Hexagon currentNeighbor = (Hexagon) hex;

                                if (currentNeighbor.getSatelliteData().isPresent()) {
                                    Link neighLink = (Link) currentNeighbor.getSatelliteData().get();
                                    HexagonActor neighHexActor = neighLink.getActor();

                                    //THE FIRST ONE IS THE FIRST PLACEMENT
                                    if (neighHexActor.getHexColor().equals("EMPTY")) {

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

    public Action decideMove(GameState currentState){

        ArrayList<String> colors = currentState.getGamingPlayer().lowestColors();
        Hand hand = currentState.getGamingPlayer().getHand();
        HexagonalGrid grid = currentState.getCurrentBoard().getGrid();

        HashMap<Tile, String> tiles = bestTilesToPlace(colors, hand);
        ArrayList<Action> bestMoves = new ArrayList<>();

        for (Tile tile : tiles.keySet()){
            bestMoves.add(bestPlacementForTile(possibleTilePlacements(tile, grid, tiles.get(tile)), grid));

        }

        //System.out.println(bestMoves.size());
        int bestGain = 0;
        Action bestAction = null;
        for (Action a : bestMoves){
            if (a != null){
                int gain = a.actionGain(grid);
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
