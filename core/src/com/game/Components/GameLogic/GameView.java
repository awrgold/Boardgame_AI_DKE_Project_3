package com.game.Components.GameLogic;

import com.game.Components.GameAssets.Board;
import com.game.Components.PlayerAssets.Player;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.Tools.Link;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.backport.Optional;
import rx.functions.Action1;


public class GameView {

    private Board boardView;
    private int[][] scores;

    public GameView(Board board){
        this.boardView = cloneBoard(board);

    }

    public Board cloneBoard(Board board){
        Board newBoard = new Board();

        board.getGrid().getHexagons().forEach(new Action1<Hexagon<Link>>(){
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
                                System.out.println("Hexagon copied: " + copyHexActor.getHexColor());
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

        nextView = new GameView(boardView);
        if(one != null && two != null){
            one.setHexColor("EMPTY");
            two.setHexColor("EMPTY");
        }

        return nextView;
    }
}
