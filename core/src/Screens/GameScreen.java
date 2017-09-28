package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;



public class GameScreen extends AbstractScreen{
    private SpriteBatch batch;

    private Skin mySkin;
    private Table table;
    Texture textureA;
    TextureRegion tregionA;
    Image hexA ;
    Texture textureB;
    Texture img;
    TextureRegion tregionB;
    Sprite sp1;
    Sprite sp2;
    Image hexB ;



    public GameScreen() {
        super();
        //skin = new Skin(Gdx.files.internal("uiskin.json"));

        Gdx.graphics.setWindowedMode(1080,840);
        batch = new SpriteBatch();

        super.clear();
        System.out.println("it started");
    }
    // Subclasses must load actors in this method
    public void buildStage(){
        // textureA = new Texture(Gdx.files.internal("Images/hexagonew.png"));
        textureA = new Texture(Gdx.files.internal("Tiles/b.png"));
        tregionA = new TextureRegion(textureA,textureA.getWidth(),textureA.getHeight());

        hexA = new Image(tregionA);
        sp1= new Sprite(textureA) ;
        //  sp1.setSize(textureA.getWidth(),textureA.getHeight());
//        sp1.setOrigin(sp1.getWidth()/2, sp1.getHeight()/2);
//        sp1.setPosition(-sp1.getWidth()/2, -sp1.getHeight()/2);
//        textureB = new Texture(Gdx.files.internal("Images/hexagonns.png"));
        textureB = new Texture(Gdx.files.internal("Tiles/mm.png"));
        tregionB = new TextureRegion(textureB,textureB.getWidth()/2,textureB.getHeight());

        // tregionB.setRegion(0,0,textureA.getWidth(),textureA.getHeight()/2);

        sp2= new Sprite(textureB) ;
        sp2.setSize(textureB.getWidth(),textureB.getHeight());
        sp2.setOrigin(sp2.getWidth()/2, sp2.getHeight()/2);
        sp2.setPosition(-sp2.getWidth()/2, -sp2.getHeight()/2);
        hexB = new Image(tregionB);
        addActor(hexB);
        //  addActor(hexA);

//addActor();
    }





    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        //Gdx.gl.glClearColor(0,0,0,1);
        // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        super.act(delta);
        //super.draw();
        batch.draw(textureA,100,200,textureA.getWidth(),textureA.getHeight());
        batch.draw(tregionB,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2,textureB.getWidth()/2,textureB.getHeight());
//batch.draw(tregionB,0,0);
        batch.end();
        // shape.begin(ShapeRenderer.ShapeType.Filled);
//        shape.setColor(Color.RED);
//        shape.circle(100,100,50);
//        shape.setColor(Color.BLUE);
//        shape.rect(300,300,20,50);
//
//        shape.end();
    }





    public void dispose(){
        super.dispose();
        mySkin.dispose();
        batch.dispose();
        textureA.dispose();
        textureB.dispose();
    }
}
