package com.game.GameAI;

import com.game.Components.GameLogic.Action;
import com.game.Components.PlayerAssets.Hand;
import org.codetome.hexameter.core.api.HexagonalGrid;

import java.util.ArrayList;

public class OtherStrategy implements Strategy {
    @Override
    public Action decideMove(ArrayList<String> colors, Hand hand, HexagonalGrid grid) {
        return null;
    }
}
