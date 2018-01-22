package com.game.GameAI;


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
import com.game.TreeStructure.Tree;


import java.util.ArrayList;


public class OpponentProbabilities {

/*
    private Player player;
    private Player gamingPlayer;
    private Bag bag;
    private Hand hand;
    private GameState state;
    private Tree currentTree = new Tree(state);
    private int numDoublesLeft = 0;
    private int numSinglesLeft = 0;
    private ArrayList<Tile> invisibleTiles = new Bag(Pieces.createBagPieces()).getBag();
    private ArrayList<Tile>[] listOfDoubles = new ArrayList[6];
    private ArrayList<Tile>[] listOfSingles = new ArrayList[6];

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

    public int getInvisibleTilesSize(){

        invisibleTiles.addAll(state.getGamingPlayer().getHand().getPieces());
        invisibleTiles.addAll(state.getCurrentBag().getBag());

        return invisibleTiles.size();
    }

    public ArrayList<Tile>[] getInvisibleDoubles(){

        // Create a full list of all remaining hidden doubles in the bag/enemy hand (This can be inferred from the visible tiles on the board)
        for (Tile tile : state.getCurrentBag().getBag()) {
            if (tile.getFirst().getHexColor().equals(tile.getSecond().getHexColor())) {
                if (tile.getFirst().getHexColor().equals("R")) {
                    listOfDoubles[0].add(tile);
                    numDoublesLeft++;
                }
                if (tile.getFirst().getHexColor().equals("O")) {
                    listOfDoubles[1].add(tile);
                    numDoublesLeft++;
                }
                if (tile.getFirst().getHexColor().equals("Y")) {
                    listOfDoubles[2].add(tile);
                    numDoublesLeft++;
                }
                if (tile.getFirst().getHexColor().equals("B")) {
                    listOfDoubles[3].add(tile);
                    numDoublesLeft++;
                }
                if (tile.getFirst().getHexColor().equals("P")) {
                    listOfDoubles[4].add(tile);
                    numDoublesLeft++;
                }
                if (tile.getFirst().getHexColor().equals("V")) {
                    listOfDoubles[5].add(tile);
                    numDoublesLeft++;
                }
            }
        }

        for (Tile tile2 : state.getPlayer(Math.abs(state.getGamingPlayer().getPlayerNo() - 1)).getHand().getPieces()) {
            if (tile2.getFirst().getHexColor().equals("R")) {
                listOfDoubles[0].add(tile2);
                numDoublesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals("O")) {
                listOfDoubles[1].add(tile2);
                numDoublesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals("Y")) {
                listOfDoubles[2].add(tile2);
                numDoublesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals("B")) {
                listOfDoubles[3].add(tile2);
                numDoublesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals("P")) {
                listOfDoubles[4].add(tile2);
                numDoublesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals("V")) {
                listOfDoubles[5].add(tile2);
                numDoublesLeft++;
            }
        }

        return listOfDoubles;
    }

    public ArrayList<Tile>[] getListOfSingles(){


        // Create a full list of all remaining hidden singles in the bag/enemy hand (This can be inferred from the visible tiles on the board)
        for (Tile tile : state.getCurrentBag().getBag()) {
            if (tile.getFirst().getHexColor().equals(tile.getSecond().getHexColor())) {
                if (tile.getFirst().getHexColor().equals("R") || tile.getSecond().getHexColor().equals("R")) {
                    listOfSingles[0].add(tile);
                    numSinglesLeft++;
                }
                if (tile.getFirst().getHexColor().equals("O") || tile.getSecond().getHexColor().equals("R")) {
                    listOfSingles[1].add(tile);
                    numSinglesLeft++;
                }
                if (tile.getFirst().getHexColor().equals("Y") || tile.getSecond().getHexColor().equals("R")) {
                    listOfSingles[2].add(tile);
                    numSinglesLeft++;
                }
                if (tile.getFirst().getHexColor().equals("B") || tile.getSecond().getHexColor().equals("R")) {
                    listOfSingles[3].add(tile);
                    numSinglesLeft++;
                }
                if (tile.getFirst().getHexColor().equals("P") || tile.getSecond().getHexColor().equals("R")) {
                    listOfSingles[4].add(tile);
                    numSinglesLeft++;
                }
                if (tile.getFirst().getHexColor().equals("V") || tile.getSecond().getHexColor().equals("R")) {
                    listOfSingles[5].add(tile);
                    numSinglesLeft++;
                }
            }
        }

        for (Tile tile2 : state.getPlayer(Math.abs(state.getGamingPlayer().getPlayerNo() - 1)).getHand().getPieces()) {
            if (tile2.getFirst().getHexColor().equals("R") || tile2.getSecond().getHexColor().equals("R")) {
                listOfSingles[0].add(tile2);
                numSinglesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals("O") || tile2.getSecond().getHexColor().equals("R")) {
                listOfSingles[1].add(tile2);
                numSinglesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals("Y") || tile2.getSecond().getHexColor().equals("R")) {
                listOfSingles[2].add(tile2);
                numSinglesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals("B") || tile2.getSecond().getHexColor().equals("R")) {
                listOfSingles[3].add(tile2);
                numSinglesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals("P") || tile2.getSecond().getHexColor().equals("R")) {
                listOfSingles[4].add(tile2);
                numSinglesLeft++;
            }
            if (tile2.getFirst().getHexColor().equals("V") || tile2.getSecond().getHexColor().equals("R")) {
                listOfSingles[5].add(tile2);
                numSinglesLeft++;
            }
        }

        return listOfSingles;
    }*/


    /*public double getProbOfColors(String c1, String c2){
        Bag currentBag = state.getCurrentBag();
        boolean isDouble = false;
        double probability = 0.0;
        String lowestColor = gamingPlayer.getScoreQ().get(0).toString();

        if (c1.equals(c2)){
            isDouble = true;
        }*/

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

                int S = numDoublesLeft;
                int s = 1;
                int N = getInvisibleTilesSize();
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

                int S = numSinglesLeft;
                int s = 1;
                int N = getInvisibleTilesSize();
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
        return -1.0;
    }

*/
}