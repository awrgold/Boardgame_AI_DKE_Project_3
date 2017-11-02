package GameHandAssets;

import GameConstants.Constants;
import Interfaces.GroupView;
import Tools.Link;
import com.game.Player;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.HexagonalGridLayout;

import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;

public class PlayerHandView extends GroupView {
    final int TILE_HEIGHT = 1;
    final int TILE_WIDTH = 2;
    final HexagonalGridLayout TILE_LAYOUT = RECTANGULAR;
    final HexagonOrientation TILE_ORIENTATION = POINTY_TOP;
    final double TILE_RADIUS = Constants.getHexRadius();
    private HexagonalGridBuilder<Link> tileBuilder;
    //private HexagonActor first;
   // private TileView selectedTile;
   // private int selectedTileIndex;

    public PlayerHandView(Player player){
        super();

    }
    public void hide(){

    }
    public void create(){

    };
    public void act( float delta) {
        super.act(delta);
    }
}
