package com.game;

import Enum.ScreenEnum;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameManager;
import com.game.Components.GameLogic.Simulation;
import com.game.Screens.GameScreen;
import com.game.Screens.ScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Root of the game which holds the screens and delegates the rendering/updating to the currently active screen.
 */
public class GameIngenious extends Game {
   public GameManager manager;
   public SpriteBatch batch;

    //private Viewport screenPort;
    private GameScreen screen;
    @Override
    public void create() {

        this.batch = new SpriteBatch();
        this.manager = new GameManager();

        //  showGameScreen();

        screen = new GameScreen(this);
        setScreen(screen);
        // screen.buildStage();
//        int player1Win = 0;
//        String player1Strategy = new String();
//        int player2Win = 0;
//        String player2Strategy = new String();
//
//
//
//        long startTime = System.currentTimeMillis();
//        for (int i = 1; i <= 10; i++){
//            GameManager manager = new GameManager();
//            player1Strategy = manager.getPlayerByIndex(0).getStrategy();
//            player2Strategy = manager.getPlayerByIndex(1).getStrategy();
//            System.out.println("Game " + i);
//            while (!manager.getBoard().gameOver()){
//                Action AiMove = manager.getGamingPlayer().applyStrategy(manager.getCurrentState());
//                //System.out.println(AiMove.toString());
//                manager.setCurrentState(manager.getCurrentState().applyAction(AiMove));
//                //System.out.println("  Score: " + manager.getPlayerByIndex(0).scoreToString());
//                //System.out.println("Gaming Player: " + manager.getGamingPlayer().getPlayerNo() + "  Score: " + manager.getGamingPlayer().scoreToString());
//            }
//            if (manager.getBoard().gameOver()){
//                //System.out.println("GAME OVER");
//                System.out.println("The winner is: Player " + manager.getCurrentState().getWinner().getPlayerNo() + " - (" + manager.getCurrentState().getWinner().getStrategy() +")");
//                if (manager.getCurrentState().getWinner().getPlayerNo() == 1) player1Win++;
//                else player2Win++;
//            }
//        }
//        long endTime   = System.currentTimeMillis();
//        long totalTime = endTime - startTime;
//        System.out.println(totalTime + " ms");
//        System.out.println("Player 1 (" + player1Strategy + ") won: " + player1Win + " times");
//        System.out.println("Player 2 (" + player2Strategy + ") won: " + player2Win + " times");

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
  public void dispose() {
    super.dispose();
    batch.dispose();

  }

  @Override
  public void render () {
    super.render();

  }


}