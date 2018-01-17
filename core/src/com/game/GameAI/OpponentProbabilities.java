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

import org.apache.commons.math3.util.*;


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
    private ArrayList<Tile> invisiblePieces = new Bag(Pieces.createBagPieces()).getBag();
    private ArrayList<Tile>[] listOfDoubles = new ArrayList[6];

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

        for (Tile tile : state.getCurrentBag().getBag()){
            if (tile.getFirst().getHexColor().equals(tile.getSecond().getHexColor())){
                if (tile.getFirst().getHexColor().equals("R") && !listOfDoubles[0].isEmpty()){
                    listOfDoubles[0].remove(0);
                }
                if (tile.getFirst().getHexColor().equals("O") && !listOfDoubles[0].isEmpty()){
                    listOfDoubles[1].remove(0);
                }
                if (tile.getFirst().getHexColor().equals("Y") && !listOfDoubles[0].isEmpty()){
                    listOfDoubles[2].remove(0);
                }
                if (tile.getFirst().getHexColor().equals("B") && !listOfDoubles[0].isEmpty()){
                    listOfDoubles[3].remove(0);
                }
                if (tile.getFirst().getHexColor().equals("P") && !listOfDoubles[0].isEmpty()){
                    listOfDoubles[4].remove(0);
                }
                if (tile.getFirst().getHexColor().equals("V") && !listOfDoubles[0].isEmpty()){
                    listOfDoubles[5].remove(0);
                }
            }
        }

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
        double probability = 0.0;

        if (c1.equals(c2)){
            isDouble = true;
        }

        /*
        S = sample size
        s = number of observed successful samples
        N = population size
        n = number of draws from the population

        P(X = s) = (C(S, s))*(C(N-S, n-s))/ C(N, n)
        */

        if(isDouble){
            String lowestColor = gamingPlayer.getScoreQ().get(0).toString();
            // Players will always have a tile of their lowest color in their hands.
            if (lowestColor.equals(c1)){
                return 1.0;
            }
            // If the tile we're passing is not the lowest color:
            else{

                int S = numDoublesLeft;
                int s = 1;
                int N = getInvisibleTiles().size();
                int n = 1;
                int factorialS = S;
                int factorialN = N;
                int factorialNS = N - S;
                int factorialSs = S - s;
                int factorialNn = N - n;
                int factorialns = n-s;
                int factorialNSns = N - S - n + s;

                for(int i =(factorialS - 1); i > 1; i--) {
                    factorialS = factorialS * i;
                }
                System.out.println("Factorial of S is " + factorialS);

                for(int i =(factorialN - 1); i > 1; i--) {
                    factorialN = factorialN * i;
                }
                System.out.println("Factorial of N is " + factorialN);

                for(int i =(factorialNS - 1); i > 1; i--) {
                    factorialNS = factorialNS * i;
                }
                System.out.println("Factorial of NS is " + factorialNS);

                for(int i =(factorialns - 1); i > 1; i--) {
                    factorialns = factorialns * i;
                }
                System.out.println("Factorial of ns is " + factorialSs);

                for(int i =(factorialSs - 1); i > 1; i--) {
                    factorialSs = factorialSs * i;
                }
                System.out.println("Factorial of Ss is " + factorialSs);

                for(int i =(factorialNn - 1); i > 1; i--) {
                    factorialNn = factorialNn * i;
                }
                System.out.println("Factorial of Nn is " + factorialNn);

                for(int i =(factorialNSns - 1); i > 1; i--) {
                    factorialNSns = factorialNSns * i;
                }
                System.out.println("Factorial of NSns is " + factorialNSns);



                probability = ((factorialS/(s*(factorialSs))) * ((factorialNS)/(factorialns * (factorialNSns)))) / ((factorialN)/(n*(factorialNn)));
                return probability;
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

                P(X = s) = (C(S, s))*(C(N-S, n-s))/ C(N, n)
                */
                int S = numSinglesLeft;
                int s = 1;
                int N = getInvisibleTiles().size();
                int n = 1;
                int factorialS = S;
                int factorialN = N;
                int factorialNS = N - S;
                int factorialSs = S - s;
                int factorialNn = N - n;
                int factorialns = n-s;
                int factorialNSns = N - S - n + s;

                for(int i =(factorialS - 1); i > 1; i--) {
                    factorialS = factorialS * i;
                }
                System.out.println("Factorial of S is " + factorialS);

                for(int i =(factorialN - 1); i > 1; i--) {
                    factorialN = factorialN * i;
                }
                System.out.println("Factorial of N is " + factorialN);

                for(int i =(factorialNS - 1); i > 1; i--) {
                    factorialNS = factorialNS * i;
                }
                System.out.println("Factorial of NS is " + factorialNS);

                for(int i =(factorialns - 1); i > 1; i--) {
                    factorialns = factorialns * i;
                }
                System.out.println("Factorial of ns is " + factorialSs);

                for(int i =(factorialSs - 1); i > 1; i--) {
                    factorialSs = factorialSs * i;
                }
                System.out.println("Factorial of Ss is " + factorialSs);

                for(int i =(factorialNn - 1); i > 1; i--) {
                    factorialNn = factorialNn * i;
                }
                System.out.println("Factorial of Nn is " + factorialNn);

                for(int i =(factorialNSns - 1); i > 1; i--) {
                    factorialNSns = factorialNSns * i;
                }
                System.out.println("Factorial of NSns is " + factorialNSns);



                probability = ((factorialS/(s*(factorialSs))) * ((factorialNS)/(factorialns * (factorialNSns)))) / ((factorialN)/(n*(factorialNn)));

                return probability;
            }
        }

    }


}
