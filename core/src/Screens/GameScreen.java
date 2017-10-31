package Screens;


import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.HEXAGONAL;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;

import java.util.*;
import java.util.List;

import javax.swing.plaf.metal.MetalBorders.TableHeaderBorder;

import GameBoardAssets.HexagonActor;
import GameConstants.Constants;
import GameScoreAssets.Bar;
import GameScoreAssets.ScoreBarGroup;
import Tools.Link;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.game.Pieces;
import com.game.Player;
import org.codetome.hexameter.core.api.*;
import org.codetome.hexameter.core.api.Hexagon.*;
import org.codetome.hexameter.core.api.contract.HexagonDataStorage;
import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.Table.Debug;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import rx.functions.Action1;


public class GameScreen extends AbstractScreen {


    /*
    Our Variables: ---------------------------------------------------------
     */
    public Player[] players;
    public Player gamingPlayer;
    
    public HexagonalGrid grid;
    public HexagonalGrid[] tiles = new HexagonalGrid[6];
    public List<HexagonActor> hexagonActors = new ArrayList<HexagonActor>();

    private Table root;
    private Group hexagonView;
    private Group[][] tileView;
    // we use this to store information about the selected tile
    private Sprite[] touched = {null, null};

	private Sprite mainMenuButton;
    private ImageButton tileButton;
    private ImageButton.ImageButtonStyle tileStyle;
    private TextureAtlas tileButtonAtlas;
    private Skin tileButtonSkin;


    private SpriteBatch batch;
    private Skin skin;

    private HexagonActor first;
    private Group selectedTile;
    private int selectedTileIndex;


    /*
    Build the game screen: ---------------------------------------------------
     */
    // create the BAG
    ArrayList<Sprite[]> bag = Pieces.createBagPieces();


    // Build screen, add skins, add players

