package com.game;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PolyHex {

    private PolygonRegion polyRegion;
    private PolygonSprite sprite;
    private Vector2 pos;

    PolyHex(Vector2 pos) {
        this.pos = pos;
       // this.sprite = createPolygonSprite();
    }

 //   private PolygonSprite createPolygonSprite() {

//        int hexWidth = GameScreen.HEXAGON_WIDTH;
//        int hexHeight = GameScreen.HEXAGON_HEIGHT;
//
//        float[] vertices = {
//                pos.x + hexWidth / 2, pos.y,
//                pos.x + hexWidth, pos.y + hexHeight * .25f,
//                pos.x + hexWidth, pos.y + hexHeight * .75f,
//                pos.x + hexWidth / 2, pos.y + hexHeight,
//                pos.x, pos.y + hexHeight * .75f,
//                pos.x, pos.y + hexHeight * .25f
//        };

//        sprite = new PolygonSprite(polyRegion);
//        sprite.setOrigin(pos.x + hexWidth / 2, pos.y + hexHeight / 2);
//        return sprite;
//    }

    void draw(PolygonSpriteBatch pSB) {
        this.sprite.draw(pSB);
    }

}


