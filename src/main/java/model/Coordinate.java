package model;

/**
 * Board coordinate used as location of a cell
 */
public class Coordinate {
  public int row;
  public int col;

  public Coordinate(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public Coordinate move(Coordinate coordinate) {
    return new Coordinate(this.row + coordinate.row, this.col + coordinate.col);
  }

  @Override public String toString() {
    return String.format("{%s, %s}", row, col);
  }
}
