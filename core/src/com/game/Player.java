package com.game;

import GameBoardAssets.HexagonActor;
import GameConstants.Constants;
import Tools.Link;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;

import java.util.ArrayList;

/*
int[0] = blue
int[1] = yellow
int[2] = orange
int[3] = purple
int[4] = violet
int[5] = red
*/


    private boolean isAI;

    private int[] playerScore = new int[6];
    int playerNo;
    private Tile selectedTile;
    private Tile[] playerPieces = new Tile[6];
    private static Sprite[] PlayerScoreSprite = new Sprite[6];
    private String name;
    private static boolean[] colorIngenious = new boolean[6];
    private Board board;

    private Action move = new Action();



    public Player(int playerNo, ArrayList<Sprite[]> piecesSprites, Board board) {
        this.playerNo = playerNo;
        this.board = board;
        for (int i = 0; i < 6 ; i++){
            this.playerPieces[i] = new Tile(piecesSprites.get(i));
        }
        this.hand = new Hand(playerPieces);
        for (int i = 0; i < 6; i++){
            this.playerScore[i] = 0;
        }
        PlayerScoreSprite[0] = Constants.blueSprite;
        PlayerScoreSprite[1] = Constants.yellowSprite;
        PlayerScoreSprite[2] = Constants.orangeSprite;
        PlayerScoreSprite[3] = Constants.purpleSprite;
        PlayerScoreSprite[4] = Constants.violetSprite;
        PlayerScoreSprite[5] = Constants.redSprite;

    }

    public Hand getHand(){
        return this.hand;
    }

    public void setClicked(){
        HexagonActor one = hand.getSelected().getFirst();
        move.setT1(one);
        Actor two = one.getParent().getChildren().get(Math.abs(one.getHexagon().getGridX() - 1));
        if (two instanceof HexagonActor){
            HexagonActor second = (HexagonActor) two;
            move.setT2(second);
        }

    }



    public void setTileToMove1(HexagonActor t1){
        move.setT1(t1);
    }

    public void setTileToMove2(HexagonActor t2){
        move.setT2(t2);
    }

    public void setHexMove1(Hexagon h1){
        move.setH1(h1);
    }

    public void setHexMove2(Hexagon h2){
        move.setH2(h2);
    }

    public Action getMove() {
        return move;
    }



    public int getColorScore(int color){
        return playerScore[color];
    }

    public void setPlayerPieces(ArrayList<Sprite[]> newPieces) {
        for (int i = 0; i < 6 ; i++){
            playerPieces[i] = new Tile(newPieces.get(i));
        }
    }

    public Tile[] getGamePieces() {
        return this.playerPieces;
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

    public void printScore(){
        System.out.println(scoreToString());
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

    private boolean isAColorPresent(Sprite color){
        for (Tile tile : playerPieces){
            if(tile.getColors()[0] == color || tile.getColors()[1] == color){
                return true;
            }
        }
        return false;

    }

    public boolean isLowestScoreTilePresent(){
        int lowest = 18;
        int lowIndex = -1;
        ArrayList<Integer> indexesOfLowest = new ArrayList<>();
        System.out.println(indexesOfLowest.size());

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

        if (indexesOfLowest.size() == 1 && !isAColorPresent(PlayerScoreSprite[lowIndex])) {
            return false;
        } if (indexesOfLowest.size() > 1 && lowest > 0){
            for (int i : indexesOfLowest){
                if (!isAColorPresent(PlayerScoreSprite[i])){
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
    Possibly set the move for the player
     */

    public void makeMove(){
    selectTile();
    placeTile();
}

    public void selectTile() {
        for (int i = 0; i < 6 ; i++){
           if(playerPieces[i].isSelected()){

           }
        }
    }
    public void placeTile(){

    }
}






