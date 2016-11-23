package strategy;

import model.Board;
import model.Piece;
import model.TreeNode;
import org.junit.Test;
import strategy.heuristic.HeuristicMethod;

public class MinMaxTreeTest {
  @Test public void testGetNextNode() {

    MinMaxTree minMaxTree = new MinMaxTree(Board.newInstance(), Piece.BLACK, new HeuristicMethod());
    TreeNode treeNode = minMaxTree.getNextNode();

    System.out.println(treeNode.getBoard());
  }
}
