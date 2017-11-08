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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.game.GameIngenious;
import com.game.Pieces;
import com.game.Player;
import org.codetome.hexameter.core.api.*;

import com.badlogic.gdx.Gdx;

import rx.functions.Action1;


public class GameScreen extends AbstractScreen implements GameHandler {

    protected GameIngenious game;
    private Skin skin;

    public Player[] players;
    public Player gamingPlayer;
    public HexagonalGrid grid;
    public HexagonalGrid[] tiles = new HexagonalGrid[6];


    private Table root;
    private Group hexagonView;
    private Group[][] tileView;

    // we use this to store information about the selected tile
    private Sprite[] touched = {null, null};

	private Sprite mainMenuButton;
	private TextButton[] changeTiles;


    private SpriteBatch batch;


    private HexagonActor first;
    private Group selectedTile;
    private int selectedTileIndex;


    /*
    Build the game screen: ---------------------------------------------------
     */

    // create the BAG
    ArrayList<Sprite[]> bag = Pieces.createBagPieces();

    // public HexagonalGridCalculator calculator = builder.buildCalculatorFor(grid);

    private CustomLabel p1;
    private CustomLabel p2;

    public GameScreen(GameIngenious game) {
    // Build screen, add skins, add players

        this.game = game;
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Gdx.graphics.setWindowedMode(Constants.getWindowWidth(),Constants.getWindowHeight());
        players = new Player[Constants.getNumberOfPlayers()];

        for (int x = 1; x <= players.length; x++){
            players[x - 1] = new Player(x, Pieces.distributePieces(bag));
        }

        changeTiles = new TextButton[2];

        for (int i = 1; i <= 2; i++){
            changeTiles[i - 1] = new TextButton("Player " + i + "\n\nChange Tiles", skin);
        }


        tileView = new Group[players.length][];
        gamingPlayer = players[0];


    }



    /*
    Build the stage upon which actors exist: ----------------------------------
     */
    // Subclasses must load actors in this method
    @SuppressWarnings("unchecked")
	public void buildStage() {
        Stage stage  = new Stage();
        // ...
        updateBoard();

        updateHand();


        this.root = new Table();
        this.root.setFillParent(true);

        //root.debug(Debug.all);

        // Create the score column add a score bar group for each player
        // can be generated directly from list of players
        Table scoreColumn = new Table();

        //scoreColumn.debug(Debug.all);
        p1 =new CustomLabel("Player 1 Score : "+ players[0].scoreToString(), skin);
        p2 = new CustomLabel("Player 2 Score : "+players[1].scoreToString(), skin);
        ScoreBarGroup scorebars1 = new ScoreBarGroup(250,350,players[0].getPlayerScore());

        scoreColumn.add(scorebars1);

        scoreColumn.row();

        scoreColumn.add(p1).bottom().padTop(20).padBottom(30);
        scoreColumn.row();

        ScoreBarGroup scorebars2 = new ScoreBarGroup(250,350,players[1].getPlayerScore());
        scoreColumn.add(scorebars2);

        scoreColumn.row();
        scoreColumn.add(p2).bottom();

        root.add(scoreColumn).colspan(3).expand().fill();

        // Create the board
        Table boardColumn = new Table();


        // boardColumn.row().height(100).top().expandX();
        boardColumn.row().height(130).top().expandX().left();
        //boardColumn.add(new Label("Player 1 Hand", skin));
        boardColumn.add(changeTiles[0]).height(100).bottom();
        changeTiles[0].setDisabled(true);
       for (int i = 0; i < 6; i++) {
           // boardColumn.add(tv[0][i]);
           boardColumn.add(tileView[0][i]);

        }

        boardColumn.row();

        // boardColumn.debug(Debug.all);
        boardColumn.row().height(400).width(-450);

        // GBV  and PHV Change
        boardColumn.row().height(750).width(-200);
        boardColumn.add(hexagonView).expandY().center();
        // boardColumn.add(gbv).expand().left();
        boardColumn.row();

        // boardColumn.row().height(100).bottom().expandX();
        boardColumn.row().height(130).bottom().expandX().left();
        //boardColumn.add(new Label("Player 2 Hand", skin));
        boardColumn.add(changeTiles[1]).height(100).top();
        changeTiles[1].setDisabled(true);
        for (int i = 0; i < 6; i++) {
           // boardColumn.add(tv[1][i]);
           boardColumn.add(tileView[1][i]);

        }

        root.add(boardColumn).colspan(6).expand().fill();
        root.pack();

        addActor(root);

    }

