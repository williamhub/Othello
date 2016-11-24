package strategy.heuristic;

import model.Board;
import model.Piece;
import model.TreeNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MobilityMethodTest {

  @Test public void testGetResult() {
    Board board = Board.newInstance("strategy/mobility_game_board.txt");
    TreeNode treeNode = new TreeNode(board, Piece.BLACK);

    MobilityMethod mobilityMethod = new MobilityMethod();
    int actual = mobilityMethod.getResult(treeNode);
    int expected = 6;

    assertEquals(expected, actual);
  }
}
