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
    private Action actionUsed;

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

    public void setWeight(double weight){
        this.weight = weight;
    }

    public void setActionUsed(Action actionUsed) {
        this.actionUsed = actionUsed;
    }

    public Action getActionUsed(){
        return actionUsed;
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


    public Node setChild(Action action) {
        //System.out.println(action.toString());

        double gain = action.actionGain(state.getCurrentBoard().getGrid());
        System.out.println("GAIN: " + gain);

        GameState nextState = state.cloneGameState();
        System.out.println("Gaming Player " + nextState.getGamingPlayer().getPlayerNo());
        Action modifiedAction = action.translateAction(nextState);

        nextState = nextState.applyAction(modifiedAction);
        //System.out.println(modifiedAction.toString());
        Node child = new Node(nextState);
        child.setActionUsed(modifiedAction);
        child.setWeight(gain);
        //Edge edge = new Edge(this, child, action);
        //child.setParentEdge(edge);
        //childrenEdges.add(edge);
        System.out.println("creating node: " + child.getWeight());

        return child;

    }




}
