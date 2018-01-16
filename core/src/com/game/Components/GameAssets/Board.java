package com.game.Components.GameAssets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.Components.Tools.HexagonActor;
import com.game.Components.GameConstants.Constants;
import com.game.Components.Tools.GroupView;
import com.game.Components.Tools.Link;
import org.codetome.hexameter.core.api.CubeCoordinate;
import org.codetome.hexameter.core.api.Hexagon;
import org.codetome.hexameter.core.api.HexagonalGrid;
import rx.functions.Action1;

public class Board extends GroupView {
private SpriteBatch batch;
    private HexagonalGrid<Link> grid;
    private boolean over;

    public Board(){
        super();
        create();
    }

    public void create(){

        this.grid = Constants.grid.build();

        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {
                // Create the Actor and link it to the hexagon (and vice-versa)
                final HexagonActor hexActor = new HexagonActor(hexagon);

                hexActor.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());

                //STARTING COLOURS FOR EACH HEXAGON ON THE BOARD

                if (hexActor.getHexagon().getGridX() == -2 && hexActor.getHexagon().getGridY() == -8 && hexActor.getHexagon().getGridZ() == 10) {
                    hexActor.setHexColor("B");
                } else if (hexActor.getHexagon().getGridX() == 3 && hexActor.getHexagon().getGridY() == -13 && hexActor.getHexagon().getGridZ() == 10) {
                    hexActor.setHexColor("Y");
                } else if (hexActor.getHexagon().getGridX() == 8 && hexActor.getHexagon().getGridY() == -13 && hexActor.getHexagon().getGridZ() == 5) {
                    hexActor.setHexColor("O");
                } else if (hexActor.getHexagon().getGridX() == 8 && hexActor.getHexagon().getGridY() == -8 && hexActor.getHexagon().getGridZ() == 0) {
                    hexActor.setHexColor("P");
                } else if (hexActor.getHexagon().getGridX() == 3 && hexActor.getHexagon().getGridY() == -3 && hexActor.getHexagon().getGridZ() == 0) {
                    hexActor.setHexColor("V");
                } else if (hexActor.getHexagon().getGridX() == -2 && hexActor.getHexagon().getGridY() == -3 && hexActor.getHexagon().getGridZ() == 5) {
                    hexActor.setHexColor("R");
                } else {
                    hexActor.setHexColor("EMPTY");
                }

                hexagon.setSatelliteData(new Link(hexActor));

                addActor(hexActor);
            }
        });
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
      //  super.act(delta);
       // updateHexActors(delta);
    }

    private void updateHexActors(float delta) {
    }
}
