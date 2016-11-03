package model;

public enum Cell {
  EMPTY("0"), BLACK("1"), WHITE("2");

  private final String text;

  private Cell(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
