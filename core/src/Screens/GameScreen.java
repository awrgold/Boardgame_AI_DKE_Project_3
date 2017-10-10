package Screens;


import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.HEXAGONAL;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.metal.MetalBorders.TableHeaderBorder;

import GameConstants.Constants;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.Pieces;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.HexagonalGridLayout;
import org.codetome.hexameter.core.api.Point;
import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.Table.Debug;

import rx.functions.Action1;


public class GameScreen extends AbstractScreen {
		
	public static class Link extends DefaultSatelliteData {
		
		public HexagonActor actor;

		public Link(HexagonActor actor) {
			this.actor = actor;
		}
	}

	public static class HexagonActor extends Image{

		protected Hexagon<Link> hexagon;
		private Sprite sprite;
		private float[] vertices;
		private ShapeRenderer renderer = new ShapeRenderer();


		public HexagonActor(Hexagon<Link> hexagon) {
			this.hexagon = hexagon;

			
			List<Point> points =  (List<Point>) hexagon.getPoints();
			this.vertices = new float[points.size() * 2];
			
			for (int i = 0; i < points.size(); i++) {
				// Translate to local coordinates
				this.vertices[i * 2] = (float) points.get(i).getCoordinateX() - (float) hexagon.getCenterX();
				this.vertices[i * 2 + 1] = (float) points.get(i).getCoordinateY() - (float) hexagon.getCenterY();
                //setBounds(this.vertices[i * 2],this.vertices[i * 2 + 1], hexagon.getInternalBoundingBox().width, hexagon.getInternalBoundingBox().height);
			}
			setSize(hexagon.getInternalBoundingBox().width, hexagon.getInternalBoundingBox().height);
		}

		public void setSprite(Sprite sprite){
		    this.sprite = sprite;
        }

		public void draw (Batch batch, float parentAlpha) {
			
			batch.end();


			// Required just so everything displays at the correct position
			renderer.setProjectionMatrix(batch.getProjectionMatrix());
			renderer.setTransformMatrix(batch.getTransformMatrix());
			
			// Move to the location of this actor
			renderer.translate(getX(), getY(), 0);

			renderer.begin(ShapeType.Filled);
			renderer.setColor(getColor());


			// Go through all vertices, draw triangles for each, using the next vertex.
			// Go in bounds of two (x,y) pairs.
			for (int i = 0; i < vertices.length; i+=2) {

				float x1 = vertices[i], y1 = vertices[i+1];
				float x2 = vertices[(i + 2) % vertices.length], y2 = vertices[(i + 3) % vertices.length];



				renderer.triangle(x1 + getWidth() / 2, 
				                  y1 + getHeight() / 2, 
				                  x2 + getWidth() / 2, 
				                  y2 + getHeight() / 2, getWidth() / 2, getHeight() / 2);
			}
			
			renderer.end();
            batch.begin();

            //draw the sprite on the actor
			batch.draw(sprite, getX() - 10, getY() - 12, getWidth() + 17, getHeight() + 24);
		}
		public Hexagon<Link> getHexagon(){
			return hexagon;
		}
	}
    

    // Ratio of width and height of a regular hexagon.
    public static final int HEXAGON_WIDTH = 100;
    public static final int HEXAGON_HEIGHT = 100;
    
    public HexagonalGrid grid;
    public HexagonalGrid[] tiles = new HexagonalGrid[6];
    public List<HexagonActor> hexagonActors = new ArrayList<HexagonActor>();

    private Table root;
    private Group hexagonView;
    final private Group[] tileView = new Group[6];

	//private Texture mainMenuButton;
    private ImageButton tileButton;
    private ImageButton.ImageButtonStyle tileStyle;
    private TextureAtlas tileButtonAtlas;
    private Skin tileButtonSkin;

    private SpriteBatch batch;
    private Skin skin;
    
    //public HexagonalGridCalculator calculator = builder.buildCalculatorFor(grid);

    public GameScreen() {
        //batch = new SpriteBatch();

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Gdx.graphics.setWindowedMode(Constants.getWindowWidth(),Constants.getWindowHeight());
    }


