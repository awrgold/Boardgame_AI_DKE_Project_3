package com.game.Components.GameLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.game.Components.GameAssets.Bag;
import com.game.Components.GameAssets.Board;
import com.game.Components.GameScoreAssets.CustomLabel;
import com.game.Components.GameScoreAssets.ScoreBarGroup;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.PlayerAssets.Tile;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.GameConstants.Constants;
import com.game.Components.GameConstants.Pieces;
import com.game.Screens.GameScreen;
import com.game.Components.Tools.Link;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.Arrays;

import static java.util.Arrays.sort;

public class GameState extends Stage {
    private CustomLabel label;

    private ScoreBarGroup scorebars1;
    private ScoreBarGroup scorebars2;
    // private Stage stage;
    private Table root;
    public static TextButton[] changeTiles;

      //private CustomLabel label;



    private Player[] players;
    private Board currentBoard;
    private Bag currentBag;
    private Player gamingPlayer;
    private String text;
    private Skin skin;
    private ExtendViewport viewport;

    public GameState() {
        players = new Player[2];
        currentBoard = new Board();
        currentBag = new Bag(Pieces.createBagPieces());
        //for (int x = 1; x <= players.length; x++){
          //  players[x - 1] = new Player(x, currentBag.pickSix());
        //}
        players[0] = new Player(1, currentBag.pickSix(), true);
        players[1] = new Player(2, currentBag.pickSix(), true);
        gamingPlayer = players[0];
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        text = "tester Label ";
        this.label = new CustomLabel(text,skin);
        this.viewport = new ExtendViewport(Constants.getWindowWidth(),Constants.getWindowHeight());
        buildStage(viewport);

    }

    public GameState(Player[] players, Board currentBoard, Bag currentBag, Player gamingPlayer) {

        this.players = players;
        this.currentBoard = currentBoard;
        this.currentBag = currentBag;
        this.gamingPlayer = gamingPlayer;
        //System.out.println(gamingPlayer.getHand().getPieces().size() + " tiles in hand");
        /*while (!gamingPlayer.isLowestScoreTilePresent()){
            activateButtonIfNeeded();
        }*/


    }
    public void buildStage(ExtendViewport viewport) {

       setViewport(viewport);
       this.label = new CustomLabel(text,skin);

        root = new Table();
        root.setFillParent(true);

        root.debug(Table.Debug.all);

        // Create the score column add a score bar group for each player
        Table scoreColumn = new Table();
//scoreColumn.validate();

        scorebars1 = new ScoreBarGroup(250,350, players[0].getPlayerScore(),players[0].getPlayerNo());
        scoreColumn.add(scorebars1);
        scoreColumn.row();
        scoreColumn.row().expandX();
        scorebars2 = new ScoreBarGroup(250,350, players[1].getPlayerScore(),players[1].getPlayerNo());
        scoreColumn.add(scorebars2);
        scoreColumn.row();
        root.add(scoreColumn).colspan(2).expand().fill();


        // Create the board
        Table boardColumn = new Table();
        //boardColumn.debug();
//boardColumn.validate();
        //2 buttons for change hand
        changeTiles = new TextButton[2];
        for (int i = 1; i <= 2; i++){
            changeTiles[i - 1] = new TextButton("Change Tiles", skin);
        }

        //p1 tiles
        //boardColumn.row().height(100).top().expandX();
        //boardColumn.add(new Label("PlayerAssets 1 Hand", skin));
        boardColumn.row().height(130).top().fillX();
        boardColumn.add(changeTiles[0]).height(100).width(100).bottom().left();
        changeTiles[0].setTouchable(Touchable.disabled);
        changeTiles[0].setVisible(false);

        boardColumn.add(players[0].getHand()).expandX().center();
        boardColumn.row().fillX();

       //board
        // boardColumn.debug(Debug.all);
        boardColumn.row().height(400).width(-450);
        // GBV  and PHV Change
        boardColumn.row().height(750).width(-200);
        boardColumn.add(currentBoard).expandY().center();
        // boardColumn.add(gbv).expand().left();
        boardColumn.row();

        //p2 tiles
        //boardColumn.row().height(100).bottom().expandX();
        //boardColumn.add(new Label("PlayerAssets 2 Hand", skin));
        boardColumn.row().height(130).bottom().fillX();
        boardColumn.add(changeTiles[1]).height(100).width(100).top().left();
        changeTiles[1].setTouchable(Touchable.disabled);
        changeTiles[1].setVisible(false);

        boardColumn.add(players[1].getHand()).expandX().center();

        root.add(boardColumn).colspan(4).expand().left().fillY();
//        root.add(label);
//      //  root.pack();
////        root.validate();
        addActor(root);
//
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
        gamingPlayer.getHand().pickFromBag(currentBag.pickTile());

        nextState = new GameState(players, currentBoard, currentBag, changeGamingPlayer());

        return nextState;
    }


    public CustomLabel getCurrentLabel() {
        return label;
    }

    public void update() {

    }
}
