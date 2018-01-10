package com.game.GameAI;

public class ExpectiMax implements Strategy {

    /*

    - p(having a tile in our hand at the begining) = 0.05
    - p(having a double tile a the begining | bag) = 0.25
    - p(having a tile at the begining|bag) = 0.75


    - p(having a double in the bag) =  (30 - #double already placed) / (#remaining tiles in the bag)
    - p(having a tile in the bag) = (90 - #tile already placed) / (#remaining tiles in the bag)


    - p(having in our hand) = 6 / (#remaining tiles in the bag)


     */
}
