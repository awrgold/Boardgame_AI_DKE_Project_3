package Enum;

import GameLogic.GameState;

public enum GameStateEnum {
    /*
    enumerate and define statesz
     */
    PLAYER_TURN {
        @Override
        public GameState getState(Object... params) {
            return null;
        }
    },
    INGENIOUS_TURN {
        @Override
        public GameState getState(Object... params) {
            return null;
        }
    },
    GAME_OVER {
        @Override
        public GameState getState(Object... params) {
            return null;
        }
    };

    public abstract GameState getState(Object... params);
}
