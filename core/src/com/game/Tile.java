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
    private Hexagon selected;

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

    //select one of the two hexagons of the tile we want to place
    public void select(Hexagon a){
        if (a == this.one) {
            selected = one;
        } else if (a == this.two) {
            selected = two;
        } else {
            System.out.println("Select one of the two");
        }
    }



}