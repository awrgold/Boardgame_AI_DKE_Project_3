package com.game.Components.PlayerAssets;

import com.game.Components.GameAssets.Board;
import com.game.Components.GameConstants.Color;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.Tools.Link;
import com.game.GameAI.*;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;

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
    private boolean[] colorIngenious = new boolean[6];
    private boolean isGreedy;
    private boolean isRandom;
    private boolean isMCTS;
    private boolean isExpectiMax;
    private Strategy strategy;

    public Player(int playerNo, ArrayList<Tile> playerPieces, boolean isAI, boolean isRandom, boolean isGreedy, boolean isExpectiMax, boolean isMCTS) {
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
        Player newPlayer = new Player(getPlayerNo(), getHand().cloneHand().getPieces(), isAI(), isRandom, isGreedy, isExpectiMax, isMCTS);
        newPlayer.setPlayerScore(playerScore.clone());
        newPlayer.colorIngenious = colorIngenious.clone();
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

    public static void updateScore(int[] scoreGains, Player player){
        //System.out.println(Arrays.toString(scoreGains));
        for (int i = 0; i < 6; i++){
            player.playerScore[i] += scoreGains[i];
            if (player.playerScore[i] > 18)
            {
                player.playerScore[i] = 18;
            }
        }

    }

    public static int[] scoreGain(HexagonActor hexActor, HexagonalGrid hexGrid, HexagonActor one) {

        int[] scoreGains = new int[6];

        int i = 0;

        int avoid = -1;

        if (hexActor.getHexColor().equals(Color.BLUE)) i = 0;
        if (hexActor.getHexColor().equals(Color.YELLOW)) i = 1;
        if (hexActor.getHexColor().equals(Color.ORANGE)) i = 2;
        if (hexActor.getHexColor().equals(Color.PURPLE)) i = 3;
        if (hexActor.getHexColor().equals(Color.VIOLET)) i = 4;
        if (hexActor.getHexColor().equals(Color.RED)) i = 5;

        //update score
        if (hexActor == one){
            for (int v : CalculateScoreHex(hexGrid, hexActor, avoid)){
                scoreGains[i] += v;
            }

        } else {
            if (hexActor.getHexagon().getGridZ() - one.getHexagon().getGridZ() == 1 &&
                    hexActor.getHexagon().getGridX() == one.getHexagon().getGridX()){
                avoid = 3;
                for (int v : CalculateScoreHex(hexGrid, hexActor, avoid)){
                    scoreGains[i] += v;
                }

            } if (hexActor.getHexagon().getGridZ() - one.getHexagon().getGridZ() == -1 &&
                    hexActor.getHexagon().getGridX() == one.getHexagon().getGridX()){
                avoid = 0;
                for (int v : CalculateScoreHex(hexGrid, hexActor, avoid)){
                    scoreGains[i] += v;
                }

            } if (hexActor.getHexagon().getGridZ() - one.getHexagon().getGridZ() == -1 &&
                    hexActor.getHexagon().getGridX() - one.getHexagon().getGridX() == 1){
                avoid = 5;
                for (int v : CalculateScoreHex(hexGrid, hexActor, avoid)){
                    scoreGains[i] += v;
                }

            } if (hexActor.getHexagon().getGridZ() - one.getHexagon().getGridZ() == 1 &&
                    hexActor.getHexagon().getGridX() - one.getHexagon().getGridX() == -1){
                avoid = 2;
                for (int v : CalculateScoreHex(hexGrid, hexActor, avoid)){
                    scoreGains[i] += v;
                }

            } if (hexActor.getHexagon().getGridX() - one.getHexagon().getGridX() == 1 &&
                    hexActor.getHexagon().getGridZ() == one.getHexagon().getGridZ()){
                avoid = 4;
                for (int v : CalculateScoreHex(hexGrid, hexActor, avoid)){
                    scoreGains[i] += v;
                }

            } if (hexActor.getHexagon().getGridX() - one.getHexagon().getGridX() == -1 &&
                    hexActor.getHexagon().getGridZ() == one.getHexagon().getGridZ()){
                avoid = 1;
                for (int v : CalculateScoreHex(hexGrid, hexActor, avoid)){
                    scoreGains[i] += v;
                }

            }
        }
        // System.out.println(Arrays.toString(scoreGains));

        return scoreGains;

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


    public static int[] CalculateScoreHex(HexagonalGrid hexGrid, HexagonActor hexActor, int avoidNext) {

        //calculates all the points in all directions for each hexagon placed on the board
        //return an array with points for each directions

        int[] sums = new int[6];
        Hexagon startingHex = hexActor.getHexagon();
        Hexagon currentHex;


        // loop 6 times (6 directions)
        for (int d = 0; d < 6; d++){
            if (d == avoidNext){
                continue;
            }
            // beginning with each loop, start a result at 0
            int result = 0;
            // boolean for each color that is the same until it becomes false
            boolean sameColor = true;
            // current Hex is at the starting position
            currentHex = startingHex;

            // as long as the colors are the same...
            while (sameColor) {

                // make the next hexagon to compare to the current hex
                Hexagon currentHexNext = Board.neighborByDirection(d, currentHex, hexGrid);

                // if not at the edge...
                if (currentHexNext != null) {

                    // if the next hex is not empty...
                    if (currentHexNext.getSatelliteData().isPresent()){
                        // create a link for the actor and hex of the next hex from current
                        Link hexLink = (Link) currentHexNext.getSatelliteData().get();
                        HexagonActor currentHexActor = hexLink.getActor();

                        // if the color of the next hexagon is the same as the current hexagon...
                        if (hexActor.getHexColor().equals(currentHexActor.getHexColor())) {
                            // increment by 1
                            result++;
                            // move the next hex forward one space
                            currentHex = currentHexNext;

                        } else {
                            sameColor = false;
                        }
                    } else {
                        sameColor = false;
                    }
                } else {
                    sameColor = false;
                }
            }
            sums[d] = result;
        }

        return sums;
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

    public HashMap<Color, Double> getScoreQ(){

        HashMap<Color, Double> scoreQ = new HashMap<>();

        ArrayList<Integer> scoreList = new ArrayList<>();
        for (int p : getPlayerScore().clone()){
            scoreList.add(p);
        }

        Color[] orderedColors = playerScoreColors.clone();

        double multiplier = 7;
        int lower = -1;

        for (int i = 0; i < 6; i++){
            int lowestScore = Collections.min(scoreList);
            int index = scoreList.indexOf(lowestScore);

            if (lowestScore > lower){
                lower = lowestScore;
                multiplier--;
            }
            scoreQ.put(orderedColors[index], multiplier);
            scoreList.set(index, 100);


        }
/*
        for (Color color: scoreQ.keySet()){

            String key = color.toString();
            System.out.println(key + " " + scoreQ.get(color));

        }*/




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

        // System.out.println("lowest colors: " + Arrays.toString(lowestColors.toArray()));
        return lowestColors;
    }

    public Action applyStrategy(GameState currentState){
        return strategy.decideMove(currentState);
    }


}






