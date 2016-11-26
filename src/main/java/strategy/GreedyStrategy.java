package strategy;

import model.Board;
import model.Piece;

/**
 * Greedy strategy is to maximize player's pieces.
 */
public class GreedyStrategy implements Strategy {

  @Override public int getBoardHeuristicValue(Board board, Piece piece) {
    return board.countPieces(piece);
  }
}
