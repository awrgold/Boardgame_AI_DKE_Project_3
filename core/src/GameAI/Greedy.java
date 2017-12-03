package GameAI;

import com.game.*;


public class Greedy implements GreedySearch {

    Action getNextMove();

    SmartEvaluations eval1 = new SmartEvaluations();

    public Action getNextMove(GameState state){

        Player nowPlaying = state.getGamingPlayer();

        return eval1.smartEvaluations(state);

    }


}