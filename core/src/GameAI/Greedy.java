package GameAI;

import com.game.*;


public class Greedy implements GreedySearch {

    SmartGreedyEval eval = new SmartGreedyEval();

    public Action getNextMove(GameState state){

        Player nowPlaying = state.getGamingPlayer();

        eval.getMove();

    }
}