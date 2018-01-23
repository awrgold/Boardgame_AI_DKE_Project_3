package com.game.TreeStructure;

import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;

import java.util.ArrayList;
import java.util.Random;

public class MCTSNode {

    private GameState state;
    private MCTSNode parent;
    private ArrayList<MCTSNode> children;
    private double score;
    private double weight;
    private int numVisits = 0;
    private Random random = new Random();
    private Action actionUsed;

    public MCTSNode(GameState state){
        this.state = state;
        this.children = new ArrayList<>();
    }

    public int getNumVisits(){
        return numVisits;
    }

    public void updateStats (double score){
        numVisits++;
        this.score += score;
    }



    public boolean isLeaf(){
        return (children.size() == 0);
    }

    public GameState getState(){
        return state;
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

    public MCTSNode getParent(){
        return parent;
    }

    public ArrayList<MCTSNode> getChildren() {
        return children;
    }

    public void setParent(MCTSNode parent){
        this.parent = parent;
    }

    public void setState(GameState state){
        this.state = state;
    }

    public MCTSNode setChild(Action action) {

        //System.out.println(action.toString());

        double gain = action.actionGain(state.getCurrentBoard().getGrid(), state.getGamingPlayer());
        //System.out.println("GAIN: " + gain);

        GameState nextState = state.cloneGameState();
        //System.out.println("Gaming Player " + nextState.getGamingPlayer().getPlayerNo());
        Action modifiedAction = action.translateAction(nextState);

        nextState = nextState.applyAction(modifiedAction);
        //System.out.println(modifiedAction.toString());
        MCTSNode child = new MCTSNode(nextState);

        child.setActionUsed(modifiedAction);
        child.setWeight(gain);

        child.setParent(this);
        children.add(child);
        // System.out.println("creating node: " + child.getWeight());

        return child;

    }


}
