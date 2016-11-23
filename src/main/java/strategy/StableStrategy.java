package strategy;

import model.Board;
import model.Piece;
import strategy.heuristic.StableMethod;

public class StableStrategy implements Strategy {
  @Override public Board choose(Piece piece, Board board) {
    MinMaxTree minMaxTree = new MinMaxTree(board, piece, new StableMethod());
    return minMaxTree.getNextNode().getBoard();
  }
}
