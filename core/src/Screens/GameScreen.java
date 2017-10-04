package Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.game.GameIngenious;

public class Gamescreen implements Screen {


    GameIngenious game;
    Texture img;

    public Gamescreen(GameIngenious game){
        this.game = game;
    }

    @Override
    public void dispose() { }

    public void render(float delta)
    {


        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();  //start to draw an image
        game.batch.draw(img, 100, 100); // draw an image at coord
        game.batch.end();
    }

    @Override
    public void show()
    {
        img = new Texture("2players.png");

    }

    @Override
    public void hide() { }

    @Override
    public void resume() { }

    @Override
    public void pause() { }

    public void resize(int width, int height){}

}


