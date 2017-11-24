package com.game;

import GameBoardAssets.HexagonActor;
import GameConstants.Constants;
import Tools.Link;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.codetome.hexameter.core.api.HexagonalGrid;
import java.util.ArrayList;

public class GameState {

    private Player[] players;
    private Board currentBoard;
    private ArrayList<Sprite[]> currentBag;
    public Player gamingPlayer;

    public GameState() {
        players = new Player[Constants.getNumberOfPlayers()];
        currentBoard = new Board();
        currentBag = Pieces.createBagPieces();
        gamingPlayer = players[0];

        for (int x = 1; x <= players.length; x++){
            players[x - 1] = new Player(x, Pieces.distributePieces(currentBag),currentBoard);
        }
    }

    private GameState(Player[] players, Board currentBoard, ArrayList<Sprite[]> currentBag) {
        this.players = players;
        this.currentBoard = currentBoard;
        this.currentBag = currentBag;
    }

    public Player[] getPlayers(){
        return players;
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public ArrayList<Sprite[]> getCurrentBag() {
        return currentBag;
    }

    public Player getGamingPlayer(){
        return gamingPlayer;
    }

    public Player getPlayer(int i){
        return players[i];
    }

    public void applyAction(Action a){

        if (a.getH1().getSatelliteData().isPresent()){
            // create a link for the actor and hex of the next hex from current
            Link hexLink = (Link) a.getH1().getSatelliteData().get();
            HexagonActor currentHexActor = hexLink.getActor();
            currentHexActor.setHexColor(a.getT1().getHexColor());
        }
        if (a.getH2().getSatelliteData().isPresent()){
            // create a link for the actor and hex of the next hex from current
            Link hexLink = (Link) a.getH2().getSatelliteData().get();
            HexagonActor currentHexActor = hexLink.getActor();
            currentHexActor.setHexColor(a.getT2().getHexColor());
        }

    }



}
