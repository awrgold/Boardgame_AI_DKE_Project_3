package com.game;

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
