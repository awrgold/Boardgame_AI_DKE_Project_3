package com.game.Components.GameLogic;

import TreeStructure.Tree;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.Components.GameAssets.Bag;
import com.game.Components.GameAssets.Board;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Player;
import com.game.GameIngenious;

public class TestManager {

    private static GameIngenious newGame;
    private static GameState currentState;
    private static int player1TurnNumber = 0;
    private static int player2TurnNumber = 0;
    private static Tree gameTree;
    private static Action move;

    public static void main(String[] args) {

        /*
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Ingenious";
        config.width = 1000;
        config.height = 800;
        new LwjglApplication(newGame, config);
        */

        newGame.create();
        simulate(1);
    }

    public TestManager(){
        newGame = new GameIngenious();
        currentState = new GameState();
        gameTree.buildTree(currentState);
        move = new Action();
    }

    public static void simulate(int iterations){

        for (int i = 0; i < iterations; i++){
            while (!currentState.getCurrentBoard().gameOver()){
                Action AiMove = currentState.getGamingPlayer().applyStrategy(currentState.getGamingPlayer().lowestColors(), currentState.getGamingPlayer().getHand(), currentState.getCurrentBoard().getGrid());
                System.out.println(AiMove.toString());
                TestManager.setCurrentState(getCurrentState().applyAction(AiMove));
                System.out.println("Gaming Player: " + currentState.getGamingPlayer().getPlayerNo() + "  Score: " + currentState.getGamingPlayer().scoreToString());
            }
            if (getBoard().gameOver()){
                System.out.println("GAME OVER");
                System.out.println("The winner is: Player " + getCurrentState().getWinner().getPlayerNo());
            }
        }
    }

    public static GameState getCurrentState(){
        return gameTree.getRoot().getState();
    }

    public static void setCurrentState(GameState newState){
        currentState = newState;
    }

    public static Player[] getPlayers(){
        return currentState.getPlayers();
    }

    public static Player getPlayerByIndex(int i){
        return currentState.getPlayer(i);
    }

    public static Player getGamingPlayer(){
        return currentState.getGamingPlayer();
    }

    public static int getnOfPlayer(){
        return currentState.getPlayers().length;
    }

    public static Hand getHandByIndex(int i){
        return getPlayers()[i].getHand();
    }

    public static Board getBoard() {
        return currentState.getCurrentBoard();
    }

    public static Bag getBag() {
        return currentState.getCurrentBag();
    }

    public static int getTotalTurnNumber(){
        return player1TurnNumber + player2TurnNumber;
    }

    public static int getPlayer1TurnNumber(){
        return player1TurnNumber;
    }

    public static int getPlayer2TurnNumber(){
        return player2TurnNumber;
    }

    public static void changeState(Action action){
        System.out.println(action.toString());
        currentState = currentState.applyAction(action);
        if (getBoard().gameOver()){
            System.out.println("The winner is: Player " + currentState.getWinner().getPlayerNo());
        }
        if (getGamingPlayer().isAI()){
            Action AiMove = getGamingPlayer().applyStrategy(getGamingPlayer().lowestColors(), getGamingPlayer().getHand(), getBoard().getGrid());
            System.out.println(AiMove.toString());
            //AiMove.getTile().moveBy(0, 30);
            /*try {
                Thread.sleep(5000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }*/

            currentState = currentState.applyAction(AiMove);
        }

        move = new Action();
        //if the new state is the last of the game, print the winner
        if (getBoard().gameOver()){
            System.out.println("The winner is: Player " + currentState.getWinner().getPlayerNo());
        }
    }
}
