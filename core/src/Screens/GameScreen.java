package Screens;


import GameBoardAssets.HexagonActor;
import GameBoardAssets.Link;
import GameBoardAssets.ScoreBarActor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Table.Debug;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.game.Score;
import org.codetome.hexameter.core.api.*;
import rx.functions.Action1;

import java.util.ArrayList;
import java.util.List;

import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.HEXAGONAL;


public class GameScreen extends AbstractScreen {
	// Ratio of width and height of a regular hexagon.
	//public static final int HEXAGON_WIDTH = 100;
	//public static final int HEXAGON_HEIGHT = 100;


	public HexagonalGrid grid;
	public Score score;
	public List<HexagonActor> hexagonActors = new ArrayList<HexagonActor>();
	public List<ScoreBarActor> scoreActors1 = new ArrayList<ScoreBarActor>();
	public List<ScoreBarActor> scoreActors2 = new ArrayList<ScoreBarActor>();
	private Table root;
	private Group hexagonView;
    private Group scoreView1;
    private Group scoreView2;
	private SpriteBatch batch;
	private Skin skin;

	//public HexagonalGridCalculator calculator = builder.buildCalculatorFor(grid);

	public GameScreen() {
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		Gdx.graphics.setWindowedMode(720, 480);
	}

    // Subclasses must load actors in this method
   // @SuppressWarnings("unchecked")
	public void buildStage(){

//      Board.createMap();
		//score...
		final int SCORE_WIDTH = 18;
		final int SCORE_HEIGHT = 6;

        // ...
        final int GRID_HEIGHT = 11;
        final int GRID_WIDTH = 11;
        final HexagonalGridLayout GRID_LAYOUT = HEXAGONAL;
        final HexagonOrientation ORIENTATION = POINTY_TOP;
        final double RADIUS = 30;
        //...
//        ScoreBarBuilder<Link> bbuild = new ScorebarBuilder<Link>();

        // ...
        HexagonalGridBuilder<Link> builder = new HexagonalGridBuilder<Link>()
                .setGridHeight(GRID_HEIGHT)
                .setGridWidth(GRID_WIDTH)
                .setGridLayout(GRID_LAYOUT)
                .setOrientation(ORIENTATION)
                .setRadius(RADIUS);

        HexagonalGrid<Link> grid = builder.build();
        this.grid = grid;
		//Create ScoreActor for both player scoreBars and attach it to the group
        this.scoreView1 = new Group();
        this.scoreView2 = new Group();
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

        //ADD SCOREACTORS .......



        this.root = new Table();
        this.root.setFillParent(true);
        
        root.debug(Debug.all);
        
        // Create the score column
        Table scoreColumn = new Table();      
        scoreColumn.debug(Debug.all);
//		for (int i = 0; i < 6; i++) {
//			scoreColumn.add(new TextButton("MEMEMEME", skin)).expandX().fill();
//		}
        scoreView1.setColor(Color.BLUE);
        scoreColumn.add(scoreView1).expand().fill();
       // scoreColumn.setColor(Color.BLUE);
        scoreColumn.row();
        scoreColumn.add(new Label("Player 1 Score", skin)).bottom();
        scoreColumn.row();
        scoreView2.setColor(Color.RED);
        scoreColumn.add(scoreView2).colspan(2).expand().fill();
      //  scoreColumn.setColor(Color.RED);
//		for (int i = 0; i < 6; i++) {
//			scoreColumn.add(new TextButton("MEMEMEME", skin)).colspan(1).expandX().left();
//		}
        scoreColumn.row();
        scoreColumn.add(new Label("Player 2 Score", skin)).bottom();

        root.add(scoreColumn).expand().fill();
        
        // Create the board
        Table boardColumn = new Table();      
        boardColumn.debug(Debug.all);
        boardColumn.add(hexagonView).colspan(6).expand().fill();
        boardColumn.row();

        for (int i = 0; i < 6; i++) {
			boardColumn.add(new TextButton("MEMEMEME", skin)).expandX().fill();
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
