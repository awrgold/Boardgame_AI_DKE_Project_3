package Enum;

import GameLogic.AIStrategy;
import Interfaces.AbstractScreen;
import Interfaces.Strategy;
import Screens.GameScreen;
import Screens.MenuScreen;
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
            return new GameScreen(new GameIngenious(),new AIStrategy());
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