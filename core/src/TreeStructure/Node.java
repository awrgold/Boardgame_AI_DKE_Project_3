package TreeStructure;

import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;

import java.util.ArrayList;

public class Node {

    private GameState state;
    private Edge parentEdge;
    private ArrayList<Edge> childrenEdges;

    public Node(GameState state){
        this.state = state;
        this.childrenEdges = new ArrayList<>();
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

    public void setState(GameState state){
        this.state = state;
    }

    public void setChild(Action action) {

        GameState nextState = state.applyAction(action);
        Node child = new Node(nextState);
        Edge edge = new Edge(this, child, action);
        child.setParentEdge(edge);
        childrenEdges.add(edge);



    }




}
