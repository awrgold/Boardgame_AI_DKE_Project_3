package com.game.Components.PlayerAssets;


import com.game.Components.Tools.GroupView;

import java.util.ArrayList;

public class TestHand {

    private ArrayList<TestTile> hand;
    private float s;

    public TestHand(ArrayList<TestTile> tiles){
        this.hand = tiles;
    }

    public ArrayList<TestTile> getPieces() {
        return hand;
    }

    public void pickFromBag(TestTile picked){
        hand.add(picked);
    }

    public void removeFromHand(TestTile placed){
        hand.remove(placed);
    }

    public void changeTiles(ArrayList<TestTile> tiles){
        this.hand = tiles;
    }

}
