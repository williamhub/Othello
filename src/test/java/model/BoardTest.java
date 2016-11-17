package model;

import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class BoardTest {
  @Test
  public void testToString() {
    Board board = Board.newInstance();
    String expected =
        "00000000\n"
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
}
