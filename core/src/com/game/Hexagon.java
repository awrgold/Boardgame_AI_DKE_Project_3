package com.game;

public class Hexagon {

    private final int x;
    private final int y;
    private final int z;
    private final String color;
    private int[] coordinates = new int[3];
    private Hexagon[] neighbors = new Hexagon[6];

    public Hexagon(int x, int y, int z, String color){
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getZ(){
        return z;
    }

    public int[] getCoordinates(){
        return coordinates;
    }

    public String getColor(){
        return color;
    }

    public Hexagon getNeighbor(int index){
        return neighbors[index];
    }

    public Hexagon[] getNeighbors(){
        return neighbors;
    }

    public void setNeighbors(Hexagon hex){
        // Inside pieces
        if(Math.abs(hex.getX()) < 5 && Math.abs(hex.getY()) < 5 && Math.abs(hex.getZ()) < 5){
            neighbors[0] = Board.getHex(hex.getX()-1, hex.getY(), hex.getZ()+1);
            neighbors[1] = Board.getHex(hex.getX()-1, hex.getY()+1, hex.getZ());
            neighbors[2] = Board.getHex(hex.getX(), hex.getY()+1, hex.getZ()-1);
            neighbors[3] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ()-1);
            neighbors[4] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ());
            neighbors[5] = Board.getHex(hex.getX(), hex.getY()-1, hex.getZ()+1);
        }
        // edge cases
        else if(Math.abs(hex.getX()) + Math.abs(hex.getY()) + Math.abs(hex.getZ()) == 10){
            // Far right corner
            if(hex.getX() == 5 && hex.getZ() == 0){
                neighbors[0] = Board.getHex(hex.getX()-1, hex.getY(), hex.getZ()+1);
                neighbors[1] = Board.getHex(hex.getX()-1, hex.getY()+1, hex.getZ());
                neighbors[2] = Board.getHex(hex.getX(), hex.getY()+1, hex.getZ()-1);
                neighbors[3] = null;
                neighbors[4] = null;
                neighbors[5] = null;
            }
            // Top right corner
            if(hex.getX() == 5 && hex.getZ() == -5){
                neighbors[0] = Board.getHex(hex.getX()-1, hex.getY(), hex.getZ()+1);
                neighbors[1] = Board.getHex(hex.getX()-1, hex.getY()+1, hex.getZ());
                neighbors[2] = null;
                neighbors[3] = null;
                neighbors[4] = null;
                neighbors[5] = Board.getHex(hex.getX(), hex.getY()-1, hex.getZ()+1);
            }
            // Top left corner
            if(hex.getY() == 5 && hex.getZ() == -5){
                neighbors[0] = Board.getHex(hex.getX()-1, hex.getY(), hex.getZ()+1);
                neighbors[1] = null;
                neighbors[2] = null;
                neighbors[3] = null;
                neighbors[4] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ());
                neighbors[5] = Board.getHex(hex.getX(), hex.getY()-1, hex.getZ()+1);
            }
            // Far left corner
            if(hex.getY() == 5 && hex.getZ() == 0){
                neighbors[0] = null;
                neighbors[1] = null;
                neighbors[2] = null;
                neighbors[3] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ()-1);
                neighbors[4] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ());
                neighbors[5] = Board.getHex(hex.getX(), hex.getY()-1, hex.getZ()+1);
            }
            // Bottom left corner
            if(hex.getZ() == 5 && hex.getY() == 0){
                neighbors[0] = null;
                neighbors[1] = null;
                neighbors[2] = Board.getHex(hex.getX(), hex.getY()+1, hex.getZ()-1);
                neighbors[3] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ()-1);
                neighbors[4] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ());
                neighbors[5] = null;
            }
            // Bottom right corner
            if(hex.getZ() == 5 && hex.getY() == -5){
                neighbors[0] = null;
                neighbors[1] = Board.getHex(hex.getX()-1, hex.getY()+1, hex.getZ());
                neighbors[2] = Board.getHex(hex.getX(), hex.getY()+1, hex.getZ()-1);
                neighbors[3] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ()-1);
                neighbors[4] = null;
                neighbors[5] = null;
            }
            // Edges side 0
            if(hex.getX() == -5){
                neighbors[0] = null;
                neighbors[1] = null;
                neighbors[2] = Board.getHex(hex.getX(), hex.getY()+1, hex.getZ()-1);
                neighbors[3] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ()-1);
                neighbors[4] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ());
                neighbors[5] = Board.getHex(hex.getX(), hex.getY()-1, hex.getZ()+1);
            }
            // Edges side 1
            if(hex.getY() == 5){
                neighbors[0] = Board.getHex(hex.getX()-1, hex.getY(), hex.getZ()+1);
                neighbors[1] = null;
                neighbors[2] = null;
                neighbors[3] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ()-1);
                neighbors[4] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ());
                neighbors[5] = Board.getHex(hex.getX(), hex.getY()-1, hex.getZ()+1);
            }
            // Edges side 2
            if(hex.getZ() == -5){
                neighbors[0] = Board.getHex(hex.getX()-1, hex.getY(), hex.getZ()+1);
                neighbors[1] = Board.getHex(hex.getX()-1, hex.getY()+1, hex.getZ());
                neighbors[2] = null;
                neighbors[3] = null;
                neighbors[4] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ());
                neighbors[5] = Board.getHex(hex.getX(), hex.getY()-1, hex.getZ()+1);
            }
            // Edges side 3
            if(hex.getX() == 5){
                neighbors[0] = Board.getHex(hex.getX()-1, hex.getY(), hex.getZ()+1);
                neighbors[1] = Board.getHex(hex.getX()-1, hex.getY()+1, hex.getZ());
                neighbors[2] = Board.getHex(hex.getX(), hex.getY()+1, hex.getZ()-1);
                neighbors[3] = null;
                neighbors[4] = null;
                neighbors[5] = Board.getHex(hex.getX(), hex.getY()-1, hex.getZ()+1);
            }
            // Edges side 4
            if(hex.getY() == -5){
                neighbors[0] = Board.getHex(hex.getX()-1, hex.getY(), hex.getZ()+1);
                neighbors[1] = Board.getHex(hex.getX()-1, hex.getY()+1, hex.getZ());
                neighbors[2] = Board.getHex(hex.getX(), hex.getY()+1, hex.getZ()-1);
                neighbors[3] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ()-1);
                neighbors[4] = null;
                neighbors[5] = null;
            }
            // Edges side 5
            if(hex.getZ() == 5){
                neighbors[0] = null;
                neighbors[1] = Board.getHex(hex.getX()-1, hex.getY()+1, hex.getZ());
                neighbors[2] = Board.getHex(hex.getX(), hex.getY()+1, hex.getZ()-1);
                neighbors[3] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ()-1);
                neighbors[4] = Board.getHex(hex.getX()+1, hex.getY()-1, hex.getZ());
                neighbors[5] = null;
            }
        }
    }


}
