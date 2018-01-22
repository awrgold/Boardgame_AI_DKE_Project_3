package com.game.TreeStructure;

import com.game.Components.GameLogic.Action;

public class Edge {

    private Action action;
    private Node parentNode;
    private Node childNode;

    public Edge(Node parent, Node child, Action action){
        this.parentNode = parent;
        this.childNode = child;
        this.action = action;
    }



    public Action getAction(){
        return action;
    }

    public Node getParentNode(){
        return parentNode;
    }

    public Node getChildNode(){
        return childNode;
    }

    public void setParentNode(Node parent){
        this.parentNode = parent;
    }

    public void setChildNode(Node child){
        this.childNode = child;
    }





}
