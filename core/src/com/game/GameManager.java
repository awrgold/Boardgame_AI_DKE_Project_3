package com.game;

import GameAI.*;
import GameBoardAssets.HexagonActor;
import Systems.AbstractSystem;
import Tools.Link;
import TreeStructure.Tree;
import com.badlogic.gdx.graphics.g2d.Sprite;

import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

import java.util.ArrayList;

public class GameManager extends AbstractSystem {



    private int player1TurnNumber = 0;
    private int player2TurnNumber = 0;
    private GameState currentState;

    private Tree gameTree;

    public GameManager(){

        this.currentState = new GameState();
        gameTree.buildTree(currentState, null, null);

    }

    public void updateState(){

        currentState.toString();

        changeState(GreedySearch.getNextMove())
        /*
        Action move = (currentState.getCurrentBoard().getFirst(), currentState.getCurrentBoard().getSecond(), getGamingPlayer().getSelectedTile());
        changeState(new Action(currentState.getCurrentBoard().getFirst(), currentState.getCurrentBoard().getSecond(), getGamingPlayer().getSelectedTile()));
        */

    }


    public GameState getCurrentState(){
        return gameTree.getRoot().getState();
    }

    public Player[] getPlayers(){
        return currentState.getPlayers();
    }

    public Player getPlayerByIndex(int i){
        return currentState.getPlayer(i);
    }

    public Player getGamingPlayer(){
        return currentState.getGamingPlayer();
    }

    public int getnOfPlayer(){
        return currentState.getPlayers().length;
    }



    public Hand getHandByIndex(int i){
        return getPlayers()[i].getHand();
    }


    public Board getBoard() {
        return currentState.getCurrentBoard();
    }




    public Bag getBag() {
        return currentState.getCurrentBag();
    }


    public int getTotalTurnNumber(){
        return player1TurnNumber + player2TurnNumber;
    }

    public int getPlayer1TurnNumber(){
        return player1TurnNumber;
    }

    public int getPlayer2TurnNumber(){
        return player2TurnNumber;
    }


    public boolean endGameCheck() {
        if (player1TurnNumber < 100){
            return false;
        } else {
            return true;
        }
        // Check if players score is complete or if no tiles can be placed.

        /* int[] playerScore = getGamingPlayer().getPlayerScore();
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

        return true; */

    }

    public void changeState(Action action){
        currentState = currentState.applyAction(action);
        System.out.println(action.toString());

    }

    /*
     Apply action, create new state, tell tree to update root
   public void moveAIPlayer() {
    CellPosition changedPosition = player2.makeAIMove();
       didMoveAtPosition(changedPosition, player2.getPlayerType());
     checkEndGame();
   }

    public void moveHumanPlayer(PlayerType playerType, CellPosition position) {
        if (playerType == PlayerType.PLAYER_O) {
            return;
        } else {
            HumanPlayer human = (HumanPlayer) player1;
            CellPosition changedPosition = human.setCellAtPosition(position);
            didMoveAtPosition(changedPosition, human.getPlayerType());
            makeNextMove();
        }
    }

    public void makeNextMove() {
        if (board.gameOver() == false) {
            moveAIPlayer();
        } else {
            endGame();
        }
    }

*/
    @Override
    public void proccessStep(float delta) {
       if (!endGameCheck()){
            updateState();
       }
    }

    /*
      public boolean isGamingPlayer(Player playerP) {
       boolean p;

       return p;
    }
   */
}
