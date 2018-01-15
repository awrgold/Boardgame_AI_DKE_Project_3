package TreeStructure;

import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameManager;
import com.game.Components.GameLogic.GameState;
import com.game.GameIngenious;

import java.util.ArrayList;


public class TreeTester {

    public TreeTester(){

    }


    public void iterate(Tree tree){
        for (int i = 0; i < 10; i++){
            ArrayList<Edge> children = new ArrayList<>();

            children.add(new Edge(new Node(new GameState()), new Node(new GameState()), new Action()));
            System.out.println("Current state's gaming player: " + tree.getRoot().getState().getGamingPlayer().getPlayerNo());
            tree.getRoot().setChildrenEdges(children);
            tree.getRoot().setState(children.get(0).getChildNode().getState());
            tree.getRoot().getState().changeGamingPlayer();
            System.out.println("Current state's gaming player: " + tree.getRoot().getState().getGamingPlayer().getPlayerNo());
        }

    }


}
