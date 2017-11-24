package com.game;

import Interfaces.GroupView;
import com.badlogic.gdx.scenes.scene2d.Group;

public class Hand {
    private Tile[] hand;
    private Group[] handView;

    public Hand(Tile[] tiles){
        this.hand = tiles;
    }

    public Group[] updateHand(){

        handView = new Group[6];
        checkSelected(hand);
        for (int i = 0; i < 6; i++){
            handView[i] = hand[i].generateTile();
            if(hand[i].isSelected()){
                handView[i].moveBy(0,30);

            }
        }

        return handView;
    }
    public void checkSelected(Tile [] h) {
//        for (Tile t : h) {
//            Tile temp = null;
//            if (t.isSelected()) {
//                temp = t;
//            }
//            if (t != temp) {
//                t.deselect();
//
//            }
//        }
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
