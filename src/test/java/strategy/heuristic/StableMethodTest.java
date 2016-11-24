package strategy.heuristic;

import model.Board;
import model.Piece;
import model.TreeNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StableMethodTest {

  @Test public void testGetResult() {
    Board board = Board.newInstance("strategy/stable_game_board.txt");
    TreeNode treeNode = new TreeNode(board, Piece.BLACK);

    StableMethod stableMethod = new StableMethod();
    int actual = stableMethod.getResult(treeNode);
    int expected = 6;

    assertEquals(expected, actual);
  }
}
