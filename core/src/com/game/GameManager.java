package com.game;

import GameBoardAssets.HexagonActor;
import GameConstants.Constants;
import Systems.AbstractSystem;
import Tools.Link;
import TreeStructure.Tree;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

import java.util.ArrayList;

public class GameManager extends AbstractSystem {



    private int player1TurnNumber = 0;
    private int player2TurnNumber = 0;
    private GameState currentState;

    private Tree gameTree;

    private Action move;



    public GameManager(){
        this.currentState = new GameState();
        //gameTree.buildTree(startingState);
        move = new Action();

    }

    public void updateState(){
       // currentState.toString();
        //
        // (currentState.getCurrentBoard().getFirst(), currentState.getCurrentBoard().getSecond(), getGamingPlayer().getSelectedTile());
//        changeState(new Action(currentState.getCurrentBoard().getFirst(), currentState.getCurrentBoard().getSecond(), getGamingPlayer().getSelectedTile()));

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
//    public void moveAIPlayer() {
//        CellPosition changedPosition = player2.makeAIMove();
//        didMoveAtPosition(changedPosition, player2.getPlayerType());
//        checkEndGame();
//    }

//    public void moveHumanPlayer(PlayerType playerType, CellPosition position) {
//        if (playerType == PlayerType.PLAYER_O) {
//            return;
//        } else {
//            HumanPlayer human = (HumanPlayer) player1;
//            CellPosition changedPosition = human.setCellAtPosition(position);
//            didMoveAtPosition(changedPosition, human.getPlayerType());
//            makeNextMove();
//        }
//    }

//    public void makeNextMove() {
//        if (board.gameOver() == false) {
//            moveAIPlayer();
//        } else {
//            endGame();
//        }
//    }


    @Override
    public void proccessStep(float delta) {
       if (!endGameCheck()){
            updateState();
       }
    }

    public void handleTileTouch(Vector2 worldTouch){
        //System.out.println(getGamingPlayer().getHand().getPieces().get(0).getHexA().getHexColor());
        outerloop:
        for (Tile tile : getGamingPlayer().getHand().getPieces()){
            for (Actor hex : tile.getChildren()){
                boolean inX = false;
                boolean inY = false;

                if (hex instanceof HexagonActor){
                    HexagonActor clicked = (HexagonActor) hex;
                    Vector2 hexLoc = clicked.localToStageCoordinates(new Vector2());

                    Hexagon one = clicked.getHexagon();

                    if (worldTouch.x > hexLoc.x &&
                            worldTouch.x < hexLoc.x + Constants.getHexRadius() * 2) {
                        inX = true;
                    }
                    if (worldTouch.y > hexLoc.y &&
                            worldTouch.y < hexLoc.y + Constants.getHexRadius() * 2) {
                        inY = true;
                    }
                    if (inX && inY) {
                        System.out.println("found");
                        if (move.getTile() != null){
                            move.getTile().moveBy(0, -30);
                        }

                        tile.setFirst(clicked);
                        move.setTile(tile);
                        tile.moveBy(0, 30);


                        System.out.println("(" + clicked.getHexagon().getGridX() + ", " + clicked.getHexagon().getGridY() + ", " + clicked.getHexagon().getGridZ() + ")" + " - " + clicked.getHexColor());
                        break outerloop;
                    }

                }

            }

        }

    }

    public void handleBoardTouch(boolean second, Vector2 worldTouch){
        for (Actor hex : getBoard().getChildren()){
            boolean inX = false;
            boolean inY = false;
            if (hex instanceof HexagonActor){
                HexagonActor clicked = (HexagonActor) hex;
                Vector2 hexLoc = clicked.localToStageCoordinates(new Vector2());

                if (worldTouch.x > hexLoc.x &&
                        worldTouch.x < hexLoc.x + Constants.getHexRadius() * 2) {
                    inX = true;
                }
                if (worldTouch.y > hexLoc.y &&
                        worldTouch.y < hexLoc.y + Constants.getHexRadius() * 2) {
                    inY = true;
                }
                if (inX && inY) {
                    //System.out.println("found");
                    if (!second){
                        move.setH1(clicked.getHexagon());
                        System.out.println("setting h1");
                    } else {
                        move.setH2(clicked.getHexagon());
                        System.out.println("setting h2");
                    }

                    System.out.println("(" + clicked.getHexagon().getGridX() + ", " + clicked.getHexagon().getGridY() + ", " + clicked.getHexagon().getGridZ() + ")" + " - " + clicked.getHexColor());
                    break;
                }
            }

        }
    }

    public boolean handleTouch(Vector2 worldTouch){
        if (getGamingPlayer().getPlayerNo() == 2){
            System.out.println("p2");
            handleTileTouch(worldTouch);
        } if(getGamingPlayer().getPlayerNo() == 1) {
            System.out.println("p1");
            handleTileTouch(worldTouch);

        } if (move.getH1() != null && move.getH2() == null){
            handleBoardTouch(true, worldTouch);
            changeState(move);
        } if (move.getH1() == null){
            handleBoardTouch(false, worldTouch);
        } if (move.getH1() != null && move.getH2() != null){
            return true;
        }
        return true;

    }

    public void reset(){

    }

}
