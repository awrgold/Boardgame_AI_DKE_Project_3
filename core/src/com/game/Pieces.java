package com.game;


import GameConstants.Constants;
import Tools.Link;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.HexagonalGridLayout;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static GameConstants.Constants.*;
import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;

public class Pieces {

    static Sprite[] BB = {blueSprite, blueSprite};
    static Sprite[] BO = {blueSprite, orangeSprite};
    static Sprite[] BP = {blueSprite, purpleSprite};
    static Sprite[] BR = {blueSprite, redSprite};
    static Sprite[] BV = {blueSprite, violetSprite};
    static Sprite[] BY = {blueSprite, yellowSprite};
    static Sprite[] OO = {orangeSprite, orangeSprite};
    static Sprite[] OV = {orangeSprite, violetSprite};
    static Sprite[] PO = {purpleSprite, orangeSprite};
    static Sprite[] PP = {purpleSprite, purpleSprite};
    static Sprite[] PV = {purpleSprite, violetSprite};
    static Sprite[] RR = {redSprite, redSprite};
    static Sprite[] RO = {redSprite, orangeSprite};
    static Sprite[] RP = {redSprite, purpleSprite};
    static Sprite[] RV = {redSprite, violetSprite};
    static Sprite[] RY = {redSprite, yellowSprite};
    static Sprite[] VV = {violetSprite, violetSprite};
    static Sprite[] YP = {yellowSprite, purpleSprite};
    static Sprite[] YO = {yellowSprite, orangeSprite};
    static Sprite[] YV = {yellowSprite, violetSprite};
    static Sprite[] YY = {yellowSprite, yellowSprite};

    //Creates the "bag" containing all the different pieces
    public static ArrayList<Sprite[]> createBagPieces() {
        ArrayList<Sprite[]> allPieces = new ArrayList<>();
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
