package delegate;

import com.google.common.base.Optional;
import java.util.List;
import model.Board;
import model.Coordinate;
import model.GameResult;
import model.Piece;

public interface BoardDelegate {

  boolean isEnd();

  List<Board> getValidChildBoard(Piece piece);

  Optional<Board> placePiece(Coordinate coordinate, Piece piece);

  GameResult getWinner();

  void printBoard();
}
