package engine;

import com.google.common.base.Optional;
import delegate.BoardDelegate;
import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.Coordinate;
import model.Piece;

import static com.google.common.base.Preconditions.checkArgument;

public class GameEngine {
  BoardDelegate boardDelegate;

  public GameEngine() {
    this.boardDelegate = Board.newInstance();
  }

  public void placePiece(Coordinate coordinate, Piece piece) {
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

    Optional<Board> boardOptional = boardDelegate.placePiece(coordinate, piece);
    if (boardOptional.isPresent()) {
      boardDelegate = boardOptional.get();
      List<Board> validChildes = new ArrayList<>();
      switch (piece) {
        case BLACK:
          validChildes.addAll(boardDelegate.getValidChildBoard(Piece.WHITE));
          break;
        case WHITE:
          validChildes.addAll(boardDelegate.getValidChildBoard(Piece.BLACK));
          break;
        default:
          throw new IllegalStateException(String.format("Cannot parse %s piece", piece));
      }

      if (!validChildes.isEmpty()) {
        boardDelegate = validChildes.get(0);
      }
    } else {
      System.out.printf("You can not put %s on %s \n", piece, coordinate);
    }
  }

  public void printBoard() {
    boardDelegate.printBoard();
  }
}
