package com.game;

import GameBoardAssets.HexagonActor;
import Screens.GameScreen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.codetome.hexameter.core.api.HexagonalGrid;

import java.util.ArrayList;

public class GameManager {

    private int nOfPlayer;
    private Player[] players;
    private Player gamingPlayer;
    private ArrayList<Sprite[]> bag;
    private boolean turn;

    public GameManager(){
        nOfPlayer = 2;
        players = new Player[2];
        bag = Pieces.createBagPieces();
        turn = true;
        for (int x = 1; x <= nOfPlayer; x++){
            players[x - 1] = new Player(x, Pieces.distributePieces(bag));
        }
        gamingPlayer = players[0];

    }





    public Player[] getPlayers(){
        return this.players;
    }

    public Player getPlayerByIndex(int i){
        return this.players[i];
    }

    public int getnOfPlayer(){
        return this.nOfPlayer;
    }

    public Player getGamingPlayer(){
        return this.gamingPlayer;
    }

    public void changeGamingPlayer(){
        gamingPlayer = players[Math.abs(gamingPlayer.getPlayerNo() - 2)];

        // Check if hand has any tiles of lowest color:
        if (!gamingPlayer.isLowestScoreTilePresent()){
            GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setTouchable(Touchable.enabled);
            GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setVisible(true);

            System.out.println("You have no tiles of your lowest color, click to change your hand");

            //CLICK TO CHANGE PIECES FROM THE BAG
            GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gamingPlayer.setPlayerPieces(Pieces.discardPieces(bag, gamingPlayer.getGamePieces()));
                    for (int i = 0; i < 6; i++){
                        Group tile = GameScreen.tileView[gamingPlayer.getPlayerNo() - 1][i];
                        int index = 0;
                        for (Actor hex : tile.getChildren()){
                            if (hex instanceof HexagonActor){
                                HexagonActor first = (HexagonActor) hex;
                                first.setSprite(gamingPlayer.getGamePieces().get(i)[index]);
                                index++;
                            }
                        }
                    }
                    GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setTouchable(Touchable.disabled);
                    GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setVisible(false);

                }


            });
        }
    }

    public void checkGameState(){
        if(!this.turn)
            this.turn = true;
    }

    public ArrayList<Sprite[]> getBag() {
        return bag;
    }

    public boolean getTurnBool(){
        return this.turn;
    }

    public boolean endGameCheck(Player player, HexagonalGrid hexGrid) {
        // Check if players score is complete or if no tiles can be placed.

        int[] playerScore = player.getPlayerScore();
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

        /*
        For each Hexagon()
        {
            If getHexColor() == "Null"
            {
                Find neighbours
                If any neighbour getHexColor() == "null" return false;
            }
        }
         */
        return true;

    }

}
