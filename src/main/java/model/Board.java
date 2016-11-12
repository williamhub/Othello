package model;

import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

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
    return !(coordinate.row < 0
        || coordinate.row > DIMENSION - 1
        || coordinate.col < 0
        || coordinate.col > DIMENSION - 1);
  }

  public static boolean checkOrValidateBoardFromCell(Board board, Cell cell,
      boolean isToValidate) {
    checkArgument(board != null, "Board must be set");
    checkArgument(cell != null, "Cell must be set");

    if (cell.getPiece().isPresent()) {
      @SuppressWarnings("OptionalGetWithoutIsPresent") Piece piece = cell.getPiece().get();
      Coordinate coordinate = cell.getCoordinate();

      for (Coordinate direction : DIRECTIONS) {
        Coordinate neighbourCoordinate =
            new Coordinate(coordinate.row + direction.row, coordinate.col + direction.col);

        Optional<Cell> optionalCell = board.getBoardCell(neighbourCoordinate);
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

              optionalCell = board.getBoardCell(nextNeighbourCoordinate);
              if (optionalCell.isPresent()) {
                Cell nextNeighbourCell = optionalCell.get();

                optionalPiece = nextNeighbourCell.getPiece();
                if (optionalPiece.isPresent()) {
                  Piece nextNeighbourPiece = optionalPiece.get();

                  if (nextNeighbourPiece == piece) {
                    if (isToValidate) {
                      return true;
                    } else {
                      updateBoard(board, cell, nextNeighbourCell, direction);
                      break;
                    }
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
    }

    return false;
  }

  public static void updateBoard(Board board, Cell start, Cell end,
      Coordinate direction) {
    checkArgument(board != null, "Board must be set");
    checkArgument(start != null, "start Cell must be set");
    checkArgument(end != null, "end Cell must be set");
    checkArgument(direction != null, "direction Coordinate must be set");
    checkArgument(start.getPiece().isPresent(), "start cell piece must be set");
    checkArgument(end.getPiece().isPresent(), "end cell piece must be set");
    checkArgument(start.getPiece().get() == end.getPiece().get(),
        "start and end cell piece is not equal");

    Cell currentCell = start;
    while (currentCell != end) {
      currentCell.setPiece(end.getPiece().get());

      Optional<Cell> optional = board.getBoardCell(
          new Coordinate(currentCell.getCoordinate().row + direction.row,
              currentCell.getCoordinate().col + direction.col));

      if (optional.isPresent()) {
        currentCell = optional.get();
      } else {
        throw new IllegalArgumentException(
            String.format("Empty cell in the line of %s - %s with direction %s",
                start.getCoordinate(), end.getCoordinate(), direction));
      }
    }
  }

  private Board() {
    // prevent initialization
  }

  public Optional<Cell> getBoardCell(Coordinate coordinate) {
    checkArgument(coordinate != null, "Coordinate must be set");

    return isWithinBoard(coordinate) ? Optional.of(board.get(coordinate.row).get(coordinate.col))
        : Optional.<Cell>absent();
  }

  public Cell setBoardCell(Coordinate coordinate, Piece piece) {
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

    if (!isWithinBoard(coordinate)) {
      throw new IllegalArgumentException(String.format("%s is not valid coordiate", coordinate));
    }

    board.get(coordinate.row).get(coordinate.col).setPiece(piece);

    return board.get(coordinate.row).get(coordinate.col);
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
