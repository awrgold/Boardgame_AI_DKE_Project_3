package com.game;

import Enum.ScreenEnum;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameManager;
import com.game.Components.PlayerAssets.Tile;
import com.game.Screens.ScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Root of the game which holds the screens and delegates the rendering/updating to the currently active screen.
 */
public class GameIngenious extends Game {

  //  public SpriteBatch batch;
    private Viewport screenPort;
    private GameManager manager;

    @Override
    public void create() {
       //batch = new SpriteBatch();
       //showGameScreen();


        long startTime = System.currentTimeMillis();

        int player1Win = 0;
        String player1Strategy = new String();
        int player2Win = 0;
        String player2Strategy = new String();

        for (int i = 1; i <= 100; i++){
            GameManager manager = new GameManager();
            player1Strategy = manager.getPlayerByIndex(0).getStrategy();
            player2Strategy = manager.getPlayerByIndex(1).getStrategy();
            System.out.println("Game " + i);
            while (!manager.getBoard().gameOver()){
                System.out.println("Player 1 score: " + manager.getPlayerByIndex(0).scoreToString());
                System.out.println("Player 2 score: " + manager.getPlayerByIndex(1).scoreToString());
                //System.out.println(manager.getGamingPlayer().isLowestScoreTilePresent());
                System.out.println("Gaming Player " + manager.getGamingPlayer().getPlayerNo());
                manager.getGamingPlayer().getScoreQ();
                System.out.println("Hand: " + manager.getGamingPlayer().getHand().toString());
                Action AiMove = manager.getGamingPlayer().applyStrategy(manager.getCurrentState());
                System.out.println(AiMove.toString() + " | gain: " + AiMove.actionGain(manager.getBoard().getGrid(), manager.getGamingPlayer()));
                manager.changeState(AiMove);
                //System.out.println("  Score: " + manager.getPlayerByIndex(0).scoreToString());
                //System.out.println("Gaming Player: " + manager.getGamingPlayer().getPlayerNo() + "  Score: " + manager.getGamingPlayer().scoreToString());
            }
            if (manager.getBoard().gameOver()){
                System.out.println("GAME OVER");
                System.out.println("The winner is: Player " + manager.getCurrentState().getWinner().getPlayerNo() + " - (" + manager.getCurrentState().getWinner().getStrategy() +")");
                if (manager.getCurrentState().getWinner().getPlayerNo() == 1) player1Win++;
                else player2Win++;
            }
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime + " ms");
        System.out.println("Player 1 (" + player1Strategy + ") won: " + player1Win + " times");
        System.out.println("Player 2 (" + player2Strategy + ") won: " + player2Win + " times");

    }
    /*
    show specific screen directly
     */

    public void showGameScreen(){
    ScreenManager.getInstance().initialize(this);
    ScreenManager.getInstance().showScreen( ScreenEnum.GAME);
    }
    public void ShowMenuScreen(){
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU);
    }
 
}