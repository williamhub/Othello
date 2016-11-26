package strategy;

import model.Board;
import model.Piece;

public class StabilityMinMaxTreeStrategy implements MinMaxTreeStrategy {
  @Override public Board getNextBoard(Board board, Piece piece) {
    MinMaxTree minMaxTree = new MinMaxTree(board, piece, new StabilityStrategy());
    return minMaxTree.getNextNode().getBoard();
  }
}
