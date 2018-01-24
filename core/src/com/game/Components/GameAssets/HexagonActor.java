package com.game.Components.GameAssets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.game.Components.GameConstants.Color;
import com.game.Components.Tools.Link;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.Point;
import com.game.Components.GameConstants.Constants;

import java.util.List;

public class HexagonActor extends Actor {
    private Sprite sprite;
    protected Hexagon<Link> hexagon;
    private float[] vertices;
    private Color color = null;
    private ShapeRenderer renderer;


    public HexagonActor(Hexagon<Link> hexagon) {
        this.hexagon = hexagon;
        this.renderer = new ShapeRenderer();

        List<Point> points =  (List<Point>) hexagon.getPoints();
        this.vertices = new float[points.size() * 2];

        for (int i = 0; i < points.size(); i++) {
            // Translate to local coordinates
            this.vertices[i * 2] = (float) points.get(i).getCoordinateX() - (float) hexagon.getCenterX();
            this.vertices[i * 2 + 1] = (float) points.get(i).getCoordinateY() - (float) hexagon.getCenterY();

        }
        setSize(hexagon.getInternalBoundingBox().width, hexagon.getInternalBoundingBox().height);
    }

    //public void setSprite(Sprite sprite){
//        this.sprite = sprite;
//}
    public void draw(Batch batch, float parentAlpha) {
        // Sprite sprite = Constants.emptySprite;
        batch.end();


        // Required just so everything displays at the correct position
        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());

        // Move to the location of this actor
        renderer.translate(getX(), getY(), 0);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(getColor());


        // Go through all vertices, draw triangles for each, using the next vertex.
        // Go in bounds of two (x,y) pairs.
        for (int i = 0; i < vertices.length; i += 2) {

            float x1 = vertices[i], y1 = vertices[i + 1];
            float x2 = vertices[(i + 2) % vertices.length], y2 = vertices[(i + 3) % vertices.length];


            renderer.triangle(x1 + getWidth() / 2,
                    y1 + getHeight() / 2,
                    x2 + getWidth() / 2,
                    y2 + getHeight() / 2, getWidth() / 2, getHeight() / 2);
        }

        renderer.end();
        batch.begin();
        setSprite();
        //draw the sprite on the actor
       batch.draw(sprite, getX() - 10, getY() - 16, getWidth() + 20, getHeight() + 32);
    }

    public void setHexColor(Color color){
        if (color.equals(Color.RED)){
            this.color = color;
            return;
        } if (color.equals(Color.ORANGE)){
            this.color = color;
            return;
        } if (color.equals(Color.YELLOW)){
            this.color = color;
            return;
        } if (color.equals(Color.BLUE)){
            this.color = color;
            return;
        } if (color.equals(Color.PURPLE)){
            this.color = color;
            return;
        } if (color.equals(Color.VIOLET)){
            this.color = color;
            return;
        } if (color.equals(Color.EMPTY)){
            this.color = color;
        } else{
            System.out.println("Invalid color choice, use the CAPITAL name of the color");
            System.out.println("Choices: RED, ORANGE, YELLOW, BLUE, PURPLE, VIOLEt");
        }
    }

    public Color getHexColor(){
        return color;
    }

    public Hexagon<Link> getHexagon(){
        return hexagon;
    }
    public void act(Color color){
        this.color = color;

        }


    public void setSprite() {
        if (color.equals(Color.RED)){
            sprite=Constants.redSprite;
            return;
        } if (color.equals(Color.ORANGE)){
            this.sprite=Constants.orangeSprite;
            return;
        } if (color.equals(Color.YELLOW)){
            this.sprite=Constants.yellowSprite;
            return;
        } if (color.equals(Color.BLUE)){
            this.sprite=Constants.blueSprite;
            return;
        } if (color.equals(Color.PURPLE)){
            this.sprite=Constants.purpleSprite;
            return;
        } if (color.equals(Color.VIOLET)){
            this.sprite=Constants.violetSprite;
            return;
        } if (color.equals(Color.EMPTY)){
            this.sprite=Constants.emptySprite;
        } else{
            System.out.println("Invalid color choice, use the CAPITAL name of the color");
            System.out.println("Choices: RED, ORANGE, YELLOW, BLUE, PURPLE, VIOLEt");
        }
    }



//    public HexagonActor copy(HexagonActor actor){
//        HexagonActor newActor = new HexagonActor(actor.getHexagon());
//        return newActor;
//    }

}
