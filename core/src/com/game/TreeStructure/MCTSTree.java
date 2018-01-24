package com.game.TreeStructure;

import com.game.Components.GameLogic.GameState;

public class MCTSTree {

    private MCTSNode root;

    public MCTSTree(GameState state){
        this.root = new MCTSNode(state);
    }

    public void buildTree(GameState state){
        root = new MCTSNode(state);
    }

    public MCTSNode getRoot(){
        return root;
    }

    public void setRoot(MCTSNode newRoot){
        this.root = newRoot;
    }

}
