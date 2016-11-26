package strategy;

import model.Board;
import model.Piece;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StabilityStrategyTest {
  @Test public void testGetBoardHeuristicValue() {
    Board board = Board.newInstance("strategy/stable_game_board.txt");
    Strategy strategy = new StabilityStrategy();
    int actual = strategy.getBoardHeuristicValue(board, Piece.BLACK);
    int expected = 6;

    assertEquals(expected, actual);
  }
}
