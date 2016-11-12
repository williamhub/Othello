package model;

import com.google.common.base.Optional;
import com.sun.javafx.beans.annotations.NonNull;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.sun.tools.javac.util.Assert.checkNull;

public class Board {
  public final static int DIMENSION = 8;

  private final static List<Coordinate> DIRECTIONS = new ArrayList<>();

  static {
    /* Above Row */
    DIRECTIONS.add(new Coordinate(-1, -1));
    DIRECTIONS.add(new Coordinate(-1, 0));
    DIRECTIONS.add(new Coordinate(-1, 1));

    /* Current Row */
    DIRECTIONS.add(new Coordinate(0, -1));
    DIRECTIONS.add(new Coordinate(0, 1));

    /* Below Row */
    DIRECTIONS.add(new Coordinate(1, -1));
    DIRECTIONS.add(new Coordinate(1, 0));
    DIRECTIONS.add(new Coordinate(1, 1));
  }

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

  private static boolean isWithinBoard(Coordinate coordinate) {
    if (coordinate.row < 0
        || coordinate.row > DIMENSION - 1
        || coordinate.col < 0
        || coordinate.col > DIMENSION - 1) {
      return false;
    }
    return true;
  }

  private Board() {
    // prevent initialization
  }

  public boolean isValid(@NonNull Coordinate coordinate, @NonNull Piece piece) {
    checkNotNull(coordinate, "Coordinate must be set");
    checkNotNull(piece, "Piece must be set");

    for (Coordinate direction : DIRECTIONS) {

      Coordinate neighbourCoordinate =
          new Coordinate(coordinate.row + direction.row, coordinate.col + direction.col);

      Optional<Cell> optionalCell = getBoardCell(neighbourCoordinate);
      if (optionalCell.isPresent()) {
        Cell neighbourCell = optionalCell.get();

        Optional<Piece> optionalPiece = neighbourCell.getPiece();
        if (optionalPiece.isPresent()) {
          Piece neighbourPiece = optionalPiece.get();

          if (neighbourPiece == piece) {
            continue;
          }

          while (true) {
            Coordinate nextNeighbourCoordinate =
                new Coordinate(neighbourCell.getCoordinate().row + direction.row,
                    neighbourCell.getCoordinate().col + +direction.col);

            optionalCell = getBoardCell(nextNeighbourCoordinate);
            if (optionalCell.isPresent()) {
              Cell nextNeighbourCell = optionalCell.get();

              optionalPiece = nextNeighbourCell.getPiece();
              if (optionalPiece.isPresent()) {
                Piece nextNeighbourPiece = optionalPiece.get();

                if (nextNeighbourPiece == piece) {
                  return true;
                }

                neighbourCell = nextNeighbourCell;
              } else {
                break;
              }
            } else {
              break;
            }
          }
        }
      }
    }

    return false;
  }

  public Optional<Cell> getBoardCell(@NonNull Coordinate coordinate) {
    checkNotNull(coordinate, "Coordinate must be set");

    return isWithinBoard(coordinate) ? Optional.of(board.get(coordinate.row).get(coordinate.col))
        : Optional.<Cell>absent();
  }

  private void setBoardCell(@NonNull Coordinate coordinate, @NonNull Piece piece) {
    checkNotNull(coordinate, "Coordinate must be set");
    checkNotNull(piece, "Piece must be set");

    board.get(coordinate.row).get(coordinate.col).setPiece(piece);
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
