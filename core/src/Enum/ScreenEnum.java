package Enum;

import com.game.Screens.AbstractScreen;
import com.game.Screens.GameScreen;
import com.game.Screens.MenuScreen;
import com.game.GameIngenious;

public enum ScreenEnum {
    MAIN_MENU {
        public AbstractScreen getScreen(Object... params)
        {
            return new MenuScreen();
        }
    },
    GAME {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen(new GameIngenious());
        }
    };

    public abstract AbstractScreen getScreen(Object... params);
}
