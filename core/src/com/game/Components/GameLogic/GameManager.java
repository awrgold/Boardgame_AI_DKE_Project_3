package com.game.Components.GameLogic;

import com.game.Components.GameAssets.*;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.GameConstants.Constants;
import TreeStructure.Tree;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.codetome.hexameter.core.api.Hexagon;

public class GameManager{

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

    public GameState getCurrentState(){
        return gameTree.getRoot().getState();
    }

    public void setCurrentState(GameState newState){
        currentState = newState;
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

    public void changeState(Action action){
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


    /*
    public void handleButtonTouch(Vector2 worldTouch){
        boolean inX = false;
        boolean inY = false;
        TextButton activeButton = GameScreen.changeTiles[getGamingPlayer().getPlayerNo()];
        Vector2 activeButtonLoc = activeButton.localToStageCoordinates(new Vector2());
        if (worldTouch.x > activeButtonLoc.x &&
                worldTouch.x < activeButtonLoc.x + 100) {
            inX = true;
        }
        if (worldTouch.y > activeButtonLoc.y &&
                worldTouch.y < activeButtonLoc.y + 100) {
            inY = true;
        }
        if (inX && inY) {
            getGamingPlayer().getHand().changeTiles(getBag().replaceHand(getGamingPlayer().getHand().getPieces()));
            for (int i = 0; i < 6; i++) {
                Group tile = getGamingPlayer().getHand().getPieces().get(i);
                int index = 0;
                for (Actor hex : tile.getChildren()) {
                    if (hex instanceof HexagonActor) {
                        HexagonActor first = (HexagonActor) hex;
                        first.setSprite(getGamingPlayer().getHand().getPieces().get(i).getColors()[index]);
                        index++;
                    }
                }
            }
            GameScreen.changeTiles[getGamingPlayer().getPlayerNo() - 1].setTouchable(Touchable.disabled);
            GameScreen.changeTiles[getGamingPlayer().getPlayerNo() - 1].setVisible(false);
        }
    }
    */

    public void handleTileTouch(Vector2 worldTouch){
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
                            worldTouch.x < hexLoc.x + Constants.getHexRadius() * 1.5) {
                        inX = true;
                    }
                    if (worldTouch.y > hexLoc.y &&
                            worldTouch.y < hexLoc.y + Constants.getHexRadius() * 1.5) {
                        inY = true;
                    }
                    if (inX && inY) {
                        if (move.getTile() != null){
                            move.getTile().moveBy(0, -30);
                        }

                        tile.setFirst(clicked);
                        tile.setSelected(true);
                        move.setTile(tile);

                        tile.moveBy(0, 30);

                        break outerloop;
                    }

                }

            }

        }

    }

    public void handleBoardTouch(boolean second, Vector2 worldTouch){
        //getGamingPlayer().bestTilesToPlace(getGamingPlayer().lowestColors());
        for (Actor hex : getBoard().getChildren()){
            boolean inX = false;
            boolean inY = false;
            if (hex instanceof HexagonActor){
                HexagonActor clicked = (HexagonActor) hex;
                Vector2 hexLoc = clicked.localToStageCoordinates(new Vector2());

                if (worldTouch.x > hexLoc.x &&
                        worldTouch.x < hexLoc.x + Constants.getHexRadius() * 1.5) {
                    inX = true;
                }
                if (worldTouch.y > hexLoc.y &&
                        worldTouch.y < hexLoc.y + Constants.getHexRadius() * 1.5) {
                    inY = true;
                }
                if (inX && inY) {

                    if (second){
                        if(clicked.getHexColor().equals("EMPTY")){
                            if(getBoard().getGrid().getNeighborsOf(clicked.getHexagon()).contains(move.getH1())){
                                move.setH2(clicked.getHexagon());
                            } else {
                                System.out.println("Select a neighbor");
                            }
                        } else {
                            System.out.println("select an empty hexagon");
                        }

                    } else {
                        if(clicked.getHexColor().equals("EMPTY")){
                            move.setH1(clicked.getHexagon());
                        } else {
                            System.out.println("select an empty hexagon");
                        }

                    }
                    break;
                }
            }
        }
    }


    public boolean handleTouch(Vector2 worldTouch){
        /*if (getGamingPlayer().getPlayerNo() == 2){
            handleTileTouch(worldTouch);
        } if(getGamingPlayer().getPlayerNo() == 1) {
            handleTileTouch(worldTouch);
        } if (move.getH1() != null && move.getH2() == null){
            handleBoardTouch(true, worldTouch);
            if (move.getH2() != null){
                changeState(move);
            }
        } if (move.getH1() == null && move.getTile() != null){
            handleBoardTouch(false, worldTouch);
        } if (move.getH1() != null && move.getH2() != null){
            return true;
        }*/
        while (!getBoard().gameOver()){
            Action AiMove = getGamingPlayer().applyStrategy(getGamingPlayer().lowestColors(), getGamingPlayer().getHand(), getBoard().getGrid());
            System.out.println(AiMove.toString());
            currentState = currentState.applyAction(AiMove);
            System.out.println("Gaming Player: " + getGamingPlayer().getPlayerNo() + "  Score: " + getGamingPlayer().scoreToString());
        }
        if (getBoard().gameOver()){
            System.out.println("GAME OVER");
            System.out.println("The winner is: Player " + currentState.getWinner().getPlayerNo());
        }
        return true;

    }

}