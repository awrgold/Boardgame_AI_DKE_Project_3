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
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.game.*;
import org.codetome.hexameter.core.api.*;
import com.badlogic.gdx.Gdx;
import rx.functions.Action1;


public class GameScreen extends AbstractScreen {
    //game data


    protected GameIngenious game;
    protected GameManager manager;


    public static Group[][] tileView;



    private Skin skin;


    private Table root;

    public static TextButton[] changeTiles;

    public static final String TAG = GameScreen.class.getName();

    /* Build the game screen: --------------------------------------------------- */



    private CustomLabel p1;
    private CustomLabel p2;

    public GameScreen(GameIngenious game) {
        // Build screen, add skins, add players

        this.game = game;
        this.manager = new GameManager();
        //handler = new GameHandler(game, comStrategy);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Gdx.graphics.setWindowedMode(Constants.getWindowWidth(),Constants.getWindowHeight());

        tileView = new Group[manager.getnOfPlayer()][];


    }


    /* Build the stage upon which actors exist: ----------------------------------*/

    // Subclasses must load actors in this method

    public void buildStage() {

        //updateBoard();
        updateHand();

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


        //boardColumn.row().height(100).top().expandX();
        //boardColumn.add(new Label("Player 1 Hand", skin));
        boardColumn.row().height(130).top().expandX().left();
        boardColumn.add(changeTiles[0]).height(100).bottom();
        changeTiles[0].setTouchable(Touchable.disabled);
        changeTiles[0].setVisible(false);

       for (int i = 0; i < 6; i++) {
           boardColumn.add(tileView[0][i]);
        }

        boardColumn.row();

        // boardColumn.debug(Debug.all);
        boardColumn.row().height(400).width(-450);

        // GBV  and PHV Change
        boardColumn.row().height(750).width(-200);
        boardColumn.add(manager.getBoard().initializeBoard()).expandY().center();
        // boardColumn.add(gbv).expand().left();
        boardColumn.row();


        //boardColumn.row().height(100).bottom().expandX();
        //boardColumn.add(new Label("Player 2 Hand", skin));
        boardColumn.row().height(130).bottom().expandX().left();
        boardColumn.add(changeTiles[1]).height(100).top();
        changeTiles[1].setTouchable(Touchable.disabled);
        changeTiles[1].setVisible(false);

        for (int i = 0; i < 6; i++) {
            // boardColumn.add(tv[1][i]);
            boardColumn.add(tileView[1][i]);

        }

        root.add(boardColumn).colspan(6).expand().fill();
        root.pack();


        addActor(root);



    }

    private void updateHand() {

        for (int p = 0; p < manager.getnOfPlayer(); p++){

            Player playerP = manager.getPlayerByIndex(p);
            tileView[p] = new Group[6];


                // now repeat for the 6 tiles
                for (int i = 0; i < 6; i++) {
                    tileView[p][i] = playerP.getGamePieces()[i].generateTile();
                }



/*
                //override call for each grid
                tiles[i].getHexagons().forEach(new Action1<Hexagon<Link>>() {
                    @Override
                    public void call(Hexagon hexagon) {
                        final HexagonActor hexTile = new HexagonActor(hexagon);

                        //give both the sprites
                        if(hexagon.getGridX() == 0) {
                            hexTile.setSprite(oneOfSix[0]);
                        } else {
                            hexTile.setSprite(oneOfSix[1]);
                        }

                        hexTile.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());

                        //and pass everything in tileGroup
                        tileGroup.addActor(hexTile);
                        hexagon.setSatelliteData(new Link(hexTile));

                        /*
                        Create a click listener for the tiles in your hand: --------------------------
                         */

/*
                        hexTile.addListener(new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y) {

                                if(touched[0] != null && touched[1] != null) {
                                    selectedTile.moveBy(0, -30);
                                }


                                if(Arrays.asList(tileView[manager.getGamingPlayer().getPlayerNo() - 1]).contains(hexTile.getParent())){

                                    hexTile.getParent().moveBy(0, 30);
                                    touched[0] = hexTile.getSprite();
                                    Actor two = hexTile.getParent().getChildren().get(Math.abs(hexTile.getHexagon().getGridX() - 1));

                                    if (two instanceof HexagonActor){
                                        HexagonActor other = (HexagonActor) two;
                                        touched[1] = other.getSprite();
                                    }

                                    //find the index in player's hand
                                    for (Sprite[] s : playerP.getGamePieces()){
                                        if((s[0] == touched[0] && s[1] == touched[1]) || (s[1] == touched[0] && s[0] == touched[1])){
                                            selectedTileIndex = playerP.getGamePieces().indexOf(s);
                                        }
                                    }
                                    selectedTile = hexTile.getParent();

                                } else {
                                    // selectedTile.moveBy(0, 30);
                                    System.out.println("It's the turn of player " + manager.getGamingPlayer().getPlayerNo());
                                }
                            }
                        });
                    }
                }); */


 //           }
        }
    }


