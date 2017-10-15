package com.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class Player {

    private boolean isAI;
    private String name;

    int playerNo;
    ArrayList<Sprite[]> gamePieces = new ArrayList<Sprite[]>();

    public Player(int playerNo, ArrayList<Sprite[]> gamePieces){
        this.playerNo = playerNo;
        this.gamePieces = gamePieces;
    }


    public boolean isAI() {
        return isAI;
    }

    public ArrayList<Sprite[]> getGamePieces(){
        return this.gamePieces;
    }
}