    private void updateHand() {


        // Build the tiles
        final HexagonalGridBuilder<Link> tileBuilder = new HexagonalGridBuilder<Link>()
                .setGridHeight(Constants.getTileHeight())
                .setGridWidth(Constants.getTileWidth())
                .setGridLayout(Constants.getTileLayout())
                .setOrientation(Constants.getHexagonOrientation())
                .setRadius(Constants.getHexRadius());


        for (int p = 0; p < players.length; p++){

            Player playerP = players[p];
            tileView[p] = new Group[6];

            // now repeat for the 6 tiles
            for (int i = 0; i < 6; i++){

                //give it a grid (2x1)
                HexagonalGrid<Link> tile = tileBuilder.build();
                this.tiles[i] = tile;

                //create a group that contains the 2 hexagons
                Group tileGroup = new Group();

                //get one of the six couple of sprites
                Sprite[] oneOfSix = playerP.getGamePieces().get(i);



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
                        hexTile.addListener(new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y) {

                                if(touched[0] != null && touched[1] != null) {

                                   selectedTile.moveBy(0, -30);

                                }


                                if(Arrays.asList(tileView[gamingPlayer.getPlayerNo() - 1]).contains(hexTile.getParent())){

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
                                    System.out.println("It's the turn of player " + gamingPlayer.getPlayerNo());
                                }
                            }
                        });
                    }
                });

                //add tileGroup to tileView
                this.tileView[p][i] = tileGroup;
            }
        }
    }

    private void updateBoard() {
        // ...
        //grid builder
        final HexagonalGridBuilder<Link> gridBuilder = new HexagonalGridBuilder<Link>()
                .setGridHeight(Constants.getBoardHeight())
                .setGridWidth(Constants.getBoardWidth())
                .setGridLayout(Constants.getBoardLayout())
                .setOrientation(Constants.getHexagonOrientation())
                .setRadius(Constants.getHexRadius());


        HexagonalGrid<Link> grid = gridBuilder.build();
        this.grid = grid;

        // Create a HexagonActor for each Hexagon and attach it to the group
        this.hexagonView = new Group();
      //  this.gbv = new GameBoardView();
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {

                // Create the Actor and link it to the hexagon (and vice-versa)
                final HexagonActor hexActor = new HexagonActor(hexagon);

                //mainMenuButton = new Sprite(new Texture(Gdx.files.internal("MainMenu.png")));
                //hexActor.setSprite(mainMenuButton)


                hexActor.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());

                hexagonView.addActor(hexActor);

                hexagon.setSatelliteData(new Link(hexActor));

            

                //STARTING COLOURS FOR EACH HEXAGON ON THE BOARD

                if (hexActor.getHexagon().getGridX() == -2 && hexActor.getHexagon().getGridY() == -8 && hexActor.getHexagon().getGridZ() == 10) {
                    hexActor.setSprite(Constants.blueSprite);
                    hexActor.setHexColor("B");
                } else if (hexActor.getHexagon().getGridX() == 3 && hexActor.getHexagon().getGridY() == -13 && hexActor.getHexagon().getGridZ() == 10) {
                    hexActor.setSprite(Constants.yellowSprite);
                    hexActor.setHexColor("Y");
                } else if (hexActor.getHexagon().getGridX() == 8 && hexActor.getHexagon().getGridY() == -13 && hexActor.getHexagon().getGridZ() == 5) {
                    hexActor.setSprite(Constants.orangeSprite);
                    hexActor.setHexColor("O");
                } else if (hexActor.getHexagon().getGridX() == 8 && hexActor.getHexagon().getGridY() == -8 && hexActor.getHexagon().getGridZ() == 0) {
                    hexActor.setSprite(Constants.purpleSprite);
                    hexActor.setHexColor("P");
                } else if (hexActor.getHexagon().getGridX() == 3 && hexActor.getHexagon().getGridY() == -3 && hexActor.getHexagon().getGridZ() == 0) {
                    hexActor.setSprite(Constants.violetSprite);
                    hexActor.setHexColor("V");
                } else if (hexActor.getHexagon().getGridX() == -2 && hexActor.getHexagon().getGridY() == -3 && hexActor.getHexagon().getGridZ() == 5) {
                    hexActor.setSprite(Constants.redSprite);
                    hexActor.setHexColor("R");
                } else {
                    hexActor.setSprite(Constants.emptySprite);
                }


                hexActor.addListener(new ClickListener(){

                    /*
                    This method allows click interaction with the tiles and the board, and then updates the score based on where a tile is placed.
                     */

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println(hexActor.getHexagon().getGridX() + ", " + hexActor.getHexagon().getGridY() + ", " + hexActor.getHexagon().getGridZ());


                        // Ensure that what we've clicked on is an empty space to place the tile upon
                        if(touched[0] != null && hexActor.getSprite() == Constants.emptySprite){
                            hexActor.setSprite(touched[0]);
                            hexActor.setHexColor(getSpriteColor(hexActor));
                            first = hexActor;
                            touched[0] = null;
                            Player.updateScore(gamingPlayer, hexActor, grid);

                        // Place the second hexagon in the tile
                        } else if (touched[0] == null && touched[1] != null && hexActor.getSprite() == Constants.emptySprite){
                            if (grid.getNeighborsOf(first.getHexagon()).contains(hexActor.getHexagon())){
                                hexActor.setSprite(touched[1]);
                                hexActor.setHexColor(getSpriteColor(hexActor));
                                touched[1] = null;
                                first = null;
                                Player.updateScore(gamingPlayer, hexActor, grid);

                                // after the second click remove from hand the placed tile
                                gamingPlayer.getGamePieces().remove(selectedTileIndex);

                                // take a new one
                                Pieces.takePiece(bag, gamingPlayer.getGamePieces());

                                // and set the new sprites
                                int ind = 0;
                                for (Actor hex : selectedTile.getChildren()){
                                    if (hex instanceof HexagonActor){
                                        HexagonActor one = (HexagonActor) hex;
                                        one.setSprite(gamingPlayer.getGamePieces().get(0)[ind]);
                                        ind++;
                                    }
                                }

                                selectedTile.moveBy(0, -30);

                                //change player

                                gamingPlayer = players[Math.abs(gamingPlayer.getPlayerNo() - 2)];

                                // Check if hand has any tiles of lowest color:
                                if (!gamingPlayer.isLowestScoreTilePresent()){
                                    changeTiles[gamingPlayer.getPlayerNo() - 1].setDisabled(false);

                                    //CLICK TO CHANGE PIECES FROM THE BAG
                                    changeTiles[gamingPlayer.getPlayerNo() - 1].addListener(new ClickListener() {
                                        @Override
                                        public void clicked(InputEvent event, float x, float y) {
                                            System.out.println("clicked!");
                                            gamingPlayer.setPlayerPieces(Pieces.discardPieces(bag, gamingPlayer.getGamePieces()));
                                            for (int i = 0; i < 6; i++){
                                                Group tile = tileView[gamingPlayer.getPlayerNo() - 1][i];
                                                int index = 0;
                                                for (Actor hex : tile.getChildren()){
                                                    if (hex instanceof HexagonActor){
                                                        HexagonActor first = (HexagonActor) hex;
                                                        first.setSprite(gamingPlayer.getGamePieces().get(i)[index]);
                                                        index++;

                                                    }

                                                }
                                            }
                                        }
                                    });


                                }

                            }
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
        });




   }
    @Override
    public void render(float delta) {
        // Clear screen
        //Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClearColor(96/255f, 96/255f, 96/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        p1.updateText("Player 1 Score : "+ players[0].scoreToString());
        p2.updateText("Player 2 Score : "+players[1].scoreToString());
       // gbv.draw(batch,delta);
      //  gbv.act(delta);
        // Calling to Stage methods
        super.act(delta);
       super.draw();
    }

    public void dispose(){
        super.dispose();
        batch.dispose();
    }

    // Gets the color of a sprite
    public String getSpriteColor(HexagonActor hexActor){
        Texture texture = hexActor.getSprite().getTexture();
        String path = ((FileTextureData)texture.getTextureData()).getFileHandle().path();

        String violet = "colours/violet.png";
        String red =    "colours/red.png";
        String blue =   "colours/blue.png";
        String yellow = "colours/yellow.png";
        String orange = "colours/orange.png";
        String purple = "colours/purple.png";


        if(path.equals(purple)){
            path = "P";
            return path;
        }

        else if(path.equals(red)){
            path = "R";
            return path;
        }

        else if(path.equals(blue)){
            path = "B";
            return path;
        }

        else if(path.equals(yellow)){
            path = "Y";
            return path;
        }

        else if(path.equals(orange)){
            path = "O";
            return path;
        }

        else if(path.equals(violet)){
            path = "V";
            return path;
        }

        else{
            return path;
        }
    }

}



