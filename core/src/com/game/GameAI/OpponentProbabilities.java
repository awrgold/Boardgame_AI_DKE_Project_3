package com.game.GameAI;


import com.game.Components.GameAssets.Bag;
import com.game.Components.GameAssets.Bag;
import com.game.Components.GameConstants.Color;
import com.game.Components.GameConstants.Pieces;
import com.game.Components.GameLogic.GameState;
import com.game.Components.GameLogic.GameState;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.PlayerAssets.Tile;
import com.game.TreeStructure.Tree;


import java.util.ArrayList;


public class OpponentProbabilities {




    private int numDoublesLeft = 0;
    private int numSinglesLeft = 0;
    private ArrayList<Tile> invisibleTiles = new Bag(Pieces.createBagPieces()).getBag();
    private ArrayList<Tile>[] listOfDoubles = new ArrayList[6];
    private ArrayList<Tile>[] listOfSingles = new ArrayList[6];

    public OpponentProbabilities(){}

    public int getInvisibleTilesSize(GameState state){

        invisibleTiles.addAll(state.getGamingPlayer().getHand().getPieces());
        invisibleTiles.addAll(state.getCurrentBag().getBag());

        return invisibleTiles.size();
    }

    public ArrayList<Tile>[] getInvisibleDoubles(GameState state){

        // Create a full list of all remaining hidden doubles in the bag/enemy hand (This can be inferred from the visible tiles on the board)
        for (Tile tile : state.getCurrentBag().getBag()) {
            if (tile.getFirst().getHexColor().equals(tile.getSecond().getHexColor())) {
                if (tile.getFirst().getHexColor().equals(Color.RED)) {
                    listOfDoubles[0].add(tile);
                    numDoublesLeft++;
                }
                if (tile.getFirst().getHexColor().equals(Color.ORANGE)) {
                    listOfDoubles[1].add(tile);
                    numDoublesLeft++;
                }
                if (tile.getFirst().getHexColor().equals(Color.YELLOW)) {
                    listOfDoubles[2].add(tile);
                    numDoublesLeft++;
                }
                if (tile.getFirst().getHexColor().equals(Color.BLUE)) {
                    listOfDoubles[3].add(tile);
                    numDoublesLeft++;
                }
                if (tile.getFirst().getHexColor().equals(Color.PURPLE)) {
                    listOfDoubles[4].add(tile);
                    numDoublesLeft++;
                }
                if (tile.getFirst().getHexColor().equals(Color.VIOLET)) {
                    listOfDoubles[5].add(tile);
                    numDoublesLeft++;
                }
            }
        }

        for (Tile tile2 : state.getPlayer(Math.abs(state.getGamingPlayer().getPlayerNo() - 1)).getHand().getPieces()) {
            if (tile2.getFirst().getHexColor().equals(Color.RED)) {
                listOfDoubles[0].add(tile2);
                numDoublesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals(Color.ORANGE)) {
                listOfDoubles[1].add(tile2);
                numDoublesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals(Color.YELLOW)) {
                listOfDoubles[2].add(tile2);
                numDoublesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals(Color.BLUE)) {
                listOfDoubles[3].add(tile2);
                numDoublesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals(Color.PURPLE)) {
                listOfDoubles[4].add(tile2);
                numDoublesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals(Color.VIOLET)) {
                listOfDoubles[5].add(tile2);
                numDoublesLeft++;
            }
        }

        return listOfDoubles;
    }

    public ArrayList<Tile>[] getInvisibleSingles(GameState state){


        // Create a full list of all remaining hidden singles in the bag/enemy hand (This can be inferred from the visible tiles on the board)
        for (Tile tile : state.getCurrentBag().getBag()) {
            if (!tile.getFirst().getHexColor().equals(tile.getSecond().getHexColor())) {
                if (tile.getFirst().getHexColor().equals(Color.RED)) {
                    listOfSingles[0].add(tile);
                    numSinglesLeft++;
                }
                if (tile.getFirst().getHexColor().equals(Color.ORANGE)) {
                    listOfSingles[1].add(tile);
                    numSinglesLeft++;
                }
                if (tile.getFirst().getHexColor().equals(Color.YELLOW)) {
                    listOfSingles[2].add(tile);
                    numSinglesLeft++;
                }
                if (tile.getFirst().getHexColor().equals(Color.BLUE)) {
                    listOfSingles[3].add(tile);
                    numSinglesLeft++;
                }
                if (tile.getFirst().getHexColor().equals(Color.PURPLE)) {
                    listOfSingles[4].add(tile);
                    numSinglesLeft++;
                }
                if (tile.getFirst().getHexColor().equals(Color.VIOLET)) {
                    listOfSingles[5].add(tile);
                    numSinglesLeft++;
                }
            }
        }

        for (Tile tile2 : state.getPlayer(Math.abs(state.getGamingPlayer().getPlayerNo() - 1)).getHand().getPieces()) {
            if (tile2.getFirst().getHexColor().equals(Color.RED)) {
                listOfSingles[0].add(tile2);
                numSinglesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals(Color.ORANGE)) {
                listOfSingles[1].add(tile2);
                numSinglesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals(Color.YELLOW)) {
                listOfSingles[2].add(tile2);
                numSinglesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals(Color.BLUE)) {
                listOfSingles[3].add(tile2);
                numSinglesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals(Color.PURPLE)) {
                listOfSingles[4].add(tile2);
                numSinglesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals(Color.VIOLET)) {
                listOfSingles[5].add(tile2);
                numSinglesLeft++;
            }
        }

        return listOfSingles;
    }


