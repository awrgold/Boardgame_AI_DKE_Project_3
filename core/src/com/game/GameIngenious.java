package com.game;

import Enum.ScreenEnum;
import TreeStructure.TestTree;
import com.game.Components.GameLogic.GameManager;
import com.game.Components.GameLogic.TestGameState;
import com.game.Components.GameLogic.TestManager;
import com.game.Screens.ScreenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Root of the game which holds the screens and delegates the rendering/updating to the currently active screen.
 */
public class GameIngenious extends Game {

    //public SpriteBatch batch;
    private Viewport screenPort;
    private GameManager manager;
    private TestManager testManager;
    private TestTree gameTree;
    private TestGameState testState;

    private final boolean isTesting = true;

    @Override
    public void create() {
        gameTree = new TestTree();
        testState = new TestGameState();
        if (!isTesting){
            manager = new GameManager();
            showGameScreen();
        }else{
            testManager = new TestManager();
            //testManager.greedySimulate(1);

        }
        gameTree.buildTree(testState);
    }


    // show specific screen directly
    public void showGameScreen(){
    ScreenManager.getInstance().initialize(this);
    ScreenManager.getInstance().showScreen( ScreenEnum.GAME);
    }
    public void ShowMenuScreen(){
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen( ScreenEnum.MAIN_MENU);
    }
 
}