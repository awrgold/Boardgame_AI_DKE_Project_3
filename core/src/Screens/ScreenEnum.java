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
    };//,
//    EXTRA {
//        public AbstractScreen getScreen(Object... params) {
//            return new GameScreen((Integer) params[0]);
//        }
 //   };

    public abstract AbstractScreen getScreen(Object... params);
}
//}