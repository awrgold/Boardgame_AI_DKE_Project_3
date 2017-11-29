package com.game;


import Interfaces.GroupView;
import Systems.AbstractSystem;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class Hand extends GroupView{

    private ArrayList<Tile> hand;
    private GroupView[] handView;
    private Tile selectedTile;
    private int s;

    public Hand(ArrayList<Tile> tiles){
        super();

        this.hand = tiles;
        create();
    }
    public void create(){
        handView = new GroupView[6];
        for (int i = 0; i < 6; i++){
            handView[i] = hand.get(i);
            addActorAt( i , handView[i]);
        }

    }
    public void act( float delta) {
        super.act(delta);

        for (int i = 0; i < 6; i++){
//            handView[i] = hand.get(i);
            if(hand.get(i).isSelected()){
               deselectTiles(i);
//
           }
//            if(selectedTile !=null && !hand.get(i).isSelected()) {
//                deselectTile(hand.get(s),i);
//
//                selectTile(hand.get(i),i);
//            }
      }

    }

    public ArrayList<Tile> getPieces() {
        return hand;
    }

    private void deselectTiles( int s) {
        for (int i = 0; i < 6; i++){
            if(i!=s && hand.get(i).isSelected()){
                hand.get(i).setSelected(false);
            }

            }
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






    public GroupView showTile(int i) {
        return handView[i];
    }
}
