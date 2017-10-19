package GameConstants;

public class Constants {
    public static final int sc_Height = 480;
    public static final int sc_Width = 640;
    public static final int centerX = sc_Width/2;
    public static final int centerY = sc_Height/2;
    public static final int col_width = sc_Width/8;
    public static final int row_height = sc_Height/8;
    public static final String skin = "skin/glassy-ui.json";
    public static final String skinAtlas = "skin/glassy-ui.atlas";
    public static final int hexRadius = 40;
    public static final int windowWidth = 1920;
    public static final int windowHeight = 1080;

    public static int getHexRadius(){
        return hexRadius;
    }

    public static int getWindowWidth() {return windowWidth;}

    public static int getWindowHeight() {return windowHeight;}


}
