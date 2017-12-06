package GameScoreAssets;

import GameCustomAssets.CustomLabel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;


public class ScoreBarGroup extends VerticalGroup{
    private Skin skin;
  //  private Player player;
    private int w;
    private int h;
    private int num;
    private Bar[] bars;
    private Color[] colors;
    private CustomLabel[] cl;
    private int[] scores;

    public ScoreBarGroup(int width, int height, int[] vals){
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
      //  this.player = player;
        this.num = vals.length;
        this.colors = new Color[num];
        this.scores = vals;
        this.cl = new CustomLabel[num];
        this.bars = new Bar[num];
        this.w = width;
        this.h = height;
        create();
    }

    public void create(){

        colors = new Color[num];
        colors[0] = Color.BLUE;
        colors[1] = Color.YELLOW;
        colors[2] = Color.ORANGE;
        colors[3] = Color.PURPLE;
        colors[4] = Color.VIOLET;
        colors[5] = Color.RED;
        //colors[6] = Color.DARK_GRAY;


        for (int i = 0; i < num; i++){
            int barH = (h / num) / 2;

            //overall score value on 140

            //score on 18
            int v = scores[i];
            int j = (v*140)/18;
            String s = Integer.toString(v);

            cl[i]=new CustomLabel(s, skin);
            cl[i].setColor(colors[i]);
            bars[i] = new Bar(w,barH,colors[i],j);
            //bars[i].setBounds(w/2,barH/2,w,barH);
            //wrap bars
            Container wrapperl = new Container(cl[i]);
            Container wrapper = new Container(bars[i]);
            wrapper.setTransform(true);
            wrapper.setWidth(bars[i].getWidth());
            // wrapper.setHeight(height);
            wrapper.setOrigin(wrapper.getPrefWidth() / 2, wrapper.getPrefHeight() / 2);
            // wrapper.setRotation(30);
            wrapper.setScaleX(2f);
           // addActor(cl[i]);
            //addActor(bars[i]);
            addActor(wrapperl);
            addActor(wrapper);

        }

    }
    public void act( float delta) {
        super.act(delta);
        for (int i = 0; i<num;i++){
            int v = scores[i];
            int j = (v*140)/18;
            String s = Integer.toString(v);
            cl[i].updateText(s);
            bars[i].updateVal(j);
        }


    }

    public int getRandom(){
        int n = (int) (Math.random()* 18);
        return n;
    }

}