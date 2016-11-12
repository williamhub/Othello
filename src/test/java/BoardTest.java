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
  public void testIsValid() {
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
        assertEquals(expected[index], board.isValid(cell.getCoordinate(), cell.getPiece().get()));
      } else {
        fail();
      }
    }
  }
}
