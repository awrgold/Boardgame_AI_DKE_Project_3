package com.game.Components.GameAssets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.Components.PlayerAssets.Tile;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Bag {
    private ArrayList<Tile> bag;

    /*
    public Bag(ArrayList<Sprite[]> pieces){
        this.bag = new ArrayList<>();
        for (Sprite[] piece : pieces){
            Tile one = new Tile(piece);
            bag.add(one);
        }
    }
    */

    public Bag(ArrayList<String[]> pieces){
        this.bag = new ArrayList<>();
        for (String[] piece : pieces){
            StringTile one = new Tile(piece);
            bag.add(one);
        }
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

    public ArrayList<Tile> replaceHand(ArrayList<Tile> hand) {
        bag.addAll(hand);
        return pickSix();
    }
}
