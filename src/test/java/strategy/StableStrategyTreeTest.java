package strategy;

import engine.GameEngine;
import org.junit.Test;

public class StableStrategyTreeTest {
  @Test public void testOptimal() {
    GameEngine gameEngine = new GameEngine(new GreedyStrategy());

    gameEngine.loadGame("strategy/stable_game_board.txt");
  }
}
