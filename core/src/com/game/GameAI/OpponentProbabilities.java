package com.game.GameAI;


import TreeStructure.Tree;
import com.game.Components.GameAssets.Bag;
import com.game.Components.GameAssets.Bag;
import com.game.Components.GameConstants.Pieces;
import com.game.Components.GameLogic.GameState;
import com.game.Components.GameLogic.GameState;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.PlayerAssets.Tile;

import java.util.ArrayList;


public class OpponentProbabilities {


    private Player player;
    private Player gamingPlayer;
    private Bag bag;
    private Hand hand;
    private GameState state;
    private Tree currentTree = new Tree();
    int numDoublesLeft = 30;
    int numSinglesLeft = 90;


    public OpponentProbabilities(GameState state){
        this.state = state;
        this.gamingPlayer = state.getGamingPlayer();
        this.bag = state.getCurrentBag();
        this.hand = state.getGamingPlayer().getHand();
        currentTree.buildTree(state);
    }

    public void updateTree(GameState state){
        currentTree.buildTree(state);
    }

    public ArrayList<Tile> getInvisibleTiles(){
        ArrayList<Tile> invisiblePieces = new Bag(Pieces.createBagPieces()).getBag();

        for (Tile t : state.getGamingPlayer().getVisibleTiles()){
            if (t.getFirst().getHexColor().equals(t.getSecond().getHexColor())){
                numDoublesLeft--;
            }else{
                numSinglesLeft--;
            }
            invisiblePieces.remove(t);
        }

        return invisiblePieces;
    }

    public double getProbOfColors(String c1, String c2){
        Bag currentBag = state.getCurrentBag();
        boolean isDouble = false;

        if (c1.equals(c2)){
            isDouble = true;
        }

        if(isDouble){
            String lowestColor = gamingPlayer.getScoreQ().get(0).toString();
            // Players will always have a tile of their lowest color in their hands.
            if (lowestColor.equals(c1)){
                return 1.0;
            }
            // If the tile we're passing is not the lowest color:
            else{
                // Probability is (6/numTilesLeft) * (numDoubles/numTilesLeft)
                int numTilesLeft = getInvisibleTiles().size();
                return ((1/numTilesLeft) * (numDoublesLeft/numTilesLeft));
            }
        }

        if(!isDouble){
            String lowestColor = gamingPlayer.getScoreQ().get(0).toString();
            // Players will always have a tile of their lowest color in their hands.
            if (lowestColor.equals(c1) || lowestColor.equals(c2)){
                return 1.0;
            }
            // If the tile we're passing is not the lowest color:
            else{
                // Probability is (6/numTilesLeft) * (numDoubles/numTilesLeft)

            }
        }


    }


}
