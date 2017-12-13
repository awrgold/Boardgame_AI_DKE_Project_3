package GameAI;

import TreeStructure.Tree;
import com.game.*;

/*
public class MiniMax implements MiniMaxSearch{

    Tree tree;
    Tree root;
    private int score;
    private int bestScore;

    //Action getNextMove();



   /*
        1. Construct the complete game tree

        2. Evaluate scores for leaves using the evaluation function

        3. Back-up scores from leaves to root, considering the player type:
            3A. For max player, select the child with the maximum score
            3B. For min player, select the child with the minimum score

        4. At the root node, choose the node with max value and perform the corresponding move

    */

    /*
    public void constructTree() {
        tree = new Tree();
        tree.getRoot();
        tree.setRoot(root);
        tree.buildTree();
    }
    */







    /*

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
*/