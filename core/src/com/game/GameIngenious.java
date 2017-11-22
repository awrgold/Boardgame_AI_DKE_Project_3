package com.game;

import Enum.ScreenEnum;
import GameLogic.AIStrategy;
import Interfaces.Strategy;
import Screens.GameScreen;
import Systems.ScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Root of the game which holds the screens and delegates the rendering/updating to the currently active screen.
 */
public class GameIngenious extends Game {

    public SpriteBatch batch;
    private Viewport screenPort;

    public GameIngenious() {
    }

    public void create() {
        batch = new SpriteBatch();
       showGameScreen();
    }
    public void showGameScreen(){
    ScreenManager.getInstance().initialize(this);
    ScreenManager.getInstance().showScreen( ScreenEnum.GAME);
    }
    public void ShowMenuScreen(){
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU);
    }
 
}