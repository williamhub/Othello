package strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Board;
import model.Piece;

public class GreedyStrategy implements Strategy {

  @Override public Board choose(final Piece piece, List<Board> childBoards) {
    return Collections.max(childBoards, new Comparator<Board>() {
      @Override public int compare(Board board1, Board board2) {
        return board1.countPieces(piece) - board2.countPieces(piece);
      }
    });
  }
}