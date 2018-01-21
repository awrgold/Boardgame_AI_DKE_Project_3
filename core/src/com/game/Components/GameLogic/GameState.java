package com.game.Components.GameLogic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.game.Components.GameAssets.Bag;
import com.game.Components.GameAssets.Board;
import com.game.Components.GameConstants.Color;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.GameConstants.Constants;
import com.game.Components.GameConstants.Pieces;
import com.game.Screens.GameScreen;
import com.game.Components.Tools.Link;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.backport.Optional;
import rx.functions.Action1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static java.util.Arrays.sort;

public class GameState {

    private Player[] players;
    private Board currentBoard;
    private Bag currentBag;
    private Player gamingPlayer;

    public GameState() {
        players = new Player[2];
        currentBoard = new Board();
        currentBag = new Bag(Pieces.createBagPieces());
        //for (int x = 1; x <= players.length; x++){
          //  players[x - 1] = new Player(x, currentBag.pickSix());
        //}
        players[0] = new Player(1, currentBag.pickSix(), true, false, true, false, false);
        players[1] = new Player(2, currentBag.pickSix(), true, false, true, false, false);
        gamingPlayer = players[0];
    }

    public GameState(Player[] players, Board currentBoard, Bag currentBag, Player gamingPlayer) {

        setPlayers(players);
        setCurrentBoard(currentBoard);
        setCurrentBag(currentBag);
        setGamingPlayer(gamingPlayer);


        //System.out.println(gamingPlayer.getHand().getPieces().size() + " tiles in hand");
        /*while (!gamingPlayer.isLowestScoreTilePresent()){
            activateButtonIfNeeded();
        }*/
    }

    public GameState cloneGameState(){
        Player[] newPlayers = new Player[2];
        Player newGamingPlayer = null;
        for(int i = 0; i < 2; i++){
            newPlayers[i] = getPlayers()[i].clonePlayer();
            if (gamingPlayer == getPlayers()[i]){
                newGamingPlayer = newPlayers[i];
            }
        }
        GameState newState = new GameState(newPlayers, getCurrentBoard().cloneBoard(), getCurrentBag().cloneBag(), newGamingPlayer);

        return newState;
    }


    public Player[] getPlayers(){
        return players;
    }

    public void setPlayers(Player[] toSet){
        players = toSet;
    }

    public Player getGamingPlayer(){
        return gamingPlayer;
    }

