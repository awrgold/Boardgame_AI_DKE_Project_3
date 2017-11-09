package com.game;

import Screens.MenuScreen;
import Screens.ScreenEnum;
import Systems.AbstractSystem;
import Systems.ScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.codetome.hexameter.core.api.HexagonalGrid;

/**
 * Root of the game which holds the screens and delegates the rendering/updating to the currently active screen.
 */
public class GameIngenious extends Game {

    public SpriteBatch batch;
    private Viewport screenPort;

    public void create() {
        batch = new SpriteBatch();
        // this.setScreen( new MenuScreen(this));
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.GAME);
    }






}