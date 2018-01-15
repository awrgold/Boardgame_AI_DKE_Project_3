package com.game.GameAI;


import com.game.Components.GameAssets.Bag;
import com.game.Components.GameAssets.Bag;
import com.game.Components.GameConstants.Color;
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

    public OpponentProbabilities(GameState state){
        this.state = state;
        this.gamingPlayer = state.getGamingPlayer();
        this.bag = state.getCurrentBag();
        this.hand = state.getGamingPlayer().getHand();

    }

    public double getProbOfColors(String c1, String c2){

        boolean hasColors = false;
        boolean isDouble = false;
        Bag tempBag = state.getCurrentBag();
        Bag remainingPieces = new Bag();

        if (c1 == c2){
            isDouble = true;
        }

        if(isDouble){

        }

        for (int i = 0; i < state.getGamingPlayer().getHand().getPieces().size(); i++){
            if ((state.getGamingPlayer().getHand().getPieces().get(i).getFirst().getHexColor().equals(c1))
                    && (state.getGamingPlayer().getHand().getPieces().get(i).getSecond().getHexColor().equals(c2))){
                hasColors = true;

            }
        }

        // Iterate over board and duplicate those pieces into the remainingPieces bag
        for (Tile t : state.getCurrentBoard().getGrid().getHexagons)


        // Iterate over my hand and duplicate those pieces into the remainingPieces bag
        for (Tile tile : state.getGamingPlayer().getHand().getPieces()){
            remainingPieces.addTileToOpponentBag(tile);
        }

        if (hasColors){

        }

    }


}
