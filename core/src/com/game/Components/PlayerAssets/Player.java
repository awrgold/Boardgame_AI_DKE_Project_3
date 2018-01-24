package com.game.Components.PlayerAssets;

import com.game.Components.GameConstants.Color;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.GameAI.*;

import java.util.*;

/*
int[0] = blue
int[1] = yellow
int[2] = orange
int[3] = purple
int[4] = violet
int[5] = red
*/

public class Player{

    private boolean isAI;
    private int[] playerScore = new int[6];
    private int playerNo;
    private Hand hand;
    private Color[] playerScoreColors = new Color[6];
    private static boolean[] colorIngenious = new boolean[6];
    private boolean isGreedy;
    private boolean isMCTS;
    private boolean isExpectiMax;
    private boolean isRandom;
    private Strategy strategy;
    private static int scoreIncr = 0;

    public Player(int playerNo, ArrayList<Tile> playerPieces, boolean isAI, boolean isGreedy, boolean isExpectiMax, boolean isMCTS, boolean isRandom) {
        this.playerNo = playerNo;
        this.hand = new Hand(playerPieces);
        this.isAI = isAI;
        for (int i = 0; i < 6; i++){
            this.playerScore[i] = 0;
        }
        playerScoreColors[0] = Color.BLUE;
        playerScoreColors[1] = Color.YELLOW;
        playerScoreColors[2] = Color.ORANGE;
        playerScoreColors[3] = Color.PURPLE;
        playerScoreColors[4] = Color.VIOLET;
        playerScoreColors[5] = Color.RED;

        if (isAI){
            if (isRandom){
                this.isRandom = true;
                strategy = new RandomStrategy();
            }
            if (isGreedy){
                this.isGreedy = true;
                strategy = new GreedyStrategy();
                isGreedy = true;
            }
            if (isMCTS){
                this.isMCTS = true;
                strategy = new MCTSSearch();
            }
            if (isExpectiMax){
                this.isExpectiMax = true;
                strategy = new ExpectimaxStrategy();
                isExpectiMax = true;
            }
            if (isRandom){
                strategy = new RandomStrategy();
                isRandom = true;
            }
        }


    }

    public Player clonePlayer(){
        Player newPlayer = new Player(getPlayerNo(), getHand().cloneHand().getPieces(), isAI(), isGreedy, isExpectiMax, isMCTS, isRandom);
        newPlayer.setPlayerScore(playerScore.clone());
        return newPlayer;

    }


    public boolean isAI() {
        return isAI;
    }

    public String getStrategy(){
        String strategy = new String();
        if (isMCTS) strategy = "MCTS";
        if (isRandom) strategy = "Random";
        if (isGreedy) strategy = "Greedy";
        if (isExpectiMax) strategy = "ExpectiMax";
        return strategy;
    }

    /*
    public void setGreedy(){
        isGreedy = true;
        isMCTS = false;
        isExpectiMax = false;
    }

    public void setMCTS(){
        isMCTS = true;
        isGreedy = false;
        isExpectiMax = false;
    }

    public void setExpectiMax(){
        isExpectiMax = true;
        isMCTS = false;
        isGreedy = false;
    }
    */


    public Hand getHand(){
        return this.hand;
    }

    public int getColorScore(int color){
        return playerScore[color];
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerScore(int[] playerScore) {
        this.playerScore = playerScore;
    }

    public void updateScore(int[] scoreGains){
        for (int i = 0; i < 6; i++){
            playerScore[i] += scoreGains[i];
            if (playerScore[i] > 18)
            {
                playerScore[i] = 18;
            }
        }

    }


public int getScoreIncrease(){
        return scoreIncr;
}
    private static int getSum(int[] scoreGains) {
        int sum = 0;
        for(int i =0; i<scoreGains.length;i++){
            sum+=scoreGains[i];
        }
        return sum;
    }

    public String scoreToString() {
        String p = "| ";
        for (int j = 0; j <= 5; j++) {
            String s = playerScore[j] + " | ";
            p = p.concat(s);
        }
        return p;
    }

    public int[] getPlayerScore() {
        return playerScore;
    }

    private boolean isAColorPresent(Color color){
        for (Tile tile : hand.getPieces()){
            if(tile.getActors()[0].getHexColor().equals(color) || tile.getActors()[1].getHexColor().equals(color)){
                return true;
            }
        }
        //System.out.println(color + " is not present");
        return false;

    }

    public boolean isLowestScoreTilePresent(){
        int lowest = 18;
        int lowIndex = -1;
        ArrayList<Integer> indexesOfLowest = new ArrayList<>();

        for (int i = 0; i < 6; i++){
            if(playerScore[i] < lowest){
                lowest = playerScore[i];
                lowIndex = i;
            }
        }

        for (int i = 0; i < 6; i++){
            if (playerScore[i] == lowest){
                indexesOfLowest.add(i);
            }
        }

        if (indexesOfLowest.size() == 1 && !isAColorPresent(playerScoreColors[lowIndex])) {
            return false;
        }

        if (indexesOfLowest.size() > 1 && lowest > 0){
            for (int i : indexesOfLowest){
                if (!isAColorPresent(playerScoreColors[i])){
                    return false;
                }
            }
        }
        return true;
    }




    public boolean hasIngenious(){
        for (int i = 0; i < 6; i++){
            if(playerScore[i] >= 18 && !colorIngenious[i]){
                // Ingenious!
                colorIngenious[i] = true;
                //System.out.println("PlayerAssets " + playerNo + " has reached Ingenious for color " + i + "!");
                return true;
            }
        }
        return false;
    }

    public boolean[] getIngeniousList(){
        return colorIngenious;
    }

    public ArrayList<Integer> getScoreQ(){
        ArrayList<Integer> scoreQ = new ArrayList<>();
        for (int i : playerScore){
            scoreQ.add(playerScore[i]);
        }
        Collections.sort(scoreQ, Collections.reverseOrder());
        return scoreQ;
    }

    public ArrayList<Color> lowestColors(){
        ArrayList<Color> lowestColors = new ArrayList<>();
        int lowest = 18;

        for (int i = 0; i < 6; i++){
            if(playerScore[i] < lowest){
                lowest = playerScore[i];
            }
        }

        for (int i = 0; i < 6; i++){
            if (playerScore[i] == lowest){
                lowestColors.add(playerScoreColors[i]);
            }
        }

        //System.out.println("lowest colors: " + Arrays.toString(lowestColors.toArray()));
        return lowestColors;
    }

    public Action applyStrategy(GameState currentState){
        return strategy.decideMove(currentState);
    }

}






