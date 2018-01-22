package com.game.Components.PlayerAssets;

import com.game.Components.GameConstants.Color;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.GameConstants.Constants;
import com.game.Components.Tools.GroupView;
import com.game.Components.Tools.Link;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.backport.Optional;
import rx.functions.Action1;

import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;

//import java.util.Arrays;

//import static com.game.Screens.GameScreen.tileView;

public class Tile extends GroupView {

    private HexagonalGrid<Link> grid;

    private Color[] colors;
    private boolean selected;
    private HexagonActor[] actors;
    private HexagonActor first;

    public Tile(Color[] colors){
        //super();
        this.colors = colors;
        this.selected = false;
        this.actors = new HexagonActor[2];
        create();
    }

    public Tile cloneTile(){

        Tile clonedTile = new Tile(getColors());
        if (first != null){
            Optional toCopy = clonedTile.grid.getByCubeCoordinate(first.getHexagon().getCubeCoordinate());
            if (toCopy.isPresent()){
                Hexagon copy = (Hexagon) toCopy.get();
                if (copy.getSatelliteData().isPresent()){
                    Link copyLink = (Link) copy.getSatelliteData().get();
                    HexagonActor newFirst = copyLink.getActor();

                    clonedTile.setFirst(newFirst);
                }

            }
        }
        //clonedTile.setFirst(first);
        return clonedTile;

    }

    public boolean isEqual(Tile one){
        //System.out.println(getColors()[0].toString() + one.getColors()[0].toString() + getColors()[1].toString() + one.getColors()[1].toString());
        //System.out.println(getColors() == one.getColors());
        return getColors() == one.getColors();

    }

    public HexagonalGrid<Link> getGrid() {
        return grid;
    }

    public void create() {

        HexagonalGridBuilder<Link> tileBuilder = new HexagonalGridBuilder<Link>()
                .setGridHeight(1)
                .setGridWidth(2)
                .setGridLayout(RECTANGULAR)
                .setOrientation(POINTY_TOP)
                .setRadius(40);

        this.grid = tileBuilder.build();

        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                final HexagonActor hexTile = new HexagonActor(hexagon);

                //give both the sprites
                if(hexagon.getGridX() == 0) {
                    hexTile.setHexColor(colors[0]);
                    actors[0] = hexTile;
                } else {
                    hexTile.setHexColor(colors[1]);
                    actors[1] = hexTile;
                }

                hexTile.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());

                //and pass everything in tileGroup
                addActor(hexTile);
                hexagon.setSatelliteData(new Link(hexTile));
            }
        });
    }

    public void act(float delta) {
        super.act(delta);
    }


    public void setSelected(boolean s) {
        selected = s;
    }

    public void setFirst(HexagonActor clicked){
        first = clicked;
    }

    public HexagonActor[] getActors() {
        return actors;
    }

    public HexagonActor getFirst() {
        return first;
    }

    public HexagonActor getSecond(){
        if (first == null){
            return null;
        }
        else {
            Actor two = first.getParent().getChildren().get(Math.abs(first.getHexagon().getGridX() - 1));

            HexagonActor second = null;
            if (two instanceof HexagonActor){
                HexagonActor other = (HexagonActor) two;
                second = other;
            }
            return second;
        }

    }

    public Color[] getColors(){
        return this.colors;
    }

    public boolean isSelected(){
        return selected;
    }

}
