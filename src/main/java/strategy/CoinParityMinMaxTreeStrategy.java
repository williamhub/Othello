package strategy;

import model.Board;
import model.Piece;

public class CoinParityMinMaxTreeStrategy implements MinMaxTreeStrategy {
  @Override
  public Board getNextBoard(Board board, Piece piece) {
    MinMaxTree minMaxTree = new MinMaxTree(board, piece, new CoinParityStrategy());
    return minMaxTree.getNextNode().getBoard();
  }
}
