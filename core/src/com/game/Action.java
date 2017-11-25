package com.game;

import GameBoardAssets.HexagonActor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.codetome.hexameter.core.api.Hexagon;

public class Action {
    private Hexagon h1;
    private Hexagon h2;

    Tile tile;

    public Action(Hexagon h1, Hexagon h2, Tile t){
        this.h1 = h1;
        this.h2 = h2;
        this.tile = t;
    }

    public Action(){};

    public Hexagon getH1() {
        return h1;
    }

    public Hexagon getH2() {
        return h2;
    }

    public Tile getTile() {
        return tile;
    }

    public Sprite[] getColors(){
        Sprite[] colors = new Sprite[2];

        colors[0] = tile.getFirst().getSprite();

        colors[1] = tile.getSecond().getSprite();

        return colors;
    }

//    public void setT2(HexagonActor t2) {
//        this.t2 = t2;
//    }
}
