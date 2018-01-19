package com.game.TreeStructure;

import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.GameLogic.GameView;

import java.util.ArrayList;
import java.util.Random;

public class Node {

    private GameState state;
    private Edge parentEdge;
    private ArrayList<Edge> childrenEdges;
    private double weight;
    private int numVisits = 0;
    private int maxChildren = 6;
    private Random random = new Random();

    public Node(GameState state){
        this.state = state;
        this.childrenEdges = new ArrayList<>();

    }

    public int getNumVisits(){
        return numVisits;
    }

    public void updateStats(double value){
        numVisits++;
        this.weight = value;
    }

    public boolean isLeaf(){
        return (childrenEdges.size() == 0);
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

        weight = action.actionGain(getState().getCurrentBoard().getGrid());
        GameState nextView = state.applyAction(action);
        Node child = new Node(nextView);
        Edge edge = new Edge(this, child, action);
        child.setParentEdge(edge);
        childrenEdges.add(edge);
        System.out.println("creating node: " + weight);

    }




}
