package GameScoreAssets;

import Tools.Utils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class Bar extends ProgressBar {
    private TextureRegion leftBorder;
    private TextureRegion rightBorder;
    private Color barColor;
    public Bar(int width, int height,Color color, int n) {
        super(0f, 1f, 0.01f, false, new ProgressBarStyle());
        this.barColor = color;

        getStyle().background = Utils.getColoredDrawable(width, height, Color.WHITE);
        getStyle().knob = Utils.getColoredDrawable(n, height, barColor);
        //getStyle().knobBefore = Utils.getColoredDrawable(width, height, barColor);
        //getStyle().knobAfter = Utils.getColoredDrawable(width, height, barColor);

        setWidth(width);
        setHeight(height);


    }

    public Color getBarColor() {
        return barColor;
    }

    public void setBarColor(Color barColor) {
        this.barColor = barColor;
    }
}
