package com.game;

import GameBoardAssets.HexagonActor;
import GameConstants.Constants;
import GameLogic.AIStrategy;
import IPLAYER.AIPlayer;
import IPLAYER.HumanPlayer;
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
    private int nOfPlayer;
    private int hn;
    private int ain;
    private Player[] players;
    private Player gamingPlayer;
    private ArrayList<Sprite[]> bag;
    private int[][] points;
    private boolean endGame=false;

    //  private ArrayList<Moves, reward>;
    private HexagonalGrid<Link> board;
    private int player1TurnNumber = 0;
    private int player2TurnNumber = 0;

    public GameManager(){
        nOfPlayer = 2;
        players = new Player[2];
        points = new int[2][];
    setUp();

    }
    /*
    use set up method and game loop with the gamestates to manage the game variables
     */
    public void setUp(){
        ain=0;
        hn=1;
        bag = Pieces.createBagPieces();
        for (int x = 1; x <= nOfPlayer; x++){
            if (x-1==ain){
                players[x - 1] = new AIPlayer(x, Pieces.distributePieces(bag),board, new AIStrategy());

            }
            if (x-1==hn){
                players[x - 1] = new HumanPlayer(x, Pieces.distributePieces(bag),board);

            }
//            players[x - 1] = new Player(x, Pieces.distributePieces(bag));
            points[x - 1] = players[x - 1].getPlayerScore();
        }
        gamingPlayer = players[0];
        board = Constants.grid.build();

    }


    public void gameLoop(){
        while(!endGame){
        /*
        game check methods
         */
        }
    }

    public void status(){
        board.getHexagons().forEach(new Action1<Hexagon<Link>>() {
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

    //public static placement(String c1, String c2, Hexagon h1, Hexagon h2, Sprite[]){



    //}

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

    public int getTotalTurnNumber(){
        return player1TurnNumber + player2TurnNumber;
    }

    public int getPlayer1TurnNumber(){
        return player1TurnNumber;
    }

    public int getPlayer2TurnNumber(){
        return player2TurnNumber;
    }

    public void changeGamingPlayer(){
        if (!gamingPlayer.hasIngenious()){
            GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setTouchable(Touchable.disabled);
            GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setVisible(false);
            gamingPlayer = players[Math.abs(gamingPlayer.getPlayerNo() - 2)];
            if(getGamingPlayer().getPlayerNo() == 0){
                player1TurnNumber++;
            }else{
                player2TurnNumber++;
            }
            status();

        } else {
            GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setTouchable(Touchable.disabled);
            GameScreen.changeTiles[gamingPlayer.getPlayerNo() - 1].setVisible(false);
            gamingPlayer = players[Math.abs(gamingPlayer.getPlayerNo() - 1)];
            if(getGamingPlayer().getPlayerNo() == 0){
                player1TurnNumber++;
            }else{
                player2TurnNumber++;
            }
        }


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
        // Call updateGraph()
    }

    public ArrayList<Sprite[]> getBag() {
        return bag;
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

        return true;

    }

    public HexagonalGrid<Link> getBoard() {
        return board;
    }

    /*
    Implement updateGraph()
     */

}
