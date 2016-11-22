package strategy;

import engine.GameEngine;
import model.Piece;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreedyStrategyTest {

  @Test public void testChoose() {
    GameEngine gameEngine = new GameEngine(new GreedyStrategy(), "strategy/greedy_game_board.txt");

    String expected =
        "00000000\n"
            + "00000000\n"
            + "00000000\n"
            + "00BBB000\n"
            + "0WWWWW00\n"
            + "00B0B000\n"
            + "00000000\n"
            + "00000000\n";

    gameEngine.placePieceByRobot(Piece.WHITE);

    String actual = gameEngine.getBoardLayout();

    assertEquals(expected, actual);
  }
}
