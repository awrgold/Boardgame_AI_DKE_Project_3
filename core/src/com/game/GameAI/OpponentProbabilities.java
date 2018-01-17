package com.game.GameAI;


import TreeStructure.Tree;
import org.apache.commons.math3.util.Combinations;
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
    private int numDoublesLeft = 30;
    private int numSinglesLeft = 90;


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
                /*
                S = sample size
                s = number of observed successful samples
                N = population size
                n = number of draws from the population

                HyperGeometric Distribution:
                P(X = s) = (C(S, s))*(C(N-S, n-s))/ C(N, n)
                */
                int S = numDoublesLeft;
                int s = 1;
                int N = getInvisibleTiles().size();
                int n = 1;

                double prob = (Combinations())

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
                /*
                S = sample size
                s = number of observed successful samples
                N = population size
                n = number of draws from the population

                HyperGeometric Distribution:
                P(X = s) = (C(S, s))*(C(N-S, n-s))/ C(N, n)
                */
                int S = numSinglesLeft;
                int s = 1;
                int N = getInvisibleTiles().size();
                int n = 1;

            }
        }

    }


}
