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


public class GameIngenious extends Game {
    private SpriteBatch batch;
    public Viewport screenPort;
    private Camera camera;
    private Screen mainScreen;
    //public SAManager samanager;

    public void create() {
        Screens.ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.GAME );
        //mainScreen = new MenuScreen(this);
        //camera = new PerspectiveCamera();
//        screenPort = new ScreenViewport();
//        setScreen(mainScreen);
////        samanager = new SAManager();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false);
        // setScreen(new GameScreen(this));
       // float startTime = TimeUtils.millis();
        Board.createMap();
    }

    public void render() {
        super.render();
    }
    public void dispose(){
        super.dispose();
       // float endTime = TimeUtils.millis();

    }

}
