package engine;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Board;
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

  public GameEngine(Strategy strategy, String boardFilePath) {
    this.board = Board.newInstance(boardFilePath);
    this.strategy = strategy;
  }

  public void placePieceByHuman(Coordinate coordinate, Piece piece) {
    checkState(this.board != null);
    checkArgument(coordinate != null, "Coordinate must be set");
    checkArgument(piece != null, "Piece must be set");

    if (!this.board.isContain(coordinate) || !this.board.isValidMove(coordinate, piece)) {
      System.err.printf("Invalid coordinate [%s]\n", coordinate);
      return;
    }

    this.board = this.board.placePiece(coordinate, piece);
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

  private void placePiece(final Piece piece) {
    List<Board> childBoards = this.board.getChildBoards(piece);

    if (childBoards.isEmpty()) {
      System.out.printf("Skipped [%s] piece step\n", piece);
      return;
    }

    this.board = Collections.max(childBoards, new Comparator<Board>() {
      @Override public int compare(Board board1, Board board2) {
        return strategy.getBoardHeuristicValue(board1, piece) - strategy.getBoardHeuristicValue(
            board2, piece);
      }
    });
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
}
