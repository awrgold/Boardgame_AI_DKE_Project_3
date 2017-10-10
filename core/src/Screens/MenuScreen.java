package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

public class MenuScreen extends AbstractScreen {


    private SpriteBatch batch;
  //  private Skin skin;
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

//       skin = new Skin(Gdx.files.internal("uiskin.json"));
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
   // skin = new Skin(Gdx.files.internal("glassy-ui.json"));
    //title.LabelStyle label1Style = new Label.LabelStyle();
    //BitmapFont myFont = new BitmapFont(Gdx.files.internal("impact.fnt"));
   // label1Style.font = myFont;
   // label1Style.fontColor = Color.BLACK;
        //fill the stage
    table = new Table();
   // table.setFillParent(true);
    table.setWidth(this.getWidth());
    table.align(Align.center|Align.top);
    table.setPosition(0, Gdx.graphics.getHeight());
   // title = new Label("INGENIOUS",skin);
//    startb = new TextButton("Start",skin,"default");
//    quitb = new TextButton("Quit",skin,"default");
//    extrab = new TextButton("EXTRAS",skin,"default");



    //gameTitle.setSize(ConstantsC.col_width*2,ConstantsC.row_height*2);
    //gameTitle.setPosition(ConstantsC.centerX - gameTitle.getWidth()/2,ConstantsC.centerY + ConstantsC.row_height);
    // gameTitle.setAlignment(Align.center);
    //startb.setSize(ConstantsC.col_width*2,ConstantsC.row_height);
    //startb.setPosition(ConstantsC.centerX - startb.getWidth()/2,ConstantsC.centerY);
    //quitb.setSize(ConstantsC.col_width*2,ConstantsC.row_height);
    //quitb.setPosition(ConstantsC.centerX - quitb.getWidth()/2,startb.getY() - ConstantsC.row_height -15);


//       startb.addListener(new ClickListener(){
//            int n = 0;
//            public void clicked(InputEvent event, float x, float y){
//                n++;
//                Gdx.app.log("clicked start"," # "+n +" ");
//
//                ScreenManager.getInstance().showScreen( ScreenEnum.GAME );
//           }
//        });
//        quitb.addListener(new ClickListener(){
//            int n = 0;
//            public void clicked(InputEvent event, float x, float y){
//                n++;
//                Gdx.app.log("clicked quit"," # "+n +" ");
//                Gdx.app.log("Exit"," End of Game");
//               Gdx.app.exit();
//
//            }
//        });
//        extrab.addListener(new ClickListener(){
//            int n = 0;
//            public void clicked(InputEvent event, float x, float y){
//                n++;
//                Gdx.app.log("clicked extra"," # "+n +" ");
//            }
//        });
    //dialog = new Dialog("Start Game", skin);

    table.padTop(70);
    //table.add(title).padBottom(70);
  //  table.row();
//    table.add(startb).padBottom(70);
//    table.row();
//    table.add(extrab).padBottom(70);
//    table.row();
//    table.add(quitb);

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
//    int count = 0;
//
//    public boolean keyDown(int keycode) {
//
//
//        return false;
//    }
//
//
//    public boolean keyUp(int keycode) {
//        return false;
//    }
//
//    public boolean keyTyped(char character) {
//        return false;
//    }
//
//
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//
//        count++;
//        Gdx.app.log("clicked Back"," # "+count +" ");
//        return false;
//    }
//
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        return false;
//    }
//
//
//    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        return false;
//    }
//
//    public boolean mouseMoved(int screenX, int screenY) {
//        return false;
//    }
//
//    public boolean scrolled(int amount) {
//        return false;
//    }
}

