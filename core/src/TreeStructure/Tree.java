package TreeStructure;

import com.game.GameState;

import java.util.ArrayList;

public class Tree {

    public Node root;

    public Tree(){}

    public void buildTree(GameState state, Edge parentEdge, ArrayList<Edge> children){
        root = new Node(state, null, null);
    }

    public Node getRoot(){
        return root;
    }

    public void setRoot(Node nextRoot){
        this.root = nextRoot;
    }



}
