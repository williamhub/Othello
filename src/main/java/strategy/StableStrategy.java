package strategy;

import model.Board;
import model.Piece;
import strategy.minmaxtree.MinMaxTree;
import strategy.minmaxtree.StableStrategyTree;

public class StableStrategy implements Strategy{

  @Override public Board choose(Piece piece, Board board) {
    MinMaxTree minMaxTree = StableStrategyTree.newInstance(board, piece);
    return minMaxTree.getNextNode().getBoard();
  }
}
