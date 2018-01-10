package TreeStructure;

import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.TestAction;

public class TestEdge {

    private TestAction action;
    private TestNode parentNode;
    private TestNode childNode;

    public TestEdge(TestNode parent, TestNode child, TestAction action){
        this.parentNode = parent;
        this.childNode = child;
        this.action = action;
    }

    public TestAction getAction(){
        return action;
    }

    public TestNode getParentNode(){
        return parentNode;
    }

    public TestNode getChildNode(){
        return childNode;
    }

    public void setParentNode(TestNode parent){
        this.parentNode = parent;
    }

    public void setChildNode(TestNode child){
        this.childNode = child;
    }





}
