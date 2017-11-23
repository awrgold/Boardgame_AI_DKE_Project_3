package com.game;

import GameBoardAssets.HexagonActor;
import GameConstants.Constants;
import Interfaces.GroupView;
import Tools.Link;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

import java.util.Arrays;

//import static Screens.GameScreen.manager;
import static Screens.GameScreen.tileView;


public class Tile {
    private HexagonalGrid<Link> grid;
    private Group tileGroup;
    private Sprite[] colors;
    private boolean selected;
    private HexagonActor second;

    public Tile(Sprite[] colors){
        this.grid = Constants.tile.build();
        this.tileGroup = new Group();
        this.colors = colors;
        this.selected = false;
    }

    public Sprite[] getColors(){
        return this.colors;
    }

    public boolean isSelected(){
        return selected;
    }

    public void deselect(){
        selected = false;
        tileGroup.moveBy(0, -30);
    }

    public Group generateTile(){
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                final HexagonActor hexTile = new HexagonActor(hexagon);

                //give both the sprites
                if(hexagon.getGridX() == 0) {
                    hexTile.setSprite(colors[0]);
                } else {
                    hexTile.setSprite(colors[1]);
                }

                hexTile.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());

                //and pass everything in tileGroup
                tileGroup.addActor(hexTile);
                hexagon.setSatelliteData(new Link(hexTile));

                /*
                        Create a click listener for the tiles in your hand: --------------------------
                         */
                hexTile.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println(hexTile.getHexColor());
                        selected = true;
                        tileGroup.moveBy(0, 10);

                        //manager.getGamingPlayer().setTileToMove1(hexTile);
                        Actor two = hexTile.getParent().getChildren().get(Math.abs(hexTile.getHexagon().getGridX() - 1));
                        if (two instanceof HexagonActor){
                            second = (HexagonActor) two;

                            //manager.getGamingPlayer().setTileToMove2(second);
                        }




/*
                        if(touched[0] != null && touched[1] != null) {
                            selectedTile.moveBy(0, -30);
                        }


                        if(Arrays.asList(tileView[manager.getGamingPlayer().getPlayerNo() - 1]).contains(hexTile.getParent())){

                            hexTile.getParent().moveBy(0, 30);
                            touched[0] = hexTile.getSprite();
                            Actor two = hexTile.getParent().getChildren().get(Math.abs(hexTile.getHexagon().getGridX() - 1));

                            if (two instanceof HexagonActor){
                                HexagonActor other = (HexagonActor) two;
                                touched[1] = other.getSprite();
                            }

                            //find the index in player's hand
                            for (Sprite[] s : playerP.getGamePieces()){
                                if((s[0] == touched[0] && s[1] == touched[1]) || (s[1] == touched[0] && s[0] == touched[1])){
                                    selectedTileIndex = playerP.getGamePieces().indexOf(s);
                                }
                            }
                            selectedTile = hexTile.getParent();

                        } else {
                            // selectedTile.moveBy(0, 30);
                            System.out.println("It's the turn of player " + manager.getGamingPlayer().getPlayerNo());
                        }*/
                    }
                });
            }
        });

        return tileGroup;


    }

}
