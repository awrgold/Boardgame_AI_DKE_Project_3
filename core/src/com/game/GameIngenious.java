package com.game;

import Enum.ScreenEnum;
import com.game.Components.GameLogic.GameManager;
import com.game.Screens.ScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Root of the game which holds the screens and delegates the rendering/updating to the currently active screen.
 */
public class GameIngenious extends Game {

  //  public SpriteBatch batch;
    private Viewport screenPort;
    private GameManager manager;

    @Override
    public void create() {
       //batch = new SpriteBatch();
        manager = new GameManager();
       showGameScreen();
    }
    /*
    show specific screen directly
     */

    public void showGameScreen(){
    ScreenManager.getInstance().initialize(this);
    ScreenManager.getInstance().showScreen( ScreenEnum.GAME);
    }
    public void ShowMenuScreen(){
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU);
    }
 
}