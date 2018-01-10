package com.game.Components.GameLogic;

import com.game.Components.GameConstants.Color;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.TestPlayer;
import com.game.Components.PlayerAssets.TestTile;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.Tools.Link;
import com.game.Components.Tools.TestHexagonActor;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;

public class TestAction {
    private Hexagon h1;
    private Hexagon h2;

    private TestTile tile;

    public TestAction(Hexagon h1, Hexagon h2, TestTile t){
        this.h1 = h1;
        this.h2 = h2;
        this.tile = t;
    }

    public TestAction(){
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

    public TestTile getTile() {
        return tile;
    }

    public void setTile(TestTile tile) {
        this.tile = tile;
    }

    public void setH1(Hexagon h1) {
        this.h1 = h1;
    }

    public void setH2(Hexagon h2) {
        this.h2 = h2;
    }

    public Color[] getTileColors(){
        Color[] colors = new Color[2];

        colors[0] = tile.getActor(0).getHexColor();
        colors[1] = tile.getActor(1).getHexColor();

        return colors;
    }

    public String toString(){

        if(tile != null && h1 != null && h2 != null){
            return "Placing Tile: [" + tile.getActor(0).getHexColor() + " - " + tile.getActor(1).getHexColor() +
                    "] in hexagons: " + h1.getCubeCoordinate().toAxialKey() + " - " + h2.getCubeCoordinate().toAxialKey();
        } else {
            return "something is missing";
        }

    }


    public int actionGain(HexagonalGrid grid){
        TestHexagonActor first = null;
        int totalGain = 0;

        if (h1.getSatelliteData().isPresent()){
            Link hexLink = (Link) h1.getSatelliteData().get();
            TestHexagonActor currentHexActor = hexLink.getTestActor();
            first = currentHexActor;
            currentHexActor.setHexColor(tile.getActor(0).getHexColor());
            int[] gain1 = TestPlayer.scoreGain(currentHexActor, grid, currentHexActor);
            for (int i = 0; i < 6; i++){
                totalGain += gain1[i];
            }
            currentHexActor.setHexColor(Color.EMPTY);
        }

        if (h2.getSatelliteData().isPresent()){
            Link hexLink = (Link) h2.getSatelliteData().get();
            TestHexagonActor currentHexActor = hexLink.getTestActor();
            if (first != null){
                currentHexActor.setHexColor(tile.getActor(1).getHexColor());
                int[] gain2 = TestPlayer.scoreGain(currentHexActor, grid, first);
                for (int i = 0; i < 6; i++){
                    totalGain += gain2[i];
                }
                currentHexActor.setHexColor(Color.EMPTY);
            }
        }


        //System.out.println(totalGain);
        return totalGain;
    }


}
