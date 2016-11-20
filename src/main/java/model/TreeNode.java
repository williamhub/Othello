package model;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

public class TreeNode {
  private List<TreeNode> childs;
  private TreeNode parent;
  private Board board;
  private Piece currentPiece;

  public TreeNode(TreeNode parent, Board board, Piece piece) {
    this.parent = parent;
    this.board = board;
    this.currentPiece = piece;
    this.childs = new ArrayList<>();
  }

  public TreeNode getParent() {
    return this.parent;
  }

  public Board getBoard() {
    return this.board;
  }

  public Piece getCurrentPiece() {
    return currentPiece;
  }

  public List<TreeNode> getChilds() {
    return Lists.newArrayList(childs);
  }

  public TreeNode addChild(Board board) {
    TreeNode treeNode = new TreeNode(this, board, currentPiece.getOpposite());
    this.childs.add(treeNode);
    return treeNode;
  }
}
