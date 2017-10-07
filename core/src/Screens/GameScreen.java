package Screens;


import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.HEXAGONAL;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.metal.MetalBorders.TableHeaderBorder;

import com.sun.org.apache.xerces.internal.impl.Constants;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.HexagonalGridLayout;
import org.codetome.hexameter.core.api.Point;
import org.codetome.hexameter.core.api.defaults.DefaultSatelliteData;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table.Debug;

import rx.functions.Action1;


public class GameScreen extends AbstractScreen {
		
	public static class Link extends DefaultSatelliteData {
		
		public HexagonActor actor;

		public Link(HexagonActor actor) {
			this.actor = actor;
		} 
		
	}
	
	public static class HexagonActor extends Actor {
		
		protected Hexagon<Link> hexagon;
		
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
			}
			
			setSize(hexagon.getInternalBoundingBox().width, hexagon.getInternalBoundingBox().height);			
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
    
    private SpriteBatch batch;
    private Skin skin;
    
    //public HexagonalGridCalculator calculator = builder.buildCalculatorFor(grid);

    public GameScreen() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        Gdx.graphics.setWindowedMode(1280, 720);
    }


    // Subclasses must load actors in this method
    @SuppressWarnings("unchecked")
	public void buildStage(){

        // ...
        final int GRID_HEIGHT = 11;
        final int GRID_WIDTH = 11;
        final HexagonalGridLayout GRID_LAYOUT = HEXAGONAL;
        final HexagonOrientation ORIENTATION = POINTY_TOP;
        final double RADIUS = GameConstants.Constants.getHexRadius();

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
        
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
            	
            	// Create the Actor and link it to the hexagon (and vice-versa)
            	final HexagonActor hexActor = new HexagonActor(hexagon);
            	hexActor.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());
            	hexagonView.addActor(hexActor);
            	
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
    						if(inputEvent.getType() == Type.enter){
    							hexActor.setColor(Color.BLACK);
    						} else if(inputEvent.getType() == Type.exit) {
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
        
        root.debug(Debug.all);
        
        // Create the score column
        Table scoreColumn = new Table();      
        scoreColumn.debug(Debug.all);
        
        scoreColumn.add(new Label("Player 1 Score", skin)).expand().top();
        scoreColumn.row();
        scoreColumn.add(new Label("Player 2 Score", skin)).expand().top();
        
        root.add(scoreColumn).expand().fill();
        
        // Create the board
        Table boardColumn = new Table();      
        //boardColumn.debug(Debug.all);
        boardColumn.add(hexagonView).colspan(6).expand().width(440).height(360);
        boardColumn.row();


        for (int i = 0; i < 6; i++) {
			boardColumn.add(new TextButton("GAME TILES", skin)).expandX().height(200);
		}
        
        root.add(boardColumn).expand().fill();
        root.pack();
                
        addActor(root);
        
        
    }


    public void dispose(){
        super.dispose();
        batch.dispose();
    }
}
