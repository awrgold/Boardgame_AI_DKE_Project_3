package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
//import java.util.concurrent.ThreadLocalRandom;

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




