package strategy;

import model.Board;
import model.Piece;
import model.TreeNode;
import org.junit.Test;

public class MinMaxTreeTest {
  @Test public void testGetNextNode() {

    MinMaxTree minMaxTree = new MinMaxTree(Board.newInstance(), Piece.BLACK, new GreedyStrategy());
    TreeNode treeNode = minMaxTree.getNextNode();

    // TODO: min max tree test
    System.out.println(treeNode.getBoard());
  }

  @Test public void testGetNextNodeOverBeforeLeaf() {
    Board board = Board.newInstance("strategy/minmax_over_before_leaf_board.txt");
    MinMaxTree minMaxTree = new MinMaxTree(board, Piece.BLACK, new GreedyStrategy());

    TreeNode treeNode = minMaxTree.getNextNode();
    System.out.println(treeNode.getBoard());
  }

  @Test public void testGetNextNodeSkipNode() {

  }
}
