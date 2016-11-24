package strategy.heuristic;

import model.Piece;
import model.TreeNode;

public class MobilityMethod implements HeuristicMethod {
  @Override public int getResult(TreeNode leafNode) {
    return leafNode.getBoard().getValidMoves(Piece.BLACK).size();
  }
}
