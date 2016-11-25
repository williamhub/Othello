package strategy;

import model.Board;
import model.Piece;

public interface Strategy {
  int getBoardHeuristicValue(Board board, Piece piece);
}
