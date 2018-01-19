package com.game.TreeStructure;

import com.game.Components.GameLogic.Action;

public class Edge {

    private Action action;
    private MCTSNode parentNode;
    private MCTSNode childNode;

    public Edge(MCTSNode parent, MCTSNode child, Action action){
        this.parentNode = parent;
        this.childNode = child;
        this.action = action;
    }

    public Action getAction(){
        return action;
    }

    public MCTSNode getParentNode(){
        return parentNode;
    }

    public MCTSNode getChildNode(){
        return childNode;
    }

    public void setParentNode(MCTSNode parent){
        this.parentNode = parent;
    }

    public void setChildNode(MCTSNode child){
        this.childNode = child;
    }





}
