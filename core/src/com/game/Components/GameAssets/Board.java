package com.game.Components.GameAssets;

import com.game.Components.GameConstants.Color;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.GameConstants.Constants;
import com.game.Components.Tools.GroupView;
import com.game.Components.Tools.Link;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import org.codetome.hexameter.core.api.HexagonalGridBuilder;
import org.codetome.hexameter.core.backport.Optional;
import rx.functions.Action1;

import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.HEXAGONAL;

public class Board extends GroupView {

    private HexagonalGrid<Link> grid;
    private boolean over;

    public Board(){
        create();
    }

    public void create(){
        HexagonalGridBuilder<Link> gridBuilder = new HexagonalGridBuilder<Link>()
                .setGridHeight(11)
                .setGridWidth(11)
                .setGridLayout(HEXAGONAL)
                .setOrientation(POINTY_TOP)
                .setRadius(40);

        this.grid = gridBuilder.build();

        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                // Create the Actor and link it to the hexagon (and vice-versa)
                final HexagonActor hexActor = new HexagonActor(hexagon);

                hexActor.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());

                //STARTING COLOURS FOR EACH HEXAGON ON THE BOARD

                if (hexActor.getHexagon().getGridX() == -2 && hexActor.getHexagon().getGridY() == -8 && hexActor.getHexagon().getGridZ() == 10) {
                    hexActor.setHexColor(Color.BLUE);
                } else if (hexActor.getHexagon().getGridX() == 3 && hexActor.getHexagon().getGridY() == -13 && hexActor.getHexagon().getGridZ() == 10) {
                    hexActor.setHexColor(Color.YELLOW);
                } else if (hexActor.getHexagon().getGridX() == 8 && hexActor.getHexagon().getGridY() == -13 && hexActor.getHexagon().getGridZ() == 5) {
                    hexActor.setHexColor(Color.ORANGE);
                } else if (hexActor.getHexagon().getGridX() == 8 && hexActor.getHexagon().getGridY() == -8 && hexActor.getHexagon().getGridZ() == 0) {
                    hexActor.setHexColor(Color.PURPLE);
                } else if (hexActor.getHexagon().getGridX() == 3 && hexActor.getHexagon().getGridY() == -3 && hexActor.getHexagon().getGridZ() == 0) {
                    hexActor.setHexColor(Color.VIOLET);
                } else if (hexActor.getHexagon().getGridX() == -2 && hexActor.getHexagon().getGridY() == -3 && hexActor.getHexagon().getGridZ() == 5) {
                    hexActor.setHexColor(Color.RED);
                } else {
                    hexActor.setHexColor(Color.EMPTY);
                }

                hexagon.setSatelliteData(new Link(hexActor));

