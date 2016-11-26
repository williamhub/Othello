package engine;

import model.Piece;
import org.junit.Test;
import strategy.CoinParityStrategy;
import strategy.CornerCapturedStrategy;
import strategy.MobilityStrategy;
import strategy.StabilityStrategy;

public class HeuristicBehaviorTest {
  @Test public void testStableStrategy() {
    GameEngine gameEngine =
        new GameEngine(new StabilityStrategy(), "engine/heuristic_behavior_board.txt");
    gameEngine.placePieceByRobot(Piece.BLACK);

    System.out.println(gameEngine.getBoardLayout());
  }

  @Test public void testMobilityStrategy() {
    GameEngine gameEngine =
        new GameEngine(new MobilityStrategy(), "engine/heuristic_behavior_board.txt");
    gameEngine.placePieceByRobot(Piece.BLACK);

    System.out.println(gameEngine.getBoardLayout());
  }

  @Test public void testCornerCapturedStrategy() {
    GameEngine gameEngine =
        new GameEngine(new CornerCapturedStrategy(), "engine/heuristic_behavior_board.txt");
    gameEngine.placePieceByRobot(Piece.BLACK);

    System.out.println(gameEngine.getBoardLayout());
  }

  @Test public void testCoinParityStrategy() {
    GameEngine gameEngine =
        new GameEngine(new CoinParityStrategy(), "engine/heuristic_behavior_board.txt");
    gameEngine.placePieceByRobot(Piece.BLACK);

    System.out.println(gameEngine.getBoardLayout());
  }
}
