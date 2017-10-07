package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class MenuScreen extends AbstractScreen {


    private SpriteBatch batch;
   // private Skin skin;
    private Table table;
    private TextButton startb;
    private TextButton quitb;
    private TextButton extrab;
    private Label title;
    private Dialog dialog;
    private Texture background ;
    private TextureRegion tregion;
    private Image bck ;
    private TextureAtlas atlas;

    public MenuScreen() {
       super();

//        skin = new Skin(Gdx.files.internal("uiskin.json"));
//        skin.addRegions(atlas);
 //      title = new Label("INGENIOUS",skin,"Default");

        //background image
        batch = new SpriteBatch();
       // Texture background = new Texture(Gdx.files.internal("backgrd.png"));
        background = new Texture(Gdx.files.internal("hexmenu.png"));
        tregion = new TextureRegion(background,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        bck = new Image(tregion);




    }


public void buildStage(){

    table = new Table();
   // table.setFillParent(true);
    table.setWidth(this.getWidth());
    table.align(Align.center|Align.top);
    table.setPosition(0, Gdx.graphics.getHeight());



    table.padTop(70);


    addActor(bck);
   addActor(table);

}



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        super.act(delta);
        super.draw();
        batch.end();
}


    public void dispose() {
    super.dispose();
    //skin.dispose();
    batch.dispose();
    }

}

