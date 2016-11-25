package strategy;

import model.Board;
import model.Piece;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StableStrategyTest {
  @Test public void testGetBoardHeuristicValue() {
    Board board = Board.newInstance("strategy/stable_game_board.txt");
    Strategy strategy = new StableStrategy();
    int actual = strategy.getBoardHeuristicValue(board, Piece.BLACK);
    int expected = 6;

    assertEquals(expected, actual);
  }
}
