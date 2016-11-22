package strategy;

import java.util.List;
import model.Board;
import model.Piece;
import model.TreeNode;
import org.junit.Test;
import strategy.heuristic.HeuristicMethod;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MinMaxTreeTest {

  @Test public void testConstructChildes() {
    Board rootBoard = Board.newInstance();
    MinMaxTree minMaxTree = MinMaxTree.newInstance(rootBoard, Piece.BLACK, null);
    TreeNode rootTreeNode = minMaxTree.getRoot();

    List<TreeNode> rootChildes = rootTreeNode.getChildes();
    List<TreeNode> levelOneChildes = rootTreeNode.getChildes().get(0).getChildes();
    List<TreeNode> levelTwoChildes =
        rootTreeNode.getChildes().get(0).getChildes().get(0).getChildes();
    List<TreeNode> levelThreeChildes =
        rootTreeNode.getChildes().get(0).getChildes().get(0).getChildes().get(0).getChildes();

    assertEquals(4, rootChildes.size());
    assertEquals(3, levelOneChildes.size());
    assertNotEquals(0, levelTwoChildes.size());
    assertEquals(6, levelThreeChildes.size());
  }

  @Test public void testGetNextNode() {
    Board rootBoard = Board.newInstance("strategy/stable_game_board.txt");
    MinMaxTree minMaxTree = MinMaxTree.newInstance(rootBoard, Piece.WHITE, new HeuristicMethod());

    TreeNode nextNode = minMaxTree.getNextNode();
    System.out.println(nextNode.getBoard().toString());
    // TODO: complete the test
  }
}
