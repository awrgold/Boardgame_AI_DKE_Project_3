package GameAI;

import TreeStructure.Node;
import TreeStructure.Tree;
import com.game.Action;
import com.game.GameManager;
import com.game.GameState;
import com.game.Player;

public class TestingManager {

    private GameState currentState;
    private Tree tree;
    private Action move;
    private SmartEvaluations eval = new SmartEvaluations();
    private int turnNumber = 0;
    private GameManager gm;

    public TestingManager(){
        gm = new GameManager();
        currentState = gm.getCurrentState();
        move = new Action();
    }

    public void makeMove(Action action){
        currentState = currentState.applyAction(action);
        move = new Action();
    }

    public GameState getCurrentState(){
        return currentState;
    }

    public void run(){
        getAgentAction();
        if(endGameCheck()){
            System.out.println("Game Over");
        }
    }


    public void getAgentAction(){
        Action currentAction = eval.evaluate(currentState, tree);
        int cntr = 0;
        if(currentAction != null){
            makeMove(currentAction);
            cntr++;
            System.out.println("Action # " + cntr + " made.");
        }
    }

    public boolean endGameCheck() {
        if (turnNumber < 100) {
            return false;
        } else {
            return true;
        }
    }






}
