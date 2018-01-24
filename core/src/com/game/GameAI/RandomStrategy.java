package com.game.GameAI;

import com.game.Components.GameConstants.Color;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.GameAssets.HexagonActor;
import com.game.Components.Tools.Link;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

import java.util.ArrayList;
import java.util.Random;

public class RandomStrategy implements Strategy {

    private Random randomGenerator = new Random();

    private ArrayList<Action> possibleTilePlacements(Tile tile, HexagonalGrid grid) {
        ArrayList<Action> possibleActions = new ArrayList<>();
        Color color = tile.getActors()[0].getHexColor();

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

        return possibleActions;
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

    public Action decideMove(GameState currentState){

        ArrayList<Tile> hand = currentState.getGamingPlayer().getHand();
        HexagonalGrid grid = currentState.getCurrentBoard().getGrid();

        ArrayList<Tile> tiles = hand;
        ArrayList<Action> bestMoves = new ArrayList<>();

        for (Tile tile : tiles){
            bestMoves.addAll(possibleTilePlacements(tile, grid));
        }

        return bestMoves.get(randomGenerator.nextInt(bestMoves.size()));

    }

}
