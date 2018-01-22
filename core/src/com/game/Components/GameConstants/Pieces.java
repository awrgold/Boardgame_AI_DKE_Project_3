package com.game.Components.GameConstants;


import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

import static com.game.Components.GameConstants.Color.*;
import static com.game.Components.GameConstants.Constants.*;

public class Pieces {

    static Color[] BB = {BLUE, BLUE};
    static Color[] BO = {BLUE, ORANGE};
    static Color[] BP = {BLUE, PURPLE};
    static Color[] BR = {BLUE, RED};
    static Color[] BV = {BLUE, VIOLET};
    static Color[] BY = {BLUE, YELLOW};
    static Color[] OO = {ORANGE, ORANGE};
    static Color[] OV = {ORANGE, VIOLET};
    static Color[] PO = {PURPLE, ORANGE};
    static Color[] PP = {PURPLE, PURPLE};
    static Color[] PV = {PURPLE, VIOLET};
    static Color[] RR = {RED, RED};
    static Color[] RO = {RED, ORANGE};
    static Color[] RP = {RED, PURPLE};
    static Color[] RV = {RED, VIOLET};
    static Color[] RY = {RED, YELLOW};
    static Color[] VV = {VIOLET, VIOLET};
    static Color[] YP = {YELLOW, PURPLE};
    static Color[] YO = {YELLOW, ORANGE};
    static Color[] YV = {YELLOW, VIOLET};
    static Color[] YY = {YELLOW, YELLOW};

    //Creates the "bag" containing all the different pieces
    public static ArrayList<Color[]> createBagPieces() {
        ArrayList<Color[]> allPieces = new ArrayList<>();
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