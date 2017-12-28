package com.game.Screens;

import com.game.Components.GameConstants.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MenuScreen extends AbstractScreen {




    Texture exitButton;
    Texture playButton;
    Texture tutorialButton;
    Texture exitInactive;
    Texture playInactive;
    Texture tutorialInactive;


    // Size of the buttons
    private static final int PLAY_WIDTH = 300;
    private static final int PLAY_HEIGHT = 50;

    private static final int EXIT_WIDTH = 300;
    private static final int EXIT_HEIGHT = 50;

    private static final int TUTORIAL_WIDTH = 300;
    private static final int TUTORIAL_HEIGHT = 50;

    private static final int EXIT = 350;
    private static final int PLAY = 500;
    private static final int TUTORIAL = 200;
    private SpriteBatch batch;

    public MenuScreen(){

        batch = new SpriteBatch();

    }

    public void show()
    {
        playButton = new Texture("playButton.png");
        exitButton = new Texture("exitButton.png");
        tutorialButton = new Texture("tutorialButton.png");

        tutorialInactive = new Texture("tutorialInactive.png");
        exitInactive = new Texture("exitInactive.png");
        playInactive =  new Texture("playInactive.png");
    }

    public void render(float delta)
    {

        Gdx.gl.glClearColor(96/255f,96/255f,96/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();  //start to draw an image

        int x = Gdx.graphics.getWidth()/2 - PLAY_WIDTH/2;
        if(Gdx.input.getX() < x + PLAY_WIDTH && Gdx.input.getX() > x && Constants.getBoardHeight()
                - Gdx.input.getY() < PLAY + PLAY_HEIGHT && Constants.getBoardHeight() - Gdx.input.getY() > PLAY)
        {
            batch.draw(playButton,x,PLAY,PLAY_WIDTH, PLAY_HEIGHT);

                /*if(Gdx.input.isTouched())
                {
                    com.game.Screens.ScreenManager.getInstance().initialize(this);
                    ScreenManager.getInstance().showScreen( ScreenEnum.GAME );
                }*/

        }
        else
        {
            batch.draw(playInactive,x,PLAY,PLAY_WIDTH, PLAY_HEIGHT);

        }

        x = Gdx.graphics.getWidth()/2 - EXIT_WIDTH/2;
        if(Gdx.input.getX() < x + EXIT_WIDTH && Gdx.input.getX() > x && Gdx.graphics.getHeight()
                - Gdx.input.getY() < EXIT + EXIT_HEIGHT && Constants.getBoardHeight() - Gdx.input.getY() > EXIT)
        {
            //   game.batch.draw(exitButton,x,EXIT,EXIT_WIDTH, EXIT_HEIGHT);
            if(Gdx.input.isTouched())
            {
                Gdx.app.exit();
            }

        } else
        {
            batch.draw(exitInactive,x,EXIT,EXIT_WIDTH, EXIT_HEIGHT);

        }

        x = Constants.getBoardHeight() /2 - TUTORIAL_WIDTH/2;
        if(Gdx.input.getX() < x + TUTORIAL_WIDTH && Gdx.input.getX() > x && Constants.getBoardHeight()
                - Gdx.input.getY() < TUTORIAL + TUTORIAL_HEIGHT && Constants.getBoardHeight() - Gdx.input.getY() > TUTORIAL)
        {
            batch.draw(tutorialButton,x,TUTORIAL,TUTORIAL_WIDTH, TUTORIAL_HEIGHT);

        }else
        {
            batch.draw(tutorialInactive,x,TUTORIAL,TUTORIAL_WIDTH, TUTORIAL_HEIGHT);

        }

        batch.end();
    }

    public void resize(int width, int height){}
    public void pause() { }
    public void resume() { }
    public void hide() { }
    public void dispose() { }

    @Override
    public void buildStage() {

    }
}
