package strategy;

import model.Board;
import model.Piece;

public class StabilityMiniMaxTreeStrategy implements MiniMaxTreeStrategy {
  @Override public Board getNextBoard(Board board, Piece piece) {
    MiniMaxTree miniMaxTree = new MiniMaxTree(board, piece, new StabilityStrategy());
    return miniMaxTree.getNextNode().getBoard();
  }
}
