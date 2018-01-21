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
    private Action actionUsed;

    public Node(GameState state){
        this.state = state;
        this.childrenEdges = new ArrayList<>();
    }

    public void setWeight(double x){
        weight = x;
    }

    public void setActionUsed(Action actionUsed) {
        this.actionUsed = actionUsed;
    }

    public Action getActionUsed() {
        return actionUsed;
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

    public Node setChild(Action action) {
        //System.out.println(action.toString());

        double gain = action.actionGain(state.getCurrentBoard().getGrid());
        //System.out.println("GAIN: " + gain);

        GameState nextSate = state.cloneGameState();
        //System.out.println("Gaming Player " + nextSate.getGamingPlayer().getPlayerNo());
        //System.out.println("Action: " + action.toString());
        Action modifiedAction = action.translateAction(nextSate);
        //System.out.println("Modified Action: " + modifiedAction.toString());
        nextSate = nextSate.applyAction(modifiedAction);
        //System.out.println(modifiedAction.toString());
        Node child = new Node(nextSate);
        child.setActionUsed(modifiedAction);

        child.setWeight(gain);
        //Edge edge = new Edge(this, child, action);
        //child.setParentEdge(edge);
        //childrenEdges.add(edge);
        //System.out.println("creating node: " + child.getWeight());

        return child;



    }




}
