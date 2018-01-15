package com.game.Components.GameLogic;



import com.game.Components.GameAssets.TestBag;
import com.game.Components.GameAssets.TestBoard;
import com.game.Components.GameConstants.TestPieces;
import com.game.Components.PlayerAssets.TestPlayer;
import com.game.Components.Tools.TestHexagonActor;
import com.game.Components.Tools.Link;
import com.game.GameAI.OpponentProbabilities;

import java.util.Arrays;

import static java.util.Arrays.sort;

public class TestGameState {

    private TestPlayer[] players;
    private TestBoard currentBoard;
    private TestBag currentBag;
    public TestPlayer gamingPlayer;
    private static OpponentProbabilities prob;


    public TestGameState() {
        players = new TestPlayer[2];
        currentBoard = new TestBoard();
        currentBag = new TestBag(TestPieces.createBagPieces());
        prob = new OpponentProbabilities(this);
        currentBoard.create();
        players[0] = new TestPlayer(1, currentBag.pickSix(), true);
        players[1] = new TestPlayer(2, currentBag.pickSix(), true);
        gamingPlayer = players[0];
        System.out.println("Starting player = " + players[0].getPlayerNo());
    }

    private TestGameState(TestPlayer[] players, TestBoard currentBoard, TestBag currentBag, TestPlayer gamingPlayer) {

        this.players = players;
        this.currentBoard = currentBoard;
        this.currentBag = currentBag;
        this.gamingPlayer = gamingPlayer;
        this.prob = new OpponentProbabilities(this);
        // System.out.println(gamingPlayer.getHand().getPieces().size() + " tiles in hand");
        // activateButtonIfNeeded();
    }

    public TestPlayer[] getPlayers(){
        return players;
    }

    public TestPlayer getGamingPlayer(){
        return gamingPlayer;
    }

    public TestBoard getCurrentBoard() {
        return currentBoard;
    }

    public TestBag getCurrentBag() {
        return currentBag;
    }

    public TestPlayer getPlayer(int i){
        return players[i];
    }

//    public TestPlayer changeGamingPlayer(){
//        int cntr = 0;
//        TestPlayer nextPlayer;
//
//        if (!gamingPlayer.hasIngenious()){
//            if (gamingPlayer.getPlayerNo() == 0){
//                nextPlayer = players[1];
//            }else{
//                nextPlayer = players[0];
//            }
//            System.out.println("Gaming Player: " + this.getGamingPlayer().getPlayerNo());
//        } else {
//            nextPlayer = this.getGamingPlayer();
//        }
//
//        System.out.println("Player " + nextPlayer.getPlayerNo() + " - Turn number: " + cntr);
//        return nextPlayer;
//    }

    public TestPlayer changeGamingPlayer(){
        TestPlayer nextPlayer;
        if (!gamingPlayer.hasIngenious()){

            nextPlayer = players[Math.abs(gamingPlayer.getPlayerNo() - 2)];
            System.out.println("New Gaming player: " + nextPlayer.getPlayerNo());


        } else {

            nextPlayer = players[Math.abs(gamingPlayer.getPlayerNo() - 1)];
            System.out.println("New Gaming player: " + nextPlayer.getPlayerNo());
        }
        return nextPlayer;
    }

    public TestPlayer getWinner(){
        TestPlayer winner;
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

    public TestGameState applyAction(TestAction action){
        TestHexagonActor first = null;
        TestGameState nextState;

        if (action == null) {
            System.out.println("Empty action, ending game");
        }


        if (action.getH1().getSatelliteData().isPresent()){
            // create a link for the actor and hex of the next hex from current
            Link hexLink = (Link) action.getH1().getSatelliteData().get();
            TestHexagonActor currentHexActor = hexLink.getTestActor();
            currentHexActor.setHexColor(action.getTileColors()[0]);
            first = currentHexActor;
            TestPlayer.updateScore(TestPlayer.scoreGain(currentHexActor, currentBoard.getGrid(), currentHexActor), gamingPlayer);
        }

        if (action.getH2().getSatelliteData().isPresent()){
            // create a link for the actor and hex of the next hex from current
            Link hexLink = (Link) action.getH2().getSatelliteData().get();
            TestHexagonActor currentHexActor = hexLink.getTestActor();
            currentHexActor.setHexColor(action.getTileColors()[1]);
            if (first != null){
                TestPlayer.updateScore(TestPlayer.scoreGain(currentHexActor, currentBoard.getGrid(), first), gamingPlayer);
            }
        }




        gamingPlayer.getHand().removeFromHand(action.getTile());
        gamingPlayer.getHand().pickFromBag(currentBag.pickTile());

        System.out.println("Current Gaming PLayer : " + gamingPlayer.getPlayerNo());
        nextState = new TestGameState(players, currentBoard, currentBag, changeGamingPlayer());

        System.out.println(" *** Moving on to the next Game State ***");
        return nextState;
    }

}
