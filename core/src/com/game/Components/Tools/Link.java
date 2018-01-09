package com.game.Components.Tools;

import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

public class Link extends DefaultSatelliteData {

    private String color;

    public Link(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
