package com.game.Components.GameScoreAssets;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CustomLabel extends Label {
    private String text;
private Skin skin;
    public CustomLabel( CharSequence text,  Skin skin) {
        super(text, skin);
        this.text = text.toString();
        this.skin= skin;
    }


    public void act( String text) {
//super.act(delta);
        this.setText(text);


    }

    public void updateText( String text) {
        this.text = text;
    }
}
