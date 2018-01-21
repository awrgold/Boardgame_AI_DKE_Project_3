package com.game.TreeStructure;

import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;
import com.game.Components.GameLogic.GameView;

import java.util.ArrayList;
import java.util.Random;

public class MCTSNode {

    private GameState state;
    private MCTSEdge parentEdge;
    private ArrayList<MCTSEdge> childrenEdges;
    private double score;
    private double weight;
    private int numVisits = 0;
    private int maxChildren = 6;
    private Random random = new Random();
    private Action actionUsed;

    public MCTSNode(GameState state){
        this.state = state;
        this.childrenEdges = new ArrayList<>();

    }

    public int getNumVisits(){
        return numVisits;
    }

    public void updateStats (double score){
        numVisits++;
        this.score = score;
    }

    public boolean isLeaf(){
        return (childrenEdges.size() == 0);
    }

    public GameState getState(){
        return state;
    }

    public void setScore(boolean didWin){
        if (didWin) score++;
        if (!didWin) score--;
        // what about draw?
    }

    public double getScore(){
        return score;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public double getWeight(){
        return weight;
    }

    public void setActionUsed(Action actionUsed) {
        this.actionUsed = actionUsed;
    }

    public Action getActionUsed(){
        return actionUsed;
    }

    public MCTSEdge getParent(){
        return parentEdge;
    }

    public ArrayList<MCTSEdge> getChildrenEdges() {
        return childrenEdges;
    }

    public void setParentEdge(MCTSEdge parent){
        this.parentEdge = parent;
    }

    public void setState(GameState state){
        this.state = state;
    }

    public MCTSNode setChild(Action action) {
        //System.out.println(action.toString());

        double gain = action.actionGain(state.getCurrentBoard().getGrid());
        //System.out.println("GAIN: " + gain);

        GameState nextState = state.cloneGameState();
        //System.out.println("Gaming Player " + nextState.getGamingPlayer().getPlayerNo());
        Action modifiedAction = action.translateAction(nextState);

        nextState = nextState.applyAction(modifiedAction);
        //System.out.println(modifiedAction.toString());
        MCTSNode child = new MCTSNode(nextState);

        child.setActionUsed(modifiedAction);
        child.setWeight(gain);

        MCTSEdge edge = new MCTSEdge(this, child, action);
        child.setParentEdge(edge);
        childrenEdges.add(edge);
        System.out.println("creating node: " + child.getWeight());

        return child;

    }


}
