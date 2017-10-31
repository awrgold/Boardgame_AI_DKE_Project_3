package com.game;

import GameBoardAssets.HexagonActor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.omg.CORBA.CurrentHelper;

import java.io.Console;
import java.util.ArrayList;

public class Player {

    private boolean isAI;
    private String name;

    private int[] PlayerScore = new int[6];
/*
int[0] = blue
int[1] = violet
int[2] = orange
int[3] = purple
int[4] = red
int[5] = yellow
*/

    int playerNo;
    ArrayList<Sprite[]> gamePieces = new ArrayList<Sprite[]>();

    public Player(int playerNo, ArrayList<Sprite[]> gamePieces) {
        this.playerNo = playerNo;
        this.gamePieces = gamePieces;
    }


    public boolean isAI() {
        this.isAI = isAI;
        return isAI;
    }

    public int[] getPlayerScore(){
        return PlayerScore;
    }

    public int getColorScore(int color){
        return PlayerScore[color];
    }

    public ArrayList<Sprite[]> getGamePieces() {
        return this.gamePieces;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public static void updateScore(Player player, HexagonActor hexActor, HexagonalGrid hexGrid) {

        int[] playerScore = player.PlayerScore;

        int i = 0;

        if (hexActor.getHexColor() == "B") i = 0;
        if (hexActor.getHexColor() == "O") i = 1;
        if (hexActor.getHexColor() == "P") i = 2;
        if (hexActor.getHexColor() == "R") i = 3;
        if (hexActor.getHexColor() == "V") i = 4;
        if (hexActor.getHexColor() == "y") i = 5;

        //Calculate color combination from hex to left:
        playerScore[i] += CalculateScoreHexToLeft(hexGrid, hexActor);
        //Calculate color combination from hex to right
        playerScore[i] += CalculateScoreHexToRight(hexGrid, hexActor);
        //Calculate color combination from hex to bottom left
        playerScore[i] += CalculateScoreHexToBotLeft(hexGrid, hexActor);
        //Calculate color combination from hex to bottom right
        playerScore[i] += CalculateScoreHexToBotRight(hexGrid, hexActor);
        //Calculate color combination from hex to top left
        playerScore[i] += CalculateScoreHexToTopLeft(hexGrid, hexActor);
        //Calculate color combination from hex to top right
        playerScore[i] += CalculateScoreHexToTopRight(hexGrid, hexActor);

/*
        //Update score of designated tile sort:
        String color = hexActor.getHexColor();

        if (color == "B") playerScore[0] = playerScore[0] + result;
        if (color == "G") playerScore[1] = playerScore[1] + result;
        if (color == "O") playerScore[2] = playerScore[2] + result;
        if (color == "P") playerScore[3] = playerScore[3] + result;
        if (color == "R") playerScore[4] = playerScore[4] + result;
        if (color == "Y") playerScore[5] = playerScore[5] + result;
*/

        for (int j = 0; j <= 5; j++)
        {
            System.out.println(playerScore[j]);
        }

    }


    public static int CalculateScoreHexToLeft(HexagonalGrid hexGrid, HexagonActor hexActor) {
        int result = 0;
        boolean sameColor = true;

        Hexagon currentHex = hexActor.getHexagon();
        Hexagon currentHex2;
        CubeCoordinate ccCurrentHex = CubeCoordinate.fromCoordinates(currentHex.getGridX(), currentHex.getGridZ());
        CubeCoordinate ccCurrentHex2;

//        currentHex = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex).get();

        HexagonActor hexActor1;

        while (currentHex.getGridX() > -2 && sameColor == true) {
            hexActor = new HexagonActor(currentHex);
            ccCurrentHex2 = CubeCoordinate.fromCoordinates(currentHex.getGridX() - 1, currentHex.getGridZ());
            if (hexGrid.getByCubeCoordinate(ccCurrentHex2).isPresent()) {
                currentHex2 = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex2).get();

                hexActor1 = new HexagonActor(currentHex2);

                if (hexActor.getHexColor() == hexActor1.getHexColor()) {
                    result += 1;
                    currentHex = currentHex2;
                } else {
                    sameColor = false;
                }
            } else {
                sameColor = false;
            }
        }
        return result;

    }