    private void updateBoard() {

        //this.grid = manager.getBoard();

       /* hexagonView = new Group();

        // Create a HexagonActor for each Hexagon and attach it to the group
        manager.getBoard().getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {

                // Create the Actor and link it to the hexagon (and vice-versa)
                final HexagonActor hexActor = new HexagonActor(hexagon);

                //mainMenuButton = new Sprite(new Texture(Gdx.files.internal("MainMenu.png")));
                //hexActor.setSprite(mainMenuButton)


                hexActor.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());


                //STARTING COLOURS FOR EACH HEXAGON ON THE BOARD

                if (hexActor.getHexagon().getGridX() == -2 && hexActor.getHexagon().getGridY() == -8 && hexActor.getHexagon().getGridZ() == 10) {
                    hexActor.setHexColor("B");
                } else if (hexActor.getHexagon().getGridX() == 3 && hexActor.getHexagon().getGridY() == -13 && hexActor.getHexagon().getGridZ() == 10) {
                    hexActor.setHexColor("Y");
                } else if (hexActor.getHexagon().getGridX() == 8 && hexActor.getHexagon().getGridY() == -13 && hexActor.getHexagon().getGridZ() == 5) {
                    hexActor.setHexColor("O");
                } else if (hexActor.getHexagon().getGridX() == 8 && hexActor.getHexagon().getGridY() == -8 && hexActor.getHexagon().getGridZ() == 0) {
                    hexActor.setHexColor("P");
                } else if (hexActor.getHexagon().getGridX() == 3 && hexActor.getHexagon().getGridY() == -3 && hexActor.getHexagon().getGridZ() == 0) {
                    hexActor.setHexColor("V");
                } else if (hexActor.getHexagon().getGridX() == -2 && hexActor.getHexagon().getGridY() == -3 && hexActor.getHexagon().getGridZ() == 5) {
                    hexActor.setHexColor("R");
                } else {
                    hexActor.setHexColor("EMPTY");
                }

                hexagon.setSatelliteData(new Link(hexActor));

                hexagonView.addActor(hexActor);
//------------------------------------------------------------------------------------------------------------------------------------------
                //if(manager.getGamingPlayer() == manager.getPlayers()[0]){

                //here we will place the method that display the AI move (selected Tile, hexagons where the tile is placed)

                //manager.changeGamingPlayer();
                //}

//------------------------------------------------------------------------------------------------------------------------------------------

                hexActor.addListener(new ClickListener(){



                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println(hexActor.getHexagon().getGridX() + ", " + hexActor.getHexagon().getGridY() + ", " + hexActor.getHexagon().getGridZ());

                        HexagonActor actorOne;

                        // Ensure that what we've clicked on is an empty space to place the tile upon
                        if(touched[0] != null && hexActor.getSprite() == Constants.emptySprite){
                            hexActor.setSprite(touched[0]);
                            hexActor.setHexColor(HexagonActor.getSpriteColor(hexActor));
                            first = hexActor;
                            touched[0] = null;
                            Player.updateScore(manager.getGamingPlayer(), hexActor, manager.getBoard(), first);



                            // Place the second hexagon in the tile
                        } else if (touched[0] == null && touched[1] != null && hexActor.getSprite() == Constants.emptySprite){
                            if (manager.getBoard().getNeighborsOf(first.getHexagon()).contains(hexActor.getHexagon())){
                                hexActor.setSprite(touched[1]);
                                hexActor.setHexColor(HexagonActor.getSpriteColor(hexActor));
                                touched[1] = null;
                                Player.updateScore(manager.getGamingPlayer(), hexActor, manager.getBoard(), first);
                                first = null;

                                // after the second click remove from hand the placed tile
                                manager.getGamingPlayer().getGamePieces().remove(selectedTileIndex);

                                // take a new one
                                Pieces.takePiece(manager.getBag(), manager.getGamingPlayer().getGamePieces());

                                // and set the new sprites
                                int ind = 0;
                                for (Actor hex : selectedTile.getChildren()){
                                    if (hex instanceof HexagonActor){
                                        HexagonActor one = (HexagonActor) hex;
                                        one.setSprite(manager.getGamingPlayer().getGamePieces().get(0)[ind]);
                                        ind++;
                                    }
                                }
                                selectedTile.moveBy(0, -30);

                                //TODO: Fix this, find way to change gaming player.
                                //manager.changeGamingPlayer();


                            }
                            // if you click on the same tile you just placed
                            else {
                                System.out.println("Select a neighbor");
                            }
                        } else if (touched[0] == null && touched[1] == null){
                            System.out.println("Select a piece from your hand!");
                        } else {
                            System.out.println("This slot is full! Color here is: " + hexActor.getHexColor());
                        }
                    }
                });
            }

        });*/

    }




    public void render(float delta) {

        Gdx.gl.glClearColor(96/255f, 96/255f, 96/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        p1.updateText("Player 1 Score : "+ manager.getPlayerByIndex(0).scoreToString());
        p2.updateText("Player 2 Score : "+ manager.getPlayerByIndex(1).scoreToString());
       // manager.render(delta,Renderer);
        super.act(delta);
        super.draw();
    }

    public void dispose(){
        super.dispose();
        this.dispose();
        }




}




