package model;

import com.google.common.base.Optional;
import java.util.Objects;

public class Cell {

  private final static String EMPTY_CELL = "0";

  private Piece piece;

  private Coordinate coordinate;

  public Cell(Piece piece, Coordinate coordinate) {
    this.piece = piece;
    this.coordinate = coordinate;
  }

  public Cell(Coordinate coordinate) {
    this.coordinate = coordinate;
  }

  public Optional<Piece> getPiece() {
    return Optional.fromNullable(piece);
  }

  public void setPiece(Piece piece) {
    this.piece = piece;
  }

  public Coordinate getCoordinate() {
    return coordinate;
  }

  @Override public String toString() {
    return piece == null ? EMPTY_CELL : piece.toString();
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cell cell = (Cell) o;
    return piece == cell.piece &&
        Objects.equals(coordinate, cell.coordinate);
  }

  @Override public int hashCode() {
    return Objects.hash(piece, coordinate);
  }
}
