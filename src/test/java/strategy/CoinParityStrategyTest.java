package strategy;

import model.Board;
import model.Piece;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoinParityStrategyTest {
  @Test public void testGetBoardHeuristicValue() {
    Board board = Board.newInstance("strategy/coin_parity_game_board.txt");
    Strategy strategy = new CoinParityStrategy();
    int actual = strategy.getBoardHeuristicValue(board, Piece.BLACK);
    int expected = 70;

    assertEquals(expected, actual);
  }
}
