package com.game;

import Screens.MenuScreen;
import Screens.ScreenEnum;
import Systems.AbstractSystem;
import Systems.ScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Root of the game which holds the screens and delegates the rendering/updating to the currently active screen.
 */
public class GameIngenious extends Game {

    GameIngenious game;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public int nOfPlayer;

    public Player[] players;
    public Player gamingPlayer;


    public SpriteBatch batch;

    private Viewport screenPort;


    public void create() {

        //batch = new SpriteBatch();
        // this.setScreen( new MenuScreen(this));
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.GAME);

    }

}