    public static int CalculateScoreHexToRight(HexagonalGrid hexGrid, HexagonActor hexActor) {
        int result = 0;
        boolean sameColor = true;

        Hexagon currentHex = hexActor.getHexagon();
        Hexagon currentHex2;
        CubeCoordinate ccCurrentHex = CubeCoordinate.fromCoordinates(currentHex.getGridX(), currentHex.getGridZ());
        CubeCoordinate ccCurrentHex2;

//        currentHex = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex).get();

        HexagonActor hexActor1;

        while (currentHex.getGridX() < 8 && sameColor == true) {
            ccCurrentHex2 = CubeCoordinate.fromCoordinates(currentHex.getGridX() + 1, currentHex.getGridZ());
            if (hexGrid.getByCubeCoordinate(ccCurrentHex2).isPresent()) {
                currentHex2 = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex2).get();

                hexActor1 = new HexagonActor(currentHex2);
                if (hexActor.getHexColor() == hexActor1.getHexColor()) {
                    result += 1;
                    currentHex = currentHex2;
                } else {
                    sameColor = false;
                }
            } else {
                sameColor = false;
            }
        }
        return result;

    }


    public static int CalculateScoreHexToTopLeft(HexagonalGrid hexGrid, HexagonActor hexActor) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex = hexActor.getHexagon();
        Hexagon currentHex2;
        CubeCoordinate ccCurrentHex = CubeCoordinate.fromCoordinates(currentHex.getGridX(), currentHex.getGridZ());
        CubeCoordinate ccCurrentHex2;

//        currentHex = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex).get();

        HexagonActor hexActor1;

        while (currentHex.getGridX() > 0 && currentHex.getGridZ() < 11 && sameColor == true) {
            hexActor1 = new HexagonActor(currentHex);

            ccCurrentHex2 = CubeCoordinate.fromCoordinates(currentHex.getGridX() - 1, currentHex.getGridZ() + 1);
            if (hexGrid.getByCubeCoordinate(ccCurrentHex2).isPresent()) {
                currentHex2 = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex2).get();
                hexActor1 = new HexagonActor(currentHex2);
                if (hexActor.getHexColor() == hexActor1.getHexColor()) {
                    result += 1;
                    currentHex = currentHex2;
                } else {
                    sameColor = false;
                }
            } else {
                sameColor = false;
            }
        }
        return result;
    }

    public static int CalculateScoreHexToTopRight(HexagonalGrid hexGrid, HexagonActor hexActor) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex = hexActor.getHexagon();
        Hexagon currentHex2;
        CubeCoordinate ccCurrentHex = CubeCoordinate.fromCoordinates(currentHex.getGridX(), currentHex.getGridZ());
        CubeCoordinate ccCurrentHex2;

//        currentHex = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex).get();

        HexagonActor hexActor1;

        while (sameColor == true) {
            hexActor1 = new HexagonActor(currentHex);
            ccCurrentHex2 = CubeCoordinate.fromCoordinates(currentHex.getGridX(), currentHex.getGridZ() + 1);
            if (hexGrid.getByCubeCoordinate(ccCurrentHex2).isPresent()) {


                currentHex2 = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex2).get();
                hexActor1 = new HexagonActor(currentHex2);
                if (hexActor.getHexColor() == hexActor1.getHexColor()) {
                    result += 1;
                    currentHex = currentHex2;
                } else {
                    sameColor = false;
                }
            }
            else
            {
                sameColor = false;

            }
        }
        return result;
    }

    public static int CalculateScoreHexToBotLeft(HexagonalGrid hexGrid, HexagonActor hexActor) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex = hexActor.getHexagon();
        Hexagon currentHex2;
        CubeCoordinate ccCurrentHex = CubeCoordinate.fromCoordinates(currentHex.getGridX(), currentHex.getGridZ());
        CubeCoordinate ccCurrentHex2;

//        currentHex = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex).get();

        HexagonActor hexActor1;


        while (sameColor == true) {
            hexActor1 = new HexagonActor(currentHex);
            ccCurrentHex2 = CubeCoordinate.fromCoordinates(currentHex.getGridX(), currentHex.getGridZ() - 1);
            if (hexGrid.getByCubeCoordinate(ccCurrentHex2).isPresent()) {
                currentHex2 = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex2).get();

                hexActor1 = new HexagonActor(currentHex2);
                if (hexActor.getHexColor() == hexActor1.getHexColor()) {
                    result += 1;
                    currentHex = currentHex2;
                } else {
                    sameColor = false;
                }
            }
            else
            {
                sameColor = false;
            }
        }
        return result;
    }

    public static int CalculateScoreHexToBotRight(HexagonalGrid hexGrid, HexagonActor hexActor) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex = hexActor.getHexagon();
        Hexagon currentHex2;
        CubeCoordinate ccCurrentHex = CubeCoordinate.fromCoordinates(currentHex.getGridX(), currentHex.getGridZ());
        CubeCoordinate ccCurrentHex2;

//        currentHex = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex).get();

        HexagonActor hexActor1;

        while (sameColor == true) {
            hexActor1 = new HexagonActor(currentHex);
            ccCurrentHex2 = CubeCoordinate.fromCoordinates(currentHex.getGridX() + 1, currentHex.getGridZ() - 1);
            if (hexGrid.getByCubeCoordinate(ccCurrentHex2).isPresent()) {
                currentHex2 = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex2).get();
                if (hexActor.getHexColor() == hexActor1.getHexColor()) {
                    result += 1;
                    currentHex = currentHex2;
                } else {
                    sameColor = false;
                }
            } else {
                sameColor = false;
            }
        }
        return result;
    }
}






