package com.game;

import GameBoardAssets.HexagonActor;
import Tools.Link;
import TreeStructure.Tree;
import com.badlogic.gdx.graphics.g2d.Sprite;

import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

import java.util.ArrayList;

public class GameManager {
//    private int nOfPlayer;
//    private int hn;
//    private int ain;
//    private Player[] players;
//    private Player gamingPlayer;
//    private ArrayList<Sprite[]> bag;
//    private int[][] points;
//    private boolean endGame=false;

    //  private ArrayList<Moves, reward>;
//    private HexagonalGrid<Link> board;


    private int player1TurnNumber = 0;
    private int player2TurnNumber = 0;
    private GameState currentState;
    private HexagonalGrid<Link> currentBoard;
    private Tree gameTree;

    public GameManager(){
//        nOfPlayer = 2;
//        players = new Player[2];

//    setUp();

//    }
    /*
    use set up method and game loop with the gamestates to manage the game variables
     */
//    public void setUp(){
//        ain=0;
//        hn=1;
//        bag = Pieces.createBagPieces();
//        points = new int[2][];
        /*
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
        */
        // gamingPlayer = players[0];
        // board = Constants.grid.build();

        // NEW SHIT HERE 22 Nov
        this.currentState = new GameState();
        //gameTree.buildTree(startingState);

    }


 //   public void gameLoop(){
//        while(!endGame){
//        /*
//        game check methods
//         */
//        }

    public Board getBoard() {
        return currentState.getCurrentBoard();
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

    public GameState getCurrentState(){
        return gameTree.getRoot().getState();
    }

    public Player[] getPlayers(){
        return currentState.getPlayers();
    }

    public Player getPlayerByIndex(int i){
        return currentState.getPlayer(i);
    }

    public int getnOfPlayer(){
        return currentState.getPlayers().length;
    }

    public Player getGamingPlayer(){
        return currentState.getGamingPlayer();
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

    public ArrayList<Sprite[]> getBag() {
        return currentState.getCurrentBag();
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

    public void changeState(Action a){

    }
    // Apply action, create new state, tell tree to update root

    public void render(float delta) {


    }
}
