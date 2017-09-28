package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Tile {

    private Hexagon one;
    private Hexagon two;

    public Tile(Hexagon one, Hexagon two){
        this.one = one;
        this.two = two;
    }

    public Hexagon getOne(){
        return one;
    }

    public Hexagon getTwo(){
        return two;
    }

    public int[] getCoordOne(){
        return one.getCoordinates();
    }

    public int[] getCoordTwo(){
        return two.getCoordinates();
    }

    public String getColor(Hexagon hex){
        return hex.getColor();
    }
}

class GameMain extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;

    @Override
    public void create () {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        ArrayList<String> allPieces = Pieces.createBagPieces();
        System.out.println(allPieces);
        ArrayList<String> player1 = Pieces.distributePieces(allPieces);
        System.out.println(player1);
        System.out.println(allPieces);
        ArrayList<String> player2 = Pieces.distributePieces(allPieces);
        System.out.println(player2);
        System.out.println(allPieces);
        player2 = Pieces.discardPieces(allPieces, player2);
        System.out.println(player2);
        System.out.println(allPieces);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}