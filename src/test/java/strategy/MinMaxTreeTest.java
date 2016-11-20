package strategy;

import java.util.List;
import model.Board;
import model.Piece;
import model.TreeNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MinMaxTreeTest {

  @Test public void testMinMaxTree() {
    Board rootBoard = Board.newInstance();
    MinMaxTree minMaxTree = MinMaxTree.newInstance(rootBoard, Piece.BLACK);
    TreeNode rootTreeNode = minMaxTree.getRoot();

    List<TreeNode> rootChilds = rootTreeNode.getChilds();
    List<TreeNode> levelOneChilds = rootTreeNode.getChilds().get(0).getChilds();
    List<TreeNode> levelTwoChilds = rootTreeNode.getChilds().get(0).getChilds().get(0).getChilds();
    List<TreeNode> levelThreeChilds =
        rootTreeNode.getChilds().get(0).getChilds().get(0).getChilds().get(0).getChilds();

    assertEquals(rootChilds.size(), 4);
    assertEquals(levelOneChilds.size(), 3);
    assertNotEquals(levelTwoChilds.size(), 0);
    assertEquals(levelThreeChilds.size(), 0);
  }
}
