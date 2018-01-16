package com.game.Components.GameLogic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.game.Components.GameAssets.Bag;
import com.game.Components.GameAssets.Board;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.GameConstants.Constants;
import com.game.Components.GameConstants.Pieces;
import com.game.Screens.GameScreen;
import com.game.Components.Tools.Link;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import org.codetome.hexameter.core.api.Hexagon;
import rx.functions.Action1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import static com.game.Components.GameConstants.Constants.tile;
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

        players[0] = new Player(1, currentBag.pickSix(), true);
        for (Tile t: players[0].getHand().getPieces()){
            players[0].addToVisibleTiles(t);
        }
        players[1] = new Player(2, currentBag.pickSix(), true);
        for (Tile t: players[1].getHand().getPieces()){
            players[1].addToVisibleTiles(t);
        }
        gamingPlayer = players[0];
    }

    private GameState(GameState previousState, Player[] players, Board currentBoard, Bag currentBag, Player gamingPlayer) {
        if (previousState == null) throw new IllegalArgumentException("Previous state cannot be null");

        this.players = players;
        this.currentBoard = currentBoard;
        this.currentBag = currentBag;
        this.gamingPlayer = gamingPlayer;
        //System.out.println(gamingPlayer.getHand().getPieces().size() + " tiles in hand");
        while (!gamingPlayer.isLowestScoreTilePresent()){
            activateButtonIfNeeded();
        }
    }

    public Player[] getPlayers(){
        return players;
    }

    public void setPlayers(GameState state, Player[] players){

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
                        first.setSprite(gamingPlayer.getHand().getPieces().get(i).getColors()[index]);
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
        GameState nextState;

        if (a.getH1().getSatelliteData().isPresent()){
            // create a link for the actor and hex of the next hex from current
            Link hexLink = (Link) a.getH1().getSatelliteData().get();
            HexagonActor currentHexActor = hexLink.getActor();
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
        Tile newTile = currentBag.pickTile();
        gamingPlayer.getHand().pickFromBag(newTile);
        gamingPlayer.addToVisibleTiles(newTile);

        nextState = new GameState(players, currentBoard, currentBag, changeGamingPlayer());

        return nextState;

    }

    public GameState copyState(GameState toCopy){

        GameState nextState = new GameState();

        toCopy.getCurrentBoard().getGrid().getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                //FOR EACH HEXAGON
                if (hexagon.getSatelliteData().isPresent()) {
                    Link hexLink = (Link) hexagon.getSatelliteData().get();
                    HexagonActor currentHexActor = hexLink.getActor();

                    if (currentHexActor.getHexColor().equals("EMPTY")) {
                        for (Object hex : grid.getNeighborsOf(hexagon)) {
                            if (hex instanceof Hexagon) {
                                Hexagon currentNeighbor = (Hexagon) hex;

                                if (currentNeighbor.getSatelliteData().isPresent()) {
                                    Link neighLink = (Link) currentNeighbor.getSatelliteData().get();
                                    HexagonActor neighHexActor = neighLink.getActor();

                                    //THE FIRST ONE IS THE FIRST PLACEMENT
                                    if (neighHexActor.getHexColor().equals("EMPTY")) {

                                        randomAction.setH1(hexagon);
                                        randomAction.setH2(currentNeighbor);
                                        randomAction.setTile(tile);
                                    }
                                }

                            }

                        }

                    }
                }

            }

        });
    }


}
