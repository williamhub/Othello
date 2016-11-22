package model;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
  private List<TreeNode> childes;
  private Board board;
  private Piece currentPiece;
  private int heuristicValue;

  public TreeNode(Board board, Piece piece) {
    this.board = board;
    this.currentPiece = piece;
    this.childes = new ArrayList<>();
  }

  public Board getBoard() {
    return this.board;
  }

  public Piece getCurrentPiece() {
    return currentPiece;
  }

  public List<TreeNode> getChildes() {
    return this.childes;
  }

  public int getHeuristicValue() {
    return heuristicValue;
  }

  public void setHeuristicValue(int heuristicValue) {
    this.heuristicValue = heuristicValue;
  }

  public TreeNode addChild(Board board) {
    TreeNode treeNode = new TreeNode(board, currentPiece.getOpposite());
    this.childes.add(treeNode);
    return treeNode;
  }
}
