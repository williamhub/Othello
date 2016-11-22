package strategy;

import java.util.List;
import model.Board;
import model.Piece;
import model.TreeNode;
import org.junit.Test;
import strategy.minmaxtree.MinMaxTree;
import strategy.minmaxtree.StableStrategyTree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MinMaxTreeTest {

  @Test public void testMinMaxTree() {
    Board rootBoard = Board.newInstance();
    MinMaxTree minMaxTree = StableStrategyTree.newInstance(rootBoard, Piece.BLACK);
    TreeNode rootTreeNode = minMaxTree.getRoot();

    List<TreeNode> rootChildes = rootTreeNode.getChilds();
    List<TreeNode> levelOneChildes = rootTreeNode.getChilds().get(0).getChilds();
    List<TreeNode> levelTwoChildes = rootTreeNode.getChilds().get(0).getChilds().get(0).getChilds();
    List<TreeNode> levelThreeChildes =
        rootTreeNode.getChilds().get(0).getChilds().get(0).getChilds().get(0).getChilds();

    assertEquals(rootChildes.size(), 4);
    assertEquals(levelOneChildes.size(), 3);
    assertNotEquals(levelTwoChildes.size(), 0);
    assertEquals(levelThreeChildes.size(), 0);
  }
}
