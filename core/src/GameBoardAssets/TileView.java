package GameBoardAssets;

import GameConstants.Constants;
import Interfaces.GroupView;
import Tools.Link;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.codetome.hexameter.core.api.HexagonOrientation;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.api.HexagonalGridLayout;

import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;

public class TileView extends GroupView {
    //tile grid (1x2)
    final int TILE_HEIGHT = 1;
    final int TILE_WIDTH = 2;
    final HexagonalGridLayout TILE_LAYOUT = RECTANGULAR;
    final HexagonOrientation TILE_ORIENTATION = POINTY_TOP;
    final double TILE_RADIUS = Constants.getHexRadius();
    private HexagonalGridBuilder<Link> tileBuilder;
    private SpriteBatch batch;

    public TileView(){
        super();

        batch = new SpriteBatch();
        create();
    }
    public void create(){
        //tile builder
        tileBuilder = new HexagonalGridBuilder<Link>()
                .setGridHeight(TILE_HEIGHT)
                .setGridWidth(TILE_WIDTH)
                .setGridLayout(TILE_LAYOUT)
                .setOrientation(TILE_ORIENTATION)
                .setRadius(TILE_RADIUS);
    }
    public void act( float delta) {
        super.act(delta);
    }
}
