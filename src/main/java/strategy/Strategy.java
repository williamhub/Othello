package strategy;

import java.util.List;
import model.Board;
import model.Piece;

public interface Strategy {
  Board choose(Piece piece, List<Board> childBoards);
}
