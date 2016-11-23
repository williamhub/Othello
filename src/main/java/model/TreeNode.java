package model;

import java.util.List;

public class TreeNode {
  private Board board;
  private Piece currentPiece;
  private int heuristicScore;
  private TreeNode chosenChild;

  public TreeNode(Board board, Piece piece) {
    this.board = board;
    this.currentPiece = piece;
  }

  public Board getBoard() {
    return this.board;
  }

  public List<Coordinate> getValidBoardMoves() {
    return this.board.getValidMoves(currentPiece);
  }

  public TreeNode getValidChild(Coordinate coordinate) {
    return new TreeNode(this.board.placePiece(coordinate, currentPiece).get(),
        currentPiece.getOpposite());
  }

  public Piece getCurrentPiece() {
    return currentPiece;
  }

  public TreeNode getChosenChild() {
    return chosenChild;
  }

  public void setChosenChild(TreeNode chosenChild) {
    this.chosenChild = chosenChild;
  }

  public int getHeuristicScore() {
    return heuristicScore;
  }

  public void setHeuristicScore(int heuristicScore) {
    this.heuristicScore = heuristicScore;
  }
}
