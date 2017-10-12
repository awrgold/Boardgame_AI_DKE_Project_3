package com.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
public class Pieces {

    static Sprite blueSprite = new Sprite(new Texture(Gdx.files.internal("colours/blue.png")));
    static Sprite yellowSprite = new Sprite(new Texture(Gdx.files.internal("colours/yellow.png")));
    static Sprite orangeSprite = new Sprite(new Texture(Gdx.files.internal("colours/orange.png")));
    static Sprite purpleSprite = new Sprite(new Texture(Gdx.files.internal("colours/purple.png")));
    static Sprite violetSprite = new Sprite(new Texture(Gdx.files.internal("colours/violet.png")));
    static Sprite redSprite = new Sprite(new Texture(Gdx.files.internal("colours/red.png")));

    static Sprite[] BB = {blueSprite, blueSprite};
    static Sprite[] YY = {yellowSprite, yellowSprite};
    static Sprite[] OO = {orangeSprite, orangeSprite};
    static Sprite[] PP = {purpleSprite, purpleSprite};
    static Sprite[] VV = {violetSprite, violetSprite};
    static Sprite[] RR = {redSprite, redSprite};
    static Sprite[] BR = {blueSprite, redSprite};
    static Sprite[] BY = {blueSprite, yellowSprite};
    static Sprite[] BP = {blueSprite, purpleSprite};
    static Sprite[] BO = {blueSprite, orangeSprite};
    static Sprite[] BV = {blueSprite, violetSprite};
    static Sprite[] RY = {redSprite, yellowSprite};
    static Sprite[] RP = {redSprite, purpleSprite};
    static Sprite[] RO = {redSprite, orangeSprite};
    static Sprite[] RV = {redSprite, violetSprite};
    static Sprite[] YP = {yellowSprite, purpleSprite};
    static Sprite[] YO = {yellowSprite, orangeSprite};
    static Sprite[] YV = {yellowSprite, violetSprite};
    static Sprite[] PO = {purpleSprite, orangeSprite};
    static Sprite[] PV = {purpleSprite, violetSprite};
    static Sprite[] OV = {orangeSprite, violetSprite};



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






    //Distributes 6 pieces to a player at the start of the game
    public static ArrayList<Sprite[]> distributePieces(ArrayList<Sprite[]> allPieces) {
        ArrayList<Sprite[]> playerPieces = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            int randomNumber = ThreadLocalRandom.current().nextInt(0, allPieces.size());
            Sprite[] piece = allPieces.get(randomNumber);
            playerPieces.add(piece);
            allPieces.remove(piece);
        }
        return playerPieces;
    }
    //Fills the amount of pieces of a player back up to 6
    public static ArrayList<Sprite[]> takePiece(ArrayList<Sprite[]> allPieces, ArrayList<Sprite[]> playerPieces){
        if(playerPieces.size() < 6){
            for(int i = playerPieces.size(); i < 6; i++ ){
                int randomNumber = ThreadLocalRandom.current().nextInt(0, allPieces.size());
                Sprite[] piece = allPieces.get(randomNumber);
                playerPieces.add(piece);
                allPieces.remove(piece);
            }
        }
        return playerPieces;
    }
    //Discards all the pieces in the players hand and distributes new ones to the player
    public static ArrayList<Sprite[]> discardPieces(ArrayList<Sprite[]> allPieces, ArrayList<Sprite[]> playerPieces){
        for(int i = 0; i < 6; i++){
            Sprite[] piece = allPieces.get(i);
            allPieces.add(piece);
            playerPieces.remove(piece);
        }
        playerPieces = distributePieces(allPieces);
        return playerPieces;
    }
}
