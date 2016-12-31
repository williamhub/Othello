package strategy;

import model.Board;
import model.Piece;

public class CornerCapturedMiniMaxTreeStrategy implements MiniMaxTreeStrategy {
  @Override public Board getNextBoard(Board board, Piece piece) {
    MiniMaxTree miniMaxTree = new MiniMaxTree(board, piece, new CoinParityStrategy());
    return miniMaxTree.getNextNode().getBoard();
  }
}
