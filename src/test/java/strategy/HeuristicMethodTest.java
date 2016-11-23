package strategy;

import model.Board;
import model.Piece;
import model.TreeNode;
import org.junit.Test;
import strategy.heuristic.HeuristicMethod;

import static org.junit.Assert.assertEquals;

public class HeuristicMethodTest {
  @Test public void testGetResult() {
    Board board = Board.newInstance("strategy/stable_game_board.txt");
    TreeNode treeNode = new TreeNode(board, Piece.BLACK);

    HeuristicMethod heuristicMethod = new HeuristicMethod();
    int actual = heuristicMethod.getResult(treeNode);

    int expected = 6;

    assertEquals(expected, actual);
  }
}
