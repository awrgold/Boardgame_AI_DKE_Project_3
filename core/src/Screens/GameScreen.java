package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;



public class GameScreen extends AbstractScreen{
    private SpriteBatch batch;
  // final GameIngenious game;
 // private OrthographicCamera cam;
 //   private PolygonSpriteBatch pSB;
    //private HexagonIm hexagon1, hexagon2;
    public static final int HEXAGON_WIDTH = 100;
    public static final int HEXAGON_HEIGHT = (int) (HEXAGON_WIDTH / (Math.sqrt(3) / 2));
    //Ratio of width and height of a regular hexagon.
    private Skin mySkin;
 //   private Table table;
//    Texture textureA;
//    TextureRegion tregionA;
//    Image hexA ;
//    Texture textureB;
//    TextureRegion tregionB;
//    Sprite sp1;
//    Sprite sp2;
//    Image hexB ;
//    Stage stage;
//    int align = Align.center;


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
//        textureA = new Texture(Gdx.files.internal("Images/hexagonew.png"));
//        tregionA = new TextureRegion(textureA,textureA.getWidth(),textureA.getHeight());
//
//        hexA = new Image(tregionA);
//        sp1= new Sprite(textureA) ;
//        sp1.setSize(textureA.getWidth(),textureA.getHeight());
//        sp1.setOrigin(sp1.getWidth()/2, sp1.getHeight()/2);
//        sp1.setPosition(-sp1.getWidth()/2, -sp1.getHeight()/2);
//        textureB = new Texture(Gdx.files.internal("Images/hexagonns.png"));
//        tregionB = new TextureRegion(textureB,textureB.getWidth(),textureB.getHeight());
//        sp2= new Sprite(textureB) ;
//        sp2.setSize(textureB.getWidth(),textureB.getHeight());
//        sp2.setOrigin(sp2.getWidth()/2, sp2.getHeight()/2);
//        sp2.setPosition(-sp2.getWidth()/2, -sp2.getHeight()/2);
//        hexB = new Image(tregionB);
//       // hexA.setAlign(align);
//       // hexB.setAlign(align);
//        addActor(hexA);
//        addActor(hexB);

//addActor();
    }





    public void render(float delta) {
         Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        //Gdx.gl.glClearColor(0,0,0,1);
       // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        super.act(delta);
        super.draw();
//batch.draw(sp1,0,0);
//batch.draw(sp2,0,0);
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
        //textureA.dispose();
        //textureB.dispose();
    }
}
