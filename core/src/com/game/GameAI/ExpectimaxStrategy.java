package com.game.GameAI;

import TreeStructure.Edge;
import TreeStructure.Node;
import TreeStructure.Tree;
import com.game.Components.GameConstants.Color;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.GameLogic.GameView;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.Tools.Link;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.backport.Optional;
import rx.functions.Action1;

import java.util.ArrayList;
import java.util.Arrays;

public class ExpectimaxStrategy implements Strategy {

    /*
    1) create a new tree with the current state as root
    2) build the first tree level with possible moves for the gaming player...
        -for each tile find the placement with highest gain and create a new node (gain is node weigth) with that placement


    */

    Tree tree;

    private Action bestTilePlacement(Tile tile, GameView currentState, Player player) {
        ArrayList<Action> possibleActions = new ArrayList<>();
        HexagonalGrid grid = currentState.getBoard().getGrid();
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
    public void buildFirstLevel(Hand hand, GameView currentState, Player player){


        for (Tile t : hand.getPieces()){
            Action move = bestTilePlacement(t, currentState, player);
            //System.out.println("New node: " + move.toString() + " || Gain: " + move.actionGain(currentState.getBoard().getGrid()));
            tree.getRoot().setChild(move);

        }


    }

    public Action decideMove(GameState currentState) {

        GameView root = new GameView(currentState.getCurrentBoard());

        tree = new Tree(root);
        buildFirstLevel(currentState.getGamingPlayer().getHand(), tree.getRoot().getState(), currentState.getGamingPlayer());

        double maxGain = 0;
        Action bestAction = null;
        for (Edge edge : tree.getRoot().getChildrenEdges()){
            if (edge.getChildNode().getWeigth() >= maxGain){
                maxGain = edge.getChildNode().getWeigth();
                bestAction = edge.getAction();
            }
        }

        Hexagon h1 = null;
        Hexagon h2 = null;


        Optional one = currentState.getCurrentBoard().getGrid().getByCubeCoordinate(bestAction.getH1().getCubeCoordinate());
        Optional two = currentState.getCurrentBoard().getGrid().getByCubeCoordinate(bestAction.getH2().getCubeCoordinate());
        if (one.isPresent() && two.isPresent()){
            h1 = (Hexagon) one.get();
            h2 = (Hexagon) two.get();

        }

        Action realAction = new Action(h1, h2, bestAction.getTile());

        return realAction;
    }
}
