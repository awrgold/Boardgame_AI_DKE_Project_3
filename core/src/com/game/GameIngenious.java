package com.game;

import Enum.ScreenEnum;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameManager;
import com.game.Screens.GameScreen;
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
    private GameScreen screen;
    @Override
    public void create() {
       //batch = new SpriteBatch();
      // showGameScreen();
        manager = new GameManager();
        screen = new GameScreen(this, manager);
        setScreen(screen);


//        long startTime = System.currentTimeMillis();
//
//        int player1Win = 0;
//        int player2Win = 0;
//
//        for (int i = 1; i <= 100; i++){
//            GameManager manager = new GameManager();
//            System.out.println("Game " + i);
//            while (!manager.getBoard().gameOver()){
//                Action AiMove = manager.getGamingPlayer().applyStrategy(manager.getGamingPlayer().lowestColors(), manager.getGamingPlayer().getHand(), manager.getBoard().getGrid());
//                //System.out.println(AiMove.toString());
//                manager.setCurrentState(manager.getCurrentState().applyAction(AiMove));
//                //System.out.println("Gaming Player: " + manager.getGamingPlayer().getPlayerNo() + "  Score: " + manager.getGamingPlayer().scoreToString());
//            }
//            if (manager.getBoard().gameOver()){
//                //System.out.println("GAME OVER");
//                System.out.println("The winner is: Player " + manager.getCurrentState().getWinner().getPlayerNo());
//                if (manager.getCurrentState().getWinner().getPlayerNo() == 1) player1Win++;
//                else player2Win++;
//            }
//        }
//        long endTime   = System.currentTimeMillis();
//        long totalTime = endTime - startTime;
//        System.out.println(totalTime + " ms");
//        System.out.println("Player 1 won: " + player1Win + " times");
//        System.out.println("Player 2 won: " + player2Win + " times");

    }
    /*
    show specific screen directly
     */

//    public void showGameScreen(){
//    ScreenManager.getInstance().initialize(this);
//    ScreenManager.getInstance().showScreen( ScreenEnum.GAME);
//    }
//    public void ShowMenuScreen(){
//        ScreenManager.getInstance().initialize(this);
//        ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU);
//    }
 
}