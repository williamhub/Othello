package model;

import com.google.common.base.Optional;
import delegate.BoardDelegate;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class Board implements BoardDelegate {
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

  /**
   * Board Constructor
   */

  public static Board newInstance() {
    Board board = new Board();
    board.initializeBoard();
    return board;
  }

  public static Board newInstance(List<List<Cell>> cells) {
    Board board = new Board();
    board.setBoard(cells);
    return board;
  }

  private void initializeBoard() {
    this.board = new ArrayList<>();
    for (int row = 0; row < DIMENSION; row++) {
      List<Cell> initialRow = new ArrayList<>();
      for (int col = 0; col < DIMENSION; col++) {
        initialRow.add(generateCell(row, col));
      }
      this.board.add(initialRow);
    }
  }

  private Cell generateCell(int row, int col) {
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

  public void setBoard(List<List<Cell>> board) {
    List<List<Cell>> newBoard = new ArrayList<>();
    for (int row = 0; row < DIMENSION; row++) {
      List<Cell> newRow = new ArrayList<>();
      for (int col = 0; col < DIMENSION; col++) {
        Cell cell = board.get(row).get(col);
        Optional<Piece> pieceOptional = cell.getPiece();
        if (pieceOptional.isPresent()) {
          newRow.add(new Cell(pieceOptional.get(), cell.getCoordinate()));
        } else {
          newRow.add(new Cell(cell.getCoordinate()));
        }
      }
      newBoard.add(newRow);
    }
    this.board = newBoard;
  }

  private Board() {
    // prevent initialization
  }

  /**
   * Board Core Operations
   */

  /**
   * Check or validate board from a target cell.
   *
   * @param isToValidate true if only check board, otherwise update board by all lines cross the
   * given coordinate
   */
  public boolean checkOrValidateBoardFromCell(Coordinate coordinate,
      Piece piece,
      boolean isToValidate) {
    checkState(this.board != null);
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

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
                  if (isToValidate) {
                    return true;
                  } else {
                    Cell start = new Cell(piece, coordinate);
                    updateBoard(start, nextNeighbourCell, direction);
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

    return false;
  }

  public void updateBoard(Cell start, Cell end,
      Coordinate direction) {
    checkState(this.board != null);
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

      Optional<Cell> optional = getBoardCell(
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

  public List<Coordinate> getPotentialMoves(Piece piece) {
    List<Coordinate> coordinates = new ArrayList<>();

    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        Coordinate coordinate = new Coordinate(row, col);
        if (!this.board.get(row).get(col).getPiece().isPresent()) {
          if (checkOrValidateBoardFromCell(coordinate, piece, true)) {
            coordinates.add(coordinate);
          }
        }
      }
    }

    return coordinates;
  }

  public int countPieces(Piece piece) {
    int result = 0;

    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        Optional<Piece> optional = this.board.get(row).get(col).getPiece();
        if (optional.isPresent()) {
          if (optional.get() == piece) {
            result++;
          }
        }
      }
    }

    return result;
  }

  /**
   * Board Delegate Implementations
   */

  @Override public boolean isEnd() {
    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        Coordinate coordinate = new Coordinate(row, col);
        if (checkOrValidateBoardFromCell(coordinate, Piece.WHITE, true)
            || checkOrValidateBoardFromCell(coordinate,
            Piece.BLACK, true)) {
          return false;
        }
      }
    }
    return true;
  }

  @Override public List<Board> getValidChildBoard(Piece piece) {
    List<Board> childBoards = new ArrayList<>();

    List<Coordinate> potentialMoves = getPotentialMoves(piece);
    for (Coordinate potentialMove : potentialMoves) {
      Optional<Board> optional = placePiece(potentialMove, piece);
      if (optional.isPresent()) {
        childBoards.add(optional.get());
      }
    }

    return childBoards;
  }

  @Override
  public Optional<Board> placePiece(Coordinate coordinate, Piece piece) {
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

    if (checkOrValidateBoardFromCell(coordinate, piece, true)) {
      Board newBoard = newInstance(this.board);
      newBoard.setBoardCell(coordinate, piece);
      newBoard.checkOrValidateBoardFromCell(coordinate, piece, false);
      return Optional.of(newBoard);
    }

    return Optional.absent();
  }

  @Override public GameResult getWinner() {
    if (!isEnd()) {
      throw new IllegalStateException("The game is end yet");
    }

    int whiteCount = countPieces(Piece.WHITE);
    int blackCount = countPieces(Piece.BLACK);

    if (whiteCount < blackCount) {
      return GameResult.BLACK;
    } else if (whiteCount > blackCount) {
      return GameResult.WHITE;
    } else {
      return GameResult.TIE;
    }
  }

  @Override public String getBoardLayout() {
    return toString();
  }

  /**
   * Board Helper Methods.
   */

  private boolean isWithinBoard(Coordinate coordinate) {
    return !(coordinate.row < 0
        || coordinate.row > DIMENSION - 1
        || coordinate.col < 0
        || coordinate.col > DIMENSION - 1);
  }

  public Optional<Cell> getBoardCell(Coordinate coordinate) {
    checkArgument(coordinate != null, "Coordinate must be set");

    return isWithinBoard(coordinate) ? Optional.of(this.board.get(coordinate.row).get(coordinate.col))
        : Optional.<Cell>absent();
  }

  public Cell setBoardCell(Coordinate coordinate, Piece piece) {
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

    if (!isWithinBoard(coordinate)) {
      throw new IllegalArgumentException(String.format("%s is not valid coordiate", coordinate));
    }

    this.board.get(coordinate.row).get(coordinate.col).setPiece(piece);

    return this.board.get(coordinate.row).get(coordinate.col);
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        builder.append(this.board.get(row).get(col).toString());
      }
      builder.append("\n");
    }

    return builder.toString();
  }

  @Override public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null || getClass() != other.getClass()) return false;

    Board otherBoard = (Board) other;

    return board.equals(otherBoard.board);
  }

  @Override public int hashCode() {
    return board.hashCode();
  }
}