    // Subclasses must load actors in this method
    @SuppressWarnings("unchecked")
	public void buildStage() {
        Stage stage  = new Stage();
        // ...

        //board grid
        final int GRID_HEIGHT = 11;
        final int GRID_WIDTH = 11;
        final HexagonalGridLayout GRID_LAYOUT = HEXAGONAL;
        final HexagonOrientation ORIENTATION = POINTY_TOP;
        final double RADIUS = Constants.getHexRadius();

        //tile grid (1x2)
        final int TILE_HEIGHT = 1;
        final int TILE_WIDTH = 2;
        final HexagonalGridLayout TILE_LAYOUT = RECTANGULAR;
        final HexagonOrientation TILE_ORIENTATION = POINTY_TOP;
        final double TILE_RADIUS = Constants.getHexRadius();

        // ...
        //grid builder
        HexagonalGridBuilder<Link> builder = new HexagonalGridBuilder<Link>()
                .setGridHeight(GRID_HEIGHT)
                .setGridWidth(GRID_WIDTH)
                .setGridLayout(GRID_LAYOUT)
                .setOrientation(ORIENTATION)
                .setRadius(RADIUS);

        //tile builder
        HexagonalGridBuilder<Link> tileBuilder = new HexagonalGridBuilder<Link>()
                .setGridHeight(TILE_HEIGHT)
                .setGridWidth(TILE_WIDTH)
                .setGridLayout(TILE_LAYOUT)
                .setOrientation(TILE_ORIENTATION)
                .setRadius(TILE_RADIUS);

        HexagonalGrid<Link> grid = builder.build();
        this.grid = grid;

        //create the BAG
        ArrayList<Sprite[]> bag = Pieces.createBagPieces();

        //distribute pieces to player 1
        ArrayList<Sprite[]> player1pieces = Pieces.distributePieces(bag);

        //now repeat for the 6 tiles
        for (int i = 0; i < 6; i++){

            //give it a grid (2x1)
            HexagonalGrid<Link> tile = tileBuilder.build();
            this.tiles[i] = tile;

            //create a group that contains the 2 hexagons
            Group tileGroup = new Group();

            //get one of the six couple of sprites
            Sprite[] oneOfSix = player1pieces.get(i);



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


                    tileGroup.addListener(new DragListener() {
                        public void drag(InputEvent event, float x, float y, int pointer) {
                            tileGroup.moveBy(x - tileGroup.getChildren().size / 2, y - tileGroup.getChildren().size / 2);
                        }
                    });

                }
            });

            //add tileGroup to tileView
            this.tileView[i] = tileGroup;
        }



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

                hexActor.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());

                hexagonView.addActor(hexActor);

                hexagon.setSatelliteData(new Link(hexActor));

                //STARTING COLOURS FOR EACH HEXAGON ON THE BOARD

                if (hexActor.hexagon.getGridX() == -2 && hexActor.hexagon.getGridY() == -8 && hexActor.hexagon.getGridZ() == 10) {
                    hexActor.setSprite(corner1Sprite);
                } else if (hexActor.hexagon.getGridX() == 3 && hexActor.hexagon.getGridY() == -13 && hexActor.hexagon.getGridZ() == 10) {
                    hexActor.setSprite(corner2Sprite);
                } else if (hexActor.hexagon.getGridX() == 8 && hexActor.hexagon.getGridY() == -13 && hexActor.hexagon.getGridZ() == 5) {
                    hexActor.setSprite(corner3Sprite);
                } else if (hexActor.hexagon.getGridX() == 8 && hexActor.hexagon.getGridY() == -8 && hexActor.hexagon.getGridZ() == 0) {
                    hexActor.setSprite(corner4Sprite);
                } else if (hexActor.hexagon.getGridX() == 3 && hexActor.hexagon.getGridY() == -3 && hexActor.hexagon.getGridZ() == 0) {
                    hexActor.setSprite(corner5Sprite);
                } else if (hexActor.hexagon.getGridX() == -2 && hexActor.hexagon.getGridY() == -3 && hexActor.hexagon.getGridZ() == 5) {
                    hexActor.setSprite(corner6Sprite);
                } else {
                    hexActor.setSprite(emptySprite);
                }


                // TODO: EXAMPLE WHERE I CHANGE THE COLOR ON HOVER OVER.
                // DO YOUR SHIT HERE IF YOU WANT TO INTERACT WITH THE HEXAGON FOR SOME REASON
                // LIKE PER EXAMPLE IF YOU HAVE ONE SELECTED AND NEED IT PLACED.
                // HINT HINT HINT
                hexActor.addListener(new EventListener() {
                    @Override
                    public boolean handle(Event event) {
                        if (event instanceof InputEvent) {
                            InputEvent inputEvent = (InputEvent) event;
                            if (inputEvent.getType() == Type.touchDown) {
                                //hexActor.hit(inputEvent.getStageX(), inputEvent.getStageY(), true);
                                hexActor.setColor(Color.BLACK);
                                System.out.println("Coordinates: (" + hexActor.hexagon.getGridX() + ", " + hexActor.hexagon.getGridY() + ", " + hexActor.hexagon.getGridZ() + ")");
                            }
                        }
                        return false;
                    }
                });
            }
        });

        this.root = new Table();
        this.root.setFillParent(true);

        root.debug(Debug.all);

        // Create the score column
        Table scoreColumn = new Table();
        scoreColumn.debug(Debug.all);

        scoreColumn.add(new Label("Player 1 Score", skin)).expand().top();
        scoreColumn.row();
        scoreColumn.add(new Label("Player 2 Score", skin)).expand().top();

        root.add(scoreColumn).colspan(1).expand().fill();

        // Create the board
        Table boardColumn = new Table();


        boardColumn.debug(Debug.all);
        boardColumn.add(hexagonView).colspan(5).expand().fill();
        boardColumn.row().bottom().colspan(5);


        /*tileButtonAtlas = new TextureAtlas("HexagonsPack.pack");
        tileButtonSkin = new Skin();
        tileButtonSkin.addRegions(tileButtonAtlas);
        tileStyle = new ImageButton.ImageButtonStyle();
        tileStyle.up = tileButtonSkin.getDrawable("Tile51");*/

        //place the tiles on the screen
        for (int i = 0; i < 6; i++) {
			boardColumn.add(tileView[i]).expandX().fill().colspan(1);
		}
        
        root.add(boardColumn).expand().fill();
        root.pack();
                
        addActor(root);



        /*

        // Create two list of whatever we want ( here sprite ), the first one is the hand of the current player and the other one is the actual game stage
        com.badlogic.gdx.scenes.scene2d.ui.List<Sprite> handTile = new com.badlogic.gdx.scenes.scene2d.ui.List<>(skin);
        com.badlogic.gdx.scenes.scene2d.ui.List<Sprite> boardTile = new com.badlogic.gdx.scenes.scene2d.ui.List<>(skin);

        // here i add the objects to the actual list
        Sprite hand = new Sprite(new Texture(Gdx.files.internal("colours/blue.png")));
        handTile.setItems(hand);
        Sprite emptySprite = new Sprite(new Texture(Gdx.files.internal("4players.png")));
        boardTile.setItems(emptySprite);


        DragAndDrop dnd  = new DragAndDrop();

        dnd.addSource(new DragAndDrop.Source(handTile) {
            DragAndDrop.Payload payload =  new DragAndDrop.Payload();

            // here is the dragstart method where you take the item selected from the handtile , once you take it it removes itself from the hand
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                Sprite item =  handTile.getSelected();
                payload.setObject(handTile.getSelected());
                handTile.getItems().removeIndex(handTile.getSelectedIndex());
                //payload.setDragActor(new Label(item,skin)); THIS IS FOR LETTING THE ITEM APPEARING ONCE ITS MOVING I NEED TO CHANGE LABEL

                return payload;
            }

            public void dragStop (InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {

                if (target == null) {
                    handTile.getItems().add((Sprite) payload.getObject());
                }
            }
        });

        // Target is the place where the tile should be placed
        dnd.addTarget(new DragAndDrop.Target(boardTile) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return false;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                boardTile.getItems().add((Sprite) payload.getObject());
            }
        });


        // PS : from this, we can implement valid dragandrop etc so i think it's very interesting and not that difficult.
*/
    }


    public void dispose(){
        super.dispose();
        batch.dispose();
    }

    //public void show(){
    	//mainMenuButton = new Texture("mainmenu.png");
	//}

}



