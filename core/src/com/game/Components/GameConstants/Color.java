package com.game.Components.GameConstants;

public class Color {

    public static final Color BLUE = new Color();
    public static final Color YELLOW = new Color();
    public static final Color RED = new Color();
    public static final Color PURPLE = new Color();
    public static final Color VIOLET = new Color();
    public static final Color ORANGE = new Color();
    public static final Color EMPTY = new Color();
    public static final Color DISABLED = new Color();

    private static Color[] colors = {BLUE, YELLOW, RED, PURPLE, VIOLET, ORANGE, EMPTY, DISABLED};

    public static Color getColorID(int id) {
        return colors[id];
    }

    public Color getColor(){
        return this;
    }

    public String toString() {
        String output = new String();
        if(this==RED) output = "RED";
        else if(this==VIOLET) output = "VIOLET";
        else if(this==BLUE) output = "BLUE";
        else if(this==PURPLE) output = "PURPLE";
        else if(this==YELLOW) output = "YELLOW";
        else if(this==ORANGE) output = "ORANGE";
        else if(this==EMPTY) output = "EMPTY";
        else if(this==DISABLED) output = "DISABLED";
        return output;
    }

}
