package com.game.Components.GameLogic;
import com.game.Components.ExcelSheetData.ExcelSheet;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;

import java.io.File;
import java.io.IOException;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.Components.GameAssets.Bag;
import com.game.Components.GameAssets.Board;
import com.game.Components.GameConstants.Constants;
import com.game.Components.GameScoreAssets.CustomLabel;
import com.game.Components.GameScoreAssets.ScoreBarGroup;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.Tools.HexagonActor;
import com.game.TreeStructure.Tree;
import jxl.write.biff.RowsExceededException;
import org.codetome.hexameter.core.api.Hexagon;

import java.util.ArrayList;
import java.util.Locale;

public class GameManager {

private ExcelSheet xl;
    private int player1TurnNumber = 0;
    private int player2TurnNumber = 0;
    private GameState currentState;
    private Tree gameTree;
    private Action move;
    private CustomLabel label;
    private Skin skin;
    private String text;
    public Simulation sim;
    private ScoreBarGroup scorebars1;
    private ScoreBarGroup scorebars2;
    private Board board;
    private Hand hand1;
    private Hand hand2;
    private int num = 0;

    public GameManager() {


        this.currentState = new GameState();

        //gameTree.buildTree(startingState);
        move = new Action();
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.text = "tester Label ";
        this.label = new CustomLabel(text, skin);
        label.setFontScale(5);
        label.setPosition(100, 100);
        //this.sim = new Simulation(this);
        scorebars1 = new ScoreBarGroup(250, 350, getPlayerScoreByIndex(0), getPlayerByIndex(0).getPlayerNo());
        scorebars2 = new ScoreBarGroup(250, 350, getPlayerScoreByIndex(1), getPlayerByIndex(1).getPlayerNo());
        this.hand1 = currentState.getPlayer(0).getHand();
        this.hand2 = currentState.getPlayer(1).getHand();
        this.board = currentState.getCurrentBoard();

    }

    public void setNum(int num) {
        this.num = num;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState newState) {
        currentState = newState;
    }

    public Player[] getPlayers() {
        return currentState.getPlayers();
    }

    public Player getPlayerByIndex(int i) {
        return currentState.getPlayer(i);
    }

    public Player getGamingPlayer() {
        return currentState.getGamingPlayer();
    }

    public int getnOfPlayer() {
        return currentState.getPlayers().length;
    }

    public Hand getHandByIndex(int i) {
        return getPlayers()[i].getHand();
    }

    public Board getBoard() {
        return currentState.getCurrentBoard();
    }

    public Bag getBag() {
        return currentState.getCurrentBag();
    }

    public int getTotalTurnNumber() {
        return player1TurnNumber + player2TurnNumber;
    }

    public int getPlayer1TurnNumber() {
        return player1TurnNumber;
    }

    public int getPlayer2TurnNumber() {
        return player2TurnNumber;
    }

//    public void changeState(Action action){
//
//        //System.out.println(action.toString());
//        currentState = currentState.applyAction(action);
//        if (getBoard().gameOver()){
//            System.out.println("The winner is: Player " + currentState.getWinner().getPlayerNo());
//
//        }
//        if (getGamingPlayer().isAI()){
//            Action AiMove = getGamingPlayer().applyStrategy(getCurrentState());
//            System.out.println(AiMove.toString());
//            //AiMove.getTile().moveBy(0, 30);
//
//            currentState = currentState.applyAction(AiMove);
//        }
//
//        move = new Action();
//        //if the new state is the last of the game, print the winner
//        if (getBoard().gameOver()){
//            System.out.println("The winner is: Player " + currentState.getWinner().getPlayerNo());
//        }
//    }

    /*public void handleButtonTouch(Vector2 worldTouch){

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

    }*/

