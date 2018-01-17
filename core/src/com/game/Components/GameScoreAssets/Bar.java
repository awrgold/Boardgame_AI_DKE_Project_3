package com.game.Components.GameScoreAssets;

import com.game.Components.Tools.Utils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class Bar extends ProgressBar {
    private TextureRegion leftBorder;
    private TextureRegion rightBorder;
    private Color barColor;
    private int val;
    private int width;
    private int height;

    public Bar(int width, int height, Color color, int n) {
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());
        this.barColor = color;
        this.width = width;
        this.height = height;
        this.val = n;
        getStyle().background = Utils.getColoredDrawable(width, height, Color.WHITE);
        getStyle().knob = Utils.getColoredDrawable(val, height, barColor);

    }

    public void act( float delta) {
       // super.act(delta);
        this.updateVal(val);
        getStyle().knob = Utils.getColoredDrawable(val, height, barColor);

    }

    public void updateVal(int v){
        this.val = v;
}



    public Color getBarColor() {
        return barColor;
    }

    public void setBarColor(Color barColor) {
        this.barColor = barColor;
    }

    public void draw(Batch batch, float delta) {
        super.draw(batch, delta);

    }
}
