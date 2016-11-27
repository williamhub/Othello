package engine;

import model.Piece;
import org.junit.Test;
import strategy.CoinParityStrategy;
import strategy.CornerCapturedStrategy;
import strategy.MobilityStrategy;
import strategy.StabilityStrategy;

import static org.junit.Assert.assertEquals;

public class HeuristicBehaviorTest {
  @Test public void testStableStrategy() {
    GameEngine gameEngine =
        new GameEngine(new StabilityStrategy(), "engine/heuristic_behavior_board.txt");
    gameEngine.placePieceByRobot(Piece.BLACK);

    String actual = gameEngine.getBoardLayout();
    String expected =
        "(\n"
            + "(00BBBB0B)\n"
            + "(00BBBB0B)\n"
            + "(BBBBBBWB)\n"
            + "(BBBBBWWB)\n"
            + "(BBBBBWBB)\n"
            + "(BBBBWBBB)\n"
            + "(B0WWBB0B)\n"
            + "(00000B00)\n"
            + ")\n";

    assertEquals(expected, actual);
  }

  @Test public void testMobilityStrategy() {
    GameEngine gameEngine =
        new GameEngine(new MobilityStrategy(), "engine/heuristic_behavior_board.txt");
    gameEngine.placePieceByRobot(Piece.BLACK);

    String actual = gameEngine.getBoardLayout();
    String expected =
        "(\n"
            + "(00BBBB0B)\n"
            + "(00BBBB0B)\n"
            + "(BBBBBBWB)\n"
            + "(BBBBBWWB)\n"
            + "(BBBBBWBB)\n"
            + "(BBBBWBBB)\n"
            + "(B0WWBB0B)\n"
            + "(00000B00)\n"
            + ")\n";

    assertEquals(expected, actual);
  }

  @Test public void testCornerCapturedStrategy() {
    GameEngine gameEngine =
        new GameEngine(new CornerCapturedStrategy(), "engine/heuristic_behavior_board.txt");
    gameEngine.placePieceByRobot(Piece.BLACK);

    String actual = gameEngine.getBoardLayout();
    String expected =
        "(\n"
            + "(00BBBB0B)\n"
            + "(00BBBB0B)\n"
            + "(BBBBBBWB)\n"
            + "(BBBBBWWB)\n"
            + "(BBBBBWBB)\n"
            + "(BBBBWBBB)\n"
            + "(B0WWBB0B)\n"
            + "(00000B00)\n"
            + ")\n";

    assertEquals(expected, actual);
  }

  @Test public void testCoinParityStrategy() {
    GameEngine gameEngine =
        new GameEngine(new CoinParityStrategy(), "engine/heuristic_behavior_board.txt");
    gameEngine.placePieceByRobot(Piece.BLACK);

    String actual = gameEngine.getBoardLayout();
    String expected =
        "(\n"
            + "(00BBBB0B)\n"
            + "(00BBBB0B)\n"
            + "(BBBBBBWB)\n"
            + "(BBBBBWWB)\n"
            + "(BBBBBWBB)\n"
            + "(BBBBWBBB)\n"
            + "(B0WWBB0B)\n"
            + "(00000B00)\n"
            + ")\n";

    assertEquals(expected, actual);
  }
}
