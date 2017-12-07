package GameAI;

import TreeStructure.Edge;
import TreeStructure.Node;
import TreeStructure.Tree;
import com.game.Action;
import com.game.GameState;
import com.game.Tile;

import java.util.ArrayList;

public class SmartEvaluations {

    /*
        - If start of game (3+ lowest colors):
        --- Prioritize double tile
        --- If no double, find tiles with lowest scores
        --- Try placements that increase score
        --- Return Action that maximizes score (hex 1 > hex 2 in tile)

        - If AI has 2 lowest colors:
        --- Prioritize tiles with both colors
        --- If not, choose tile with random lowest color
        --- Try placements that increase score
        --- Return Action that maximizes score

        - If AI has 1 lowest color:
        --- Prioritize double color tiles
        --- If not, choose tile with lowest color + 2nd (or 3rd or 4th) lowest color
        --- Try placements that increase score
        --- Prioritize placements that lead to ingenious
        --- Return Action that maximizes score
         */

    private ArrayList<Double> evalMultipliers = new ArrayList<>();

    // Set an ingenious weight to increase ingenious score
    private double ingMultiplier = 2.0;

    public SmartEvaluations(){

    }

    public void setEvalMultipliers(){
        // set the multiplier weight to score each move based on priorities
        evalMultipliers.set(0, 1.0);
        evalMultipliers.set(1, 0.8);
        evalMultipliers.set(2, 0.6);
        evalMultipliers.set(3, 0.4);
        evalMultipliers.set(4, 0.2);
        evalMultipliers.set(5, 0.1);
    }




    public Action evaluate(GameState state, Tree tree){

        setEvalMultipliers();

        Action newAction = new Action();

        if(state.getGamingPlayer().howManyLowestColors() > 2){
            /*
            1) Search hand
            2) Find all tiles with lowest colors
            3) Place them in a priority queue, with any double tiles or tiles with both lowest colors at top of queue
            4) Search root node's children (getAllValidActions) and score each one, evaluate each tile in the queue for best scoring move
            6) Choose tile with best score, or at random if score is same (can update to predict future moves)
            7) Return action placing that tile
            */

            /*
            // Assign scores to each "state" node and evaluate
            for(Edge e : tree.getRoot().getChildrenEdges()){
                e.getChildNode().
            }
            */



            newAction = new Action(state.getCurrentBoard().getGrid().getByPixelCoordinate(500.0, 500.0).get(),
                    state.getCurrentBoard().getGrid().getByPixelCoordinate(500.0, 500.0).get(),
                            state.getGamingPlayer().getSortedTiles().get(0));
        }

        if(state.getGamingPlayer().howManyLowestColors() == 2){
            /*
            1) Search hand
            2) Find both tiles with lowest colors
            3) Place them in a priority queue, with any double tiles or tiles with both lowest colors at top of queue
            5) Evaluate each tile in the queue for best scoring move
            6) Choose tile with best score, or at random if score is same (can update to predict future moves)
            7) Return action placing that tile
            */

            newAction = new Action(state.getCurrentBoard().getGrid().getByPixelCoordinate(500.0, 500.0).get(),
                    state.getCurrentBoard().getGrid().getByPixelCoordinate(500.0, 500.0).get(),
                    state.getGamingPlayer().getSortedTiles().get(0));
        }

        if(state.getGamingPlayer().howManyLowestColors() == 1){
            /*
            1) Search hand
            2) Find any tiles with lowest colors
            3) Place them in a priority queue, with any double tiles or tiles with lower scoring colors at top of queue
            5) Evaluate each tile in the queue for best scoring move
            6) Choose tile with best score, or at random if score is same (can update to predict future moves)
            7) Return action placing that tile
            */
            newAction = new Action(state.getCurrentBoard().getGrid().getByPixelCoordinate(500.0, 500.0).get(),
                    state.getCurrentBoard().getGrid().getByPixelCoordinate(500.0, 500.0).get(),
                    state.getGamingPlayer().getSortedTiles().get(0));
        }
        return newAction;
    }

    /*
    public double getNodeweight(Node node){

    }


    public void setNodeWeight(Node node){

        ArrayList<Integer> actionScores = new ArrayList<>();

        node.getState().getAllValidActions
    }
    */


}
