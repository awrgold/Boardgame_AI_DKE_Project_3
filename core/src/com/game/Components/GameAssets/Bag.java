package com.game.Components.GameAssets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.Components.GameConstants.Color;
import com.game.Components.PlayerAssets.Tile;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Bag {

    private ArrayList<Tile> bag;
    private ArrayList<Color[]> pieces;

    public Bag(ArrayList<Color[]> pieces){
        this.pieces = pieces;
        this.bag = new ArrayList<>();
        for (Color[] piece : pieces){
            Tile one = new Tile(piece);
            bag.add(one);
        }
    }

    public Bag(){
    }

    public Bag cloneBag(){
        ArrayList<Tile> newTiles= new ArrayList<>();
        for (Tile tile : getBag()){
            newTiles.add(tile.cloneTile());
        }
        Bag newBag = new Bag();
        newBag.setBag(newTiles);
        return newBag;

    }

    public void setBag(ArrayList<Tile> bag) {
        this.bag = bag;
    }

    public ArrayList<Tile> getBag(){
        return this.bag;
    }

    public ArrayList<Color[]> getPieces(){
        return pieces;
    }


    public Tile pickTile(){
        int randomNumber = ThreadLocalRandom.current().nextInt(0, bag.size());
        Tile piece = bag.get(randomNumber);
        bag.remove(piece);
        return piece;
    }

    public ArrayList<Tile> pickSix() {
        ArrayList<Tile> six = new ArrayList<>();

        for(int i = 0; i < 6; i++){
            six.add(pickTile());
        }

        return six;
    }

    public Tile replaceTile(Tile t){
        bag.add(t);
        return pickTile();
    }

    public int getBagSize(){
        return bag.size();
    }

    public ArrayList<Tile> replaceHand(ArrayList<Tile> hand) {
        bag.addAll(hand);
        return pickSix();
    }
}
