package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.game.GameIngenious;

public class MenuScreen extends AbstractScreen {

        GameIngenious game;


        Texture exitButton;
        Texture playButton;
        Texture tutorialButton;
        Texture exitInactive;
        Texture playInactive;
        Texture tutorialInactive;


        private static final int PLAY_WIDTH = 300;
        private static final int PLAY_HEIGHT = 50;

        private static final int EXIT_WIDTH = 300;
        private static final int EXIT_HEIGHT = 50;

        private static final int TUTORIAL_WIDTH = 300;
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

            int x = GameIngenious.WIDTH/2 - PLAY_WIDTH/2;
            if(Gdx.input.getX() < x + PLAY_WIDTH && Gdx.input.getX() > x && GameIngenious.HEIGHT
                    - Gdx.input.getY() < PLAY + PLAY_HEIGHT && GameIngenious.HEIGHT - Gdx.input.getY() > PLAY)
            {
                game.batch.draw(playButton,x,PLAY,PLAY_WIDTH, PLAY_HEIGHT);

                /*if(Gdx.input.isTouched())
                {
                    Screens.ScreenManager.getInstance().initialize(this);
                    ScreenManager.getInstance().showScreen( ScreenEnum.GAME );
                }*/


            }
            else
            {
                game.batch.draw(playInactive,x,PLAY,PLAY_WIDTH, PLAY_HEIGHT);

public void buildStage(){

    table = new Table();
   // table.setFillParent(true);
    table.setWidth(this.getWidth());
    table.align(Align.center|Align.top);
    table.setPosition(0, Gdx.graphics.getHeight());



    table.padTop(70);


    addActor(bck);
   addActor(table);

}


            }

            x = GameIngenious.WIDTH/2 - EXIT_WIDTH/2;
            if(Gdx.input.getX() < x + EXIT_WIDTH && Gdx.input.getX() > x && GameIngenious.HEIGHT
                    - Gdx.input.getY() < EXIT + EXIT_HEIGHT && GameIngenious.HEIGHT - Gdx.input.getY() > EXIT)
            {
                game.batch.draw(exitButton,x,EXIT,EXIT_WIDTH, EXIT_HEIGHT);
                if(Gdx.input.isTouched())
                {
                    Gdx.app.exit();
                }

            } else
            {
                game.batch.draw(exitInactive,x,EXIT,EXIT_WIDTH, EXIT_HEIGHT);

            }

            x = GameIngenious.WIDTH /2 - TUTORIAL_WIDTH/2;
            if(Gdx.input.getX() < x + TUTORIAL_WIDTH && Gdx.input.getX() > x && GameIngenious.HEIGHT
                    - Gdx.input.getY() < TUTORIAL + TUTORIAL_HEIGHT && GameIngenious.HEIGHT - Gdx.input.getY() > TUTORIAL)
            {
                game.batch.draw(tutorialButton,x,TUTORIAL,TUTORIAL_WIDTH, TUTORIAL_HEIGHT);

            }else
            {
                game.batch.draw(tutorialInactive,x,TUTORIAL,TUTORIAL_WIDTH, TUTORIAL_HEIGHT);

            }

            game.batch.end();
        }

        public void resize(int width, int height){}
        public void pause() { }
        public void resume() { }
        public void hide() { }
        public void dispose() { }

    @Override

    public void buildStage() {

    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        super.act(delta);
        super.draw();
        batch.end();
}


    public void dispose() {
    super.dispose();
    //skin.dispose();
    batch.dispose();
    }

}


    }
}