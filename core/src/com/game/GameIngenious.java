package com.game;

import Enum.ScreenEnum;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameManager;
import com.game.Screens.ScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Root of the game which holds the screens and delegates the rendering/updating to the currently active screen.
 */
public class GameIngenious extends Game {

  //  public SpriteBatch batch;
    private Viewport screenPort;

    @Override
    public void create() {
       //batch = new SpriteBatch();
       //showGameScreen();

        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= 10; i++){
            GameManager manager = new GameManager();
            System.out.println("Game " + i);
            while (!manager.getBoard().gameOver()){
                Action AiMove = manager.getGamingPlayer().applyStrategy(manager.getGamingPlayer().lowestColors(), manager.getGamingPlayer().getHand(), manager.getBoard().getGrid());
                //System.out.println(AiMove.toString());
                manager.setCurrentState(manager.getCurrentState().applyAction(AiMove));
                //System.out.println("Gaming Player: " + manager.getGamingPlayer().getPlayerNo() + "  Score: " + manager.getGamingPlayer().scoreToString());
            }
            if (manager.getBoard().gameOver()){
                //System.out.println("GAME OVER");
                System.out.println("The winner is: Player " + manager.getCurrentState().getWinner().getPlayerNo());
            }
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime + " ms");

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