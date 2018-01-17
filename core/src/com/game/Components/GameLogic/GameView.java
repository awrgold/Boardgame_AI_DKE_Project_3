package com.game.Components.GameLogic;

import com.game.Components.GameAssets.Board;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.Tools.Link;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.backport.Optional;
import rx.functions.Action1;


public class GameView {
    private boolean over;
    //private HexagonalGrid<Link> grid;
    private Board board;
    private int[][] scores;

    public GameView(Board board){
        this.board = cloneBoard(board);

    }

    public Board getBoard() {
        return board;
    }

    public Board cloneBoard(Board boardToCopy){
        Board newBoard = new Board();

        boardToCopy.getGrid().getHexagons().forEach(new Action1<Hexagon<Link>>(){
            @Override
            public void call(Hexagon hexagon) {
                //FOR EACH HEXAGON
                if (hexagon.getSatelliteData().isPresent()) {
                    Link hexLink = (Link) hexagon.getSatelliteData().get();
                    HexagonActor currentHexActor = hexLink.getActor();
                    //IF AN HEXAGON IN PREVIOUS BOARD IS NOT EMPTY
                    if (!currentHexActor.getHexColor().equals("EMPTY")) {
                        //TAKE THE CORRESPONDING HEXAGON IN THE NEW BOARD
                        Optional toCopy = newBoard.getGrid().getByCubeCoordinate(hexagon.getCubeCoordinate());
                        if (toCopy.isPresent()){
                            Hexagon copy = (Hexagon) toCopy.get();
                            if (copy.getSatelliteData().isPresent()){
                                Link copyLink = (Link) copy.getSatelliteData().get();
                                HexagonActor copyHexActor = copyLink.getActor();
                                //AND GIVE IT THE SAME COLOR
                                copyHexActor.setHexColor(currentHexActor.getHexColor());
                                //System.out.println("Hexagon copied: " + copyHexActor.getHexColor());
                            }
                        }

                    }
                }

            }

        });

        return newBoard;
    }

    public GameView simulateAction(Action a){
        HexagonActor one = null;
        HexagonActor two = null;
        GameView nextView;

        if (a.getH1().getSatelliteData().isPresent()){
            // create a link for the actor and hex of the next hex from current
            Link hexLink = (Link) a.getH1().getSatelliteData().get();
            HexagonActor currentHexActor = hexLink.getActor();
            currentHexActor.setHexColor(a.getTileColors()[0]);
            one = currentHexActor;

        }

        if (a.getH2().getSatelliteData().isPresent()){
            // create a link for the actor and hex of the next hex from current
            Link hexLink = (Link) a.getH2().getSatelliteData().get();
            HexagonActor currentHexActor = hexLink.getActor();
            currentHexActor.setHexColor(a.getTileColors()[1]);
            two = currentHexActor;

        }

        nextView = new GameView(board);
        if(one != null && two != null){
            one.setHexColor("EMPTY");
            two.setHexColor("EMPTY");
        }

        return nextView;
    }
    public boolean gameOver() {
        this.over = true;
        board.getGrid().getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                if (hexagon.getSatelliteData().isPresent()) {
                    Link hexLink = (Link) hexagon.getSatelliteData().get();
                    HexagonActor currentHexActor = hexLink.getActor();

                    if (currentHexActor.getHexColor().equals("EMPTY")) {
                        for (Object hex : board.getGrid().getNeighborsOf(hexagon)) {

                            if (hex instanceof Hexagon) {
                                Hexagon currentNeighbor = (Hexagon) hex;

                                if (currentNeighbor.getSatelliteData().isPresent()) {
                                    Link neighLink = (Link) currentNeighbor.getSatelliteData().get();
                                    HexagonActor neighHexActor = neighLink.getActor();

                                    if (neighHexActor.getHexColor().equals("EMPTY")) {
                                        over = false;
                                        break;
                                    }

                                }
                            }
                        }
                    }
                }
            }
        });
        return over;
    }
}
