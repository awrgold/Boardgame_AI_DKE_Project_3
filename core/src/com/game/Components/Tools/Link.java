package com.game.Components.Tools;

import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

public class Link extends DefaultSatelliteData {

    public HexagonActor actor;

    public String color;

    public Link(HexagonActor actor) {

        this.actor = actor;
        this.color = actor.getHexColor();
    }

    public HexagonActor getActor() {
        return actor;
    }

    public String getColor() {
        return color;
    }
}
