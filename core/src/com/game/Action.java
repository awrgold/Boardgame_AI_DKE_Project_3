package com.game;

import GameBoardAssets.HexagonActor;
import org.codetome.hexameter.core.api.Hexagon;

public class Action {
    private Hexagon h1;
    private Hexagon h2;

    private HexagonActor t1;
    private HexagonActor t2;

    public Action(Hexagon h1, Hexagon h2, HexagonActor t1, HexagonActor t2){
        this.h1 = h1;
        this.h2 = h2;
        this.t1 = t1;
        this.t2 = t2;
    }

    public Action(){};

    public Hexagon getH1() {
        return h1;
    }

    public Hexagon getH2() {
        return h2;
    }

    public HexagonActor getT1() {
        return t1;
    }

    public HexagonActor getT2() {
        return t2;
    }

    public void setH1(Hexagon h1) {
        this.h1 = h1;
    }

    public void setH2(Hexagon h2) {
        this.h2 = h2;
    }

    public void setT1(HexagonActor t1) {
        this.t1 = t1;
    }

    public void setT2(HexagonActor t2) { this.t2 = t2; }
}
