package GameBoardAssets;

import GameConstants.Constants;
import Systems.GroupView;
import Tools.Link;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.codetome.hexameter.core.api.*;
import rx.functions.Action1;

import static org.codetome.hexameter.core.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.HEXAGONAL;

public class GameBoardView extends GroupView {
    //board grid
    private HexagonalGrid grid;
    private final int GRID_HEIGHT = 11;
    private final int GRID_WIDTH = 11;
    private final HexagonalGridLayout GRID_LAYOUT = HEXAGONAL;
    private final HexagonOrientation ORIENTATION = POINTY_TOP;
    private HexagonalGridBuilder<Link> builder;
    private SpriteBatch batch;
    final double RADIUS = Constants.getHexRadius();
    private Sprite[] touched = {null, null};
    private HexagonActor first;
    private TileView selectedTile;
    private int selectedTileIndex;

    public GameBoardView(){
        super();
        batch = new SpriteBatch();
        create();
    }
    public void create(){
        //grid builder
        builder = new HexagonalGridBuilder<Link>()
                .setGridHeight(GRID_HEIGHT)
                .setGridWidth(GRID_WIDTH)
                .setGridLayout(GRID_LAYOUT)
                .setOrientation(ORIENTATION)
                .setRadius(RADIUS);


        HexagonalGrid<Link> grid = builder.build();
        this.grid = grid;
        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
            @Override
            public void call(Hexagon hexagon) {

                // Create the Actor and link it to the hexagon (and vice-versa)
                final HexagonActor hexActor = new HexagonActor(hexagon);

                Sprite emptySprite = new Sprite(new Texture(Gdx.files.internal("4players.png")));
                Sprite corner1Sprite = new Sprite(new Texture(Gdx.files.internal("colours/blue.png")));
                Sprite corner2Sprite = new Sprite(new Texture(Gdx.files.internal("colours/yellow.png")));
                Sprite corner3Sprite = new Sprite(new Texture(Gdx.files.internal("colours/orange.png")));
                Sprite corner4Sprite = new Sprite(new Texture(Gdx.files.internal("colours/purple.png")));
                Sprite corner5Sprite = new Sprite(new Texture(Gdx.files.internal("colours/violet.png")));
                Sprite corner6Sprite = new Sprite(new Texture(Gdx.files.internal("colours/red.png")));

                //mainMenuButton = new Sprite(new Texture(Gdx.files.internal("MainMenu.png")));
                //hexActor.setSprite(mainMenuButton)


                hexActor.setPosition((float) hexagon.getCenterX(), (float) hexagon.getCenterY());

                addActor(hexActor);

                hexagon.setSatelliteData(new Link(hexActor));
                //STARTING COLOURS FOR EACH HEXAGON ON THE BOARD

                if (hexActor.getHexagon().getGridX() == -2 && hexActor.getHexagon().getGridY() == -8 && hexActor.getHexagon().getGridZ() == 10) {
                    hexActor.setSprite(corner1Sprite);
                    hexActor.setHexColor("B");
                } else if (hexActor.getHexagon().getGridX() == 3 && hexActor.getHexagon().getGridY() == -13 && hexActor.getHexagon().getGridZ() == 10) {
                    hexActor.setSprite(corner2Sprite);
                    hexActor.setHexColor("Y");
                } else if (hexActor.getHexagon().getGridX() == 8 && hexActor.getHexagon().getGridY() == -13 && hexActor.getHexagon().getGridZ() == 5) {
                    hexActor.setSprite(corner3Sprite);
                    hexActor.setHexColor("O");
                } else if (hexActor.getHexagon().getGridX() == 8 && hexActor.getHexagon().getGridY() == -8 && hexActor.getHexagon().getGridZ() == 0) {
                    hexActor.setSprite(corner4Sprite);
                    hexActor.setHexColor("P");
                } else if (hexActor.getHexagon().getGridX() == 3 && hexActor.getHexagon().getGridY() == -3 && hexActor.getHexagon().getGridZ() == 0) {
                    hexActor.setSprite(corner5Sprite);
                    hexActor.setHexColor("V");
                } else if (hexActor.getHexagon().getGridX() == -2 && hexActor.getHexagon().getGridY() == -3 && hexActor.getHexagon().getGridZ() == 5) {
                    hexActor.setSprite(corner6Sprite);
                    hexActor.setHexColor("R");
            }  else if (hexActor.getHexagon().getGridX() == 3 && hexActor.getHexagon().getGridY() == -8 && hexActor.getHexagon().getGridZ() == 5) {
                hexActor.setSprite(corner6Sprite);
                hexActor.setHexColor("R");
            }   else {
                    hexActor.setSprite(emptySprite);
                }

                hexActor.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println(hexActor.getHexagon().getGridX() + ", " + hexActor.getHexagon().getGridY() + ", " + hexActor.getHexagon().getGridZ());


                       if(touched[0] != null && hexActor.getSprite() == emptySprite){
                            hexActor.setSprite(touched[0]);
                           hexActor.setHexColor(getSpriteColor(hexActor));
                            first = hexActor;
                           touched[0] = null;
//                            Player.updateScore(gamingPlayer, hexActor, grid);



                        } else if (touched[0] == null && touched[1] != null && hexActor.getSprite() == emptySprite){
                            if (grid.getNeighborsOf(first.getHexagon()).contains(hexActor.getHexagon())){
                               hexActor.setSprite(touched[1]);
                                hexActor.setHexColor(getSpriteColor(hexActor));
                                touched[1] = null;
                                first = null;
//                                Player.updateScore(gamingPlayer, hexActor, grid);




                                //after the second click remove from hand the placed tile
//                                gamingPlayer.getGamePieces().remove(selectedTileIndex);

                                //take a new one
//                                Pieces.takePiece(bag, gamingPlayer.getGamePieces());

                                //and set the new sprites
                                int ind = 0;
                                for (Actor hex : selectedTile.getChildren()){

                                    if (hex instanceof HexagonActor){
                                       HexagonActor one = (HexagonActor) hex;
//                                       one.setSprite(gamingPlayer.getGamePieces().get(0)[ind]);
                                        ind++;
                                   }
                               }
//                                ///////////////////////////////score update
//                                gamingPlayer.printScore();
//
//                                //little work around blips and bloops @Michael
////                                if (gamingPlayer == players[0]) {
//                                   // selectedTile.moveBy(0, 30);
////                                }
////                                if(gamingPlayer==players[1]){
//                                       selectedTile.moveBy(0, -30);
////                                }
//                                //end
//                                gamingPlayer = players[Math.abs(gamingPlayer.getPlayerNo() - 2)];



                            }
                            else {
                                System.out.println("Select a neighbor");
                            }

                        } else if (touched[0] == null && touched[1] == null){
                            System.out.println("Select a piece from your hand!");
                        } else {
                            System.out.println("This slot is full! Color here is: " + hexActor.getHexColor());
                        }


                    }
                });



            }

        });
    }
    public void act( float delta) {
        super.act(delta);
    }

            //Gets the color of a sprite
            public String getSpriteColor(HexagonActor hexActor){
                Texture texture = hexActor.getSprite().getTexture();
                String path = ((FileTextureData)texture.getTextureData()).getFileHandle().path();

                String purple = "colours/purple.png";
                String red =    "colours/red.png";
                String blue =   "colours/blue.png";
                String yellow = "colours/yellow.png";
                String orange = "colours/orange.png";
                String violet = "colours/violet.png";


                if(path.equals(purple)){
                    path = "P";
                    return path;
                }

                else if(path.equals(red)){
                    path = "R";
                    return path;
                }

                else if(path.equals(blue)){
                    path = "B";
                    return path;
                }

                else if(path.equals(yellow)){
                    path = "Y";
                    return path;
                }

                else if(path.equals(orange)){
                    path = "O";
                    return path;
                }

                else if(path.equals(violet)){
                    path = "V";
                    return path;
                }

                else{
                    return path;
                }
            }
}
