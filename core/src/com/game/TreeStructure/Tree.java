package com.game.TreeStructure;

import com.game.Components.GameLogic.GameView;

public class Tree {

    private Node root;

    public Tree(GameView state){
        this.root = new Node(state);
    }

    public void buildTree(GameView state){
        root = new Node(state);
    }

    public Node getRoot(){
        return root;
    }

}
