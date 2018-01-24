package com.game.Components.GameAssets;

import com.game.Components.Tools.GroupView;
import com.game.Components.Tools.Link;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import rx.functions.Action1;

public class BoardView extends GroupView {

    private HexagonalGrid<Link> grid;

    public BoardView(HexagonalGrid<Link> grid){
    this.grid=grid;
    create();
    }
    public void create(){
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {

                //FOR EACH HEXAGON
                if (hexagon.getSatelliteData().isPresent()) {
                    Link hexLink = (Link) hexagon.getSatelliteData().get();
                    HexagonActor currentHexActor = hexLink.getActor();
                    currentHexActor.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());
                    addActor(currentHexActor);

                }

            }
            });
    }
    public void act(HexagonalGrid<Link> grid){
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {

                //FOR EACH HEXAGON
                if (hexagon.getSatelliteData().isPresent()) {
                    Link hexLink = (Link) hexagon.getSatelliteData().get();
                    HexagonActor currentHexActor = hexLink.getActor();
                    currentHexActor.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());
                    addActor(currentHexActor);
                }

            }
        });
    }
    public void dispose() {

    }
}
