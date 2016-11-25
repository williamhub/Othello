package strategy;

import model.Board;
import model.Piece;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CornerCapturedStrategyTest {
  @Test public void testGetBoardHeuristicValue() {
    Board board = Board.newInstance("strategy/corner_captured_game_board.txt");
    Strategy strategy = new CoinParityStrategy();
    int actual = strategy.getBoardHeuristicValue(board, Piece.BLACK);
    int expected = 3;

    assertEquals(expected, actual);
  }
}
