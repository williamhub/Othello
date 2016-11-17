package engine;

import com.google.common.base.Optional;
import delegate.BoardDelegate;
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
  BoardDelegate boardDelegate;

  public GameEngine() {
    this.boardDelegate = Board.newInstance();
  }

  public void placePiece(Coordinate coordinate, Piece piece) {
    checkState(this.boardDelegate != null);
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

    if (!this.boardDelegate.isWithinBoard(coordinate)) {
      System.out.printf("%s is not within the board\n", coordinate);
      return;
    }

    Optional<Board> boardOptional = this.boardDelegate.placePiece(coordinate, piece);
    if (boardOptional.isPresent()) {
      this.boardDelegate = boardOptional.get();

      placePieceOpposite(piece.getOpposite());

      while (this.boardDelegate.getValidChildBoard(piece).isEmpty()) {
        System.out.printf("Skipped %s piece step", piece);
        placePieceOpposite(piece.getOpposite());
      }
    } else {
      System.out.printf("You can not put %s on %s \n", piece, coordinate);
    }
  }

  private void placePieceOpposite(Piece piece) {
    List<Board> validChildes = new ArrayList<>();

    validChildes.addAll(this.boardDelegate.getValidChildBoard(piece));

    if (!validChildes.isEmpty()) {
      this.boardDelegate = validChildes.get(0);
    } else {
      System.out.printf("Skipped %s piece step", piece);
    }
  }

  public boolean isFinished() {
    return this.boardDelegate.isEnd();
  }

  public String getBoardLayout() {
    return this.boardDelegate.getBoardLayout();
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

    boardDelegate = Board.newInstance(boardCells);
  }

  public String getFile(String fileName) {

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
