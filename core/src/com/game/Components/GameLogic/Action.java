package com.game.Components.GameLogic;

import com.game.Components.GameConstants.Color;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.Tools.Link;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.backport.Optional;

import java.util.HashMap;

public class Action {
    private Hexagon h1;
    private Hexagon h2;

    private Tile tile;

    public Action(Hexagon h1, Hexagon h2, Tile t){
        this.h1 = h1;
        this.h2 = h2;
        this.tile = t;
    }

    public Action(){
        this.h1 = null;
        this.h2 = null;
        this.tile = null;
    }

    public Hexagon getH1() {
        return h1;
    }

    public Hexagon getH2() {
        return h2;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setH1(Hexagon h1) {
        this.h1 = h1;
    }

    public void setH2(Hexagon h2) {
        this.h2 = h2;
    }

    public Color[] getTileColors(){

        return tile.getColors();
    }

    public String toString(){
        if(tile != null && h1 != null && h2 != null){
            return "Placing Tile: " + tile.getColors()[0].toString() + " - " + tile.getColors()[1].toString() +
                    " || in hexagons: " + h1.getCubeCoordinate().toAxialKey() + " - " + h2.getCubeCoordinate().toAxialKey();
        } else if(tile == null){
            return "tile is missing";
        }else if(h1 == null){
            return "h1 is missing";
        } else if (h2 == null){
            return "h2 is missing";
        } else return null;

    }

    public Action translateAction(GameState stateToPlay){

        Action rightAction = new Action();
        // System.out.println(tile.getColors()[0].toString() + " - " + tile.getColors()[1].toString());

        boolean inHand = false;

        for (Tile t : stateToPlay.getGamingPlayer().getHand().getPieces()){
            // System.out.println(t.getColors()[0].toString() + " - " + t.getColors()[1].toString());

            if (t.isEqual(tile)){

                if (tile.getFirst() != null){
                    Optional toCopy = t.getGrid().getByCubeCoordinate(tile.getFirst().getHexagon().getCubeCoordinate());
                    if (toCopy.isPresent()){
                        Hexagon copy = (Hexagon) toCopy.get();
                        if (copy.getSatelliteData().isPresent()){
                            Link copyLink = (Link) copy.getSatelliteData().get();
                            HexagonActor newFirst = copyLink.getActor();

                            t.setFirst(newFirst);
                        }

                    }
                }
                rightAction.setTile(t);
                inHand = true;
            }
        }

        if (!inHand){
            rightAction.setTile(tile);
        }

        Optional one = stateToPlay.getCurrentBoard().getGrid().getByCubeCoordinate(h1.getCubeCoordinate());
        Optional two = stateToPlay.getCurrentBoard().getGrid().getByCubeCoordinate(h2.getCubeCoordinate());
        if (one.isPresent() && two.isPresent()){
            rightAction.setH1((Hexagon) one.get());
            rightAction.setH2((Hexagon) two.get());
        }

        //System.out.println(rightAction.toString());

        return rightAction;
    }

    public double actionGain(HexagonalGrid grid, Player player){
        HexagonActor first = null;
        double totalGain = 0;
        HashMap<Color, Double> colorRanking = player.getScoreQ();

        if (h1.getSatelliteData().isPresent()){
            Link hexLink = (Link) h1.getSatelliteData().get();
            HexagonActor currentHexActor = hexLink.getActor();
            first = currentHexActor;
            //System.out.println(tile.getFirst() == null);
            if (tile.getFirst() != null){

                currentHexActor.setHexColor(tile.getFirst().getHexColor());
            } else {
                System.out.println("First is null!");
                currentHexActor.setHexColor(tile.getActors()[0].getHexColor());
            }

            int[] gain1 = Player.scoreGain(currentHexActor, grid, currentHexActor);
            int partialGain = 0;
            for (int i = 0; i < 6; i++){
                partialGain += gain1[i];
            }
            totalGain += partialGain * colorRanking.get(currentHexActor.getHexColor());

            currentHexActor.setHexColor(Color.EMPTY);
        }

        if (h2.getSatelliteData().isPresent()){
            Link hexLink = (Link) h2.getSatelliteData().get();
            HexagonActor currentHexActor = hexLink.getActor();
            if (first != null){
                if (tile.getSecond() != null){
                    currentHexActor.setHexColor(tile.getSecond().getHexColor());
                } else {
                    currentHexActor.setHexColor(tile.getActors()[1].getHexColor());
                }

                int partialGain = 0;
                int[] gain2 = Player.scoreGain(currentHexActor, grid, first);
                for (int i = 0; i < 6; i++){
                    partialGain += gain2[i];
                }
                totalGain += partialGain * colorRanking.get(currentHexActor.getHexColor());
                currentHexActor.setHexColor(Color.EMPTY);
            }
        }


        //System.out.println(totalGain);
        return totalGain;
    }


}
