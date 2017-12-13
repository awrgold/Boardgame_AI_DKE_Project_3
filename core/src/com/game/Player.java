package com.game;

import GameBoardAssets.HexagonActor;
import GameConstants.Constants;
import GameScoreAssets.ScoreTuple;
import Tools.Link;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;

import java.util.ArrayList;
import java.util.Collections;
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
    public static int playerNo;
    private Hand hand;
    private String[] playerScoreString = new String[6];
    private static boolean[] colorIngenious = new boolean[6];
    private Board board;
    private ArrayList<Integer> scoreQ = new ArrayList<>();
    private ArrayList<ScoreTuple> scoreTuples = new ArrayList<>();
    private Sprite[] PlayerScoreSprite = new Sprite[6];


    //private Action move = new Action();



    public Player(int playerNo, ArrayList<Tile> playerPieces) {
        this.playerNo = playerNo;
        this.hand = new Hand(playerPieces);

        for (int i = 0; i < 6; i++){
            this.playerScore[i] = 0;
        }
        PlayerScoreSprite[0] = Constants.blueSprite;
        ScoreTuple tuple0 = new ScoreTuple(0, Constants.blueSprite);
        PlayerScoreSprite[1] = Constants.yellowSprite;
        ScoreTuple tuple1 = new ScoreTuple(1, Constants.yellowSprite);
        PlayerScoreSprite[2] = Constants.orangeSprite;
        ScoreTuple tuple2 = new ScoreTuple(2, Constants.orangeSprite);
        PlayerScoreSprite[3] = Constants.purpleSprite;
        ScoreTuple tuple3 = new ScoreTuple(3, Constants.purpleSprite);
        PlayerScoreSprite[4] = Constants.violetSprite;
        ScoreTuple tuple4 = new ScoreTuple(4, Constants.violetSprite);
        PlayerScoreSprite[5] = Constants.redSprite;
        ScoreTuple tuple5 = new ScoreTuple(5, Constants.redSprite);
        playerScoreString[0] = "B";
        playerScoreString[1] = "Y";
        playerScoreString[2] = "O";
        playerScoreString[3] = "P";
        playerScoreString[4] = "V";
        playerScoreString[5] = "R";

        // add tuple objects that can be sorted in "dictionary" format
        scoreTuples.add(0, tuple0);
        scoreTuples.add(1, tuple1);
        scoreTuples.add(2, tuple2);
        scoreTuples.add(3, tuple3);
        scoreTuples.add(4, tuple4);
        scoreTuples.add(5, tuple5);
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

    public static void updateScore(Player player, HexagonActor hexActor, HexagonalGrid hexGrid, HexagonActor one) {

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
                player.playerScore[i] += v;
            }

        } else {
            if (hexActor.getHexagon().getGridZ() - one.getHexagon().getGridZ() == 1 &&
                    hexActor.getHexagon().getGridX() == one.getHexagon().getGridX()){
                avoid = 3;
                for (int v : CalculateScoreHex(hexGrid, hexActor, avoid)){
                    player.playerScore[i] += v;
                }

            } if (hexActor.getHexagon().getGridZ() - one.getHexagon().getGridZ() == -1 &&
                    hexActor.getHexagon().getGridX() == one.getHexagon().getGridX()){
                avoid = 0;
                for (int v : CalculateScoreHex(hexGrid, hexActor, avoid)){
                    player.playerScore[i] += v;
                }

            } if (hexActor.getHexagon().getGridZ() - one.getHexagon().getGridZ() == -1 &&
                    hexActor.getHexagon().getGridX() - one.getHexagon().getGridX() == 1){
                avoid = 5;
                for (int v : CalculateScoreHex(hexGrid, hexActor, avoid)){
                    player.playerScore[i] += v;
                }

            } if (hexActor.getHexagon().getGridZ() - one.getHexagon().getGridZ() == 1 &&
                    hexActor.getHexagon().getGridX() - one.getHexagon().getGridX() == -1){
                avoid = 2;
                for (int v : CalculateScoreHex(hexGrid, hexActor, avoid)){
                    player.playerScore[i] += v;
                }

            } if (hexActor.getHexagon().getGridX() - one.getHexagon().getGridX() == 1 &&
                    hexActor.getHexagon().getGridZ() == one.getHexagon().getGridZ()){
                avoid = 4;
                for (int v : CalculateScoreHex(hexGrid, hexActor, avoid)){
                    player.playerScore[i] += v;
                }

            } if (hexActor.getHexagon().getGridX() - one.getHexagon().getGridX() == -1 &&
                    hexActor.getHexagon().getGridZ() == one.getHexagon().getGridZ()){
                avoid = 1;
                for (int v : CalculateScoreHex(hexGrid, hexActor, avoid)){
                    player.playerScore[i] += v;
                }

            }
        }
        if (player.playerScore[i] > 18)
        {
            player.playerScore[i] = 18;
        }

    }

    public String scoreToString(){

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
        System.out.println("LowIndex = " + lowIndex);

        if (indexesOfLowest.size() == 1 && !isAColorPresent(playerScoreString[lowIndex])) {
            return false;
        } if (indexesOfLowest.size() > 1 && lowest > 0){
            for (int i : indexesOfLowest){
                if (!isAColorPresent(playerScoreString[i])){
                    return false;
                }

            }
        }


        return true;
    }

    public static Hexagon neighborByDirection(int d, Hexagon hexagon, HexagonalGrid hexagonalGrid){

        // d is the direction [0 = topLeft; 1 = left; 2 = botLeft; 3 = botRight; 4 = right; 5 = topRight]
        //given a direction the method checks if there is a neighbor, if positive return that neighbor, else null

        Hexagon hexNext;

        if(d == 0) {
            CubeCoordinate nextCoordinates = CubeCoordinate.fromCoordinates(hexagon.getGridX(), hexagon.getGridZ() + 1);
            if (hexagonalGrid.getByCubeCoordinate(nextCoordinates).isPresent()) {
                hexNext = (Hexagon) hexagonalGrid.getByCubeCoordinate(nextCoordinates).get();
                return hexNext;
            } else {
                return null;
            }

        } if(d == 1) {
            CubeCoordinate nextCoordinates = CubeCoordinate.fromCoordinates(hexagon.getGridX() + 1, hexagon.getGridZ());
            if (hexagonalGrid.getByCubeCoordinate(nextCoordinates).isPresent()) {
                hexNext = (Hexagon) hexagonalGrid.getByCubeCoordinate(nextCoordinates).get();
                return hexNext;
            } else {
                return null;
            }

        } if(d == 2) {
            CubeCoordinate nextCoordinates = CubeCoordinate.fromCoordinates(hexagon.getGridX() + 1, hexagon.getGridZ() - 1);
            if (hexagonalGrid.getByCubeCoordinate(nextCoordinates).isPresent()) {
                hexNext = (Hexagon) hexagonalGrid.getByCubeCoordinate(nextCoordinates).get();
                return hexNext;
            } else {
                return null;
            }

        } if(d == 3) {
            CubeCoordinate nextCoordinates = CubeCoordinate.fromCoordinates(hexagon.getGridX(), hexagon.getGridZ() - 1);
            if (hexagonalGrid.getByCubeCoordinate(nextCoordinates).isPresent()) {
                hexNext = (Hexagon) hexagonalGrid.getByCubeCoordinate(nextCoordinates).get();
                return hexNext;
            } else {
                return null;
            }

        } if(d == 4) {
            CubeCoordinate nextCoordinates = CubeCoordinate.fromCoordinates(hexagon.getGridX() - 1, hexagon.getGridZ());
            if (hexagonalGrid.getByCubeCoordinate(nextCoordinates).isPresent()) {
                hexNext = (Hexagon) hexagonalGrid.getByCubeCoordinate(nextCoordinates).get();
                return hexNext;
            } else {
                return null;
            }

        } if(d == 5) {
            CubeCoordinate nextCoordinates = CubeCoordinate.fromCoordinates(hexagon.getGridX() - 1, hexagon.getGridZ() + 1);
            if (hexagonalGrid.getByCubeCoordinate(nextCoordinates).isPresent()) {
                hexNext = (Hexagon) hexagonalGrid.getByCubeCoordinate(nextCoordinates).get();
                return hexNext;
            } else {
                return null;
            }

        } else {
            return null;
        }
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
                Hexagon currentHexNext = Player.neighborByDirection(d, currentHex, hexGrid);

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
                System.out.println("Player " + playerNo + " has reached Ingenious for color " + i + "!");
                return true;
            }
        }
        return false;
    }



    public boolean[] getIngeniousList(){
        return colorIngenious;
    }

    /*
    public ArrayList<Integer> getScoreQ(){
        ArrayList<Integer> scoreQ = new ArrayList<>();
        for (int i : playerScore){
            scoreQ.add(playerScore[i]);
        }
        Collections.sort(scoreQ, Collections.reverseOrder());
        return scoreQ;
    }
    */


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
        System.out.println("lowest colors: " + Arrays.toString(lowestColors.toArray()));
        return lowestColors;

    }

    public ArrayList<Integer> getScoreQ(){

        // under construction

        ArrayList<Integer> indexes = new ArrayList<>();

        for (int i : playerScore){
            scoreQ.add(playerScore[i]);
        }

        Collections.sort(scoreQ, Collections.reverseOrder());

        for (int i : scoreQ){
            for (int j = i; j < scoreQ.size(); j++){
                if (scoreQ.get(i) == playerScore[j]){
                    indexes.add(j);
                    j++;
                }
            }
        }
        return indexes;
    }



    public int howManyLowestColors(){
        int count = 0;
        for (int i = 1; i < scoreQ.size(); i++){
            if (scoreQ.get(i).equals(scoreQ.get(0))){
                count++;
            }
        }
        return count;
    }

    //TODO
    public ArrayList<Tile> getSortedTiles(){

        /*
        Priorities:
        1) Doubles of lowest score
        2) Tiles with lowest score + 2nd lowest score
        3) Tiles with lowest score + 3rd lowest score
        4) Doubles of second lowest score
        5) Doubles of third lowest score
        6) random
         */
        // tiles in hand to be sorted by priority
        ArrayList<Tile> scoreTileQ = this.getHand().getPieces();
        // list of colors found in hand
        ArrayList<Sprite[]> tileColors = new ArrayList<>();
        // scoreTupleQ of tuples tied to index, needs to be sorted, used to sort scoreTileQ
        ArrayList<ScoreTuple> scoreTupleQ = new ArrayList<>();

        // sort the scoreTupleQ based on score index (lowest to highest).
        // Use this to find the color tied to score index!!!
        for (int i : getScoreQ()){
            tileColors.set(i, this.getHand().getPieces().get(i).getColors());

            for (int j = 0; j < scoreTupleQ.size(); j++){

                if (getScoreQ().get(i) == scoreTuples.get(j).getScoreIndex()){
                    scoreTupleQ.set(i, scoreTuples.get(j));
                    i++;
                }
            }
        }

        int temp = 0;
        for (int i : getScoreQ()){
            for (Tile t : scoreTileQ){

                /*
                PRIORITY HEURISTICS:
                 */

                // find doubles of the lowest color
                if (t.getFirst().getHexColor().equals(scoreTupleQ.get(0).getScoreColorString()) && t.getSecond().getHexColor().equals(scoreTupleQ.get(0).getScoreColorString())){
                    scoreTileQ.set(temp,t);
                    temp++;
                }
                // find (2)
                if (t.getFirst().getHexColor().equals(scoreTupleQ.get(0).getScoreColorString()) && t.getSecond().getHexColor().equals(scoreTupleQ.get(1).getScoreColorString())){
                    scoreTileQ.set(temp,t);
                    temp++;
                }
                // find (3)
                if (t.getFirst().getHexColor().equals(scoreTupleQ.get(0).getScoreColorString()) && t.getSecond().getHexColor().equals(scoreTupleQ.get(2).getScoreColorString())){
                    scoreTileQ.set(temp,t);
                    temp++;
                }
                // find (4)
                if (t.getFirst().getHexColor().equals(scoreTupleQ.get(1).getScoreColorString()) && t.getSecond().getHexColor().equals(scoreTupleQ.get(1).getScoreColorString())){
                    scoreTileQ.set(temp,t);
                    temp++;
                }
                // find (5)
                if (t.getFirst().getHexColor().equals(scoreTupleQ.get(2).getScoreColorString()) && t.getSecond().getHexColor().equals(scoreTupleQ.get(2).getScoreColorString())){
                    scoreTileQ.set(temp,t);
                    temp++;
                }
                // find (6) - random
                scoreTileQ.set(i,t);
            }
        }
        return scoreTileQ;
    }

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


        return pieces;

    }
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