    public double getProbOfColors(GameState state, Tile tile){
        Bag currentBag = state.getCurrentBag();
        boolean isDouble = false;
        double probability = 0.0;
        Color lowestColor = state.getGamingPlayer().lowestColors().get(0);
        Color c1 = tile.getFirst().getHexColor();
        Color c2 = tile.getSecond().getHexColor();

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
/*
        if(isDouble){
            // Players will always have a tile of their lowest color in their hands.
            if (lowestColor.equals(c1)){
                return 1.0;
            }
            // If the tile we're passing is not the lowest color:
            else{

                int S = getInvisibleDoubles(state).length;
                int s = 1;
                int N = getInvisibleTilesSize(state);
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
                System.out.println("Factorial of " + S + " is " + factorialS);

                for(int i =(factorialN - 1); i > 1; i--) {
                    factorialN = factorialN * i;
                }
                System.out.println("Factorial of " + N + " is " + factorialN);

                for(int i =(factorialNS - 1); i > 1; i--) {
                    factorialNS = factorialNS * i;
                }
                System.out.println("Factorial of " + N + "-" + S + " is " + factorialNS);

                for(int i =(factorialns - 1); i > 1; i--) {
                    factorialns = factorialns * i;
                }
                System.out.println("Factorial of " + n + "-" + s + " is " + factorialSs);

                for(int i =(factorialSs - 1); i > 1; i--) {
                    factorialSs = factorialSs * i;
                }
                System.out.println("Factorial of " + S + "-" + s + " is " + factorialSs);

                for(int i =(factorialNn - 1); i > 1; i--) {
                    factorialNn = factorialNn * i;
                }
                System.out.println("Factorial of " + N + "-" + n + " is " + factorialNn);

                for(int i =(factorialNSns - 1); i > 1; i--) {
                    factorialNSns = factorialNSns * i;
                }
                System.out.println("Factorial of " + N + "-" + S + "-" + n + "+" + s + " is " + factorialNSns);

                probability = ((factorialS/(s*(factorialSs))) * ((factorialNS)/(factorialns * (factorialNSns)))) / ((factorialN)/(n*(factorialNn)));
                return probability;
            }
        }


        if(!isDouble){
            // Players will always have a tile of their lowest color in their hands.
            if (lowestColor.equals(c1) || lowestColor.equals(c2)){
                return 1.0;
            }
            // If the tile we're passing is not the lowest color:
            else{

                int S = getInvisibleSingles(state).length;
                int s = 1;
                int N = getInvisibleTilesSize(state);
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
                System.out.println("Factorial of " + S + " is " + factorialS);

                for(int i =(factorialN - 1); i > 1; i--) {
                    factorialN = factorialN * i;
                }
                System.out.println("Factorial of " + N + " is " + factorialN);

                for(int i =(factorialNS - 1); i > 1; i--) {
                    factorialNS = factorialNS * i;
                }
                System.out.println("Factorial of " + N + "-" + S + " is " + factorialNS);

                for(int i =(factorialns - 1); i > 1; i--) {
                    factorialns = factorialns * i;
                }
                System.out.println("Factorial of " + n + "-" + s + " is " + factorialSs);

                for(int i =(factorialSs - 1); i > 1; i--) {
                    factorialSs = factorialSs * i;
                }
                System.out.println("Factorial of " + S + "-" + s + " is " + factorialSs);

                for(int i =(factorialNn - 1); i > 1; i--) {
                    factorialNn = factorialNn * i;
                }
                System.out.println("Factorial of " + N + "-" + n + " is " + factorialNn);

                for(int i =(factorialNSns - 1); i > 1; i--) {
                    factorialNSns = factorialNSns * i;
                }
                System.out.println("Factorial of " + N + "-" + S + "-" + n + "+" + s + " is " + factorialNSns);



                probability = ((factorialS/(s*(factorialSs))) * ((factorialNS)/(factorialns * (factorialNSns)))) / ((factorialN)/(n*(factorialNn)));

                return probability;
            }
        }*/
        return -1.0;
    }


}