package com.game.Components.GameScoreAssets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;


public class ScoreBarGroup extends VerticalGroup{
    private Skin skin;
  //  private PlayerAssets player;
    private int w;
    private int h;
    private int num;
    private Bar[] bars;
    private Color[] colors;
    private CustomLabel[] cl;
    private int[] scores;
    private CustomLabel scoreLabel;
    private String sc;
    private int pNum;
    public ScoreBarGroup(int width, int height, int[] vals, int player){
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
         this.pNum = player;
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
        sc = " | ";
        for (int i = 0; i < num; i++){
            int barH = (h / num) / 2;
            String c = scores[i] + " | ";
            sc = sc.concat(c);
            //overall score value on 140

            //score on 18
            int v = scores[i];
            int j = (v*140)/18;
            String s = Integer.toString(v);
            scoreLabel = new CustomLabel("Player Assets "+pNum+" Score : "+ sc , skin);
            scoreLabel.setColor(colors[pNum]);
            scoreLabel.setFontScale(2);
            cl[i]=new CustomLabel(s, skin);
            cl[i].setColor(colors[i]);
            cl[i].setFontScale(2);
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


       addActor(scoreLabel);


    }
    public void act( float delta) {
        super.act(delta);
       sc = " | ";
        for (int i = 0; i<num;i++){
            int v = scores[i];
            int j = (v*140)/18;
            String c = scores[i] + " | ";
            sc = sc.concat(c);
            String s = Integer.toString(v);
            cl[i].updateText(s);
           bars[i].updateVal(j);

       }
       scoreLabel.updateText("Player "+pNum+" Score : "+ sc );


    }

    public int getRandom(){
        int n = (int) (Math.random()* 18);
        return n;
    }
    public String scoreToString() {
        String p = "| ";
        for (int j = 0; j <= num; j++) {
            String s = scores[j] + " | ";
            p = p.concat(s);
        }
        return p;
    }
}