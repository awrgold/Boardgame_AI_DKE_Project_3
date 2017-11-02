package GameConstants;

import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGridLayout;

import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.HEXAGONAL;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;

public class Constants {



    // Window parameters

    public static final int sc_Height = 480;
    public static final int sc_Width = 640;
    public static final int centerX = sc_Width/2;
    public static final int centerY = sc_Height/2;
    public static final int col_width = sc_Width/8;
    public static final int row_height = sc_Height/8;
    public static final String skin = "skin/glassy-ui.json";
    public static final String skinAtlas = "skin/glassy-ui.atlas";

    public static final int windowWidth = 1920;
    public static final int windowHeight = 1080;


   // public static final int GRID_HEIGHT = 11;
   // public static final int GRID_WIDTH = 11;
    public static final int BOARD_HEIGHT = 11;
    public static final int BOARD_WIDTH = 11;
    public final static HexagonalGridLayout TILE_LAYOUT = RECTANGULAR;
    public final static HexagonalGridLayout BOARD_LAYOUT = HEXAGONAL;

    public static final int TILE_HEIGHT = 1;
    public static final int TILE_WIDTH = 2;
    public static final int hexRadius = 40;


    public final static HexagonOrientation HEXAGON_ORIENTATION = POINTY_TOP;

    // Player parameters

    public static int noPlayers = 2;

    public static void setNumberOfPlayers(int number){
        noPlayers = number;
    }

    public static int getNumberOfPlayers(){
        return noPlayers;
    }
    public static int getTileWidth(){
        return TILE_WIDTH;
    }
    public static int getTileHeight(){
        return TILE_HEIGHT;
                }
    public static int getBoardHeight(){
        return BOARD_HEIGHT;
    }

    public static int getBoardWidth(){
        return BOARD_WIDTH;
    }

    public static HexagonalGridLayout getTileLayout(){
        return TILE_LAYOUT;
    }

    public static HexagonalGridLayout getBoardLayout(){
        return BOARD_LAYOUT;
    }
    public static HexagonOrientation getHexagonOrientation(){
        return HEXAGON_ORIENTATION;
    }
    public static int getHexRadius(){
        return hexRadius;
    }

    public static int getWindowWidth() {return windowWidth;}

    public static int getWindowHeight() {return windowHeight;}


}
