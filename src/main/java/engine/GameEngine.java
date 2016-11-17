package engine;

import com.google.common.base.Optional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Board;
import model.Cell;
import model.Coordinate;
import model.Piece;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class GameEngine {
  Board board;

  public GameEngine() {
    this.board = Board.newInstance();
  }

  public void placePiece(Coordinate coordinate, Piece piece) {
    checkState(this.board != null);
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

    if (!this.board.isContain(coordinate)) {
      System.out.printf("%s is not within the board\n", coordinate);
      return;
    }

    Optional<Board> boardOptional = this.board.placePiece(coordinate, piece);
    if (!boardOptional.isPresent()) {
      System.out.printf("You can not put %s on %s \n", piece, coordinate);
      return;
    }

    this.board = boardOptional.get();

    placePieceOpposite(piece.getOpposite());

    while (this.board.getValidMoves(piece).isEmpty()) {
      System.out.printf("Skipped %s piece step", piece);
      placePieceOpposite(piece.getOpposite());
    }
  }

  private void placePieceOpposite(Piece piece) {
    List<Board> validChildes = new ArrayList<>();

    validChildes.addAll(this.board.getChildBoards(piece));

    if (validChildes.isEmpty()) {
      System.out.printf("Skipped %s piece step", piece);
      return;
    }

    this.board = validChildes.get(0);
  }

  public boolean isFinished() {
    return this.board.isEnd();
  }

  public String getBoardLayout() {
    return this.board.toString();
  }

  public void loadGame(String filePath) {
    String gameState = getFile(filePath);
    String[] gameStateRows = gameState.split("\n");

    List<List<Cell>> boardCells = new ArrayList<>();

    for (int row = 0; row < gameStateRows.length; row++) {
      String rowLine = gameStateRows[row];
      List<Cell> rowCells = new ArrayList<>();
      for (int col = 0; col < rowLine.length(); col++) {
        Coordinate coordinate = new Coordinate(row, col);
        switch (rowLine.charAt(col)) {
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
            throw new IllegalStateException(
                String.format("Cannot parse %s input from file: %s", rowLine.charAt(col),
                    filePath));
        }
      }
      boardCells.add(rowCells);
    }

    Board newBoard = Board.newInstance(boardCells);
    if (!newBoard.isValid()) {
      System.err.println("The given board file is not valid");
      return;
    }

    this.board = newBoard;
  }

  private String getFile(String fileName) {

    StringBuilder result = new StringBuilder("");

    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());

    try (Scanner scanner = new Scanner(file)) {

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        result.append(line).append("\n");
      }

      scanner.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result.toString();
  }
}
