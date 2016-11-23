package model;

import org.junit.Test;

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
    Board board1 = board.placePiece(new Coordinate(3, 2), Piece.BLACK);
    Board board2 = board1.placePiece(new Coordinate(2, 2), Piece.WHITE);
    Board board3 = board2.placePiece(new Coordinate(2, 3), Piece.BLACK);
    Board board4 = board3.placePiece(new Coordinate(4, 2), Piece.WHITE);
    Board board5 = board4.placePiece(new Coordinate(5, 3), Piece.BLACK);
    Board board6 = board5.placePiece(new Coordinate(2, 4), Piece.WHITE);

    String[] actual =
        new String[] {board1.toString(), board2.toString(), board3.toString(), board4.toString(),
            board5.toString(), board6.toString()};

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
            + "00000000\n",
        "00000000\n"
            + "00000000\n"
            + "00WB0000\n"
            + "00WBB000\n"
            + "00WWW000\n"
            + "00000000\n"
            + "00000000\n"
            + "00000000\n",
        "00000000\n"
            + "00000000\n"
            + "00WB0000\n"
            + "00WBB000\n"
            + "00WBW000\n"
            + "000B0000\n"
            + "00000000\n"
            + "00000000\n",
        "00000000\n"
            + "00000000\n"
            + "00WWW000\n"
            + "00WWW000\n"
            + "00WBW000\n"
            + "000B0000\n"
            + "00000000\n"
            + "00000000\n"
    };

    for (int index = 0; index < expected.length; index++) {
      assertEquals(expected[index], actual[index]);
    }
  }

  @Test public void testLoadBoard() {
    Board board = Board.newInstance("model/board_to_load.txt");

    String expected =
        "00000000\n"
            + "0WB00000\n"
            + "00WB0000\n"
            + "000BWB00\n"
            + "0000BW00\n"
            + "00000000\n"
            + "00000000\n"
            + "00000000\n";

    String actual = board.toString();

    assertEquals(expected, actual);
  }
}
