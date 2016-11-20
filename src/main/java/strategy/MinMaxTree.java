package strategy;

import java.util.List;
import model.Board;
import model.Piece;
import model.TreeNode;

import static com.google.common.base.Preconditions.checkState;

public class MinMaxTree {
  public final static int LEVELS = 3;

  private TreeNode root;

  public static MinMaxTree newInstance(Board board, Piece piece) {
    TreeNode treeNode = new TreeNode(null, board, piece);
    MinMaxTree minMaxTree = new MinMaxTree();
    minMaxTree.root = treeNode;

    minMaxTree.initialize();

    return minMaxTree;
  }

  private MinMaxTree() {
    // prevent initialization
  }

  private void initialize() {
    checkState(this.root != null);

    constructChilds(this.root, LEVELS);
  }

  private void constructChilds(TreeNode root, int restLevels) {
    if (restLevels == 0) {
      return;
    }

    List<Board> childBoards = root.getBoard().getChildBoards(root.getCurrentPiece());

    for (Board childBoard : childBoards) {
      TreeNode childTreeNode = root.addChild(childBoard);
      constructChilds(childTreeNode, restLevels - 1);
    }
  }

  public TreeNode getRoot() {
    return this.root;
  }
}
