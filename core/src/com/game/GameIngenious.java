package com.game;

import Screens.ScreenEnum;
import Screens.AbstractScreen;
import Screens.ScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Root of the game which holds the screens and delegates the rendering/updating to the currently active screen.
 */
public class GameIngenious extends Game {

    public Viewport screenPort;
    public SpriteBatch batch;
    GameIngenious game;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;


    public void create() {

        batch = new SpriteBatch();
        /*this.setScreen( new MenuScreen(this));*/
       Screens.ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.GAME );

    }

}
