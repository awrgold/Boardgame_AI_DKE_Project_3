package com.game;

import GameBoardAssets.HexagonActor;
import GameConstants.Constants;
import Tools.Link;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.omg.CORBA.CurrentHelper;

import java.io.Console;
import java.util.ArrayList;
import static GameConstants.Constants.*;


public class Player {

    private boolean isAI;
    private String name;

    private int[] PlayerScore = new int[6];
    private static Sprite[] PlayerScoreSprite = new Sprite[6];


/*
int[0] = blue
int[1] = violet
int[2] = orange
int[3] = purple
int[4] = red
int[5] = yellow
*/

    int playerNo;
    ArrayList<Sprite[]> playerPieces = new ArrayList<>();

    public Player(int playerNo, ArrayList<Sprite[]> playerPieces) {
        this.playerNo = playerNo;
        this.playerPieces = playerPieces;
        for (int i = 0; i < 6; i++){
            this.PlayerScore[i] = 0;
        }
        PlayerScoreSprite[0] = Constants.blueSprite;
        PlayerScoreSprite[1] = Constants.violetSprite;
        PlayerScoreSprite[2] = Constants.orangeSprite;
        PlayerScoreSprite[3] = Constants.purpleSprite;
        PlayerScoreSprite[4] = Constants.redSprite;
        PlayerScoreSprite[5] = Constants.yellowSprite;
    }



    public boolean isAI() {
        this.isAI = isAI;
        return isAI;
    }

    public int getColorScore(int color){
        return PlayerScore[color];
    }

    public ArrayList<Sprite[]> getGamePieces() {
        return this.playerPieces;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public static void updateScore(Player player, HexagonActor hexActor, HexagonalGrid hexGrid) {

        int i = 0;

        if (hexActor.getHexColor().equals("B")) i = 0;
        if (hexActor.getHexColor().equals("Y")) i = 1;
        if (hexActor.getHexColor().equals("O")) i = 2;
        if (hexActor.getHexColor().equals("P")) i = 3;
        if (hexActor.getHexColor().equals("V")) i = 4;
        if (hexActor.getHexColor().equals("R")) i = 5;

        //update score
        for (int v : CalculateScoreHex(hexGrid, hexActor)){
            player.PlayerScore[i] += v;
        }

    }

    public void printScore(){
        System.out.println(scoreToString());
    }

    public String scoreToString(){

        String p = "| ";
        for (int j = 0; j <= 5; j++) {
            String s = PlayerScore[j] + " | ";
            p = p.concat(s);
        }
        return p;
    }

    public int[] getPlayerScore() {
        return PlayerScore;
    }

    public int getPlayerScoreAtIndex(int index){
        return PlayerScore[index];
    }

    public Sprite getPlayerScoreSprite(int index){
        return PlayerScoreSprite[index];
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

    public static int[] CalculateScoreHex(HexagonalGrid hexGrid, HexagonActor hexActor) {

        //calculates all the points in all directions for each hexagon placed on the board
        //return an array with points for each directions

        int[] sums = new int[6];
        Hexagon startingHex = hexActor.getHexagon();
        Hexagon currentHex;


        for (int d = 0; d < 6; d++){
            int result = 0;
            boolean sameColor = true;
            currentHex = startingHex;

            while (sameColor) {

                Hexagon currentHexNext = Player.neighborByDirection(d, currentHex, hexGrid);

                if (currentHexNext != null) {
                    //hexActor1 = new HexagonActor(currentHex2);
                    if (currentHexNext.getSatelliteData().isPresent()){
                        Link hexLink = (Link) currentHexNext.getSatelliteData().get();
                        HexagonActor currentHexActor = hexLink.getActor();

                        if (hexActor.getHexColor().equals(currentHexActor.getHexColor())) {
                            result++;
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

    public static boolean isLowestScoreTilePresent(Player player){
        int lowest = 19;
        for (int i = 0; i < 6; i++){
            if(player.getPlayerScoreAtIndex(i) <= lowest){
                lowest = player.getPlayerScoreAtIndex(i);
            }
        }
        for (Sprite[] sprite : player.getGamePieces()){
            if (sprite[0] == player.getPlayerScoreSprite(lowest) || sprite[1] == player.getPlayerScoreSprite(lowest)){
                return true;
            }
        }
        return false;
    }
}






