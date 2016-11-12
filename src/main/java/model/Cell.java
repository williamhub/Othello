package model;

import com.google.common.base.Optional;

public class Cell {

  private static final String EMPTY_CELL = "0";

  private Piece piece;

  private int row, col;

  public Cell(Piece piece, int row, int col) {
    this.piece = piece;
    this.row = row;
    this.col = col;
  }

  public Cell(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public Optional<Piece> getPiece() {
    return Optional.fromNullable(piece);
  }

  public int getCol() {
    return col;
  }

  public int getRow() {
    return row;
  }

  @Override public String toString() {
    return piece == null ? EMPTY_CELL : piece.toString();
  }
}
