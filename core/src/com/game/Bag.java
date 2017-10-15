package com.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Bag {
    private ArrayList<Sprite[]> bag;

    public Bag(){
        this.bag = Pieces.createBagPieces();
    }

    public void givePieces(Player player){

    }
}