                addActor(hexActor);
            }
        });
    }

    public Board copy(Board board){
        Board newBoard = new Board();

        board.getGrid().getHexagons().forEach(new Action1<Hexagon<Link>>(){
            @Override
            public void call(Hexagon hexagon) {
                //FOR EACH HEXAGON
                if (hexagon.getSatelliteData().isPresent()) {
                    Link hexLink = (Link) hexagon.getSatelliteData().get();
                    HexagonActor currentHexActor = hexLink.getActor();

                    //IF AN HEXAGON IN PREVIOUS BOARD IS NOT EMPTY
                    if (!currentHexActor.getHexColor().getColor().equals(Color.EMPTY)) {
                        //TAKE THE CORRESPONDING HEXAGON IN THE NEW BOARD
                        Optional toCopy = newBoard.getGrid().getByCubeCoordinate(hexagon.getCubeCoordinate());
                        if (toCopy.isPresent()){
                            Hexagon copy = (Hexagon) toCopy.get();
                            if (copy.getSatelliteData().isPresent()){
                                Link copyLink = (Link) copy.getSatelliteData().get();
                                HexagonActor copyHexActor = copyLink.getActor();
                                //AND GIVE IT THE SAME COLOR
                                copyHexActor.setHexColor(currentHexActor.getHexColor());
                                System.out.println("Hexagon copied: " + copyHexActor.getHexColor().toString());
                            }
                        }
                    }
                }
            }
        });

        return newBoard;
    }

    public HexagonalGrid<Link> getGrid() {
        return grid;
    }

    public boolean gameOver() {
        this.over = true;
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                if (hexagon.getSatelliteData().isPresent()) {
                    Link hexLink = (Link) hexagon.getSatelliteData().get();
                    HexagonActor currentHexActor = hexLink.getActor();

                    if (currentHexActor.getHexColor().equals(Color.EMPTY)) {
                        for (Object hex : grid.getNeighborsOf(hexagon)) {

                            if (hex instanceof Hexagon) {
                                Hexagon currentNeighbor = (Hexagon) hex;

                                if (currentNeighbor.getSatelliteData().isPresent()) {
                                    Link neighLink = (Link) currentNeighbor.getSatelliteData().get();
                                    HexagonActor neighHexActor = neighLink.getActor();

                                    if (neighHexActor.getHexColor().equals(Color.EMPTY)) {
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

    public static Hexagon neighborByDirection(int d, Hexagon hexagon, HexagonalGrid hexagonalGrid){

        // d is the direction [0 = topLeft; 1 = left; 2 = botLeft; 3 = botRight; 4 = right; 5 = topRight]
        //given a direction the method checks if there is a neighbor, if positive return that neighbor, else null

        Hexagon hexNext;

        if(d == 0) {
            CubeCoordinate nextCoordinates = CubeCoordinate.fromCoordinates(hexagon.getGridX(), hexagon.getGridZ() + 1);
            if (hexagonalGrid.getByCubeCoordinate(nextCoordinates).isPresent()) {
                hexNext = (Hexagon) hexagonalGrid.getByCubeCoordinate(nextCoordinates).get();
                return hexNext;
            } else {
                return null;
            }

        } if(d == 1) {
            CubeCoordinate nextCoordinates = CubeCoordinate.fromCoordinates(hexagon.getGridX() + 1, hexagon.getGridZ());
            if (hexagonalGrid.getByCubeCoordinate(nextCoordinates).isPresent()) {
                hexNext = (Hexagon) hexagonalGrid.getByCubeCoordinate(nextCoordinates).get();
                return hexNext;
            } else {
                return null;
            }

        } if(d == 2) {
            CubeCoordinate nextCoordinates = CubeCoordinate.fromCoordinates(hexagon.getGridX() + 1, hexagon.getGridZ() - 1);
            if (hexagonalGrid.getByCubeCoordinate(nextCoordinates).isPresent()) {
                hexNext = (Hexagon) hexagonalGrid.getByCubeCoordinate(nextCoordinates).get();
                return hexNext;
            } else {
                return null;
            }

        } if(d == 3) {
            CubeCoordinate nextCoordinates = CubeCoordinate.fromCoordinates(hexagon.getGridX(), hexagon.getGridZ() - 1);
            if (hexagonalGrid.getByCubeCoordinate(nextCoordinates).isPresent()) {
                hexNext = (Hexagon) hexagonalGrid.getByCubeCoordinate(nextCoordinates).get();
                return hexNext;
            } else {
                return null;
            }

        } if(d == 4) {
            CubeCoordinate nextCoordinates = CubeCoordinate.fromCoordinates(hexagon.getGridX() - 1, hexagon.getGridZ());
            if (hexagonalGrid.getByCubeCoordinate(nextCoordinates).isPresent()) {
                hexNext = (Hexagon) hexagonalGrid.getByCubeCoordinate(nextCoordinates).get();
                return hexNext;
            } else {
                return null;
            }

        } if(d == 5) {
            CubeCoordinate nextCoordinates = CubeCoordinate.fromCoordinates(hexagon.getGridX() - 1, hexagon.getGridZ() + 1);
            if (hexagonalGrid.getByCubeCoordinate(nextCoordinates).isPresent()) {
                hexNext = (Hexagon) hexagonalGrid.getByCubeCoordinate(nextCoordinates).get();
                return hexNext;
            } else {
                return null;
            }

        } else {
            return null;
        }
    }
    public void act(float delta) {
        super.act(delta);
    }

}
