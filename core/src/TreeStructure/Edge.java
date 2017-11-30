package TreeStructure;

import com.game.Action;

public class Edge {

    private Action action;
    private Node parentNode;
    private Node childNode;
    private Tree tree;

    public Edge(Tree tree, Node parent, Node child, Action action){
        this.parentNode = parent;
        this.childNode = child;
        this.action = action;
        this.tree = tree;

        parent.addChildEdge(this);
        child.setParentEdge(this);
    }

    public Action getAction(){
        return action;
    }

    public Node getParentNode(){
        return parentNode;
    }

    public Node getChildNode(){
        return childNode;
    }

    public void setParentNode(Node parent){
        this.parentNode = parent;
    }

    public void setChildNode(Node child){
        this.childNode = child;
    }

    public Tree getTree(){
        return tree;
    }





}
