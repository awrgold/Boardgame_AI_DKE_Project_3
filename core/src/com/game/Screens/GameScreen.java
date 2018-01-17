package com.game.Screens;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.Components.GameLogic.GameManager;
import com.game.Components.GameConstants.Constants;
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

    private GameIngenious game;
    private GameManager manager;
//    private Skin skin;
//private ScoreBarGroup scorebars1;
//private ScoreBarGroup scorebars2;
//    // private Stage stage;
//    private Table root;
//	public static TextButton[] changeTiles;
    private Stage stage;
   // private ExtendViewport viewport;
////private CustomLabel label;
//private String text;
    //ShapeRenderer renderer;
//    private SpriteBatch batch;
    public static final String TAG = GameScreen.class.getName();

    public GameScreen(GameIngenious game,GameManager manager) {
    // Build screen, add skins, add players
        this.game = game;

      //  this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

     //   this.batch = new SpriteBatch();
       // this.stage = new Stage(viewport);
        //Gdx.graphics.setWindowedMode(Constants.getWindowWidth(),Constants.getWindowHeight());
        this.manager = manager;
        this.stage = manager.getCurrentState();

       // buildStage();

    }

//	public void buildStage() {
//
//        viewport = new ExtendViewport(Constants.getWindowWidth(),Constants.getWindowHeight());
//        batch = new SpriteBatch();
//        setViewport(viewport);
//        text = "tester Label " + manager.getNum();
//       this.label = new CustomLabel(text,skin);
//
//        root = new Table();
//        root.setFillParent(true);
//
//        //root.debug(Table.Debug.all);
//
//        // Create the score column add a score bar group for each player
//        Table scoreColumn = new Table();
////scoreColumn.validate();
//
//        scorebars1 = new ScoreBarGroup(250,350, manager.getPlayerByIndex(0).getPlayerScore(),manager.getPlayerByIndex(0).getPlayerNo());
//        scoreColumn.add(scorebars1);
//        scoreColumn.row();
//        scoreColumn.row().expandX();
//        scorebars2 = new ScoreBarGroup(250,350, manager.getPlayerByIndex(1).getPlayerScore(),manager.getPlayerByIndex(1).getPlayerNo());
//        scoreColumn.add(scorebars2);
//        scoreColumn.row();
//        root.add(scoreColumn).colspan(2).expand().fill();
//
//
//        // Create the board
//        Table boardColumn = new Table();
//        //boardColumn.debug();
////boardColumn.validate();
//        //2 buttons for change hand
//        changeTiles = new TextButton[2];
//        for (int i = 1; i <= 2; i++){
//            changeTiles[i - 1] = new TextButton("Change Tiles", skin);
//        }
//
//        //p1 tiles
//        //boardColumn.row().height(100).top().expandX();
//        //boardColumn.add(new Label("PlayerAssets 1 Hand", skin));
//        boardColumn.row().height(130).top().fillX();
//        boardColumn.add(changeTiles[0]).height(100).width(100).bottom().left();
//        changeTiles[0].setTouchable(Touchable.disabled);
//        changeTiles[0].setVisible(false);
//
//        boardColumn.add(manager.getHandByIndex(0)).expandX().center();
//        boardColumn.row().fillX();
//
//       //board
//        // boardColumn.debug(Debug.all);
//        boardColumn.row().height(400).width(-450);
//        // GBV  and PHV Change
//        boardColumn.row().height(750).width(-200);
//        boardColumn.add(manager.getBoard()).expandY().center();
//        // boardColumn.add(gbv).expand().left();
//        boardColumn.row();
//
//        //p2 tiles
//        //boardColumn.row().height(100).bottom().expandX();
//        //boardColumn.add(new Label("PlayerAssets 2 Hand", skin));
//        boardColumn.row().height(130).bottom().fillX();
//        boardColumn.add(changeTiles[1]).height(100).width(100).top().left();
//        changeTiles[1].setTouchable(Touchable.disabled);
//        changeTiles[1].setVisible(false);
//
//        boardColumn.add(manager.getHandByIndex(1)).expandX().center();
//
//        root.add(boardColumn).colspan(4).expand().left().fillY();
//        root.add(label);
//      //  root.pack();
////        root.validate();
//        addActor(root);
//
//    }


    @Override
    public void resize (int width, int height) {
        manager.getCurrentState().getViewport().update(width, height, true);
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
        Gdx.input.setInputProcessor(this);
        //viewport = new ExtendViewport(Constants.getWindowWidth(),Constants.getWindowHeight());
       // manager.getCurrentState().buildStage(viewport);


        //label.draw(batch,delta);

//        manager.getLabel().setFontScale(5);
//        manager.getLabel().setPosition(100,100);
//        stage.addActor(manager.getLabel());
//        root = new Table();
//        root.setFillParent(true);

        //root.debug(Table.Debug.all);

        // Create the score column add a score bar group for each player
//        Table scoreColumn = new Table();
//scoreColumn.validate();

//        scorebars1 = new ScoreBarGroup(250,350, manager.getPlayerByIndex(0).getPlayerScore(),manager.getPlayerByIndex(0).getPlayerNo());
//        scoreColumn.add(scorebars1);
//        scoreColumn.row();
//        scoreColumn.row().expandX();
//        scorebars2 = new ScoreBarGroup(250,350, manager.getPlayerByIndex(1).getPlayerScore(),manager.getPlayerByIndex(1).getPlayerNo());
//        scoreColumn.add(scorebars2);
//        scoreColumn.row();
//        root.add(scoreColumn).colspan(2).expand().fill();


        // Create the board
//        Table boardColumn = new Table();
//        //boardColumn.debug();
////boardColumn.validate();
//        //2 buttons for change hand
//        changeTiles = new TextButton[2];
//        for (int i = 1; i <= 2; i++){
//            changeTiles[i - 1] = new TextButton("Change Tiles", skin);
//        }
//
//        //p1 tiles
//        //boardColumn.row().height(100).top().expandX();
//        //boardColumn.add(new Label("PlayerAssets 1 Hand", skin));
//        boardColumn.row().height(130).top().fillX();
//        boardColumn.add(changeTiles[0]).height(100).width(100).bottom().left();
//        changeTiles[0].setTouchable(Touchable.disabled);
//        changeTiles[0].setVisible(false);
//
//        boardColumn.add(manager.getHandByIndex(0)).expandX().center();
//        boardColumn.row().fillX();
//
//       //board
//        // boardColumn.debug(Debug.all);
//        boardColumn.row().height(400).width(-450);
//        // GBV  and PHV Change
//        boardColumn.row().height(750).width(-200);
//        boardColumn.add(manager.getBoard()).expandY().center();
//        // boardColumn.add(gbv).expand().left();
//        boardColumn.row();
//
//        //p2 tiles
//        //boardColumn.row().height(100).bottom().expandX();
//        //boardColumn.add(new Label("PlayerAssets 2 Hand", skin));
//        boardColumn.row().height(130).bottom().fillX();
//        boardColumn.add(changeTiles[1]).height(100).width(100).top().left();
//        changeTiles[1].setTouchable(Touchable.disabled);
//        changeTiles[1].setVisible(false);
//
//        boardColumn.add(manager.getHandByIndex(1)).expandX().center();
//
//        root.add(boardColumn).colspan(4).expand().left().fillY();
//        //root.add(label);
//      //  root.pack();
////        root.validate();
//        stage.addActor(root);

    }


    public void render(float delta) {

        Gdx.gl.glClearColor(96/255f, 96/255f, 96/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        Gdx.graphics.setContinuousRendering(false);
//        Gdx.graphics.requestRendering();
     //

       // batch.begin();



/*update assets here, call update method followed by the act method(unless super.act() is called in the actor class)
1- set rules for rendering
2-assign it to Manager
3- Updating the actors at each change of state might be optimal
* */


       //manager.getLabel().act(text);
//       manager.getBoard().act();
//       manager.getHandByIndex(0).act();
//       manager.getHandByIndex(1).act();

      //manager.updateAssets();

      //  batch.end();
        // setup drawing for world
        //manager.getCurrentState().getViewport().apply();
//        manager.runSimulation();
        //this.stage = manager.getCurrentState();
        manager.getCurrentState().draw();
        manager.getCurrentState().act(delta);
       // buildStage();
        //renderer.setProjectionMatrix(viewport.getCamera().combined);
        //renderer.begin(ShapeRenderer.ShapeType.Filled);
//        root.validate();
      //stage.draw();
     // stage.act(delta);

        ///manager.getBoard().setPosition(0,0);


        //renderer.end();

    }



    public void dispose(){
        manager.getCurrentState().dispose();
       // batch.dispose();
       // super.dispose();

}




    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = manager.getCurrentState().getViewport().unproject(new Vector2(screenX, screenY));
        //Vector2 tableTouch = screenToStageCoordinates(worldTouch);
        System.out.println("select an empty hexagon");
        manager.handleTouch(worldTouch);
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



}




