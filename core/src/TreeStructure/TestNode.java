package TreeStructure;

import com.game.Components.GameLogic.GameState;
import com.game.Components.GameLogic.TestGameState;

import java.util.ArrayList;

public class TestNode {

    private TestGameState state;
    private TestEdge parentEdge;
    private ArrayList<TestEdge> childrenEdges;

    public TestNode(TestGameState state){
        this.state = state;
    }

    public TestGameState getState(){
        return state;
    }

    public TestEdge getParentEdge(){
        return parentEdge;
    }

    public ArrayList<TestEdge> getChildrenEdges() {
        return childrenEdges;
    }

    public void setParentEdge(TestEdge parent){
        this.parentEdge = parent;
    }

    public void setChildrenEdges(ArrayList<TestEdge> children){
        this.childrenEdges = children;
    }

    public void setState(TestGameState state){
        this.state = state;
    }




}
