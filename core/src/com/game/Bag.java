package com.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Bag {
    ArrayList<Tile> bag;

    public Bag(ArrayList<Sprite[]> pieces){
        this.bag = new ArrayList<>();
        for (Sprite[] piece : pieces){
            Tile one = new Tile(piece);
            bag.add(one);
        }
    }

    /*public void fillHand(Player player){
        for(int i = 0; i < 6; i++){
            if (player.getHand().getPieces()[i] == null){
                int randomNumber = ThreadLocalRandom.current().nextInt(0, bag.size());
                Tile piece = bag.get(randomNumber);
                player.getHand().add(piece);
                allPieces.remove(piece);
            }
        }
    }*/

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
        for (Tile t : hand){
            bag.add(t);
        }
        return pickSix();
    }
}
