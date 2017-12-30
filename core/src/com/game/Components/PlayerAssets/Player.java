package com.game.Components.PlayerAssets;

import com.game.Components.GameAssets.Board;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.Tools.Link;
import com.game.GameAI.GreedyStrategy;
import com.game.GameAI.Strategy;
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
    private String[] playerScoreString = new String[6];
    private static boolean[] colorIngenious = new boolean[6];
    private GreedyStrategy strategy;

    public Player(int playerNo, ArrayList<Tile> playerPieces, boolean isAI) {
        this.playerNo = playerNo;
        this.hand = new Hand(playerPieces);
        this.isAI = isAI;
        for (int i = 0; i < 6; i++){
            this.playerScore[i] = 0;
        }
        playerScoreString[0] = "B";
        playerScoreString[1] = "Y";
        playerScoreString[2] = "O";
        playerScoreString[3] = "P";
        playerScoreString[4] = "V";
        playerScoreString[5] = "R";

        if (isAI) strategy = new GreedyStrategy();
    }

    public boolean isAI() {
        return isAI;
    }

    public Hand getHand(){
        return this.hand;
    }

    public int getColorScore(int color){
        return playerScore[color];
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public static void updateScore(int[] scoreGains, Player player){
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

        if (hexActor.getHexColor().equals("B")) i = 0;
        if (hexActor.getHexColor().equals("Y")) i = 1;
        if (hexActor.getHexColor().equals("O")) i = 2;
        if (hexActor.getHexColor().equals("P")) i = 3;
        if (hexActor.getHexColor().equals("V")) i = 4;
        if (hexActor.getHexColor().equals("R")) i = 5;

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
        //System.out.println(Arrays.toString(scoreGains));

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

    private boolean isAColorPresent(String color){
        for (Tile tile : hand.getPieces()){
            if(tile.getActors()[0].getHexColor().equals(color) || tile.getActors()[1].getHexColor().equals(color)){
                return true;
            }
        }
        System.out.println(color + " is not present");
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

        if (indexesOfLowest.size() == 1 && !isAColorPresent(playerScoreString[lowIndex])) {
            return false;
        }

        if (indexesOfLowest.size() > 1 && lowest > 0){
            for (int i : indexesOfLowest){
                if (!isAColorPresent(playerScoreString[i])){
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
                System.out.println("PlayerAssets " + playerNo + " has reached Ingenious for color " + i + "!");
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

    public boolean hasManyLowestColors(){
        int counter = 0;
        for (int i : playerScore){
            int temp = playerScore[i];
            for (int j = i; j < playerScore.length; j++){
                if (playerScore[j] == temp){
                    counter++;
                }
            }

        }
        if (counter > 2){
            return true;
        }
        return false;
    }

    public boolean hasTwoLowestColors(){
        int counter = 0;
        for (int i : playerScore){
            int temp = playerScore[i];
            for (int j = i; j < playerScore.length; j++){
                if (playerScore[j] == temp){
                    counter++;
                }
            }
        }
        if (counter == 2){
            return true;
        }
        return false;
    }

    //TRYING TO IMPLEMENT THE STRATEGY

//  FIND THE LOWEST COLORS
    public ArrayList<String> lowestColors(){
        ArrayList<String> lowestColors = new ArrayList<>();
        int lowest = 18;

        for (int i = 0; i < 6; i++){
            if(playerScore[i] < lowest){
                lowest = playerScore[i];

            }
        }

        for (int i = 0; i < 6; i++){
            if (playerScore[i] == lowest){
                lowestColors.add(playerScoreString[i]);
            }

        }
        //System.out.println("lowest colors: " + Arrays.toString(lowestColors.toArray()));
        return lowestColors;

    }

    public Action applyStrategy(ArrayList<String> lowestColors, Hand currentHand, HexagonalGrid currentGrid){
        return strategy.decideMove(lowestColors, currentHand, currentGrid);


    }
/*
//  PICK FROM HAND TILES THAT CONTAIN THAT COLORS (IF THERE'S A DOUBLE IS THE BEST ONE)
    public HashMap<Tile, String> bestTilesToPlace(ArrayList<String> colors){
        HashMap<Tile, String> pieces = new HashMap<>();

        for(String color : colors){
            for(Tile t : hand.getPieces()){
                if (t.getActors()[0].getHexColor().equals(color) && t.getActors()[1].getHexColor().equals(color)){
                    pieces.entrySet().removeIf(entry -> entry.getValue().equals(color));
                    pieces.put(t, color);
                    System.out.println("Found a double to place: " + color + " - " + color);
                    break;
                } if (t.getActors()[0].getHexColor().equals(color) || t.getActors()[1].getHexColor().equals(color)){
                    pieces.put(t, color);

                }
            }
        }

        for(Tile piece : pieces.keySet()){
            System.out.println(piece.getActors()[0].getHexColor() + " - " + piece.getActors()[1].getHexColor() + " : is a good tile to place"
            + " because there is " + pieces.get(piece));
        }

        return pieces;

    }*/
/*
//  FOR A TILE RETURN THE GAME STATE THAT RETURN THE HIGHEST SCORE ON THE INTERESTED COLOR
    public GameState bestMoveForTile(Tile t, Board currentBoard, String color){

    }

//  APPLY THE STRATEGY AND RETURN ALL POSSIBLE STATES
    public ArrayList<GameState> applyStrategy(Board currentBoard){
        ArrayList<GameState> aiMoves = new ArrayList<>();
        ArrayList<String> colorsToPlay = lowestColors();
        HashMap<Tile, String> tilesToPlay = bestTilesToPlace(colorsToPlay);

        for (HashMap.Entry<Tile, String> entry : tilesToPlay.entrySet()) {
            aiMoves.add(bestMoveForTile(entry.getKey(), currentBoard, entry.getValue()));
        }

        return aiMoves;


    }
*/

}






