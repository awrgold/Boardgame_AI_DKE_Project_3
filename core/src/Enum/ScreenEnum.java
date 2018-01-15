package Enum;

import com.game.Components.GameLogic.GameManager;
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
//    GAME {
//        public AbstractScreen getScreen(Object... params)
//        {
//            return new GameScreen(new GameIngenious());
//        }
//    };//,
//    EXTRA {
//        public AbstractScreen getScreen(Object... params) {
//            return new GameScreen((Integer) params[0]);
//        }
    //   };

  //  public abstract AbstractScreen getScreen(Object... params);
}
//}