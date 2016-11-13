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
  public void testPlacePiece() {
    Board board = Board.newInstance();
    Board board1 = board.placePiece(new Coordinate(3, 2), Piece.BLACK).get();
    Board board2 = board1.placePiece(new Coordinate(2, 2), Piece.WHITE).get();
    Board board3 = board2.placePiece(new Coordinate(2, 3), Piece.BLACK).get();

    String[] actual = new String[] {board1.toString(), board2.toString(), board3.toString()};

    String[] expected = new String[] {
        "00000000\n"
            + "00000000\n"
            + "00000000\n"
            + "00BBB000\n"
            + "000BW000\n"
            + "00000000\n"
            + "00000000\n"
            + "00000000\n",
        "00000000\n"
            + "00000000\n"
            + "00W00000\n"
            + "00BWB000\n"
            + "000BW000\n"
            + "00000000\n"
            + "00000000\n"
            + "00000000\n",
        "00000000\n"
            + "00000000\n"
            + "00WB0000\n"
            + "00BBB000\n"
            + "000BW000\n"
            + "00000000\n"
            + "00000000\n"
            + "00000000\n"
    };

    for (int index = 0; index < expected.length; index++) {
      assertEquals(expected[index], actual[index]);
    }
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
    Board[] boards =
        new Board[] {Board.newInstance(), Fixtures.createBoard(), Fixtures.createBoard()};

    Cell[] starts = new Cell[] {boards[0].setBoardCell(new Coordinate(3, 2), Piece.BLACK),
        boards[1].setBoardCell(new Coordinate(5, 5), Piece.BLACK),
        boards[2].setBoardCell(new Coordinate(5, 2), Piece.BLACK)};
    Cell[] ends = new Cell[] {boards[0].getBoardCell(new Coordinate(3, 4)).get(),
        boards[1].getBoardCell(new Coordinate(2, 2)).get(),
        boards[2].getBoardCell(new Coordinate(3, 2)).get()};
    Coordinate[] directions =
        new Coordinate[] {new Coordinate(0, 1), new Coordinate(-1, -1), new Coordinate(-1, 0)};

    String[] expected = new String[] {
        "00000000\n"
            + "00000000\n"
            + "00000000\n"
            + "00BBB000\n"
            + "000BW000\n"
            + "00000000\n"
            + "00000000\n"
            + "00000000\n",
        "00000000\n"
            + "00B00000\n"
            + "00B00000\n"
            + "00BBB000\n"
            + "00WWB000\n"
            + "00000B00\n"
            + "00000000\n"
            + "00000000\n",
        "00000000\n"
            + "00B00000\n"
            + "00B00000\n"
            + "00BWB000\n"
            + "00BWW000\n"
            + "00B00000\n"
            + "00000000\n"
            + "00000000\n"
    };
    String[] actual = new String[expected.length];
    for (int index = 0; index < boards.length; index++) {
      Board.updateBoard(boards[index], starts[index], ends[index], directions[index]);
      actual[index] = boards[index].toString();
    }

    for (int index = 0; index < expected.length; index++) {
      assertEquals(expected[index], actual[index]
      );
    }
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
