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
import com.game.GameIngenious;
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

    //Scanner userInput = new Scanner(System.in);

    public int nOfPlayer;

    public Player[] players;

    public Player gamingPlayer;


    // Ratio of width and height of a regular hexagon.
    public static final int HEXAGON_WIDTH = 100;
    public static final int HEXAGON_HEIGHT = 100;
    
    public HexagonalGrid grid;
    public HexagonalGrid[] tiles = new HexagonalGrid[6];
    public List<HexagonActor> hexagonActors = new ArrayList<HexagonActor>();

    private Table root;
    private Group hexagonView;
    private Group[][] tileView;
    //we use this to store informations about the selected tile
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

    //board grid
    private final int GRID_HEIGHT = 11;
    private final int GRID_WIDTH = 11;
    private final HexagonalGridLayout GRID_LAYOUT = HEXAGONAL;
    private final HexagonOrientation ORIENTATION = POINTY_TOP;
    private HexagonalGridBuilder<Link> builder;

    final double RADIUS = Constants.getHexRadius();

    //tile grid (1x2)
    final int TILE_HEIGHT = 1;
    final int TILE_WIDTH = 2;
    final HexagonalGridLayout TILE_LAYOUT = RECTANGULAR;
    final HexagonOrientation TILE_ORIENTATION = POINTY_TOP;
    final double TILE_RADIUS = Constants.getHexRadius();
    private  HexagonalGridBuilder<Link> tileBuilder;
    //create the BAG
    ArrayList<Sprite[]> bag = Pieces.createBagPieces();



    //public HexagonalGridCalculator calculator = builder.buildCalculatorFor(grid);

    public GameScreen() {
        //batch = new SpriteBatch();

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Gdx.graphics.setWindowedMode(Constants.getWindowWidth(),Constants.getWindowHeight());

        //System.out.println("How many players are playing?");
        //nOfPlayer = userInput.nextInt();
        nOfPlayer = 2;

        players = new Player[nOfPlayer];

        for (int x = 1; x <= nOfPlayer; x++){
            players[x - 1] = new Player(x, Pieces.distributePieces(bag));
        }

        tileView = new Group[nOfPlayer][];

        gamingPlayer = players[0];
    }



    // Subclasses must load actors in this method
    @SuppressWarnings("unchecked")
	public void buildStage() {
        Stage stage  = new Stage();
        // ...
/* @Michael Extracted board construction
* */
    updateBoard();





/* @Michael Extracted Player hands construction
* */
    updateHand();




/* @Michael Generating and adding is all this BuildStage method should do

* */

        this.root = new Table();
        this.root.setFillParent(true);

        //root.debug(Debug.all);

        // Create the score column add a score bar group for each player
        //can be generated directly from list of players
        Table scoreColumn = new Table();
        //scoreColumn.debug(Debug.all);

        ScoreBarGroup scorebars1 = new ScoreBarGroup(140,350,players[0]);

        scoreColumn.add(scorebars1);

        scoreColumn.row();

        scoreColumn.add(new Label("Player 1 Score", skin)).bottom().padTop(20).padBottom(30);
        scoreColumn.row();

        ScoreBarGroup scorebars2 = new ScoreBarGroup(140,350,players[1]);
        scoreColumn.add(scorebars2);

        scoreColumn.row();
        scoreColumn.add(new Label("Player 2 Score", skin)).bottom();

        root.add(scoreColumn).colspan(3).expand().fill();

        // Create the board
        Table boardColumn = new Table();


        boardColumn.row().height(100).top().expandX();
        boardColumn.add(new Label("Player 1 Hand", skin));
        for (int i = 0; i < 6; i++) {

            boardColumn.add(tileView[0][i]);

        }

        boardColumn.row();

        //boardColumn.debug(Debug.all);
        boardColumn.row().height(400).width(-450);
        boardColumn.add(hexagonView).expandY().center();

        boardColumn.row();

        boardColumn.row().height(100).bottom().expandX();
        boardColumn.add(new Label("Player 2 Hand", skin));
        for (int i = 0; i < 6; i++) {

            boardColumn.add(tileView[1][i]);

        }




        root.add(boardColumn).colspan(6).expand().fill();
        root.pack();

        addActor(root);




    }

    private void updateHand() {
        //tile builder
        tileBuilder = new HexagonalGridBuilder<Link>()
                .setGridHeight(TILE_HEIGHT)
                .setGridWidth(TILE_WIDTH)
                .setGridLayout(TILE_LAYOUT)
                .setOrientation(TILE_ORIENTATION)
                .setRadius(TILE_RADIUS);

        //if(tiles.length < 6)


        for (int p = 0; p < nOfPlayer; p++){

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




                        hexTile.addListener(new ClickListener(){
                            @Override
                            public void clicked(InputEvent event, float x, float y) {

                                if(touched[0] != null && touched[1] != null) {

                                   selectedTile.moveBy(0, -30);

                                }

                                if(Arrays.asList(tileView[gamingPlayer.getPlayerNo() - 1]).contains(hexTile.getParent())){
                                    //little work around blips and bloops @Michael
//                                    if (gamingPlayer == players[0]) {
                                      //  hexTile.getParent().moveBy(0, -30);
//                                    }
//                                    if(gamingPlayer==players[1]){
                                        hexTile.getParent().moveBy(0, 30);
//                                    }
                                    //end


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
    }

    private void updateBoard() {
        // ...
        //grid builder
        builder = new HexagonalGridBuilder<Link>()
                .setGridHeight(GRID_HEIGHT)
                .setGridWidth(GRID_WIDTH)
                .setGridLayout(GRID_LAYOUT)
                .setOrientation(ORIENTATION)
                .setRadius(RADIUS);


        HexagonalGrid<Link> grid = builder.build();
        this.grid = grid;

        // Create a HexagonActor for each Hexagon and attach it to the group
        this.hexagonView = new Group();

        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {

                // Create the Actor and link it to the hexagon (and vice-versa)
                final HexagonActor hexActor = new HexagonActor(hexagon);

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



                // TODO: EXAMPLE WHERE I CHANGE THE COLOR ON HOVER OVER.
                // DO YOUR SHIT HERE IF YOU WANT TO INTERACT WITH THE HEXAGON FOR SOME REASON
                // LIKE PER EXAMPLE IF YOU HAVE ONE SELECTED AND NEED IT PLACED.


                hexActor.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println(hexActor.getHexagon().getGridX() + ", " + hexActor.getHexagon().getGridY() + ", " + hexActor.getHexagon().getGridZ());


                        if(touched[0] != null && hexActor.getSprite() == emptySprite){
                            hexActor.setSprite(touched[0]);
                            hexActor.setHexColor(getSpriteColor(hexActor));
                            first = hexActor;
                            touched[0] = null;
                            Player.updateScore(gamingPlayer, hexActor, grid);




                        } else if (touched[0] == null && touched[1] != null && hexActor.getSprite() == emptySprite){
                            if (grid.getNeighborsOf(first.getHexagon()).contains(hexActor.getHexagon())){
                                hexActor.setSprite(touched[1]);
                                hexActor.setHexColor(getSpriteColor(hexActor));
                                touched[1] = null;
                                first = null;
                                Player.updateScore(gamingPlayer, hexActor, grid);






                                //after the second click remove from hand the placed tile
                                gamingPlayer.getGamePieces().remove(selectedTileIndex);


                                //take a new one
                                Pieces.takePiece(bag, gamingPlayer.getGamePieces());

                                //and set the new sprites
                                int ind = 0;
                                for (Actor hex : selectedTile.getChildren()){

                                    if (hex instanceof HexagonActor){
                                        HexagonActor one = (HexagonActor) hex;
                                        one.setSprite(gamingPlayer.getGamePieces().get(0)[ind]);
                                        ind++;
                                    }
                                }
                                //little work around blips and bloops @Michael
//                                if (gamingPlayer == players[0]) {
                                   // selectedTile.moveBy(0, 30);
//                                }
//                                if(gamingPlayer==players[1]){
                                       selectedTile.moveBy(0, -30);
//                                }
                                //end
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

    }


    public void dispose(){
        super.dispose();
        batch.dispose();
    }

    public int getRandom(){
        int n = (int) (Math.random()*100);
        return n;
    }



    //Gets the color of a sprite
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



