package GameAI;

import com.game.*;


public class Greedy implements GreedySearch {

    SmartGreedyEval eval1 = new SmartGreedyEval();

    public Action getNextMove(GameState state){

        Player nowPlaying = state.getGamingPlayer();

        eval1.getMove();

    }
}