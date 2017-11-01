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

    private Viewport screenPort;
    public SpriteBatch batch;
    GameIngenious game;
    public int nOfPlayer;

    public Player[] players;

    public Player gamingPlayer;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
/* @Michael Add Game Loop,Game State Check Methods, Player Selection, Score calculations,.... (call and generate user input/Ambiguous-let s see what feels best)
* */

    public void create() {

        batch = new SpriteBatch();
       // this.setScreen( new MenuScreen(this));
       ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.GAME );

    }

}
