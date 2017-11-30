package GameBoardAssets;

import Screens.GameScreen;
import Tools.Link;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import javafx.application.Preloader;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.Point;
import org.codetome.hexameter.core.api.contract.SatelliteData;
import GameConstants.Constants;

import java.util.List;

public class HexagonActor extends Actor{

    protected Hexagon<Link> hexagon;
    private Sprite sprite;
    private float[] vertices;
    private ShapeRenderer renderer = new ShapeRenderer();
    private String hexColor = null;



    public HexagonActor(Hexagon<Link> hexagon) {
        this.hexagon = hexagon;


        List<Point> points =  (List<Point>) hexagon.getPoints();
        this.vertices = new float[points.size() * 2];

        for (int i = 0; i < points.size(); i++) {
            // Translate to local coordinates
            this.vertices[i * 2] = (float) points.get(i).getCoordinateX() - (float) hexagon.getCenterX();
            this.vertices[i * 2 + 1] = (float) points.get(i).getCoordinateY() - (float) hexagon.getCenterY();
            //setBounds(this.vertices[i * 2],this.vertices[i * 2 + 1], hexagon.getInternalBoundingBox().width, hexagon.getInternalBoundingBox().height);
        }
        setSize(hexagon.getInternalBoundingBox().width, hexagon.getInternalBoundingBox().height);
    }

    public void setHexColor(String color){
        if (color.equals("R")){
            this.hexColor = color;
            this.setSprite(Constants.redSprite);
            return;
        } if (color.equals("O")){
            this.hexColor = color;
            this.setSprite(Constants.orangeSprite);
            return;
        } if (color.equals("Y")){
            this.hexColor = color;
            this.setSprite(Constants.yellowSprite);
            return;
        } if (color.equals("B")){
            this.hexColor = color;
            this.setSprite(Constants.blueSprite);
            return;
        } if (color.equals("P")){
            this.hexColor = color;
            this.setSprite(Constants.purpleSprite);
            return;
        } if (color.equals("V")){
            this.hexColor = color;
            this.setSprite(Constants.violetSprite);
            return;
        } if (color.equals("EMPTY")){
            this.hexColor = color;
            this.setSprite(Constants.emptySprite);

        } else{
            System.out.println("Invalid color choice, use the CAPITAL first letter of the color");
            System.out.println("Choices: R, O, Y, B, P, V");
        }
    }

    public String getHexColor(){
        return this.hexColor;
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;

        if(sprite == Constants.emptySprite){ this.hexColor = "EMPTY"; }
        if(sprite == Constants.redSprite){ this.hexColor = "R"; }
        if(sprite == Constants.blueSprite){ this.hexColor = "B"; }
        if(sprite == Constants.purpleSprite){ this.hexColor = "P"; }
        if(sprite == Constants.violetSprite){ this.hexColor = "V"; }
        if(sprite == Constants.orangeSprite){ this.hexColor = "O"; }
        if(sprite == Constants.yellowSprite){ this.hexColor = "Y"; }



    }

    public Sprite getSprite(){ return this.sprite; }

    public void draw (Batch batch, float parentAlpha) {

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
        for (int i = 0; i < vertices.length; i+=2) {

            float x1 = vertices[i], y1 = vertices[i+1];
            float x2 = vertices[(i + 2) % vertices.length], y2 = vertices[(i + 3) % vertices.length];



            renderer.triangle(x1 + getWidth() / 2,
                    y1 + getHeight() / 2,
                    x2 + getWidth() / 2,
                    y2 + getHeight() / 2, getWidth() / 2, getHeight() / 2);
        }

        renderer.end();
        batch.begin();

        //draw the sprite on the actor
        batch.draw(sprite, getX() - 10, getY() - 16, getWidth() + 20, getHeight() + 32);
    }

    public Hexagon<Link> getHexagon(){
        return hexagon;
    }

    // Gets the color of a sprite
    public String getSpriteColor(){
        Texture texture = this.getSprite().getTexture();
        String path = ((FileTextureData)texture.getTextureData()).getFileHandle().path();

        String violet = "colours/violet.png";
        String red =    "colours/red.png";
        String blue =   "colours/blue.png";
        String yellow = "colours/yellow.png";
        String orange = "colours/orange.png";
        String purple = "colours/purple.png";
        String empty =  "4players.png";



        if(path.equals(purple)){
            path = "P";
            return path;
        }

        else if(path.equals(red)){
            path = "R";
            return path;
        }

        else if(path.equals(blue)){
            path = "B";
            return path;
        }

        else if(path.equals(yellow)){
            path = "Y";
            return path;
        }

        else if(path.equals(orange)){
            path = "O";
            return path;
        }

        else if(path.equals(violet)){
            path = "V";
            return path;
        }

        else if(path.equals(empty)){
            path = "EMPTY";
            return path;
        }

        else {
            return null;
        }
    }

}
