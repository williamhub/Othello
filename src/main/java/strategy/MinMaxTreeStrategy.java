package strategy;

import model.Board;
import model.Piece;

public interface MinMaxTreeStrategy {
  Board getNextBoard(Board board, Piece piece);
}
