package com.game;


import Interfaces.GroupView;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;

public class Hand {
    private Tile  selectedTile ;
    private int selected;
    private ArrayList<Tile> hand;
    private GroupView[] handView;

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

    public void changeTiles(ArrayList<Tile> tiles){
        this.hand = tiles;
    }


    public GroupView[] displayHand(){
        handView = new GroupView[6];
        for (int i = 0; i < 6; i++){
            handView[i] = hand.get(i);
        }

        return handView;

    }


    public void setSelectedTile(Tile t,int i){
        t = selectedTile;
        if(!t.isSelected()){
            t.setSelected();
        }
        updateHand(i);
    }

    private void updateHand(int i) {

    }

    public Tile getSelected(){
        return selectedTile;
    }


}