    public void handleTileTouch(Vector2 worldTouch) {
        outerloop:
        for (Tile tile : getGamingPlayer().getHand().getPieces()) {
            for (Actor hex : tile.getChildren()) {
                boolean inX = false;
                boolean inY = false;

                if (hex instanceof HexagonActor) {
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
                        if (move.getTile() != null) {
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

    public void handleBoardTouch(boolean second, Vector2 worldTouch) {
        //getGamingPlayer().bestTilesToPlace(getGamingPlayer().lowestColors());
        for (Actor hex : getBoard().getChildren()) {
            boolean inX = false;
            boolean inY = false;
            if (hex instanceof HexagonActor) {
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

                    if (second) {
                        if (clicked.getHexColor().equals("EMPTY")) {
                            if (getBoard().getGrid().getNeighborsOf(clicked.getHexagon()).contains(move.getH1())) {
                                move.setH2(clicked.getHexagon());
                            } else {
                                //System.out.println("Select a neighbor");
                            }
                        } else {
                            //System.out.println("select an empty hexagon");
                        }

                    } else {
                        if (clicked.getHexColor().equals("EMPTY")) {
                            move.setH1(clicked.getHexagon());
                        } else {
                            //System.out.println("select an empty hexagon");
                        }

                    }
                    break;
                }
            }
        }
    }


    public boolean handleTouch(Vector2 worldTouch) {
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
        //   Simulation sim = new Simulation(this);
//       if(!sim.isRunning()) {
//           sim.run();
//
//       }
        runSimulation();


//        for (int i = 1; i <= 10; i++){
//            System.out.println("Game " + i);
//            while (!getBoard().gameOver()){
//                Action AiMove = getGamingPlayer().applyStrategy(getGamingPlayer().lowestColors(), getGamingPlayer().getHand(), getBoard().getGrid());
//                //System.out.println(AiMove.toString());
//                currentState = currentState.applyAction(AiMove);
//                //System.out.println("Gaming Player: " + getGamingPlayer().getPlayerNo() + "  Score: " + getGamingPlayer().scoreToString());
//            }
//            if (getBoard().gameOver()){
//                //System.out.println("GAME OVER");
//                System.out.println("The winner is: Player " + currentState.getWinner().getPlayerNo());
//               currentState = new GameState();
//            }
//        }

        return true;

    }

    private int player1Win = 0;
    private String player1Strategy = new String();
    private int player2Win = 0;
    private String player2Strategy = new String();

    private ArrayList<Long> turnTimes = new ArrayList<Long>();
    private ArrayList<Integer> turnScores = new ArrayList<Integer>();

   private int turns = 0;
   private int n = 1;
    private void runSimulation() {

        long startTime = System.currentTimeMillis();
       // for (int i = 0; i <= n; i++) {
            // int turns = 0;
            player1Strategy = getPlayerByIndex(0).getStrategy();
            player2Strategy = getPlayerByIndex(1).getStrategy();

            //  boolean run= true;

           // System.out.println("Game " + i);

            if (!getBoard().gameOver()) {

                long sTime = System.currentTimeMillis();


                turns++;
                Action AiMove = getGamingPlayer().applyStrategy(getCurrentState());
                //System.out.println(AiMove.toString());
                setCurrentState(getCurrentState().applyAction(AiMove));
                //System.out.println("  Score: " + manager.getPlayerByIndex(0).scoreToString());
                //System.out.println("Gaming Player: " + manager.getGamingPlayer().getPlayerNo() + "  Score: " + manager.getGamingPlayer().scoreToString());
                int gpScore = getGamingPlayer().getScoreIncrease();
                long eTime = System.currentTimeMillis();
                long tTime = eTime - sTime;
                turnTimes.add(tTime);
                turnScores.add(gpScore);
               // System.out.println(tTime + " ms");
            }
            if (getBoard().gameOver()) {


                //System.out.println("GAME OVER");
                System.out.println("The winner is: Player " + getCurrentState().getWinner().getPlayerNo() + " - (" + getCurrentState().getWinner().getStrategy() + ")");
                if (getCurrentState().getWinner().getPlayerNo() == 1) player1Win++;
                else player2Win++;


                this.currentState = new GameState();
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                System.out.println(totalTime + " ms");
                System.out.println("Player 1 (" + player1Strategy + ") won: " + player1Win + " times");
                System.out.println("Player 2 (" + player2Strategy + ") won: " + player2Win + " times");

        //    }
                String test = player1Strategy+" vs "+player2Strategy;
                String gn = "Game "+ turns;

                xl = new ExcelSheet(gn,test,turns,turnScores,turnTimes);

                xl.setgn(gn);
                xl.setTitle(test);
                xl.setTurns(turns);
                xl.setScores(turnScores);
                xl.setTimes(turnTimes);


        }





        }






    public int[] getPlayerScoreByIndex(int i) {
        return currentState.getPlayer(i).getPlayerScore();
    }

    public void updateAssets(float delta) {
        text = "tester Label " + num;
        label.act(text);

        scorebars1.act(getPlayerScoreByIndex(0));
        scorebars2.act(getPlayerScoreByIndex(1));

        //  this.hand1 = currentState.getPlayer(0).getHand();
        // this.hand2 = currentState.getPlayer(1).getHand();
        // board.act(currentState.getCurrentBoard().getGrid());


    }

    public CustomLabel getLabel() {
        return label;
    }

    public ScoreBarGroup getScoreBarByIndex(int i) {
        if (i == 0)
            return scorebars1;
        else
            return scorebars2;
    }

}


