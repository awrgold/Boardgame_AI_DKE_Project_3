package com.game.Components.Tools;

import com.game.Components.GameAssets.HexagonActor;
import com.game.Components.GameConstants.Color;
import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

public class Link extends DefaultSatelliteData {

    public HexagonActor actor;

    public Color color;

    public Link(HexagonActor actor) {

        this.actor = actor;
        this.color = actor.getHexColor();
    }

    public HexagonActor getActor() {
        return actor;
    }

    public String getColorString() {
        return color.toString();
    }


}
