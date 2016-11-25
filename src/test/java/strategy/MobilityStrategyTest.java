package strategy;

import model.Board;
import model.Piece;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MobilityStrategyTest {

  @Test public void testGetBoardHeuristicValue() {
    Board board = Board.newInstance("strategy/mobility_game_board.txt");
    Strategy strategy = new MobilityStrategy();
    int actual = strategy.getBoardHeuristicValue(board, Piece.BLACK);
    int expected = 6;

    assertEquals(expected, actual);
  }
}
