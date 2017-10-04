package com.game;

import Screens.Gamescreen;
import Screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameIngenious extends Game
{

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    public SpriteBatch batch;
    Texture img;


    @Override
    public void create () {
        batch = new SpriteBatch();
        this.setScreen( new MenuScreen(this));
    }

    @Override
    public void render () {
        super.render();

    }


}


