package engine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameEngineTest {

  GameEngine gameEngine;

  @Before
  public void prepareGameEngine() {
    gameEngine = new GameEngine();
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

}
