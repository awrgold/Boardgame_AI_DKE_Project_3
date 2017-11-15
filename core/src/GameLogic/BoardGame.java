package GameLogic;

public class BoardGame {
    private GameState currentState;

    public GameState getState() {
        return this.currentState;
    }

    public void setState(GameState state) {
        this.currentState = state;
    }
}
