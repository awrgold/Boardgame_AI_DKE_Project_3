package com.game.Components.PlayerAssets;

import com.game.Components.GameConstants.Color;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.GameConstants.Constants;
import com.game.Components.Tools.GroupView;
import com.game.Components.Tools.Link;
import com.game.Components.Tools.TestHexagonActor;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

//import java.util.Arrays;

//import static com.game.Screens.GameScreen.tileView;

public class TestTile {

    private HexagonalGrid<Link> grid;

    private Color[] colors;
    private Color c1;
    private Color c2;
    private boolean selected;
    private TestHexagonActor[] actors;
    private TestHexagonActor first;


    public TestTile(Color[] colors){
        super();
        this.colors = colors;
        this.selected = false;
        this.actors = new TestHexagonActor[2];
        create();
    }

    public void create() {

        this.grid = Constants.tile.build();

        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                final TestHexagonActor hexTile = new TestHexagonActor(hexagon);

                //give both the sprites
                if(hexagon.getGridX() == 0) {
                    hexTile.setHexColor(colors[0]);
                    actors[0] = hexTile;
                } else {
                    hexTile.setHexColor(colors[1]);
                    actors[1] = hexTile;
                }

                // hexTile.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());

                //and pass everything in tileGroup
                //addActor(hexTile);
                hexagon.setSatelliteData(new Link(hexTile));

            }
        });
    }

    public void setActor(TestHexagonActor actor, int index){
        actors[index] = actor;
    }

    /*
    public void act(float delta) {

        super.act(delta);
    }
    */

    public void setSelected(boolean s) {
        selected = s;
    }

    public TestHexagonActor[] getActors() {
        return actors;
    }

    public TestHexagonActor getActor(int index){
        return actors[index];
    }

    public Color[] getColors(){
        return this.colors;
    }

    public boolean isSelected(){
        return selected;
    }

}
