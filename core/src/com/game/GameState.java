package com.game;

import GameConstants.Constants;
import Tools.Link;
import com.badlogic.gdx.graphics.g2d.Sprite;
import org.codetome.hexameter.core.api.HexagonalGrid;
import java.util.ArrayList;

public class GameState {

    private Player[] players;
    private HexagonalGrid<Link> currentBoard;
    private ArrayList<Sprite[]> currentBag;
    private Player gamingPlayer;

    public GameState() {
        players = new Player[Constants.getNumberOfPlayers()];
        currentBoard = Constants.grid.build();
        currentBag = Pieces.createBagPieces();
        gamingPlayer = players[0];

        for (int x = 1; x <= players.length; x++){
            players[x - 1] = new Player(x, Pieces.distributePieces(currentBag));
        }
    }

    private GameState(Player[] players, HexagonalGrid<Link> currentBoard, ArrayList<Sprite[]> currentBag) {
        this.players = players;
        this.currentBoard = currentBoard;
        this.currentBag = currentBag;
    }

    public Player[] getPlayers(){
        return players;
    }

    public HexagonalGrid<Link> getCurrentBoard() {
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


}
