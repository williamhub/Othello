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
import strategy.Strategy;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class GameEngine {
  Board board;

  Strategy strategy;

  public GameEngine(Strategy strategy) {
    this.board = Board.newInstance();
    this.strategy = strategy;
  }

  public void placePieceByHuman(Coordinate coordinate, Piece piece) {
    checkState(this.board != null);
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

    Optional<Board> boardOptional = this.board.placePiece(coordinate, piece);
    if (!boardOptional.isPresent()) {
      return;
    }
    this.board = boardOptional.get();
    if (isOver()) {
      return;
    }

    placePiece(piece.getOpposite());
    if (isOver()) {
      return;
    }

    while (this.board.getValidMoves(piece).isEmpty()) {
      System.out.printf("Skipped %s piece step\n", piece);
      placePiece(piece.getOpposite());

      if (isOver()) {
        return;
      }
    }
  }

  public void placePieceByRobot(Piece piece) {
    placePiece(piece);
  }

  private void placePiece(Piece piece) {
    List<Board> validChildes = new ArrayList<>();

    validChildes.addAll(this.board.getChildBoards(piece));

    if (validChildes.isEmpty()) {
      System.out.printf("Skipped %s piece step\n", piece);
      return;
    }

    this.board = this.strategy.choose(piece, validChildes);
  }

  public boolean isOver() {
    boolean isGameOver = this.board.isOver();
    if (isGameOver) {
      System.out.printf("The winner is [%s]\n", this.board.getWinner());
    }
    return isGameOver;
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
