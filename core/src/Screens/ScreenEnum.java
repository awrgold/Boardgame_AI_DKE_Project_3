
package Screens;
import Screens.AbstractScreen;
import Screens.GameScreen;
import Screens.MenuScreen;
import com.badlogic.gdx.Game;

public enum ScreenEnum {
    MAIN_MENU {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen();
        }
    },
    GAME {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen();
        }
    };

    public abstract AbstractScreen getScreen(Object... params);
}