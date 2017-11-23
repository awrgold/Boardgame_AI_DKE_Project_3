package IPLAYER;

import GameLogic.AIStrategy;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.Board;
import com.game.Player;
import org.codetome.hexameter.core.api.HexagonalGrid;

import java.util.ArrayList;

public class AIPlayer extends Player {

    public AIPlayer(int playerNo, ArrayList<Sprite[]> piecesSprites, Board board) {
        super(playerNo, piecesSprites, board);
    }
}
