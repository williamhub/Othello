package strategy;

import model.Board;
import model.Piece;

public class MobilityMiniMaxTreeStrategy implements MiniMaxTreeStrategy {
  @Override public Board getNextBoard(Board board, Piece piece) {
    MiniMaxTree miniMaxTree = new MiniMaxTree(board, piece, new MobilityStrategy());
    return miniMaxTree.getNextNode().getBoard();
  }
}
