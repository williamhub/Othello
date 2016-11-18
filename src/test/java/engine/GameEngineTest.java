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

  @Test public void testPlacePieceByHuman() {
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
}
