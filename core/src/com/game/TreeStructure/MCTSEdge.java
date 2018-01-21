package com.game.TreeStructure;

import com.game.Components.GameLogic.Action;

public class MCTSEdge {

    private Action action;
    private MCTSNode parentNode;
    private MCTSNode childNode;
    private double weight;


    public MCTSEdge(MCTSNode parent, MCTSNode child, Action action){
        this.parentNode = parent;
        this.childNode = child;
        this.action = action;
        this.weight = action.actionGain(parent.getState().getCurrentBoard().getGrid());
    }

    public Action getAction(){
        return action;
    }

    public double getWeight(){
        return weight;
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
