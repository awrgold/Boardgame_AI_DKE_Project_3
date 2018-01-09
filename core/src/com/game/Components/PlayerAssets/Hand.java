package com.game.Components.PlayerAssets;


import com.badlogic.gdx.scenes.scene2d.Group;
import com.game.Components.Tools.GroupView;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Tile> hand;
    private float s;


    public Hand(ArrayList<Tile> tiles){
        this.hand = tiles;

    }

    public Group displayHand(){
        Group handGroup = new Group();
        float x = 0;
        for (Tile tile: hand){
            System.out.println(tile.getColors()[0] + " - " + tile.getColors()[1]);
            Group one = tile.displayTile();
            handGroup.addActor(one);
            one.setPosition(x, 0);

            x += 180;
        }

        return handGroup;
    }

    public ArrayList<Tile> getPieces() {
        return hand;
    }

    public void pickFromBag(Tile picked){
        hand.add(picked);

    }

    public void removeFromHand(Tile placed){
        hand.remove(placed);

    }

    public void changeTiles(ArrayList<Tile> tiles){
        this.hand = tiles;
    }

}
