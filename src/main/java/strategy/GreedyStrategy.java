package strategy;

import model.Board;
import model.Piece;

public class GreedyStrategy implements Strategy {

  @Override public int getBoardHeuristicValue(Board board, Piece piece) {
    return board.countPieces(piece);
  }
}
