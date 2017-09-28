package com.game;

public class Board {

    public static Hexagon[][][] board;

    public Board(){}

    public static void place(Tile tile, int x, int y, int z){
        /*
        Need a method to get the coordinates of the mouse on the game board,
        and then pass them to the hexagon so it knows where each hex is sitting.
        (??) Will this conflict
         */
    }

    public static Hexagon getHex(int x, int y, int z){
        return board[x][y][z];
    }


}
