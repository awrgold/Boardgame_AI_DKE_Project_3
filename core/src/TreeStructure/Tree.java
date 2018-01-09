package TreeStructure;

import com.game.Components.GameLogic.GameState;
import com.game.Components.GameLogic.TestGameState;

public class Tree {

    private Node root;

    public Tree(){}

    public void buildTree(GameState state){
        root = new Node(state);
    }

    public Node getRoot(){
        return root;
    }

}
