package com.game;
import sun.security.provider.certpath.Vertex;

import java.util.ArrayList;

public class Node extends Graph {

    /*
    * Colors: (7 - 6 colors and an empty for non-colored spaces)
    * 0 - EMPTY
    * 1 - Red
    * 2 - Orange
    * 3 - Yellow
    * 4 - Green
    * 5 - Blue
    * 6 - Purple
     */

    private static int nodeNumber;
    private static int Color;
    private static int[] coordinates = new int[2];
    private static ArrayList<Node> edges;


    public Node (int nodeNum){
        nodeNumber = nodeNum;
    }

    public static int getNodeNumber() {
        return nodeNumber;
    }

    public static void setNodeNumber(int number) {
        nodeNumber = number;
    }

    public static int getColor(){
        return Color;
    }

    public static void setColor(int color) {
        Color = color;
    }

    public static ArrayList<Node> getEdges() {
        return edges;
    }

    public static void setEdges(Node node){
        for(int i = 0; i < 6; i++){
            // Loop through the game board and find all adjacent nodes, and add those nodes to a list of edges.
            // I need the hexagon tile Object's coordinates so that I can create an adjacency matrix.

        }
    }
}