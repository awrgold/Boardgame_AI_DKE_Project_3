package Screens;

import Interfaces.AbstractScreen;
import com.game.GameIngenious;
import com.game.GameManager;

public enum ScreenEnum {
    MAIN_MENU {
        public AbstractScreen getScreen(Object... params) {
            return new MenuScreen();
        }
    },
    GAME {
        public AbstractScreen getScreen(Object... params) {
            return new GameScreen(new GameIngenious());
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