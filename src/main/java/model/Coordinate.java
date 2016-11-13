package model;

public class Coordinate {
  public int row;
  public int col;

  public Coordinate(int row, int col) {
    this.row = row;
    this.col = col;
  }

  @Override public String toString() {
    return String.format("{%s, %s}", row, col);
  }
}