    public void setGamingPlayer(Player toSet){
        gamingPlayer = toSet;
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(Board toSet){
        currentBoard = toSet;
    }

    public Bag getCurrentBag() {
        return currentBag;
    }

    public void setCurrentBag(Bag toSet){
        currentBag = toSet;
    }

    public Player getPlayer(int i){
        return players[i];
    }

    public Player changeGamingPlayer(){
        Player nextPlayer;
        if (!gamingPlayer.hasIngenious()){
            //GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setTouchable(Touchable.disabled);
            //GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setVisible(false);
            nextPlayer = players[Math.abs(gamingPlayer.getPlayerNo() - 2)];
        } else {
            //GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setTouchable(Touchable.disabled);
            //GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setVisible(false);
            nextPlayer = players[Math.abs(gamingPlayer.getPlayerNo() - 1)];
        }
        return nextPlayer;
    }

    public Player getWinner(){
        Player winner;
        int[] p1Score = getPlayer(0).getPlayerScore();
        int[] p2Score = getPlayer(1).getPlayerScore();
        Arrays.sort(p1Score);
        Arrays.sort(p2Score);

        for (int i = 0; i < 6; i++){
            if (p1Score[i] < p2Score[i]){
                winner = players[1];
                return winner;
            } if (p2Score[i] < p1Score[i]){
                winner = players[0];
                return winner;
            }
        }
        return null;
    }

    public void activateButtonIfNeeded(){
        // Check if hand has any tiles of lowest color:
        //GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setTouchable(Touchable.enabled);
        //GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setVisible(true);

        //System.out.println("You have no tiles of your lowest color, click to change your hand");
        //for (Tile tile : gamingPlayer.getHand().getPieces()){
            //System.out.print(tile.getActors()[0].getHexColor() + "-" + tile.getActors()[1].getHexColor() + "  ");
        //}
        if (gamingPlayer.isAI()){

            gamingPlayer.getHand().changeTiles(currentBag.replaceHand(gamingPlayer.getHand().getPieces()));
            //System.out.println("Changing tiles..");
            //for (Tile tile : gamingPlayer.getHand().getPieces()){
                //System.out.print(tile.getActors()[0].getHexColor() + "-" + tile.getActors()[1].getHexColor() + "  ");
            //}
            for (int i = 0; i < 6; i++) {
                Tile tile = gamingPlayer.getHand().getPieces().get(i);
                int index = 0;
                for (Actor hex : tile.getChildren()) {
                    if (hex instanceof HexagonActor) {
                        HexagonActor first = (HexagonActor) hex;
                        first.setHexColor(gamingPlayer.getHand().getPieces().get(i).getColors()[index]);
                        index++;
                    }
                }
            }
        }

        //CLICK TO CHANGE PIECES FROM THE BAG
        /*GameScreen.changeTiles[getGamingPlayer().getPlayerNo() - 1].addListener(new ClickListener() {
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
        });*/

    }

    public GameState applyAction(Action a){

        HexagonActor first = null;
        GameState nextState = cloneGameState();

        if (a.getH1().getSatelliteData().isPresent()){
            // create a link for the actor and hex of the next hex from current
            Link hexLink = (Link) a.getH1().getSatelliteData().get();
            HexagonActor currentHexActor = hexLink.getActor();
            //System.out.println(a.getTile().getColors()[0].toString() + a.getTile().getColors()[1].toString());
            currentHexActor.setHexColor(a.getTileColors()[0]);
            first = currentHexActor;
            Player.updateScore(Player.scoreGain(currentHexActor, currentBoard.getGrid(), currentHexActor), gamingPlayer);
        }

        if (a.getH2().getSatelliteData().isPresent()){
            // create a link for the actor and hex of the next hex from current
            Link hexLink = (Link) a.getH2().getSatelliteData().get();
            HexagonActor currentHexActor = hexLink.getActor();
            currentHexActor.setHexColor(a.getTileColors()[1]);
            if (first != null){
                Player.updateScore(Player.scoreGain(currentHexActor, currentBoard.getGrid(), first), gamingPlayer);
            }
        }
        gamingPlayer.getHand().removeFromHand(a.getTile());
        gamingPlayer.getHand().pickFromBag(currentBag.pickTile());

        //System.out.println(a.toString());


        return nextState;
    }

    public HashMap<Tile, Double> tilesExpectations(ArrayList<Color> colors){
        HashMap<Tile, Double> possibilities = new HashMap<>();
        //create a pool tht contains all the tiles not already seen
        ArrayList<Tile> pool = currentBag.getBag();
        pool.addAll(gamingPlayer.getHand().getPieces());
        //for each color we are expecting
        for (Color color : colors){
            //find tiles with that color in the pool
            for (int i = 0; i < pool.size(); i++){
                //if the tile contains that color
                if (pool.get(i).getColors()[0] == color || pool.get(i).getColors()[1] == color){
                    boolean isSeen = false;
                    //check if it is already in the HashMap
                    for (Tile seen : possibilities.keySet()){
                        if (pool.get(i).isEqual(seen)){

                            isSeen = true;
                        }
                    }

                    if (!isSeen){
                        int occ = 0;
                        for (Tile tile : pool){
                            if (pool.get(i).equals(tile)) occ++;
                        }
                        //System.out.println(pool.get(i).getColors()[0].toString() + " - " +
                                //pool.get(i).getColors()[1].toString() + " Prob: " + (double) occ / (double) pool.size());
                        possibilities.put(pool.get(i), (double) occ / (double) pool.size());

                    }
                }
            }

        }
        return possibilities;
    }


}
