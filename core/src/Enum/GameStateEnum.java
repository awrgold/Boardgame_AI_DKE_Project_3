package Enum;
import Interfaces.State;
public enum GameStateEnum {
    /*
    enumerate and define statesz
     */
    PLAYER_TURN {
        @Override
        public State getState(Object... params) {
            return null;
        }
    },
    INGENIOUS_TURN {
        @Override
        public State getState(Object... params) {
            return null;
        }
    },
    GAME_OVER {
        @Override
        public State getState(Object... params) {
            return null;
        }
    };

    public abstract State getState(Object... params);
}
