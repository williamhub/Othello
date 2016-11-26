package strategy;

import model.Board;
import model.Piece;

/**
 * A strategy is an algorithm or method to calculate given board possibilities of success.
 */
public interface Strategy {
  int getBoardHeuristicValue(Board board, Piece piece);
}
