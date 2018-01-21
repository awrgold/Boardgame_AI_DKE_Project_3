package com.game.Components.PlayerAssets;

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

public class Tile extends GroupView {

    private HexagonalGrid<Link> grid;

    private Sprite[] colors;
    private boolean selected;
    private HexagonActor[] actors;
    private HexagonActor first;

    public Tile(Sprite[] colors){
        super();
        this.colors = colors;
        this.selected = false;
        this.actors = new HexagonActor[2];
        create();
    }

    public void create() {

        this.grid = Constants.tile.build();

        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                final HexagonActor hexTile = new HexagonActor(hexagon);

                //give both the sprites
                if(hexagon.getGridX() == 0) {
                    hexTile.setSprite(colors[0]);
                    actors[0] = hexTile;
                } else {
                    hexTile.setSprite(colors[1]);
                    actors[1] = hexTile;
                }

                hexTile.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());
                //and pass everything in tileGroup
                addActor(hexTile);
                hexagon.setSatelliteData(new Link(hexTile));


            }

        });

    }

  //  public void act() {

//        for (HexagonActor hexact : actors){
//            hexact.act();
//        }
//        this.setGrid(grid);
//        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
//            @Override
//            public void call(Hexagon hexagon) {
//               // final HexagonActor hexTile = new HexagonActor(hexagon);
//                Link hexLink = (Link) hexagon.getSatelliteData().get();
//                HexagonActor hexTile = hexLink.getActor();
//                //give both the sprites
//                if(hexagon.getGridX() == 0) {
//                    hexTile.setSprite(colors[0]);
//                    actors[0] = hexTile;
//                } else {
//                    hexTile.setSprite(colors[1]);
//                    actors[1] = hexTile;
//                }
//
//               // HexagonActor HexTile = hexLink.getActor();
//                hexTile.act();
//               // hexTile.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());
//                //and pass everything in tileGroup
//               // addActor(hexTile);
//                //hexagon.setSatelliteData(new Link(hexTile));
//
//
//            }
//
//        });

 //   }

    @Override
    public void dispose() {

//            actors[0].dispose();
//            actors[1].dispose();


        for(HexagonActor actor : actors){
    actor.dispose();
        }
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
        Actor two = first.getParent().getChildren().get(Math.abs(first.getHexagon().getGridX() - 1));

        HexagonActor second = null;
        if (two instanceof HexagonActor){
            HexagonActor other = (HexagonActor) two;
            second = other;
        }
        return second;
    }

    public Sprite[] getColors(){
        return this.colors;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setGrid(HexagonalGrid<Link> grid) {
        this.grid = grid;
    }
}
