package model;

public enum Piece {
  BLACK("B"), WHITE("W");

  private final String text;

  private Piece(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
