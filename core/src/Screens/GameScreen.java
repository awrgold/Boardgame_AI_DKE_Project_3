package Screens;
import java.util.*;

import GameBoardAssets.HexagonActor;
import GameConstants.Constants;
import GameCustomAssets.CustomLabel;
import GameScoreAssets.ScoreBarGroup;
import Interfaces.AbstractScreen;
import Tools.Link;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.*;
import org.codetome.hexameter.core.api.*;
import com.badlogic.gdx.Gdx;
import rx.functions.Action1;


public class GameScreen extends AbstractScreen {
    //game data


    GameIngenious game;
    GameManager manager;
    private Skin skin;

   // private Stage stage;
    private Table root;
	public static TextButton[] changeTiles;
    //private CustomLabel p1;
    //private CustomLabel p2;
    ExtendViewport viewport;

    //ShapeRenderer renderer;
    SpriteBatch batch;
    public static final String TAG = GameScreen.class.getName();



    /* Build the game screen: --------------------------------------------------- */



    private CustomLabel p1;
    private CustomLabel p2;

    public GameScreen(GameIngenious game) {
    // Build screen, add skins, add players
        this.game = game;
        //this.manager = new GameManager();
        //handler = new GameHandler(game, comStrategy);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        //Gdx.graphics.setWindowedMode(Constants.getWindowWidth(),Constants.getWindowHeight());
        manager = new GameManager();

        buildStage();


    }


	public void buildStage() {

        viewport = new ExtendViewport(Constants.getWindowWidth(),Constants.getWindowHeight());
        batch = new SpriteBatch();
        //stage = new Stage(viewport);
        setViewport(viewport);
        Gdx.input.setInputProcessor(this);

        this.root = new Table();
        this.root.setFillParent(true);

        //root.debug(Table.Debug.all);

        // Create the score column add a score bar group for each player
        // can be generated directly from list of players
        Table scoreColumn = new Table();
        //scoreColumn.debug(Debug.all);
        p1 = new CustomLabel("Player 1 Score : "+ manager.getPlayerByIndex(0).scoreToString(), skin);
        p2 = new CustomLabel("Player 2 Score : "+ manager.getPlayerByIndex(1).scoreToString(), skin);
        ScoreBarGroup scorebars1 = new ScoreBarGroup(250,350, manager.getPlayerByIndex(0).getPlayerScore());
        scoreColumn.add(scorebars1);
        scoreColumn.row();
        scoreColumn.add(p1).bottom().padTop(20).padBottom(30);
        scoreColumn.row().expandX();
        ScoreBarGroup scorebars2 = new ScoreBarGroup(250,350, manager.getPlayerByIndex(1).getPlayerScore());
        scoreColumn.add(scorebars2);
        scoreColumn.row();
        scoreColumn.add(p2).bottom();
        root.add(scoreColumn).colspan(2).expand().fill();


        // Create the board
        Table boardColumn = new Table();
        //boardColumn.debug();

        //2 buttons for change hand
        changeTiles = new TextButton[2];
        for (int i = 1; i <= 2; i++){
            changeTiles[i - 1] = new TextButton("Change Tiles", skin);
        }

        //p1 tiles
        //boardColumn.row().height(100).top().expandX();
        //boardColumn.add(new Label("Player 1 Hand", skin));
        boardColumn.row().height(130).top().fillX();
        boardColumn.add(changeTiles[0]).height(100).width(100).bottom().left();
        changeTiles[0].setTouchable(Touchable.disabled);
        changeTiles[0].setVisible(false);

        boardColumn.add(manager.getHandByIndex(0)).expandX().center();

        /*for (int i = 0; i < 6; i++) {

           boardColumn.add(manager.getHandByIndex(0).showTile(i));

        }*/



        boardColumn.row().fillX();

       //board
        // boardColumn.debug(Debug.all);
        boardColumn.row().height(400).width(-450);
        // GBV  and PHV Change
        boardColumn.row().height(750).width(-200);
        boardColumn.add(manager.getBoard()).expandY().center();
        // boardColumn.add(gbv).expand().left();
        boardColumn.row();


        //p2 tiles
        //boardColumn.row().height(100).bottom().expandX();
        //boardColumn.add(new Label("Player 2 Hand", skin));
        boardColumn.row().height(130).bottom().fillX();
        boardColumn.add(changeTiles[1]).height(100).width(100).top().left();
        changeTiles[1].setTouchable(Touchable.disabled);
        changeTiles[1].setVisible(false);

        boardColumn.add(manager.getHandByIndex(1)).expandX().center();
        /*
        for (int i = 0; i < 6; i++) {
            boardColumn.add(manager.getHandByIndex(1).showTile(i));

        }*/

        root.add(boardColumn).colspan(4).expand().left().fillY();
        root.pack();


        //stage.addActor(root);
        addActor(root);

    }


    @Override
    public void resize (int width, int height) {
        viewport.update(width, height, true);
    }

    public static Vector2 getStageLocation(Actor actor) {
        return actor.localToStageCoordinates(new Vector2(0, 0));
    }



    public void render(float delta) {

        Gdx.gl.glClearColor(96/255f, 96/255f, 96/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        batch.begin();
        batch.end();
        // setup drawing for world
        viewport.apply();
        //renderer.setProjectionMatrix(viewport.getCamera().combined);
        //renderer.begin(ShapeRenderer.ShapeType.Filled);
        p1.updateText("Player 1 Score : "+ manager.getPlayerByIndex(0).scoreToString());
        p2.updateText("Player 2 Score : "+ manager.getPlayerByIndex(1).scoreToString());

        //manager.proccessStep(delta);


        this.act(delta);
        this.draw();
        //renderer.end();

    }

    public void dispose(){
       // stage.dispose();
        super.dispose();


    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {

        Vector2 worldTouch = viewport.unproject(new Vector2(screenX, screenY));
        //Vector2 tableTouch = screenToStageCoordinates(worldTouch);
        manager.handleTouch(worldTouch);

        return true;
    }




}




