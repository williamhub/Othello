package model;

import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.List;
import utils.FileReaderUtil;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class Board {
  private final static int DIMENSION = 8;

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

  public static Board newInstance(String filePath) {
    String rawBoard = FileReaderUtil.getFile(filePath);
    String[] rawBoardRows = rawBoard.split("\n");

    List<List<Cell>> boardCells = new ArrayList<>();

    for (int row = 0; row < rawBoardRows.length; row++) {
      String rawBoardRow = rawBoardRows[row];
      List<Cell> rowCells = new ArrayList<>();
      for (int col = 0; col < rawBoardRow.length(); col++) {
        Coordinate coordinate = new Coordinate(row, col);
        switch (rawBoardRow.charAt(col)) {
          case '0':
            rowCells.add(new Cell(coordinate));
            break;
          case 'B':
            rowCells.add(new Cell(Piece.BLACK, coordinate));
            break;
          case 'W':
            rowCells.add(new Cell(Piece.WHITE, coordinate));
            break;
          default:
            throw new IllegalArgumentException(
                String.format("Unknown char [%s] in file: %s", rawBoardRow.charAt(col),
                    filePath));
        }
      }
      boardCells.add(rowCells);
    }

    Board newBoard = Board.newInstance(boardCells);
    if (!newBoard.isValid()) {
      throw new IllegalArgumentException("The given board file is not valid");
    }

    return newBoard;
  }

  private static Board newInstance(List<List<Cell>> cells) {
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
      childBoards.add(placePiece(potentialMove, piece));
    }

    return childBoards;
  }

  public List<Coordinate> getValidMoves(Piece piece) {
    List<Coordinate> coordinates = new ArrayList<>();

    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        Coordinate coordinate = new Coordinate(row, col);
        if (isValidMove(coordinate, piece)) {
          coordinates.add(coordinate);
        }
      }
    }

    return coordinates;
  }

  public GameResult getWinner() {
    if (!isOver()) {
      throw new IllegalStateException("The game is not end yet");
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

  public Board placePiece(Coordinate coordinate, Piece piece) {
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

    Board newBoard = newInstance(this.board);
    newBoard.placeAndFlip(coordinate, piece);
    return newBoard;
  }

  public boolean isOver() {
    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        Coordinate coordinate = new Coordinate(row, col);
        if (isValidMove(coordinate, Piece.WHITE) || isValidMove(coordinate,
            Piece.BLACK)) {
          return false;
        }
      }
    }
    return true;
  }

  public int countPieces(Piece piece) {
    int result = 0;

    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        Optional<Piece> optional = getBoardCell(row, col).getPiece();
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

  public int getAmountCornerCaptured(Piece piece) {
    int result = 0;

    if (getBoardCell(0, 0).getPiece().get() == piece) {
      result++;
    }
    if (getBoardCell(0, 7).getPiece().get() == piece) {
      result++;
    }
    if (getBoardCell(7, 0).getPiece().get() == piece) {
      result++;
    }
    if (getBoardCell(7, 7).getPiece().get() == piece) {
      result++;
    }

    return result;
  }

  public Cell getBoardCell(Coordinate coordinate) {
    checkArgument(coordinate != null, "Coordinate must be set");

    if (!isContain(coordinate)) {
      throw new IllegalArgumentException(String.format("Invalid coordinate [%s]", coordinate));
    }
    return this.board.get(coordinate.row).get(coordinate.col);
  }

  public Cell getBoardCell(int row, int col) {
    if (!isContain(row, col)) {
      throw new IllegalArgumentException(String.format("Invalid coordinate [%s, %s]", row, col));
    }
    return this.board.get(row).get(col);
  }

  public boolean isContain(Coordinate coordinate) {
    return !(coordinate.row < 0
        || coordinate.row > DIMENSION - 1
        || coordinate.col < 0
        || coordinate.col > DIMENSION - 1);
  }

  public boolean isValidMove(Coordinate coordinate, Piece piece) {
    return isEmptyCell(coordinate) && checkOrFlip(coordinate, piece, true);
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        builder.append(getBoardCell(row, col).toString());
      }
      builder.append("\n");
    }

    return builder.toString();
  }

  /**
   * Board Helper Methods.
   */

  private boolean isEmptyCell(Coordinate coordinate) {
    return !getBoardCell(coordinate).getPiece().isPresent();
  }

  private boolean isContain(int row, int col) {
    return !(row < 0
        || row > DIMENSION - 1
        || col < 0
        || col > DIMENSION - 1);
  }

  private boolean isValid() {
    if (this.board.size() != DIMENSION) {
      return false;
    }

    for (List<Cell> row : this.board) {
      if (row.size() != DIMENSION) {
        return false;
      }
    }
    return true;
  }

  private boolean placeAndFlip(Coordinate coordinate, Piece piece) {
    getBoardCell(coordinate).setPiece(piece);

    return checkOrFlip(coordinate, piece, false);
  }

  private boolean checkOrFlip(Coordinate coordinate,
      Piece piece,
      boolean isToCheck) {
    checkState(this.board != null);
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

    for (Coordinate direction : DIRECTIONS) {
      Coordinate neighbourCoordinate = coordinate.move(direction);
      if (!isContain(neighbourCoordinate)) {
        continue;
      }

      Cell currentCell = getBoardCell(neighbourCoordinate);
      if (!currentCell.getPiece().isPresent()) {
        continue;
      }
      if (currentCell.getPiece().get() == piece) {
        continue;
      }

      while (true) {
        Coordinate endCoordinate = currentCell.getCoordinate().move(direction);
        if (!isContain(endCoordinate)) {
          break;
        }

        Cell endCell = getBoardCell(endCoordinate);
        if (!endCell.getPiece().isPresent()) {
          break;
        }

        if (endCell.getPiece().get() == piece) {
          if (isToCheck) {
            return true;
          } else {
            Cell startCell = new Cell(piece, coordinate);
            flipLine(startCell, endCell, direction);
            break;
          }
        }

        currentCell = endCell;
      }
    }

    return false;
  }

  private void flipLine(Cell start, Cell end,
      Coordinate direction) {
    checkState(this.board != null);
    checkArgument(start != null, "start Cell must be set");
    checkArgument(end != null, "end Cell must be set");
    checkArgument(direction != null, "direction Coordinate must be set");
    checkArgument(start.getPiece().isPresent(), "start cell piece must be set");
    checkArgument(end.getPiece().isPresent(), "end cell piece must be set");
    checkArgument(start.getPiece().get() == end.getPiece().get(),
        "start and end cell piece color is not equal");

    Cell currentCell = start;
    while (currentCell != end) {
      currentCell.setPiece(end.getPiece().get());
      currentCell = getBoardCell(currentCell.getCoordinate().move(direction));
    }
  }
}
