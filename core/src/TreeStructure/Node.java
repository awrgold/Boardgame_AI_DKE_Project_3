package TreeStructure;

import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.GameLogic.GameView;

import java.util.ArrayList;

public class Node {

    private GameView state;
    private Edge parentEdge;
    private ArrayList<Edge> childrenEdges;

    public Node(GameView state){
        this.state = state;
        this.childrenEdges = new ArrayList<>();
    }

    public GameView getState(){
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

    public void setState(GameView state){
        this.state = state;
    }

    public void setChild(Action action) {

        GameView nextView = state.simulateAction(action);
        Node child = new Node(nextView);
        Edge edge = new Edge(this, child, action);
        child.setParentEdge(edge);
        childrenEdges.add(edge);



    }




}
