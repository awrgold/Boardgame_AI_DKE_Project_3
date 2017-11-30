package com.game;

import GameBoardAssets.HexagonActor;
import GameConstants.Constants;
import Interfaces.GroupView;
import Tools.Link;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

//import java.util.Arrays;

//import static Screens.GameScreen.tileView;

public class Tile extends GroupView{
    private HexagonalGrid<Link> grid;
    //private boolean c;
    private Sprite[] colors;
    private boolean selected;
    private HexagonActor hexA;
    private HexagonActor hexB;
    private HexagonActor hex;
    private HexagonActor first;

    public Tile(Sprite[] colors){
        super();
        this.colors = colors;
        this.selected = false;
        //this.c = false;
        create();

    }

    public void create(){
        this.grid = Constants.tile.build();
    }

    public void act( float delta) {
        super.act(delta);

        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
    @Override
    public void call(Hexagon hexagon) {
    final HexagonActor hexTile = new HexagonActor(hexagon);

        //give both the sprites
        if(hexagon.getGridX() == 0) {
        hexTile.setSprite(colors[0]);
        setHexA(hexTile);

        } else {
        hexTile.setSprite(colors[1]);
        setHexB(hexTile);

        }

        hexTile.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());

        //and pass everything in tileGroup
        addActor(hexTile);
        hexagon.setSatelliteData(new Link(hexTile));


                /*
                        Create a click listener for the tiles in your hand: --------------------------
                         */

        hexTile.addListener(new ClickListener(){
@Override
public void clicked(InputEvent event, float x, float y) {


    // handleTouch();
//if(!isSelected()) {
//    hex = hexTile;
//    System.out.println(getPieceColors(hexTile) + " selected");
//    first = hexTile;
//    setSelected(true);
//    moveBy(0,30);
//}else{
//    moveBy(0,-30);
//    setSelected(false);
//    System.out.println(getPieceColors(hexTile) + " deselected");
//}


        //manager.getGamingPlayer().setTileToMove1(hexTile);
        //Actor two = hexTile.getParent().getChildren().get(Math.abs(hexTile.getHexagon().getGridX() - 1));
        //if (two instanceof HexagonActor){
        //second = (HexagonActor) two;
        //manager.getGamingPlayer().setTileToMove2(second);
        //}




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
    }
    public void setSelected(boolean s) {
    this.selected=s;
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


    public String[] getPieceColors(HexagonActor hexTile) {
        HexagonActor temp=null;
        if(hexTile != getHexA()){
            temp = getHexA();
        }
        if(hexTile != getHexB()){
            temp = getHexB();
        }
        String[] pColor = new String[2];
        pColor[0] = hexTile.getHexColor();
        pColor[1] = temp.getHexColor();

        return pColor;
    }

    public HexagonActor getHexA() {
        return hexA;
    }

    public void setHexA(HexagonActor hexA) {
        this.hexA = hexA;
    }

    public HexagonActor getHexB() {
        return hexB;
    }

    public void setHexB(HexagonActor hexB) {
        this.hexB = hexB;
    }

    public String[] getTileColors(){
        String[] colors = new String[2];

        colors[0] = first.getHexColor();

        colors[1] = getSecond().getHexColor();

        return colors;
    }

}
