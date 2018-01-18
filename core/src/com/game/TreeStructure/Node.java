package com.game.TreeStructure;

import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.GameLogic.GameView;

import java.util.ArrayList;

public class Node {

    private GameState state;
    private Edge parentEdge;
    private ArrayList<Edge> childrenEdges;
    private double weight;

    public Node(GameState state){
        this.state = state;
        this.childrenEdges = new ArrayList<>();
    }

    public void setWeight(double x){
        weight = x;
    }

    public GameState getState(){
        return state;
    }

    public double getWeight() {
        return weight;
    }

    public Edge getParentEdge(){
        return parentEdge;
    }

    public ArrayList<Edge> getChildrenEdges() {
        return childrenEdges;
    }

    public void setParentEdge(Edge parent){
        this.parentEdge = parent;
    }

    public void setState(GameState state){
        this.state = state;
    }

    public void setChild(Action action) {

        weight = action.actionGain(getState().getBoard().getGrid());
        GameView nextView = state.simulateAction(action);
        Node child = new Node(nextView);
        Edge edge = new Edge(this, child, action);
        child.setParentEdge(edge);
        childrenEdges.add(edge);
        System.out.println("creating node: " + weight);



    }




}
