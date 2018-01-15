package com.game.Components.GameAssets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.Components.GameConstants.Color;
import com.game.Components.PlayerAssets.TestTile;
import com.game.Components.PlayerAssets.Tile;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TestBag {
    private ArrayList<TestTile> bag;
    private ArrayList<TestTile> bag2;

    public TestBag(ArrayList<Color[]> pieces){
        this.bag = new ArrayList<>();
        for (Color[] piece : pieces){
            TestTile one = new TestTile(piece);
            bag.add(one);
        }
    }

    public TestBag(){
        this.bag2 = new ArrayList<TestTile>();
    }

    public void addTileToOpponentBag(TestTile tile){
        bag2.add(tile);
    }

    public ArrayList<TestTile> getEmptyTestBag(){
        return bag2;
    }

    public TestTile pickTile(){
        int randomNumber = ThreadLocalRandom.current().nextInt(0, bag.size());
        TestTile piece = bag.get(randomNumber);
        bag.remove(piece);
        return piece;
    }

    public int getBagSize(){
        return bag.size();
    }

    public ArrayList<TestTile> pickSix() {
        ArrayList<TestTile> six = new ArrayList<>();

        for(int i = 0; i < 6; i++){
            six.add(pickTile());
        }

        return six;
    }

    public TestTile replaceTile(TestTile t){
        bag.add(t);
        return pickTile();
    }

    public ArrayList<TestTile> replaceHand(ArrayList<TestTile> hand) {
        bag.addAll(hand);
        return pickSix();
    }
}
