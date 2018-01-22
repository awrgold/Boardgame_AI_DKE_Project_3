package com.game.Components.PlayerAssets;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.game.Components.Tools.GroupView;

import java.util.ArrayList;

public class Hand extends GroupView{

    private ArrayList<Tile> hand;
    private float s;

    public Hand(ArrayList<Tile> tiles){
        this.hand = tiles;
        create();
    }

    public void create(){
        float x = 0;
        for (Tile tile: hand){
            tile.setPosition(x, 0);
            addActor(tile);
            x += 180;
        }
    }

//    public Hand cloneHand(){
//        //System.out.println("Cloning hand: ");
//        ArrayList<Tile> newTiles= new ArrayList<>();
//        for (Tile tile : getPieces()){
//            newTiles.add(tile.cloneTile());
//            //System.out.println("Cloning a Tile: passing as colors " + tile.getColors()[0].toString() + " - " + tile.getColors()[1].toString());
//        }
//        return new Hand(newTiles);
//
//    }

    public ArrayList<Tile> getPieces() {
        return hand;
    }

    public void pickFromBag(Tile picked){
        hand.add(picked);
        picked.setPosition(s, 0);
        addActor(picked);
    }

    public void removeFromHand(Tile placed){
        s = placed.getX();
        hand.remove(placed);
        removeActor(placed);
    }

    public void changeTiles(ArrayList<Tile> tiles){
        this.hand = tiles;
    }
//    public void act(ArrayList<Tile> tiles) {
//
//        this.setHand(tiles);
//    for(Tile tile : tiles){
//      tile.act(tiles.get());
//    }
//
//
//
//    }

    public void setHand(ArrayList<Tile> hand)
    {
        this.hand = hand;
    }
    public void dispose() {


//        for(Tile tile : hand){
//        tile.dispose();
 //   }
}
}
