package com.game.GameAI;

import com.game.Components.GameAssets.Board;
import com.game.Components.GameLogic.Action;

public class ExpectimaxStrategy implements Strategy {


//        public static final String TAG = ExpectimaxStrategy.class.getName();
//
//        private class ExpectimaxResults {
//            int bestScore;
//            Action bestMove;
//
//            public ExpectimaxResults(ExpectimaxResults results) {
//                this.bestScore = results.bestScore;
//                this.bestMove = results.bestMove;
//            }
//
//            public ExpectimaxResults(int bestScore, Action bestMove) {
//                this.bestScore = bestScore;
//                this.bestMove = bestMove;
//            }
//        }
//
//
//        public Action determineBestMove(Board board, Player forPlayer) {
//            ExpectimaxResults results = ExpectiMax(board, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, forPlayer.getPlayerType());
//            return results.bestMove;
//        }
//
//        private ExpectimaxResults ExpectiMax(Board tempBoard, int depth, int alpha, int beta, Player.PlayerType playerType) {
//
//            if (tempBoard.gameOver() || depth == 0) {
//                return new ExpectimaxResults(tempBoard.getScore(), new CellPosition(-1, -1));
//            }
//
//            // playerX is trying to maximize, therefore we initialize to "-infinity" and begin looking for higher scores
//            // playerO is trying to minimize, therefore we initialize to "+infinity" and begin looking for lower scores
//            int bestScore = (playerType == Player.PlayerType.PLAYER_X) ? Integer.MIN_VALUE: Integer.MAX_VALUE;
//            CellPosition bestPosition = new CellPosition(-1, -1);
//
//            for(CellPosition position: tempBoard.emptyCellPositions()) {
//
//                Board nextBoard = new Board(tempBoard.boardAfterMove(position, playerType.getValue()));
//
//                if (playerType == Player.PlayerType.PLAYER_X) {
//                    ExpectimaxResults result = new ExpectimaxResults(miniMax(nextBoard, depth-1, alpha, beta, playerType.oppositePlayer()));
//                    if (result.bestScore > bestScore) {
//                        bestScore = result.bestScore;
//                        bestPosition = new CellPosition(position);
//                    }
//                    alpha = Math.max(alpha, result.bestScore);
//                    if (beta <= alpha) {
//                        break;
//                    }
//                } else {
//                    expectimaxResults result = new ExpectimaxResults(expectiMax(nextBoard, depth-1, alpha, beta, playerType.oppositePlayer()));
//                    if (result.bestScore < bestScore) {
//                        bestScore = result.bestScore;
//                       // bestPosition = new CellPosition(position);
//                    }
//                    beta = Math.min(beta, result.bestScore);
//                    if (beta <= alpha) {
//                        break;
//                    }
//                }
//            }
//
//            return new ExpectimaxResults(bestScore, bestPosition);
//        }
//    }
}
