package strategy;

import model.Board;
import model.Piece;
import strategy.heuristic.MobilityMethod;

public class MobilityStrategy implements Strategy {
  @Override public Board choose(Piece piece, Board board) {
    MinMaxTree minMaxTree = new MinMaxTree(board, piece, new MobilityMethod());
    return minMaxTree.getNextNode().getBoard();
  }
}
