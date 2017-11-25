package IPLAYER;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.Board;
import com.game.Player;
import com.game.Tile;
import org.codetome.hexameter.core.api.HexagonalGrid;

import java.util.ArrayList;

public class HumanPlayer extends Player{


    public HumanPlayer(int playerNo, ArrayList<Tile> playerPieces, Board board) {
        super(playerNo, playerPieces, board);
    }
}
