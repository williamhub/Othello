import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.Cell;
import model.Coordinate;
import model.Piece;

import static model.Board.DIMENSION;
import static model.Board.newInstance;

public class Fixtures {

  /**
   * Create a new board with below layout:
   * 00000000
   * 00B00000
   * 00B00000
   * 00BWB000
   * 00WWW000
   * 00000000
   * 00000000
   * 00000000
   *
   * @return
   */
  public static Board createBoard() {
    List<List<Cell>> cells = new ArrayList<>();
    for (int row = 0; row < DIMENSION; row++) {
      List<Cell> initialRow = new ArrayList<>();
      for (int col = 0; col < DIMENSION; col++) {
        initialRow.add(new Cell(new Coordinate(row, col)));
      }
      cells.add(initialRow);
    }

    cells.get(1).get(2).setPiece(Piece.BLACK);
    cells.get(2).get(2).setPiece(Piece.BLACK);
    cells.get(3).get(2).setPiece(Piece.BLACK);
    cells.get(3).get(3).setPiece(Piece.WHITE);
    cells.get(3).get(4).setPiece(Piece.BLACK);
    cells.get(4).get(2).setPiece(Piece.WHITE);
    cells.get(4).get(3).setPiece(Piece.WHITE);
    cells.get(4).get(4).setPiece(Piece.WHITE);

    Board board = newInstance(cells);
    return board;
  }
}
