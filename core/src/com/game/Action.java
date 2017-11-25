package com.game;

import GameBoardAssets.HexagonActor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.codetome.hexameter.core.api.Hexagon;

public class Action {
    private Hexagon h1;
    private Hexagon h2;

    private Tile tile;

    public Action(Hexagon h1, Hexagon h2, Tile t){
        this.h1 = h1;
        this.h2 = h2;
        this.tile = t;
    }

    //public Action(){};

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

    public String[] getTileColors(){
        String[] colors = new String[2];

        colors[0] = tile.getFirst().getHexColor();

        colors[1] = tile.getSecond().getHexColor();

        return colors;
    }

    public String toString(){
        return "Placing Tile: " + tile.getFirst().getHexColor() + " - " + tile.getSecond().getHexColor() +
                " || in hexagons: " + h1.getCubeCoordinate().toAxialKey() + " - " + h2.getCubeCoordinate().toAxialKey();
    }


}
