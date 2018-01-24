package com.game.GameAI;

import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;

import java.util.ArrayList;

public class OtherStrategy implements Strategy {
    private ArrayList<Action> Moves;

    @Override
    public Action decideMove(GameState currentState) {
        return null;
    }
}
