package TreeStructure;

import com.game.Components.GameLogic.GameState;

import java.util.ArrayList;

public class Node {

    private GameState state;
    private Edge parentEdge;
    private ArrayList<Edge> childrenEdges;

    public Node(GameState state){
        this.state = state;
    }

    public GameState getState(){
        return state;
    }

    public Edge getParentEdge(){
        return parentEdge;
    }

    public ArrayList<Edge> getChildrenEdges() {
        return childrenEdges;
    }

    public void setParentEdge(Edge parent){
        this.parentEdge = parent;
    }

    public void setChildrenEdges(ArrayList<Edge> children){
        this.childrenEdges = children;
    }

    public void setState(GameState state){
        this.state = state;
    }




}
