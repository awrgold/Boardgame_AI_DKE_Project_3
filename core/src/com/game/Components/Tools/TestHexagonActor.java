package com.game.Components.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.game.Components.GameConstants.Color;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.Point;
import com.game.Components.GameConstants.Constants;

import java.util.List;

public class TestHexagonActor extends Actor{

    protected Hexagon<Link> hexagon;
    private float[] vertices;
    private Color hexColor = null;



    public TestHexagonActor(Hexagon<Link> hexagon) {
        this.hexagon = hexagon;


        List<Point> points =  (List<Point>) hexagon.getPoints();
        this.vertices = new float[points.size() * 2];

        for (int i = 0; i < points.size(); i++) {
            // Translate to local coordinates
            this.vertices[i * 2] = (float) points.get(i).getCoordinateX() - (float) hexagon.getCenterX();
            this.vertices[i * 2 + 1] = (float) points.get(i).getCoordinateY() - (float) hexagon.getCenterY();

        }
        setSize(hexagon.getInternalBoundingBox().width, hexagon.getInternalBoundingBox().height);
    }

    public void setHexColor(Color color){
        if (color.equals(Color.RED)){
            this.hexColor = color;
            return;
        } if (color.equals(Color.ORANGE)){
            this.hexColor = color;
            return;
        } if (color.equals(Color.YELLOW)){
            this.hexColor = color;
            return;
        } if (color.equals(Color.BLUE)){
            this.hexColor = color;
            return;
        } if (color.equals(Color.PURPLE)){
            this.hexColor = color;
            return;
        } if (color.equals(Color.VIOLET)){
            this.hexColor = color;
            return;
        } if (color.equals(Color.EMPTY)){
            this.hexColor = color;
        } else{
            System.out.println("Invalid color choice, use the CAPITAL name of the color");
            System.out.println("Choices: RED, ORANGE, YELLOW, BLUE, PURPLE, VIOLEt");
        }
    }

    public Color getHexColor(){
        return hexColor;
    }

    public Hexagon<Link> getHexagon(){
        return hexagon;
    }


}
