package model;

import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

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

  private void setBoard(List<List<Cell>> board) {
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
   * Public Methods
   */

  public List<Board> getChildBoards(Piece piece) {
    List<Board> childBoards = new ArrayList<>();

    List<Coordinate> potentialMoves = getValidMoves(piece);
    for (Coordinate potentialMove : potentialMoves) {
      Optional<Board> optional = placePiece(potentialMove, piece);
      if (optional.isPresent()) {
        childBoards.add(optional.get());
      }
    }

    return childBoards;
  }

  public List<Coordinate> getValidMoves(Piece piece) {
    List<Coordinate> coordinates = new ArrayList<>();

    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        Coordinate coordinate = new Coordinate(row, col);
        if (!this.board.get(row).get(col).getPiece().isPresent()) {
          if (checkOrValidate(coordinate, piece, true)) {
            coordinates.add(coordinate);
          }
        }
      }
    }

    return coordinates;
  }

  public GameResult getWinner() {
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

  public Optional<Board> placePiece(Coordinate coordinate, Piece piece) {
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

    if (checkOrValidate(coordinate, piece, true)) {
      Board newBoard = newInstance(this.board);
      newBoard.setBoardCell(coordinate, piece);
      newBoard.checkOrValidate(coordinate, piece, false);
      return Optional.of(newBoard);
    }

    return Optional.absent();
  }

  public boolean isContain(Coordinate coordinate) {
    return !(coordinate.row < 0
        || coordinate.row > DIMENSION - 1
        || coordinate.col < 0
        || coordinate.col > DIMENSION - 1);
  }

  public boolean isEnd() {
    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        Coordinate coordinate = new Coordinate(row, col);
        if (checkOrValidate(coordinate, Piece.WHITE, true) || checkOrValidate(coordinate,
            Piece.BLACK, true)) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean isValid() {
    if (this.board.size() != DIMENSION) {
      return false;
    }

    for (int row = 0; row < this.board.size(); row++) {
      if (this.board.get(row).size() != DIMENSION) {
        return false;
      }
    }

    return true;
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

  /**
   * Board Helper Methods.
   */

  private Optional<Cell> getBoardCell(Coordinate coordinate) {
    checkArgument(coordinate != null, "Coordinate must be set");

    return isContain(coordinate) ? Optional.of(
        this.board.get(coordinate.row).get(coordinate.col))
        : Optional.<Cell>absent();
  }

  private Cell setBoardCell(Coordinate coordinate, Piece piece) {
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

    if (!isContain(coordinate)) {
      throw new IllegalArgumentException(String.format("%s is not valid coordiate", coordinate));
    }

    this.board.get(coordinate.row).get(coordinate.col).setPiece(piece);

    return this.board.get(coordinate.row).get(coordinate.col);
  }

  /**
   * Check or validate board from a target cell.
   *
   * @param isToValidate true if only check board, otherwise update board by all lines cross the
   * given coordinate
   */
  private boolean checkOrValidate(Coordinate coordinate,
      Piece piece,
      boolean isToValidate) {
    checkState(this.board != null);
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

    for (Coordinate direction : DIRECTIONS) {
      Coordinate neighbourCoordinate = coordinate.move(direction);
      Optional<Cell> optionalCell = getBoardCell(neighbourCoordinate);
      if (!optionalCell.isPresent()) {
        continue;
      }
      Cell neighbourCell = optionalCell.get();

      if (!neighbourCell.getPiece().isPresent()) {
        continue;
      }

      Piece neighbourPiece = neighbourCell.getPiece().get();
      if (neighbourPiece == piece) {
        continue;
      }

      while (true) {
        Coordinate nextNeighbourCoordinate = neighbourCell.getCoordinate().move(direction);
        optionalCell = getBoardCell(nextNeighbourCoordinate);
        if (!optionalCell.isPresent()) {
          break;
        }

        Cell nextNeighbourCell = optionalCell.get();
        if (!nextNeighbourCell.getPiece().isPresent()) {
          break;
        }

        Piece nextNeighbourPiece = nextNeighbourCell.getPiece().get();
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
      }
    }

    return false;
  }

  private void updateBoard(Cell start, Cell end,
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

      Optional<Cell> optional = getBoardCell(currentCell.getCoordinate().move(direction));

      if (optional.isPresent()) {
        currentCell = optional.get();
      } else {
        throw new IllegalArgumentException(
            String.format("Empty cell in the line of %s - %s with direction %s",
                start.getCoordinate(), end.getCoordinate(), direction));
      }
    }
  }

  private int countPieces(Piece piece) {
    int result = 0;

    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        Optional<Piece> optional = this.board.get(row).get(col).getPiece();
        if (!optional.isPresent()) {
          continue;
        }

        if (optional.get() == piece) {
          result++;
        }
      }
    }

    return result;
  }
}
