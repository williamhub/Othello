package engine;

import model.Coordinate;
import model.Piece;
import org.junit.Before;
import org.junit.Test;
import strategy.GreedyStrategy;

import static org.junit.Assert.assertEquals;

public class GameEngineTest {

  GameEngine gameEngine;

  @Before
  public void prepareGameEngine() {
    gameEngine = new GameEngine(new GreedyStrategy());
  }

  @Test
  public void testLoadGame() {
    gameEngine.loadGame("engine/sample_board.txt");

    String expected = "00000000\n"
        + "0WB00000\n"
        + "00WB0000\n"
        + "000BWB00\n"
        + "0000BW00\n"
        + "00000000\n"
        + "00000000\n"
        + "00000000\n";

    String actual = gameEngine.getBoardLayout();

    assertEquals(expected, actual);
  }

  @Test public void testPlacePieceByHumanSkipBlack() {
    gameEngine.loadGame("engine/skip_black_piece_board.txt");

    String expected =
        "00BBBBB0\n"
            + "00BBBB0B\n"
            + "BBBBBBBB\n"
            + "BBBBBBWB\n"
            + "BBBWBBWB\n"
            + "BBWBWWBB\n"
            + "BWWWWW0B\n"
            + "0BBBBBBB\n";

    gameEngine.placePieceByHuman(new Coordinate(6, 7), Piece.BLACK);

    String actual = gameEngine.getBoardLayout();

    assertEquals(expected, actual);
  }

  @Test public void testPlacePieceByHumanSkipWhite() {
    gameEngine.loadGame("engine/skip_white_piece_board.txt");

    String expected =
        "WWWWW000\n"
            + "WWWW0000\n"
            + "WWWWWWW0\n"
            + "BBBBBB00\n"
            + "WWWWWWWW\n"
            + "WWWWWWW0\n"
            + "WWWWWW00\n"
            + "WWWWWW00\n";

    gameEngine.placePieceByHuman(new Coordinate(3, 5), Piece.BLACK);

    String actual = gameEngine.getBoardLayout();

    assertEquals(expected, actual);
  }

  @Test public void testPlacePieceByHumanInvalid() {
    gameEngine.loadGame("engine/sample_board.txt");
    gameEngine.placePieceByHuman(new Coordinate(9, 9), Piece.BLACK);

    String expected =
        "00000000\n"
            + "0WB00000\n"
            + "00WB0000\n"
            + "000BWB00\n"
            + "0000BW00\n"
            + "00000000\n"
            + "00000000\n"
            + "00000000\n";

    String actual = gameEngine.getBoardLayout();
    assertEquals(expected, actual);
  }

  @Test public void testPlacePieceByRobotInvalid() {
    gameEngine.loadGame("engine/skip_white_piece_board.txt");
    gameEngine.placePieceByRobot(Piece.WHITE);

    String expected =
        "WWWWW000\n"
            + "WWWW0000\n"
            + "WWWWWWW0\n"
            + "BWWWW000\n"
            + "WWWWWWWW\n"
            + "WWWWWWW0\n"
            + "WWWWWW00\n"
            + "WWWWWW00\n";

    String actual = gameEngine.getBoardLayout();
    assertEquals(expected, actual);
  }
}
