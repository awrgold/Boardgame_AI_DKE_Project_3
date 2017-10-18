package GameScoreAssets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;


public class ScoreBarGroup extends VerticalGroup{
    private Skin skin;
    private float w;
    public ScoreBarGroup(int width, int height, int bars){
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
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


        for (int i = 0; i<bars;i++){
            int barH = (height/bars)/2;
            //System.out.println(height+"    "+width);
            //overall score value on 140
            int j = getRandom();
            //score on 18
            int v = (j*18)/140;
            String s = Integer.toString(v);

            Label label=new Label(s, skin);
            label.setColor(colors[i]);
            Bar bar = new Bar(width,barH,colors[i],j*8);
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
//System.out.println(bar.getWidth());
            addActor(wrapperl);
            addActor(wrapper);

        }
    }


    public int getRandom(){
        int n = (int) (Math.random()* 18);
        return n;
    }
}