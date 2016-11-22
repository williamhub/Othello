package engine;

import com.google.common.base.Optional;
import java.util.ArrayList;
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
}
