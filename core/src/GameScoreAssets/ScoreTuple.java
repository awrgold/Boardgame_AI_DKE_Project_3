package GameScoreAssets;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class ScoreTuple {

    Sprite scoreColor;
    int scoreIndex;

    public ScoreTuple(int scoreIndex, Sprite scoreColor){
        this.scoreColor = scoreColor;
        this.scoreIndex = scoreIndex;
    }

    public int getScoreIndex(){
        return scoreIndex;
    }

    public void setScoreIndex(int index){
        this.scoreIndex = index;
    }

    public Sprite getScoreColor(){
        return scoreColor;
    }

    public void setScoreColor(Sprite colorSprite){
        this.scoreColor = colorSprite;
    }

    public String getScoreColorString(){
        return scoreColor.getColor().toString();
    }


}
