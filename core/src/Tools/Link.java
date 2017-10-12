package Tools;

import Screens.GameScreen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

public class Link extends DefaultSatelliteData {

    public Actor actor;

    public Link(Actor actor) {
        this.actor = actor;
    }
}
