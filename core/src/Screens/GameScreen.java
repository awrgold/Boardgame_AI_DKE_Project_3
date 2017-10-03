package Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.game.Board;
import com.game.PolyHex;
import org.codetome.hexameter.core.api.*;
import org.codetome.hexameter.core.api.contract.SatelliteData;
import org.codetome.hexameter.core.backport.Optional;
import rx.Observable;
import rx.functions.Action1;

import java.util.List;

import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.HEXAGONAL;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;


public class GameScreen extends AbstractScreen{
    private SpriteBatch batch;

    public static final int HEXAGON_WIDTH = 100;
    public static final int HEXAGON_HEIGHT = 100;
    public static Observable<Hexagon> hexagons;

    //Ratio of width and height of a regular hexagon.
    private Skin mySkin;
    //public HexagonalGridCalculator calculator = builder.buildCalculatorFor(grid);

    public GameScreen() {
        super();
        //skin = new Skin(Gdx.files.internal("uiskin.json"));

        Gdx.graphics.setWindowedMode(1920,1080);
        batch = new SpriteBatch();


        super.clear();
        System.out.println("it started");
    }


    // Subclasses must load actors in this method
    public void buildStage(){

        // ...
        final int GRID_HEIGHT = 11;
        final int GRID_WIDTH = 11;
        final HexagonalGridLayout GRID_LAYOUT = HEXAGONAL;
        final HexagonOrientation ORIENTATION = POINTY_TOP;
        final double RADIUS = 30;

        // ...
        HexagonalGridBuilder builder = new HexagonalGridBuilder()
                .setGridHeight(GRID_HEIGHT)
                .setGridWidth(GRID_WIDTH)
                .setGridLayout(GRID_LAYOUT)
                .setOrientation(ORIENTATION)
                .setRadius(RADIUS);

        HexagonalGrid grid = builder.build();
        hexagons = grid.getHexagons();



    }





    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draw inside here
        batch.begin();
        super.act(delta);
        super.draw();
        hexagons.forEach(new Action1<Hexagon>() {
            @Override
            public void call(Hexagon hexagon) {
                List<Point> points = hexagon.getPoints();
                for (Point point : points) {
                    draw();
                    System.out.println(point.getCoordinateX() + " " + point.getCoordinateY());
                }
            }
        });

        batch.end();

    }




    public void dispose(){
        super.dispose();
        mySkin.dispose();
        batch.dispose();
        //textureA.dispose();
        //textureB.dispose();
    }
}
