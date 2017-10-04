package com.game;

import java.util.ArrayList;
import java.util.List;
import org.codetome.hexameter.core.api.HexagonalGridLayout;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;

import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.HEXAGONAL;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;
import static org.codetome.hexameter.core.api.HexagonOrientation.FLAT_TOP;

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

    public static Hex[][] createMap(){
        Hex[][] hexMap = new Hex[11][11];
        int y = -5;
        for (int i = 0; i < 11; i++){
            int x = -5;
            for(int j = 0; j < 11; j++){
                int z = -1 * (x + y);
                if(Math.abs(z) <= 5) {
                    hexMap[i][j] = new Hex(x, y, z);
                }
                x++;
            }
            y += 1;
        }
        return hexMap;
    }
}


