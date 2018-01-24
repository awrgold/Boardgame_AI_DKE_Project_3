package com.game.TreeStructure;

import com.game.Components.GameLogic.Action;

public class MCTSEdge {

    private Action action;
    private MCTSNode parentNode;
    private MCTSNode childNode;
    private double parentWeight;
    private double childWeight;


    public MCTSEdge(MCTSNode parent, MCTSNode child, Action action){
        this.parentNode = parent;
        this.childNode = child;
        this.action = action;
        this.parentWeight = action.actionGain(parent.getState());
        this.childWeight = action.actionGain(child.getState());
    }

    public Action getAction(){
        return action;
    }

    public double getParentWeight(){
        return parentWeight;
    }

    public double getChildWeight(){
        return childWeight;
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
