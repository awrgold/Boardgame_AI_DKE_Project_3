package com.game;

import java.util.Random;

// To do: fix the while loop for each calculate function
// To do: Detect and ignore the other placed tile of the piece in each calculate

public class Score {

    Player player;
    private int[] PlayerScore = new int[6];
//    int[0] = blue
//    int[1] = green
//    int[2] = orange
//    int[3] = purple
//    int[4] = red
//    int[5] = yellow

    public Score CreateNewScore(Player player) {
        Score newScore = null;
        newScore.PlayerScore = new int[6];
        newScore.player = player;
        return newScore;
    }

    public int[] updateRandomScore(Player player)
    {
        Random rand = new Random();
        int[] randomScore = getPlayerScore(player);
        int i = rand.nextInt(6);
        int j = rand.nextInt(6) + 1;
        randomScore[i] = randomScore[i] + j;

        return randomScore;
    }

    public void setPlayerScore(int[] playerScore) {
        PlayerScore = playerScore;
    }

    public int[] getPlayerScore(Player player) {
        return PlayerScore;
    }


    public void updateScore(Player player, Hexagon hex) {
        int result = 0;
        int[] playerScore = getPlayerScore(player);

        //Calculate color combination from hex to left:
        result = result + CalculateScoreHexToLeft(hex);
        //Calculate color combination from hex to right
        result = result + CalculateScoreHexToRight(hex);
        //Calculate color combination from hex to bottom left
        result = result + CalculateScoreHexToBotLeft(hex);
        //Calculate color combination from hex to bottom right
        result = result + CalculateScoreHexToBotRight(hex);
        //Calculate color combination from hex to top left
        result = result + CalculateScoreHexToTopLeft(hex);
        //Calculate color combination from hex to top right
        result = result + CalculateScoreHexToTopRight(hex);


        //Update score of designated tile sort:
        String color = hex.getColor();
        if (color == "blue") playerScore[0] = playerScore[0] + result;
        if (color == "green") playerScore[1] = playerScore[1] + result;
        if (color == "orange") playerScore[2] = playerScore[2] + result;
        if (color == "purple") playerScore[3] = playerScore[3] + result;
        if (color == "red") playerScore[4] = playerScore[4] + result;
        if (color == "yellow") playerScore[5] = playerScore[5] + result;
    }

    public int CalculateScoreHexToLeft(Hexagon hex) {
        int result = 0;
        boolean sameColor = true;

        Hexagon currentHex = hex;
        Hexagon currentHex2;

        while (currentHex.getX() >= 0 && sameColor == true) {
            currentHex2 = Board.getHex(currentHex.getX() - 1, currentHex.getY(), currentHex.getZ() + 1);
            if (currentHex.getColor() == currentHex2.getColor()) {
                result += 1;
                currentHex = currentHex2;
            } else {
                sameColor = false;
            }
        }
        return result;
    }

    public int CalculateScoreHexToRight(Hexagon hex) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex = hex;
        Hexagon currentHex2;

        while (currentHex.getX() >= 0 && sameColor == true) {
            currentHex2 = Board.getHex(currentHex.getX() + 1, currentHex.getY(), currentHex.getZ() + 1);
            if (currentHex.getColor() == currentHex2.getColor()) {
                result += 1;
                currentHex = currentHex2;
            } else {
                sameColor = false;
            }
        }
        return result;
    }

    public int CalculateScoreHexToTopLeft(Hexagon hex) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex = hex;
        Hexagon currentHex2;

        while (currentHex.getX() >= 0 && sameColor == true) {
            currentHex2 = Board.getHex(currentHex.getX(), currentHex.getY() - 1, currentHex.getZ() + 1);
            if (currentHex.getColor() == currentHex2.getColor()) {
                result += 1;
                currentHex = currentHex2;
            } else {
                sameColor = false;
            }
        }
        return result;
    }

    public int CalculateScoreHexToTopRight(Hexagon hex) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex = hex;
        Hexagon currentHex2;

        while (currentHex.getX() >= 0 && sameColor == true) {
            currentHex2 = Board.getHex(currentHex.getX() + 1, currentHex.getY() - 1, currentHex.getZ() + 2);
            if (currentHex.getColor() == currentHex2.getColor()) {
                result += 1;
                currentHex = currentHex2;
            } else {
                sameColor = false;
            }
        }
        return result;
    }

    public int CalculateScoreHexToBotLeft(Hexagon hex) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex = hex;
        Hexagon currentHex2;

        while (currentHex.getX() >= 0 && sameColor == true) {
            currentHex2 = Board.getHex(currentHex.getX() - 1, currentHex.getY() + 1, currentHex.getZ() + 2);
            if (currentHex.getColor() == currentHex2.getColor()) {
                result += 1;
                currentHex = currentHex2;
            } else {
                sameColor = false;
            }
        }
        return result;
    }

    public int CalculateScoreHexToBotRight(Hexagon hex) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex = hex;
        Hexagon currentHex2;

        while (currentHex.getX() >= 0 && sameColor == true) {
            currentHex2 = Board.getHex(currentHex.getX(),currentHex.getY() + 1, currentHex.getZ() + 1);
            if (currentHex.getColor() == currentHex2.getColor()) {
                result += 1;
                currentHex = currentHex2;
            } else {
                sameColor = false;
            }
        }
        return result;
    }
}
