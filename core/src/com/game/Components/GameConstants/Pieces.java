package com.game.Components.GameConstants;


import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import static com.game.Components.GameConstants.Constants.*;

public class Pieces {

    static String[] BB = {"B", "B"};
    static String[] BO = {"B", "O"};
    static String[] BP = {"B", "P"};
    static String[] BR = {"B", "R"};
    static String[] BV = {"B", "V"};
    static String[] BY = {"B", "Y"};
    static String[] OO = {"O", "O"};
    static String[] OV = {"O", "V"};
    static String[] PO = {"P", "O"};
    static String[] PP = {"P", "P"};
    static String[] PV = {"P", "V"};
    static String[] RR = {"R", "R"};
    static String[] RO = {"R", "O"};
    static String[] RP = {"R", "P"};
    static String[] RV = {"R", "V"};
    static String[] RY = {"R", "Y"};
    static String[] VV = {"V", "V"};
    static String[] YP = {"Y", "P"};
    static String[] YO = {"Y", "O"};
    static String[] YV = {"Y", "V"};
    static String[] YY = {"Y", "Y"};

    //Creates the "bag" containing all the different pieces
    public static ArrayList<String[]> createBagPieces() {
        ArrayList<String[]> allPieces = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            allPieces.add(BB);
            allPieces.add(YY);
            allPieces.add(OO);
            allPieces.add(PP);
            allPieces.add(VV);
            allPieces.add(RR);
        }
        for(int i = 0; i < 6; i++) {
            allPieces.add(BR);
            allPieces.add(BY);
            allPieces.add(BP);
            allPieces.add(BO);
            allPieces.add(BV);
            allPieces.add(RY);
            allPieces.add(RP);
            allPieces.add(RO);
            allPieces.add(RV);
            allPieces.add(YP);
            allPieces.add(YO);
            allPieces.add(YV);
            allPieces.add(PO);
            allPieces.add(PV);
            allPieces.add(OV);
        }
        return allPieces;
    }

}
