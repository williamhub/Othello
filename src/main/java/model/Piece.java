package model;

public enum Piece {
  BLACK("B"), WHITE("W");

  private final String text;

  Piece(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }

  public Piece getOpposite() {
    switch (text) {
      case "B":
        return WHITE;
      case "W":
        return BLACK;
      default:
        throw new IllegalStateException(String.format("Unknown piece [%s]", text));
    }
  }
}
