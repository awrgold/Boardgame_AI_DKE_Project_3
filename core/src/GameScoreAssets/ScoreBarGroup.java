package GameScoreAssets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.game.Player;


public class ScoreBarGroup extends VerticalGroup{
    private Skin skin;
    private Player player;
    private float w;
    private int num;
    private Bar[] bars;
    private int[] scores;
    public ScoreBarGroup(int width, int height, Player player){
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.player = player;
        this.num = player.getPlayerScore().length;
        this.scores = player.getPlayerScore();
        this.bars = new Bar[num];
//    int[0] = blue
//    int[1] = violet
//    int[2] = orange
//    int[3] = purple
//    int[4] = red
//    int[5] = yellow

        Color[] colors = new Color[6];
        colors[0] = Color.BLUE;
        colors[1] = Color.VIOLET;
        colors[2] = Color.ORANGE;
        colors[3] = Color.PURPLE;
        colors[4] = Color.RED;
        colors[5] = Color.YELLOW;
//        colors[6] = Color.DARK_GRAY;


        for (int i = 0; i<num;i++){
            int barH = (height/num)/2;

            //overall score value on 140

            //score on 18
            int v = scores[i];
            int j = (v*140)/18;
            String s = Integer.toString(v);

            Label label=new Label(s, skin);
            label.setColor(colors[i]);
            Bar bar = new Bar(width,barH,colors[i],j);
            this.w = bar.getWidth();
            //bar.setBounds(width/2,barH/2,width,barH);
            //wrap bars
            Container wrapperl = new Container(label);
            Container wrapper = new Container(bar);

            wrapper.setTransform(true);
           // wrapper.setWidth(bar.getWidth());
           // wrapper.setHeight(height);
            wrapper.setOrigin(wrapper.getPrefWidth() / 2, wrapper.getPrefHeight() / 2);
            //  wrapper.setRotation(30);
            wrapper.setScaleX(2f);

            addActor(wrapperl);
            addActor(wrapper);

        }
    }

    public void update(){

    }

    public int getRandom(){
        int n = (int) (Math.random()* 140);
        return n;
    }
}