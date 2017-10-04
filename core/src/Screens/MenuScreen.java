package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.game.GameIngenious;

public class MenuScreen implements Screen
{
    GameIngenious game;


    Texture exitButton;
    Texture playButton;
    Texture tutorialButton;
    Texture exitInactive;
    Texture playInactive;
    Texture tutorialInactive;


    private static final int PLAY_WDITH = 300;
    private static final int PLAY_HEIGHT = 50;

    private static final int EXIT_WDITH = 300;
    private static final int EXIT_HEIGHT = 50;

    private static final int TUTORIAL_WDITH = 300;
    private static final int TUTORIAL_HEIGHT = 50;

    private static final int EXIT = 350;
    private static final int PLAY = 500;
    private static final int TUTORIAL = 200;


    public MenuScreen(GameIngenious game){
        this.game = game;


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
        game.batch.begin();  //start to draw an image

        int x = GameIngenious.WIDTH/2 - PLAY_WDITH/2;
        if(Gdx.input.getX() < x + PLAY_WDITH && Gdx.input.getX() > x && GameIngenious.HEIGHT
                - Gdx.input.getY() < PLAY + PLAY_HEIGHT && GameIngenious.HEIGHT - Gdx.input.getY() > PLAY)
        {
            game.batch.draw(playButton,x,PLAY,PLAY_WDITH, PLAY_HEIGHT);

            if(Gdx.input.isTouched())
            {
                game.setScreen(new Gamescreen(game));
            }

        }
        else
        {
            game.batch.draw(playInactive,x,PLAY,PLAY_WDITH, PLAY_HEIGHT);

        }

         x = GameIngenious.WIDTH/2 - EXIT_WDITH/2;
        if(Gdx.input.getX() < x + EXIT_WDITH && Gdx.input.getX() > x && GameIngenious.HEIGHT
                - Gdx.input.getY() < EXIT + EXIT_HEIGHT && GameIngenious.HEIGHT - Gdx.input.getY() > EXIT)
        {
            game.batch.draw(exitButton,x,EXIT,EXIT_WDITH, EXIT_HEIGHT);
            if(Gdx.input.isTouched())
            {
                Gdx.app.exit();
            }

        } else
        {
            game.batch.draw(exitInactive,x,EXIT,EXIT_WDITH, EXIT_HEIGHT);

        }

         x = GameIngenious.WIDTH/2 - TUTORIAL_WDITH/2;
        if(Gdx.input.getX() < x + TUTORIAL_WDITH && Gdx.input.getX() > x && GameIngenious.HEIGHT
                - Gdx.input.getY() < TUTORIAL + TUTORIAL_HEIGHT && GameIngenious.HEIGHT - Gdx.input.getY() > TUTORIAL)
        {
            game.batch.draw(tutorialButton,x,TUTORIAL,TUTORIAL_WDITH, TUTORIAL_HEIGHT);

        }else
        {
            game.batch.draw(tutorialInactive,x,TUTORIAL,TUTORIAL_WDITH, TUTORIAL_HEIGHT);

        }

        game.batch.end();
    }

    public void resize(int width, int height){}
    public void pause() { }
    public void resume() { }
    public void hide() { }
    public void dispose() { }









}
