package TreeStructure;

import com.game.GameState;

import java.util.ArrayList;

public class Node {

    private GameState state;
    private Edge parentEdge;
    private ArrayList<Edge> childrenEdges;

    public Node(GameState state, Edge parentEdge, ArrayList<Edge> childrenEdges){
        this.state = state;
        this.parentEdge = parentEdge;
        this.childrenEdges = childrenEdges;
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

    public void addChildEdge(Edge childEdge){
        childrenEdges.add(childEdge);
    }

    public void setState(GameState state){
        this.state = state;
    }

}