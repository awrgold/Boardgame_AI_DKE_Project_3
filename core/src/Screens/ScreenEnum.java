package Screens;

import com.badlogic.gdx.Game;

public enum ScreenEnum {
    MAIN_MENU {
        public AbstractScreen getScreen(Object... params) {
            return new MenuScreen();
        }
    },
    GAME {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen();
        }
    };



    public abstract AbstractScreen getScreen(Object... params);
}

