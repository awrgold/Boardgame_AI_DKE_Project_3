package GameScoreAssets;

import com.game.Player;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;

import java.util.Random;

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


    public void updateScore(Player player, Hexagon hex, HexagonalGrid hexGrid) {
        int result = 0;
        int[] playerScore = getPlayerScore(player);

        //Calculate color combination from hex to left:
        result = result + CalculateScoreHexToLeft(hexGrid, hex);
        //Calculate color combination from hex to right
        result = result + CalculateScoreHexToRight(hexGrid, hex);
        //Calculate color combination from hex to bottom left
        result = result + CalculateScoreHexToBotLeft(hexGrid, hex);
        //Calculate color combination from hex to bottom right
        result = result + CalculateScoreHexToBotRight(hexGrid, hex);
        //Calculate color combination from hex to top left
        result = result + CalculateScoreHexToTopLeft(hexGrid, hex);
        //Calculate color combination from hex to top right
        result = result + CalculateScoreHexToTopRight(hexGrid, hex);


        //Update score of designated tile sort:
        /*String color = hex.getColor();
        if (color == "blue") playerScore[0] = playerScore[0] + result;
        if (color == "green") playerScore[1] = playerScore[1] + result;
        if (color == "orange") playerScore[2] = playerScore[2] + result;
        if (color == "purple") playerScore[3] = playerScore[3] + result;
        if (color == "red") playerScore[4] = playerScore[4] + result;
        if (color == "yellow") playerScore[5] = playerScore[5] + result;*/
    }

    public int CalculateScoreHexToLeft(HexagonalGrid hexGrid, Hexagon hex) {
        int result = 0;
        boolean sameColor = true;

        Hexagon currentHex;
        Hexagon currentHex2;
        CubeCoordinate ccCurrentHex = CubeCoordinate.fromCoordinates(hex.getGridX(), hex.getGridZ());
        CubeCoordinate ccCurrentHex2;

        currentHex = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex).get();

        while (currentHex.getGridX() > -2 && sameColor == true) {
            ccCurrentHex2 = CubeCoordinate.fromCoordinates(currentHex.getGridX() - 1, currentHex.getGridZ());
            currentHex2 = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex2).get();

            /*if (currentHex.getColor() == currentHex2.getColor()) {
                result += 1;
                currentHex = currentHex2;
            } else {
                sameColor = false;
            }*/
        }
        return result;
    }

    public int CalculateScoreHexToRight(HexagonalGrid hexGrid, Hexagon hex) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex;
        Hexagon currentHex2;
        CubeCoordinate ccCurrentHex = CubeCoordinate.fromCoordinates(hex.getGridX(), hex.getGridZ());
        CubeCoordinate ccCurrentHex2;
        currentHex = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex).get();

        while (currentHex.getGridX() < 8 && sameColor == true) {
            ccCurrentHex2 = CubeCoordinate.fromCoordinates(currentHex.getGridX() + 1, currentHex.getGridZ());
            currentHex2 = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex2).get();

            /*if (currentHex.getColor() == currentHex2.getColor()) {
                result += 1;
                currentHex = currentHex2;
            } else {
                sameColor = false;
            }*/
        }
        return result;
    }

    public int CalculateScoreHexToTopLeft(HexagonalGrid hexGrid, Hexagon hex) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex;
        Hexagon currentHex2;
        CubeCoordinate ccCurrentHex = CubeCoordinate.fromCoordinates(hex.getGridX(), hex.getGridZ());
        CubeCoordinate ccCurrentHex2;

        currentHex = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex).get();

        while (currentHex.getGridX() > 0 && currentHex.getGridZ() < 11 && sameColor == true) {
            ccCurrentHex2 = CubeCoordinate.fromCoordinates(currentHex.getGridX() - 1, currentHex.getGridZ() + 1);
            currentHex2 = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex2).get();
            /*if (currentHex.getColor() == currentHex2.getColor()) {
                result += 1;
                currentHex = currentHex2;
            } else {
                sameColor = false;
            }*/
        }
        return result;
    }

    public int CalculateScoreHexToTopRight(HexagonalGrid hexGrid, Hexagon hex) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex;
        Hexagon currentHex2;
        CubeCoordinate ccCurrentHex = CubeCoordinate.fromCoordinates(hex.getGridX(), hex.getGridZ());
        CubeCoordinate ccCurrentHex2;

        currentHex = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex).get();

        while (sameColor == true) {
            ccCurrentHex2 = CubeCoordinate.fromCoordinates(currentHex.getGridX(), currentHex.getGridZ() + 1);
            currentHex2 = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex2).get();
           /* if (currentHex.getColor() == currentHex2.getColor()) {
                result += 1;
                currentHex = currentHex2;
            } else {
                sameColor = false;
            }*/
        }
        return result;
    }

    public int CalculateScoreHexToBotLeft(HexagonalGrid hexGrid, Hexagon hex) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex;
        Hexagon currentHex2;
        CubeCoordinate ccCurrentHex = CubeCoordinate.fromCoordinates(hex.getGridX(), hex.getGridZ());
        CubeCoordinate ccCurrentHex2;

        currentHex = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex).get();

        while (sameColor == true) {
            ccCurrentHex2 = CubeCoordinate.fromCoordinates(currentHex.getGridX(),  currentHex.getGridZ() - 1);
            currentHex2 = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex2).get();
            /*if (currentHex.getColor() == currentHex2.getColor()) {
                result += 1;
                currentHex = currentHex2;
            } else {
                sameColor = false;
            }*/
        }
        return result;
    }

    public int CalculateScoreHexToBotRight(HexagonalGrid hexGrid, Hexagon hex) {
        int result = 0;

        boolean sameColor = true;

        Hexagon currentHex;
        Hexagon currentHex2;
        CubeCoordinate ccCurrentHex = CubeCoordinate.fromCoordinates(hex.getGridX(), hex.getGridZ());
        CubeCoordinate ccCurrentHex2;

        currentHex = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex).get();

        while (sameColor == true) {
            ccCurrentHex2 = CubeCoordinate.fromCoordinates(currentHex.getGridX() + 1, currentHex.getGridZ() - 1);
            currentHex2 = (Hexagon) hexGrid.getByCubeCoordinate(ccCurrentHex2).get();
            /*if (currentHex.getColor() == currentHex2.getColor()) {
                result += 1;
                currentHex = currentHex2;
            } else {
                sameColor = false;
            }*/
        }
        return result;
    }
}
