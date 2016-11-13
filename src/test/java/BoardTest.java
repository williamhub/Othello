import model.Board;
import model.Cell;
import model.Coordinate;
import model.Piece;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class BoardTest {
  @Test
  public void testToString() {
    Board board = Board.newInstance();
    String expected = "00000000\n"
        + "00000000\n"
        + "00000000\n"
        + "000WB000\n"
        + "000BW000\n"
        + "00000000\n"
        + "00000000\n"
        + "00000000\n";
    String actual = board.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testCheckOrValidateBoardFromCell() {
    Board board = Board.newInstance();
    Cell[] cells = new Cell[] {new Cell(Piece.BLACK, new Coordinate(0, 0)),
        new Cell(Piece.BLACK, new Coordinate(2, 2)),
        new Cell(Piece.BLACK, new Coordinate(2, 3)), new Cell(Piece.BLACK, new Coordinate(2, 4)),
        new Cell(Piece.BLACK, new Coordinate(2, 5)), new Cell(Piece.BLACK, new Coordinate(3, 2)),
        new Cell(Piece.BLACK, new Coordinate(3, 5)), new Cell(Piece.BLACK, new Coordinate(4, 2)),
        new Cell(Piece.BLACK, new Coordinate(4, 5)), new Cell(Piece.BLACK, new Coordinate(5, 2)),
        new Cell(Piece.BLACK, new Coordinate(5, 3)), new Cell(Piece.BLACK, new Coordinate(5, 4)),
        new Cell(Piece.BLACK, new Coordinate(5, 5))};

    boolean[] expected =
        {false, false, true, false, false, true, false, false, true, false, false, true, false};

    for (int index = 0; index < expected.length; index++) {
      Cell cell = cells[index];
      if (cell.getPiece().isPresent()) {
        assertEquals(expected[index],
            Board.checkOrValidateBoardFromCell(board, cell.getCoordinate(), cell.getPiece().get(),
                true));
      } else {
        fail();
      }
    }
  }

  @Test
  public void testUpdateBoard() {
    Board board = Board.newInstance();
    Cell start = board.setBoardCell(new Coordinate(3, 2), Piece.BLACK);
    Cell end = board.getBoardCell(new Coordinate(3, 4)).get();
    Coordinate direction = new Coordinate(0, 1);
    Board.updateBoard(board, start, end, direction);

    String expected = "00000000\n"
        + "00000000\n"
        + "00000000\n"
        + "00BBB000\n"
        + "000BW000\n"
        + "00000000\n"
        + "00000000\n"
        + "00000000\n";
    String actual = board.toString();

    assertEquals(expected, actual);
  }

  @Test
  public void testMove() {
    Board board = Board.newInstance();
    board = board.placePiece(new Coordinate(3, 2), Piece.BLACK).get();

    String expected = "00000000\n"
        + "00000000\n"
        + "00000000\n"
        + "00BBB000\n"
        + "000BW000\n"
        + "00000000\n"
        + "00000000\n"
        + "00000000\n";

    String actual = board.toString();

    assertEquals(expected, actual);
  }
}
