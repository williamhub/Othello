package strategy;

import model.Board;
import model.Piece;

public interface MiniMaxTreeStrategy {
  /**
   * Return the child board determined by heuristic strategy with minimax tree
   * @param board
   * @param piece
   * @return
   */
  Board getNextBoard(Board board, Piece piece);
}
