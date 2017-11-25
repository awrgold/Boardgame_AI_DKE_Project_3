package com.game;

import GameBoardAssets.HexagonActor;
import GameConstants.Constants;
import Screens.GameScreen;
import Tools.Link;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.Observable;
import rx.functions.Action1;

import java.util.ArrayList;

public class GameManager {

    private int player1TurnNumber = 0;
    private int player2TurnNumber = 0;
    private GameState currentState;
    private HexagonalGrid<Link> currentBoard;
    private Tree gameTree;

    public GameManager(){

        this.currentState = new GameState();
        //gameTree.buildTree(startingState);

    }

    public void updateState(){
        System.out.println("what");
        //Action move = new Action(currentState.getCurrentBoard().getFirst(), currentState.getCurrentBoard().getSecond(), getGamingPlayer().getSelectedTile());
        changeState(new Action(currentState.getCurrentBoard().getFirst(), currentState.getCurrentBoard().getSecond(), getGamingPlayer().getSelectedTile()));

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

    public void status(){
        currentBoard.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon<Link> linkHexagon) {
                if (linkHexagon.getSatelliteData().isPresent()){
                    // create a link for the actor and hex of the next hex from current
                    Link hexLink = (Link) linkHexagon.getSatelliteData().get();
                    HexagonActor currentHexActor = hexLink.getActor();
                    System.out.println(currentHexActor.getHexColor());
                }
            }
        });
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

        /*int[] playerScore = getGamingPlayer().getPlayerScore();
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

        return true;*/

    }

    public void changeState(Action action){
        currentState = currentState.applyAction(action);
        System.out.println(action.toString());

    }
    // Apply action, create new state, tell tree to update root


}
