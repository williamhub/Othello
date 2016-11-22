package strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Board;
import model.Piece;
import model.TreeNode;
import strategy.heuristic.HeuristicMethod;

import static com.google.common.base.Preconditions.checkState;

public class MinMaxTree {
  public final static int LEVELS = 3;

  private TreeNode root;

  private HeuristicMethod heuristicMethod;

  public static MinMaxTree newInstance(Board board, Piece piece, HeuristicMethod heuristicMethod) {
    TreeNode treeNode = new TreeNode(board, piece);
    MinMaxTree minMaxTree = new MinMaxTree();
    minMaxTree.root = treeNode;
    minMaxTree.heuristicMethod = heuristicMethod;

    minMaxTree.initialize();

    return minMaxTree;
  }

  private MinMaxTree() {
    // prevent initialization
  }

  private void initialize() {
    checkState(this.root != null);

    constructChildes(this.root, LEVELS + 1);
  }

  private void constructChildes(TreeNode root, int restLevels) {
    if (restLevels == 0) {
      return;
    }

    List<Board> childBoards = root.getBoard().getChildBoards(root.getCurrentPiece());

    for (Board childBoard : childBoards) {
      TreeNode childTreeNode = root.addChild(childBoard);
      constructChildes(childTreeNode, restLevels - 1);
    }
  }

  public TreeNode getRoot() {
    return this.root;
  }

  public TreeNode getNextNode() {
    return getNextNode(this.root, LEVELS);
  }

  private TreeNode getNextNode(TreeNode parent, final int restLevels) {
    if (restLevels == 0) {
      return getOptimalChild(parent);
    }

    TreeNode child = getOptimalChild(parent, restLevels);
    parent.setHeuristicValue(child.getHeuristicValue());

    return child;
  }

  private TreeNode getOptimalChild(TreeNode parent, final int restLevels) {
    switch (parent.getCurrentPiece()) {
      case BLACK:
        return Collections.max(parent.getChildes(), new Comparator<TreeNode>() {
          @Override public int compare(TreeNode treeNode1, TreeNode treeNode2) {
            return getNextNode(treeNode1, restLevels - 1).getHeuristicValue() - getNextNode(
                treeNode2,
                restLevels - 1).getHeuristicValue();
          }
        });
      case WHITE:
        return Collections.min(parent.getChildes(), new Comparator<TreeNode>() {
          @Override public int compare(TreeNode treeNode1, TreeNode treeNode2) {
            return getNextNode(treeNode1, restLevels - 1).getHeuristicValue() - getNextNode(
                treeNode2,
                restLevels - 1).getHeuristicValue();
          }
        });
      default:
        throw new IllegalArgumentException(
            String.format("Cannot use [%s] piece", parent.getCurrentPiece()));
    }
  }

  private TreeNode getOptimalChild(TreeNode parent) {
    switch (parent.getCurrentPiece()) {
      case BLACK:
        return Collections.max(parent.getChildes(), new Comparator<TreeNode>() {
          @Override public int compare(TreeNode treeNode1, TreeNode treeNode2) {
            return heuristicMethod.getResult(treeNode1) - heuristicMethod.getResult(treeNode2);
          }
        });
      case WHITE:
        return Collections.min(parent.getChildes(), new Comparator<TreeNode>() {
          @Override public int compare(TreeNode treeNode1, TreeNode treeNode2) {
            return heuristicMethod.getResult(treeNode1) - heuristicMethod.getResult(treeNode2);
          }
        });
      default:
        throw new IllegalArgumentException(
            String.format("Cannot use [%s] piece", parent.getCurrentPiece()));
    }
  }
}
