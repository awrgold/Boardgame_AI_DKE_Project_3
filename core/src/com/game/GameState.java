package com.game;

import GameBoardAssets.HexagonActor;
import GameConstants.Constants;
import Screens.GameScreen;
import Tools.Link;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.codetome.hexameter.core.api.HexagonalGrid;
import java.util.ArrayList;

public class GameState {

    private Player[] players;
    private Board currentBoard;
    private Bag currentBag;
    public Player gamingPlayer;

    public GameState() {
        players = new Player[Constants.getNumberOfPlayers()];
        currentBoard = new Board();
        currentBag = new Bag(Pieces.createBagPieces());
        for (int x = 1; x <= players.length; x++){
            players[x - 1] = new Player(x, currentBag.pickSix());
        }
        gamingPlayer = players[0];
    }

    private GameState(Player[] players, Board currentBoard, Bag currentBag, Player gamingPlayer) {
        this.players = players;
        this.currentBoard = currentBoard;
        this.currentBag = currentBag;
        this.gamingPlayer = gamingPlayer;
        activateButtonIfNeeded();
    }

    public Player[] getPlayers(){
        return players;
    }

    public Player getGamingPlayer(){
        return gamingPlayer;
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public Bag getCurrentBag() {
        return currentBag;
    }

    public Player getPlayer(int i){
        return players[i];
    }

    public Player changeGamingPlayer(){
        Player nextPlayer;
        if (!gamingPlayer.hasIngenious()){
            GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setTouchable(Touchable.disabled);
            GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setVisible(false);
            nextPlayer = players[Math.abs(gamingPlayer.getPlayerNo() - 2)];



        } else {
            GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setTouchable(Touchable.disabled);
            GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setVisible(false);
            nextPlayer = players[Math.abs(gamingPlayer.getPlayerNo() - 1)];

        }

        return nextPlayer;



    }

    public void activateButtonIfNeeded(){
        // Check if hand has any tiles of lowest color:
        if (!gamingPlayer.isLowestScoreTilePresent()){
            GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setTouchable(Touchable.enabled);
            GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setVisible(true);


            System.out.println("You have no tiles of your lowest color, click to change your hand");

            /** the button doesn't appear */

            //CLICK TO CHANGE PIECES FROM THE BAG
            GameScreen.changeTiles[getGamingPlayer().getPlayerNo() - 1].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gamingPlayer.getHand().changeTiles(currentBag.replaceHand(gamingPlayer.getHand().getPieces()));
                    for (int i = 0; i < 6; i++) {
                        Group tile = gamingPlayer.getHand().getPieces().get(i);
                        int index = 0;
                        for (Actor hex : tile.getChildren()) {
                            if (hex instanceof HexagonActor) {
                                HexagonActor first = (HexagonActor) hex;
                                first.setSprite(gamingPlayer.getHand().getPieces().get(i).getColors()[index]);
                                index++;
                            }
                        }
                    }
                    GameScreen.changeTiles[getGamingPlayer().getPlayerNo() - 1].setTouchable(Touchable.disabled);
                    GameScreen.changeTiles[getGamingPlayer().getPlayerNo() - 1].setVisible(false);

                }


            });
        }
    }



    public GameState applyAction(Action a){
        HexagonActor first = null;

        GameState nextState;

        if (a.getH1().getSatelliteData().isPresent()){
            // create a link for the actor and hex of the next hex from current
            Link hexLink = (Link) a.getH1().getSatelliteData().get();
            HexagonActor currentHexActor = hexLink.getActor();
            currentHexActor.setHexColor(a.getTileColors()[0]);
            first = currentHexActor;
            Player.updateScore(gamingPlayer, currentHexActor, currentBoard.getGrid(), currentHexActor);
        }

        if (a.getH2().getSatelliteData().isPresent()){
            // create a link for the actor and hex of the next hex from current
            Link hexLink = (Link) a.getH2().getSatelliteData().get();
            HexagonActor currentHexActor = hexLink.getActor();
            currentHexActor.setHexColor(a.getTileColors()[1]);
            if (first != null){
                Player.updateScore(gamingPlayer, currentHexActor, currentBoard.getGrid(), first);
            }

        }

        gamingPlayer.getHand().removeFromHand(a.getTile());
        gamingPlayer.getHand().pickFromBag(currentBag.pickTile());


        nextState = new GameState(players, currentBoard, currentBag, changeGamingPlayer());

        return nextState;


    }


}
