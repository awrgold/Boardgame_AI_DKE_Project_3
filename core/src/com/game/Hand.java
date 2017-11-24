package com.game;

import com.badlogic.gdx.scenes.scene2d.Group;

public class Hand {
    private Tile[] hand;
    private Group[] handView;

    public Hand(Tile[] tiles){
        this.hand = tiles;
    }

    public Group[] updateHand(){

        handView = new Group[6];

        for (int i = 0; i < 6; i++){
            handView[i] = hand[i].generateTile();
        }

        return handView;
    }

    public Tile getSelected(){
        for (Tile x : hand){
            if(x.isSelected()){
                x.deselect();
                return x;
            }
        }
        return null;
    }
}
