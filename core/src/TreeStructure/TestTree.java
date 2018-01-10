package TreeStructure;

import com.game.Components.GameLogic.GameState;
import com.game.Components.GameLogic.TestGameState;

public class TestTree {

    private TestNode root;

    public TestTree(){}

    public void buildTree(TestGameState state){
        root = new TestNode(state);
    }


    public TestNode getRoot(){
        return root;
    }

}
