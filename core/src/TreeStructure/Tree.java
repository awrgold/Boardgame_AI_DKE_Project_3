package TreeStructure;

import com.game.Components.GameLogic.GameState;
import com.game.Components.GameLogic.GameView;

public class Tree {

    private Node root;

    public Tree(GameState state){
        this.root = new Node(state);
    }

    public void buildTree(GameState state){
        root = new Node(state);
    }

    public Node getRoot(){
        return root;
    }

}
