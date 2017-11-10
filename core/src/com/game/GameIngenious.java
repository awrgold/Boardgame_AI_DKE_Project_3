package com.game;

import Screens.MenuScreen;
import Screens.ScreenEnum;
import Systems.AbstractSystem;
import Systems.ScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.codetome.hexameter.core.api.HexagonalGrid;

/**
 * Root of the game which holds the screens and delegates the rendering/updating to the currently active screen.
 */
public class GameIngenious extends Game {

    GameIngenious game;

    public int nOfPlayer;
    public Player[] players;
    public Player gamingPlayer;
    public SpriteBatch batch;
    private Viewport screenPort;

    public void create() {
        batch = new SpriteBatch();
        // this.setScreen( new MenuScreen(this));
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.GAME);
    }

    public boolean endGameCheck(Player player, HexagonalGrid hexGrid) {
        // Check if players score is complete or if no tiles can be placed.

        int[] playerScore = player.getPlayerScore();
        int totalScore = 0;
        boolean completeScore = true;
        for (int i = 0; i <= 5; i++) {
            if (playerScore[i] < 18) // Check if all colors are 18 or higher.
            {
                completeScore = false;
            }
        }
        if (completeScore == true) // If all colors are 18 or higher, game is over.
        {
            return true;
        }

        /*
        For each Hexagon()
        {
            If getHexColor() == "Null"
            {
                Find neighbours
                If any neighbour getHexColor() == "null" return false;
            }
        }
         */
        return true;

    }





}