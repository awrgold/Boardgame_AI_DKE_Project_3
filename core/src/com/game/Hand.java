package com.game;

import Interfaces.GroupView;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Tile> hand;
    private Group[] handView;

    public Hand(ArrayList<Tile> tiles){
        this.hand = tiles;
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

    public Group[] displayHand(){
        handView = new Group[6];
        for (int i = 0; i < 6; i++){
            handView[i] = hand.get(i).displayHand();
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
