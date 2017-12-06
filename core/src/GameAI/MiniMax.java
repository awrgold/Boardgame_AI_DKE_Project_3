package GameAI;

import com.game.*;

public class MiniMax implements MiniMaxSearch{

    private int score;
    private int bestScore;

   Action getNextMove();



   /*
        Tree search Algo explanation and procedure

        lowest color == 1
        --- If double tile is found AND + 18 on score on the next move than give +1000
        --- If double tile is found and its the lowest color +750
        --- If and a tile of that color is placed on next move then +500

       Lowest color == 2
       --- Double tile of second lowest +300
       --- Tile with lowest color +200

       Lowest color > 3
       -- Double tiles +150
       -- Tiles + 50


       Based on the Evaluation function and our "priority queue", those points are given for the given move.
       Those points will be useful when build the tree and apply the min and max when comparisons between move are made

       BestScore should be set up to -infinity so that any move would be better than nothing
       ( also better so that the first move is taken as best move to be compared)`

       Set up Player one to Maximize and Player 2 to Minimize. No matter which player is who.

       Set up a gameover method that check if no longer moves can be made ( Check if a leaf node is reached )

       A child is basically the next move


    */


   public int MiniMax(int depth){

       //Max player
       if (Player.playerNo == 0) {
           bestScore = Integer.MIN_VALUE;

           //for each child
           score = MiniMax(depth - 1);
           if (score > bestScore) bestScore = score;
           return bestScore;

       }else{

           //Min Player

           bestScore = Integer.MAX_VALUE;

           //for each child
           score = MiniMax(depth- 1);
           if (score < bestScore) bestScore = score;
           return bestScore;

           }


   }


}
