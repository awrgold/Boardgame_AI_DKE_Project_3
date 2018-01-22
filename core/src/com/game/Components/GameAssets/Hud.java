package com.game.Components.GameAssets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.Components.GameConstants.Constants;
import com.game.Components.GameLogic.GameManager;
import com.game.Components.GameLogic.GameState;
import com.game.Components.GameScoreAssets.CustomLabel;
import com.game.Components.GameScoreAssets.ScoreBarGroup;

public class Hud implements Disposable {

    private GameManager manager;
    private CustomLabel label;
    private SpriteBatch batch;
    public Stage stage;
    private Table root;
    public static TextButton[] changeTiles;
    //private String text;
    private Skin skin;
    private ExtendViewport viewport;
    private int num = 0;

    public Hud(SpriteBatch sb, GameManager manager) {

        //setup the HUD viewport using a new camera seperate from gamecam
        //define stage using that viewport and games spritebatch
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
//        text = "tester Label ";
//        this.label = new CustomLabel(text,skin);
//        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
//        label.setFontScale(5);
//        label.setPosition(100,100);
        this.manager = manager;
        this.viewport = new ExtendViewport(Constants.getWindowWidth(), Constants.getWindowHeight());
        stage = new Stage(viewport, sb);
        buildStage();
        this.batch = sb;
    }

    public void buildStage() {

        // setViewport(viewport);
//       this.label = new CustomLabel(text,skin);
//       text = "tester Label ";
//       label.setFontScale(5);
//       label.setPosition(100,100);

        root = new Table();
        root.setFillParent(true);
        root.addActor(manager.getLabel());
        // root.debug(Table.Debug.all);

        // Create the score column add a score bar group for each player
        Table scoreColumn = new Table();
//scoreColumn.validate();


        scoreColumn.add(manager.getScoreBarByIndex(0));
        scoreColumn.row();
        scoreColumn.row().expandX();
        scoreColumn.add(manager.getScoreBarByIndex(1));
        scoreColumn.row();
        root.add(scoreColumn).colspan(2).expand().fill();


        // Create the board
        Table boardColumn = new Table();
        //boardColumn.debug();
//boardColumn.validate();
        //2 buttons for change hand
        changeTiles = new TextButton[2];
        for (int i = 1; i <= 2; i++) {
            changeTiles[i - 1] = new TextButton("Change Tiles", skin);
        }

        //p1 tiles
        //boardColumn.row().height(100).top().expandX();
        //boardColumn.add(new Label("PlayerAssets 1 Hand", skin));
        boardColumn.row().height(130).top().fillX();
        boardColumn.add(changeTiles[0]).height(100).width(100).bottom().left();
        changeTiles[0].setTouchable(Touchable.disabled);
        changeTiles[0].setVisible(false);

        boardColumn.add(manager.getHandByIndex(0)).expandX().center();
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
        //boardColumn.add(new Label("PlayerAssets 2 Hand", skin));
        boardColumn.row().height(130).bottom().fillX();
        boardColumn.add(changeTiles[1]).height(100).width(100).top().left();
        changeTiles[1].setTouchable(Touchable.disabled);
        changeTiles[1].setVisible(false);

        boardColumn.add(manager.getHandByIndex(1)).expandX().center();

        root.add(boardColumn).colspan(4).expand().left().fillY();
       // root.invalidateHierarchy();
        stage.addActor(root);

    }

    public void act(float delta) {
        if (!manager.getBoard().gameOver()) {
            manager.updateAssets(delta);

        }
       // manager = new GameManager();

    }

    //public CustomLabel getLabel() {
     //   return label;
   // }



    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }


}
