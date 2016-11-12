package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
  public final static int DIMENSION = 8;

  private List<List<Cell>> board;

  public static Board newInstance() {
    List<List<Cell>> cells = constructInitialBoard();
    Board board = new Board();
    board.setBoard(cells);
    return board;
  }

  private static List<List<Cell>> constructInitialBoard() {
    List<List<Cell>> initialBoard = new ArrayList<>();
    for (int row = 0; row < DIMENSION; row++) {
      List<Cell> initialRow = new ArrayList<>();
      for (int col = 0; col < DIMENSION; col++) {
        initialRow.add(constructInitialCell(row, col));
      }
      initialBoard.add(initialRow);
    }
    return initialBoard;
  }

  private static Cell constructInitialCell(int row, int col) {
    int topRow = DIMENSION / 2 - 1;
    int bottomRow = DIMENSION / 2;
    int leftCol = DIMENSION / 2 - 1;
    int rightCol = DIMENSION / 2;

    Coordinate coordinate = new Coordinate(row, col);

    if (row == topRow && col == leftCol) {
      return new Cell(Piece.WHITE, coordinate);
    } else if (row == topRow && col == rightCol) {
      return new Cell(Piece.BLACK, coordinate);
    } else if (row == bottomRow && col == leftCol) {
      return new Cell(Piece.BLACK, coordinate);
    } else if (row == bottomRow && col == rightCol) {
      return new Cell(Piece.WHITE, coordinate);
    } else {
      return new Cell(coordinate);
    }
  }

  private Board() {
    // prevent initialization
  }

  public List<List<Cell>> getBoard() {
    return board;
  }

  public void setBoard(List<List<Cell>> board) {
    this.board = board;
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        builder.append(board.get(row).get(col).toString());
      }
      builder.append("\n");
    }

    return builder.toString();
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Board board1 = (Board) o;

    return board.equals(board1.board);
  }

  @Override public int hashCode() {
    return board.hashCode();
  }
}
