package strategy;

import model.Board;
import model.Piece;

public interface Strategy {
  Board choose(Piece piece, Board board);
}
