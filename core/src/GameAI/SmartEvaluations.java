package GameAI;

import com.game.Action;
import com.game.GameState;
import com.game.Tile;

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

    public SmartEvaluations(){

    }



    public Action smartEvaluations(GameState state){

        if(state.getGamingPlayer().howManyLowestColors() > 2){
            /*
            1) Search hand
            2) Find all tiles with lowest colors
            3) Place them in a priority queue, with any double tiles or tiles with both lowest colors at top of queue
            5) Evaluate each tile in the queue for best scoring move
            6) Choose tile with best score, or at random if score is same (can update to predict future moves)
            7) Return action placing that tile
             */

            Action newAction = new Action(state.getGamingPlayer().getSortedTiles().get(0));
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
        }
        return Action();
    }

}
