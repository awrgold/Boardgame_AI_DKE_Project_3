package com.game.Components.GameAssets;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.GameConstants.Constants;
import com.game.Components.Tools.GroupView;
import com.game.Components.Tools.Link;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

public class Board {

    private HexagonalGrid<Link> grid;
    private boolean over;

    public Board(){
        create();
    }

    public void create(){
        this.grid = Constants.grid.build();

        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {


                //STARTING COLOURS FOR EACH HEXAGON ON THE BOARD

                if (hexagon.getGridX() == -2 && hexagon.getGridY() == -8 && hexagon.getGridZ() == 10) {
                    hexagon.setSatelliteData(new Link("B"));
                } else if (hexagon.getGridX() == 3 && hexagon.getGridY() == -13 && hexagon.getGridZ() == 10) {
                    hexagon.setSatelliteData(new Link("Y"));
                } else if (hexagon.getGridX() == 8 && hexagon.getGridY() == -13 && hexagon.getGridZ() == 5) {
                    hexagon.setSatelliteData(new Link("O"));
                } else if (hexagon.getGridX() == 8 && hexagon.getGridY() == -8 && hexagon.getGridZ() == 0) {
                    hexagon.setSatelliteData(new Link("P"));
                } else if (hexagon.getGridX() == 3 && hexagon.getGridY() == -3 && hexagon.getGridZ() == 0) {
                    hexagon.setSatelliteData(new Link("V"));
                } else if (hexagon.getGridX() == -2 && hexagon.getGridY() == -3 && hexagon.getGridZ() == 5) {
                    hexagon.setSatelliteData(new Link("R"));
                } else {
                    hexagon.setSatelliteData(new Link("EMPTY"));
                }




            }
        });
    }

    public Group displayBoard(){
        Group boardGroup = new Group();
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                HexagonActor hex = new HexagonActor(hexagon);
                hex.setHexColor();
                hex.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());

                boardGroup.addActor(hex);
            }

        });

        return boardGroup;
    }

    public HexagonalGrid<Link> getGrid() {
        return grid;
    }

  /*  public boolean gameOver() {
        this.over = true;
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
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
    }*/

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


}