    public GameScreen() {

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Gdx.graphics.setWindowedMode(Constants.getWindowWidth(),Constants.getWindowHeight());
        players = new Player[Constants.getNumberOfPlayers()];

        for (int x = 1; x <= players.length; x++){
            players[x - 1] = new Player(x, Pieces.distributePieces(bag));
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

        // Build the hexagonal grid
        HexagonalGridBuilder<Link> builder = new HexagonalGridBuilder<Link>()
                .setGridHeight(Constants.getBoardHeight())
                .setGridWidth(Constants.getBoardWidth())
                .setGridLayout(Constants.getBoardLayout())
                .setOrientation(Constants.getHexagonOrientation())
                .setRadius(Constants.getHexRadius());

        // Create the Link between the hexagon objects and their abstract components
        HexagonalGrid<Link> grid = builder.build();
        this.grid = grid;

        // Create a HexagonActor for each Hexagon and attach it to the group
        this.hexagonView = new Group();


        /*
        Placing the actors in the stage, initializing the game state --------------------------
         */

        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {

            /*
            Within this method other functions to be performed upon the hexagons are called
            */

            @Override
            public void call(Hexagon hexagon) {

                // Create the Actor and link it to the hexagon (and vice-versa)
                final HexagonActor hexActor = new HexagonActor(hexagon);

                // Instantiate the starting colors for the corners
                Sprite emptySprite = new Sprite(new Texture(Gdx.files.internal("4players.png")));
                Sprite corner1Sprite = new Sprite(new Texture(Gdx.files.internal("colours/blue.png")));
                Sprite corner2Sprite = new Sprite(new Texture(Gdx.files.internal("colours/yellow.png")));
                Sprite corner3Sprite = new Sprite(new Texture(Gdx.files.internal("colours/orange.png")));
                Sprite corner4Sprite = new Sprite(new Texture(Gdx.files.internal("colours/purple.png")));
                Sprite corner5Sprite = new Sprite(new Texture(Gdx.files.internal("colours/violet.png")));
                Sprite corner6Sprite = new Sprite(new Texture(Gdx.files.internal("colours/red.png")));

                //mainMenuButton = new Sprite(new Texture(Gdx.files.internal("MainMenu.png")));
                //hexActor.setSprite(mainMenuButton)


                hexActor.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());

                hexagonView.addActor(hexActor);
                hexagon.setSatelliteData(new Link(hexActor));

            

                //STARTING COLOURS FOR EACH HEXAGON ON THE BOARD

                if (hexActor.getHexagon().getGridX() == -2 && hexActor.getHexagon().getGridY() == -8 && hexActor.getHexagon().getGridZ() == 10) {
                    hexActor.setSprite(corner1Sprite);
                    hexActor.setHexColor("B");
                } else if (hexActor.getHexagon().getGridX() == 3 && hexActor.getHexagon().getGridY() == -13 && hexActor.getHexagon().getGridZ() == 10) {
                    hexActor.setSprite(corner2Sprite);
                    hexActor.setHexColor("Y");
                } else if (hexActor.getHexagon().getGridX() == 8 && hexActor.getHexagon().getGridY() == -13 && hexActor.getHexagon().getGridZ() == 5) {
                    hexActor.setSprite(corner3Sprite);
                    hexActor.setHexColor("O");
                } else if (hexActor.getHexagon().getGridX() == 8 && hexActor.getHexagon().getGridY() == -8 && hexActor.getHexagon().getGridZ() == 0) {
                    hexActor.setSprite(corner4Sprite);
                    hexActor.setHexColor("P");
                } else if (hexActor.getHexagon().getGridX() == 3 && hexActor.getHexagon().getGridY() == -3 && hexActor.getHexagon().getGridZ() == 0) {
                    hexActor.setSprite(corner5Sprite);
                    hexActor.setHexColor("V");
                } else if (hexActor.getHexagon().getGridX() == -2 && hexActor.getHexagon().getGridY() == -3 && hexActor.getHexagon().getGridZ() == 5) {
                    hexActor.setSprite(corner6Sprite);
                    hexActor.setHexColor("R");
                } else {
                    hexActor.setSprite(emptySprite);
                }


                hexActor.addListener(new ClickListener(){

                    /*
                    This method allows click interaction with the tiles and the board, and then updates the score based on where a tile is placed.
                     */

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println(hexActor.getHexagon().getGridX() + ", " + hexActor.getHexagon().getGridY() + ", " + hexActor.getHexagon().getGridZ());


                        // Ensure that what we've clicked on is an empty space to place the tile upon
                        if(touched[0] != null && hexActor.getSprite() == emptySprite){
                            hexActor.setSprite(touched[0]);
                            hexActor.setHexColor(getSpriteColor(hexActor));
                            first = hexActor;
                            touched[0] = null;
                            Player.updateScore(gamingPlayer, hexActor, grid);

                        // Place the second hexagon in the tile
                        } else if (touched[0] == null && touched[1] != null && hexActor.getSprite() == emptySprite){
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

                                // When a tile is clicked, it moves upwards to indicate it is selected
                                selectedTile.moveBy(0, -30);

                                // Change player after tile is fully placed
                                gamingPlayer = players[Math.abs(gamingPlayer.getPlayerNo() - 2)];

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




        /*
        The tile parameters: ----------------------------------------------------
         */


        // Build the tiles
        final HexagonalGridBuilder<Link> tileBuilder = new HexagonalGridBuilder<Link>()
                .setGridHeight(Constants.getTileHeight())
                .setGridWidth(Constants.getTileWidth())
                .setGridLayout(Constants.getTileLayout())
                .setOrientation(Constants.getHexagonOrientation())
                .setRadius(Constants.getHexRadius());
        //if(tiles.length < 6)


        // place the tiles in their hand
        for (int p = 0; p < players.length; p++){
            Player playerP = players[p];
            tileView[p] = new Group[6];

            //now repeat for the 6 tiles
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

                                // Move the selected tile up a bit to indicate it was selected
                                if(touched[0] != null && touched[1] != null){
                                    selectedTile.moveBy(0, -30);
                                }

                                //
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

                                    //selectedTile is the Group of the current tile
                                    selectedTile = hexTile.getParent();

                                } else {
                                    selectedTile.moveBy(0, 30);
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



        /*DragAndDrop dnd  = new DragAndDrop();

                    dnd.addSource(new DragAndDrop.Source(tileGroup) {
                        DragAndDrop.Payload payload =  new DragAndDrop.Payload();

                        public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {  // here is the dragstart method where you take the item selected from the handtile , once you take it, it removes itself from the hand
                            payload.setObject(tileGroup);
                            payload.setDragActor(tileGroup);
                            return payload;
                        }

                        public void dragStop (InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                            // trying to replace the tile to its initial position if it's not placed on the board CURRENTLY NOT WORKING !!!
                            x = (float )hexagon.getCenterX();
                            y = (float) hexagon.getCenterY();
                            dnd.setDragActorPosition(-tileGroup.getWidth() / 2, tileGroup.getHeight() / 2);

                            if(target == null){
                                tileGroup.setPosition(x,y);
                            }
                            // Actor gets removed from the stage apparently
                            //stage.addActor(tileGroup);
                        }
                    });

                    // Target is the place where the tile should be placed
                    dnd.addTarget(new DragAndDrop.Target(hexagonView) { // This is the target class where i'm trying to point out the board BUT ALSO NOT WORKING NOW
                        @Override
                        public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                            return true;
                        }

                        @Override
                        public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {

                            //hexagonView.getItems().add((HexagonActor) payload.getObject());
                            Actor hexagon = (Actor) payload.getObject();
                            hexagonView.addActor(hexagon);
                        }
                    }); */



        this.root = new Table();
        this.root.setFillParent(true);

        //root.debug(Debug.all);

        // Create the score column
        Table scoreColumn = new Table();
        //scoreColumn.debug(Debug.all);

        ScoreBarGroup scorebars1 = new ScoreBarGroup(100,350,6);

        scoreColumn.add(scorebars1);

        scoreColumn.row();

        scoreColumn.add(new Label("Player 1 Score", skin)).bottom().padTop(20).padBottom(30);
        scoreColumn.row();

        ScoreBarGroup scorebars2 = new ScoreBarGroup(100,350,6);
        scoreColumn.add(scorebars2);

        scoreColumn.row();
        scoreColumn.add(new Label("Player 2 Score", skin)).bottom();

        root.add(scoreColumn).colspan(3).expand().fill();

        // Create the board
        Table boardColumn = new Table();


        boardColumn.row().height(150).top().expandX().left();
        for (int i = 0; i < 6; i++) {

            boardColumn.add(tileView[0][i]);

        }

        boardColumn.row();

        //boardColumn.debug(Debug.all);
        boardColumn.row().height(750).width(-200);
        boardColumn.add(hexagonView).expandY().center();

        boardColumn.row();

        boardColumn.row().height(150).bottom().expandX().left();
        for (int i = 0; i < 6; i++) {

            boardColumn.add(tileView[1][i]);

        }


        root.add(boardColumn).colspan(6).expand().fill();
        root.pack();

        addActor(root);

    }

    public void dispose(){
        super.dispose();
        batch.dispose();
    }

    // Gets the color of a sprite
    public String getSpriteColor(HexagonActor hexActor){
        Texture texture = hexActor.getSprite().getTexture();
        String path = ((FileTextureData)texture.getTextureData()).getFileHandle().path();

        String purple = "colours/purple.png";
        String red =    "colours/red.png";
        String blue =   "colours/blue.png";
        String yellow = "colours/yellow.png";
        String orange = "colours/orange.png";
        String violet = "colours/violet.png";


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



