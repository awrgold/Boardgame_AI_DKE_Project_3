package com.game;


import Interfaces.GroupView;
import Systems.AbstractSystem;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class Hand extends GroupView{

    private ArrayList<Tile> hand;
    //private GroupView[] handView;
    private Tile selectedTile;
    private int s;

    public Hand(ArrayList<Tile> tiles){

        this.hand = tiles;
        create();
    }

    public void create(){
        float x = 0;
        for (Tile tile: hand){
            tile.setPosition(x, -20);
            addActor(tile);
            x += 165;
        }
    }


    public ArrayList<Tile> getPieces() {
        return hand;
    }

    /*private void deselectTiles( int s) {
        for (int i = 0; i < 6; i++){
            if(i!=s && hand.get(i).isSelected()){
                hand.get(i).setSelected(false);
            }

            }
        }*/



    public void pickFromBag(Tile picked){
        hand.add(s, picked);
        addActor(picked);
    }

    public void removeFromHand(Tile placed){
        for (int i = 0; i < 6; i++){
            if (hand.get(i) == placed){
                s = i;
            }
        }
        hand.remove(placed);
        removeActor(placed);
    }

    public void changeTiles(ArrayList<Tile> tiles){
        this.hand = tiles;
    }

    public void swapSelected(Tile newOne){
        for (Tile t : hand){
            if (t != newOne && t.isSelected()){
                t.setSelected(false);
                t.moveBy(0, -30);
            }
        }
        newOne.setSelected(true);
        selectedTile = newOne;
    }






    public GroupView showTile(int i) {
        return hand.get(i);
    }

    public void act( float delta) {
        super.act(delta);
    }
}
