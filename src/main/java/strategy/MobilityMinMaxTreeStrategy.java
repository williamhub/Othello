package strategy;

import model.Board;
import model.Piece;

public class MobilityMinMaxTreeStrategy implements MinMaxTreeStrategy {
  @Override public Board getNextBoard(Board board, Piece piece) {
    MinMaxTree minMaxTree = new MinMaxTree(board, piece, new MobilityStrategy());
    return minMaxTree.getNextNode().getBoard();
  }
}
