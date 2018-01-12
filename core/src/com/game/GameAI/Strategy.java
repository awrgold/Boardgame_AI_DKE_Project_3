package com.game.GameAI;

import com.game.Components.GameLogic.Action;
import com.game.Components.PlayerAssets.Hand;
import com.game.Components.PlayerAssets.Tile;
import org.codetome.hexameter.core.api.HexagonalGrid;

import java.util.ArrayList;
import java.util.HashMap;

public interface Strategy {

    Action decideMove(ArrayList<String> colors, Hand hand, HexagonalGrid grid);




}
