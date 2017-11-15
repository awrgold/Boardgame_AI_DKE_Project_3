package Systems;
import Enum.GameStateEnum;
import GameLogic.BoardGame;
import GameLogic.GameState;

public class GameManager {
    // Singleton: unique instance
    private static GameManager instance;

    // Reference to game
    protected BoardGame game;

    // Singleton: private constructor
    private GameManager() {
        super();
    }

    // Singleton: retrieve instance
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // Initialization with the game class
    public void initialize(BoardGame game) {
        this.game = game;
    }

    // update the state of the game which enum type is received
    public void update(GameStateEnum GameStateEnum, Object... params) {

        // Get current screen to dispose it
        GameState currentState = game.getState();

        // Show new screen
        GameState newState = GameStateEnum.getState(params);
        newState.enter();
        game.setState(newState);

        // Dispose previous screen
        if (currentState != null) {
            currentState.exit();
        }
    }
}
