package com.game.Screens;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.Components.GameAssets.Hud;
import com.game.Components.GameLogic.GameManager;
import com.game.Components.GameConstants.Constants;
import com.game.Components.GameLogic.Simulation;
import com.game.Components.GameScoreAssets.CustomLabel;
import com.game.Components.GameScoreAssets.ScoreBarGroup;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.game.*;
import com.badlogic.gdx.Gdx;


//public class GameScreen extends AbstractScreen {
public class GameScreen extends InputAdapter implements Screen {

    private InputMultiplexer inputMultiplexer;
    private Hud hud;
    private GameIngenious game;

    private ExtendViewport viewport;

    public static final String TAG = GameScreen.class.getName();

    public GameScreen(GameIngenious game) {
    // Build screen, add skins, add players
        this.game = game;
        //create a FitViewport to maintain virtual aspect ratio despite screen size
        this.viewport = new ExtendViewport(Constants.getWindowWidth(),Constants.getWindowHeight());
        //create our game HUD for scores/timers/level info
        hud = new Hud(game.batch,game.manager);
        inputMultiplexer = new InputMultiplexer(this);


    }




    @Override
    public void resize (int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public static Vector2 getStageLocation(Actor actor) {
        return actor.localToStageCoordinates(new Vector2(0, 0));
    }


    @Override
    public void show() {
        hud.stage.draw();

        Gdx.input.setInputProcessor(inputMultiplexer);

    }
public void update(float delta) {


}

    public void render(float delta) {


        Gdx.gl.glClearColor(96/255f, 96/255f, 96/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
     // Gdx.graphics.setContinuousRendering(false);
    //  Gdx.graphics.requestRendering();
       // update(delta);
       // if(game.manager.updateAssets()){
            hud.act(delta);
            hud.stage.draw();
            game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        //}


/*update assets here, call update method followed by the act method(unless super.act() is called in the actor class)
1- set rules for rendering
2-assign it to Manager
3- Updating the actors at each change of state might be optimal
* */
//Set batch to now draw what the Hud camera sees.
        //game.batch.setProjectionMatrix(gamecam.combined);
//       game.batch.begin();

//        player.draw(game.batch);
//        for (Enemy enemy : creator.getEnemies())
//            enemy.draw(game.batch);
//        for (Item item : items)
//            item.draw(game.batch);

//        game.batch.end();

        //Set our batch to now draw what the Hud camera sees.




    }



    public void dispose(){
     hud.dispose();

}




    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));
        //Vector2 tableTouch = screenToStageCoordinates(worldTouch);

        game.manager.handleTouch(worldTouch);

        return true;
    }




//    public void hide() {
//        for(Actor actor : getActors())
//        {
//            actor.clearListeners();
//        }
//        root.clearChildren();
//        root.clear();
//        clear();
//    }

    public Hud getHud(){ return hud; }

}




