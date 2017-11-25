package Screens;
import java.util.*;
import java.util.List;
import GameBoardAssets.HexagonActor;
import GameConstants.Constants;
import GameCustomAssets.CustomLabel;
import GameScoreAssets.ScoreBarGroup;
import Interfaces.AbstractScreen;
import Interfaces.GameHandler;
import Tools.Link;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.*;
import org.codetome.hexameter.core.api.*;
import com.badlogic.gdx.Gdx;
import rx.functions.Action1;


public class GameScreen extends AbstractScreen implements GameHandler {

    protected GameIngenious game;
    protected GameManager manager;
    private Skin skin;
	private Sprite mainMenuButton;
    private Table root;
	public static TextButton[] changeTiles;
    private CustomLabel p1;
    private CustomLabel p2;

    SpriteBatch batch;



    public GameScreen(GameIngenious game) {
        this.game = game;
        this.manager = new GameManager();
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Gdx.graphics.setWindowedMode(Constants.getWindowWidth(),Constants.getWindowHeight());
    }


	public void buildStage() {
        Stage stage  = new Stage();

        while (!manager.endGameCheck()){
            manager.updateState();
        }

        this.root = new Table();
        this.root.setFillParent(true);

        //root.debug(Debug.all);

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
        scoreColumn.row();
        ScoreBarGroup scorebars2 = new ScoreBarGroup(250,350, manager.getPlayerByIndex(1).getPlayerScore());
        scoreColumn.add(scorebars2);
        scoreColumn.row();
        scoreColumn.add(p2).bottom();
        root.add(scoreColumn).colspan(3).expand().fill();


        // Create the board
        Table boardColumn = new Table();

        //2 buttons for change hand
        changeTiles = new TextButton[2];
        for (int i = 1; i <= 2; i++){
            changeTiles[i - 1] = new TextButton("Change Tiles", skin);
        }

        //p1 tiles
        //boardColumn.row().height(100).top().expandX();
        //boardColumn.add(new Label("Player 1 Hand", skin));
        boardColumn.row().height(130).top().expandX().left();
        boardColumn.add(changeTiles[0]).height(100).bottom();
        changeTiles[0].setTouchable(Touchable.disabled);
        changeTiles[0].setVisible(false);

       for (int i = 0; i < 6; i++) {
           boardColumn.add(manager.getHandByIndex(0).displayHand()[i]);
        }

        boardColumn.row();

       //board
        // boardColumn.debug(Debug.all);
        boardColumn.row().height(400).width(-450);
        // GBV  and PHV Change
        boardColumn.row().height(750).width(-200);
        boardColumn.add(manager.getBoard().initializeBoard()).expandY().center();
        // boardColumn.add(gbv).expand().left();
        boardColumn.row();


        //p2 tiles
        //boardColumn.row().height(100).bottom().expandX();
        //boardColumn.add(new Label("Player 2 Hand", skin));
        boardColumn.row().height(130).bottom().expandX().left();
        boardColumn.add(changeTiles[1]).height(100).top();
        changeTiles[1].setTouchable(Touchable.disabled);
        changeTiles[1].setVisible(false);

        for (int i = 0; i < 6; i++) {
            boardColumn.add(manager.getHandByIndex(1).displayHand()[i]);

        }

        root.add(boardColumn).colspan(6).expand().fill();
        root.pack();


        addActor(root);

    }

    public static void update(){

    }

    
    public void render(float delta) {

        Gdx.gl.glClearColor(96/255f, 96/255f, 96/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        p1.updateText("Player 1 Score : "+ manager.getPlayerByIndex(0).scoreToString());
        p2.updateText("Player 2 Score : "+ manager.getPlayerByIndex(1).scoreToString());

        super.act(delta);
        super.draw();
    }

    public void dispose(){
        super.dispose();
        batch.dispose();
    }




}



