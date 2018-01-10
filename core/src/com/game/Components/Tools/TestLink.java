package com.game.Components.Tools;

import com.game.Components.GameConstants.Color;
import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

public class TestLink extends DefaultSatelliteData {

    public HexagonActor actor;
    public TestHexagonActor testActor;
    public Color c;

    public String color;

    public TestLink(HexagonActor actor) {

        this.actor = actor;
        this.color = actor.getHexColor();
    }

    public TestLink(TestHexagonActor testActor) {
        this.testActor = testActor;
        this.c = testActor.getHexColor();
    }

    public HexagonActor getActor() {
        return actor;
    }

    public String getColor() {
        return color;
    }
}
