package Screens;


import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.HEXAGONAL;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.metal.MetalBorders.TableHeaderBorder;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
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

			this.sprite = new Sprite(new Texture(Gdx.files.internal("4players.png")));


			
			List<Point> points =  (List<Point>) hexagon.getPoints();
			this.vertices = new float[points.size() * 2];
			
			for (int i = 0; i < points.size(); i++) {
				// Translate to local coordinates
				this.vertices[i * 2] = (float) points.get(i).getCoordinateX() - (float) hexagon.getCenterX();
				this.vertices[i * 2 + 1] = (float) points.get(i).getCoordinateY() - (float) hexagon.getCenterY();
                //setBounds(this.vertices[i * 2],this.vertices[i * 2 + 1], hexagon.getInternalBoundingBox().width, hexagon.getInternalBoundingBox().height);
			}
			
			setSize(hexagon.getInternalBoundingBox().width, hexagon.getInternalBoundingBox().height);
            //System.out.println(hexagon.getInternalBoundingBox().width + " " + hexagon.getInternalBoundingBox().height);

            //
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
    public List<HexagonActor> hexagonActors = new ArrayList<HexagonActor>();

    private Table root;
    private Group hexagonView;
    //private Group buttons;

    private ImageButton hexButton;
    private ImageButton.ImageButtonStyle hexStyle;
    private TextureAtlas hexButtonAtlas;
    private Skin hexButtonSkin;


	private Texture mainMenuButton;
    private Texture hexTex;

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


        Gdx.graphics.setWindowedMode(1920,1080);
    }


    // Subclasses must load actors in this method
    @SuppressWarnings("unchecked")
	public void buildStage(){

//      Board.createMap();

        // ...
        final int GRID_HEIGHT = 11;
        final int GRID_WIDTH = 11;
        final HexagonalGridLayout GRID_LAYOUT = HEXAGONAL;
        final HexagonOrientation ORIENTATION = POINTY_TOP;
        final double RADIUS = 30;

        // ...
        HexagonalGridBuilder<Link> builder = new HexagonalGridBuilder<Link>()
                .setGridHeight(GRID_HEIGHT)
                .setGridWidth(GRID_WIDTH)
                .setGridLayout(GRID_LAYOUT)
                .setOrientation(ORIENTATION)
                .setRadius(RADIUS);

        HexagonalGrid<Link> grid = builder.build();
        this.grid = grid;

        // Create a HexagonActor for each Hexagon and attach it to the group
        this.hexagonView = new Group();
        //this.buttons = new Group();

        //I've tried to use a stack to merge the two groups
        //Stack board = new Stack(hexagonView, buttons);







        
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {

            	// Create the Actor and link it to the hexagon (and vice-versa)
            	final HexagonActor hexActor = new HexagonActor(hexagon);



            	hexButtonAtlas = new TextureAtlas("HexagonsPack.pack");
            	hexButtonSkin = new Skin();
            	hexButtonSkin.addRegions(hexButtonAtlas);


            	hexStyle = new ImageButton.ImageButtonStyle();
            	hexStyle.up = hexButtonSkin.getDrawable("4players");
            	hexStyle.imageChecked = hexButtonSkin.getDrawable("Asset 34");



                /*
                hexButton = new ImageButton(hexStyle);
                hexButton.setSize((float) RADIUS * 1.75f, (float)RADIUS * 2);

                hexButton.setPosition((float) hexagon.getCenterX() - 8, (float) hexagon.getCenterY() - 11);*/

            	hexActor.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());


                hexagonView.addActor(hexActor);
                //buttons.addActor(hexButton);

            	hexagon.setSatelliteData(new Link(hexActor));

            	
            	// TODO: EXAMPLE WHERE I CHANGE THE COLOR ON HOVER OVER.
            	// DO YOUR SHIT HERE IF YOU WANT TO INTERACT WITH THE HEXAGON FOR SOME REASON
            	// LIKE PER EXAMPLE IF YOU HAVE ONE SELECTED AND NEED IT PLACED.
            	// HINT HINT HINT



                hexActor.addListener(new EventListener() {

    				
    				@Override
    				public boolean handle(Event event) {
    					if(event instanceof InputEvent){
    						InputEvent inputEvent = (InputEvent) event;
    						if(inputEvent.getType() == Type.touchDown){
    						    //hexActor.hit(inputEvent.getStageX(), inputEvent.getStageY(), true);
    							hexActor.setColor(Color.BLACK);
                                System.out.println(hexActor.hexagon.getCubeCoordinate());
                            } else if (inputEvent.getType() == Type.exit){
    						    hexActor.setColor(Color.WHITE);
                            }
    					}
    					
    					return false;
    				}
    			});


            }
        });

        
        this.root = new Table();
        this.root.setFillParent(true);
        
        //root.debug(Debug.all);
        
        // Create the score column
        Table scoreColumn = new Table();      
        //scoreColumn.debug(Debug.all);
        
        scoreColumn.add(new Label("Player 1 Score", skin)).expand().top();
        scoreColumn.row();
        scoreColumn.add(new Label("Player 2 Score", skin)).expand().top();
        
        root.add(scoreColumn).expand().fill();
        
        // Create the board
        Table boardColumn = new Table();


        //boardColumn.debug(Debug.all);
        boardColumn.add(hexagonView).colspan(5).expand().fill();
        boardColumn.row().bottom().colspan(5);


        tileButtonAtlas = new TextureAtlas("HexagonsPack.pack");
        tileButtonSkin = new Skin();
        tileButtonSkin.addRegions(hexButtonAtlas);
        tileStyle = new ImageButton.ImageButtonStyle();
        tileStyle.up = tileButtonSkin.getDrawable("Tile51");
        
        for (int i = 0; i < 6; i++) {

			//Pieces.distributePieces();
			boardColumn.add(new ImageButton(tileStyle)).expandX();

		}
        
        root.add(boardColumn).expand().fill();
        root.pack();
                
        addActor(root);



    }


    public void dispose(){
        super.dispose();
        batch.dispose();
    }

    /*public void show(){
    	mainMenuButton = new Texture("mainmenu.png");
    	}
    	*/


}



