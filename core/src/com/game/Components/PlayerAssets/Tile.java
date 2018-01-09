package com.game.Components.PlayerAssets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.GameConstants.Constants;
import com.game.Components.Tools.GroupView;
import com.game.Components.Tools.Link;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

//import java.util.Arrays;

//import static com.game.Screens.GameScreen.tileView;

public class Tile {

    private HexagonalGrid<Link> grid;

    private String[] colors;
    private boolean selected;
    private HexagonActor first;

    public Tile(String[] colors){
        this.colors = colors;
        this.selected = false;
        create();
    }

    public void create() {

        this.grid = Constants.tile.build();

        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                //give the colors
                if(hexagon.getGridX() == 0) {
                    hexagon.setSatelliteData(new Link(colors[0]));
                } else {
                    hexagon.setSatelliteData(new Link(colors[1]));
                }
            }
        });
    }

    public Group displayTile(){
        Group tileGroup = new Group();
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                HexagonActor hexTile = new HexagonActor(hexagon);
                hexTile.setHexColor();
                hexTile.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());

                tileGroup.addActor(hexTile);
            }

        });

        return tileGroup;
    }

    public void setSelected(boolean s) {
        selected = s;
    }

    public void setFirst(HexagonActor clicked){
        first = clicked;
    }

    public HexagonActor getFirst() {
        return first;
    }

    public HexagonActor getSecond(){
        Actor two = first.getParent().getChildren().get(Math.abs(first.getHexagon().getGridX() - 1));

        HexagonActor second = null;
        if (two instanceof HexagonActor){
            HexagonActor other = (HexagonActor) two;
            second = other;
        }
        return second;
    }

    public String[] getColors(){
        return this.colors;
    }

    public boolean isSelected(){
        return selected;
    }

}
