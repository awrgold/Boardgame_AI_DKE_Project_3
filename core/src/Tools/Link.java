package Tools;

import GameBoardAssets.HexagonActor;
import Screens.GameScreen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.codetome.hexameter.core.api.Hexagon;
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
