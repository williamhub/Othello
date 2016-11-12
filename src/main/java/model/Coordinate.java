package model;

import com.sun.javafx.beans.annotations.NonNull;

public class Coordinate {
  @NonNull
  public int row;
  @NonNull
  public int col;

  public Coordinate(int row, int col) {
    this.row = row;
    this.col = col;
  }
}
