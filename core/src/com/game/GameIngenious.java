package com.game;

import Screens.GameScreen;
import Screens.ScreenEnum;
import Screens.ScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

/**
 * Root of the game which holds the screens and delegates the rendering/updating to the currently active screen.
 */
public class GameIngenious extends Game {

    public Viewport screenPort;
    public SpriteBatch batch;
    
    public void create() {

        batch = new SpriteBatch();
    	
        Screens.ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.GAME );
                
    }

}
