package strategy;

import java.util.List;
import model.Board;
import model.Coordinate;
import model.Piece;
import model.TreeNode;
import strategy.heuristic.HeuristicMethod;

public class MinMaxTree {
  public final static int LEVELS = 3;

  private TreeNode root;

  private AlphaBeta alphaBeta;

  private HeuristicMethod heuristicMethod;

  public MinMaxTree(Board board, Piece piece, HeuristicMethod heuristicMethod) {
    this.root = new TreeNode(board, piece);
    this.heuristicMethod = heuristicMethod;
    this.alphaBeta = new AlphaBeta(Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  public TreeNode getRoot() {
    return this.root;
  }

  public TreeNode getNextNode() {
    getNextNode(getRoot(), LEVELS + 1);
    return getRoot().getChosenChild();
  }

  private int getNextNode(TreeNode parent, final int restLevels) {
    if (restLevels == 0) {
      return heuristicMethod.getResult(parent);
    }

    List<Coordinate> childes = parent.getValidBoardMoves();
    switch (parent.getCurrentPiece()) {
      case BLACK:
        TreeNode maxChild = null;

        for (Coordinate coordinate : childes) {
          TreeNode childNode = parent.getValidChild(coordinate);
          int heuristicScore = getNextNode(childNode, restLevels - 1);

          if (maxChild == null) {
            maxChild = childNode;
          } else {
            if (maxChild.getHeuristicScore() < childNode.getHeuristicScore()) {
              maxChild = childNode;
            }
          }

          if (heuristicScore > alphaBeta.alpha) {
            alphaBeta.alpha = heuristicScore;
          }
          if (alphaBeta.alpha >= alphaBeta.beta) {
            // beta cut-off
            break;
          }
        }

        parent.setChosenChild(maxChild);
        return alphaBeta.alpha;
      case WHITE:
        TreeNode minChild = null;

        for (Coordinate coordinate : childes) {
          TreeNode childNode = parent.getValidChild(coordinate);
          int heuristicScore = getNextNode(childNode, restLevels - 1);

          if (minChild == null) {
            minChild = childNode;
          } else {
            if (minChild.getHeuristicScore() > childNode.getHeuristicScore()) {
              minChild = childNode;
            }
          }

          if (heuristicScore < alphaBeta.beta) {
            alphaBeta.beta = heuristicScore;
          }
          if (alphaBeta.alpha >= alphaBeta.beta) {
            // alpha cut-off
            break;
          }
        }

        parent.setChosenChild(minChild);
        return alphaBeta.beta;
      default:
        throw new IllegalArgumentException(
            String.format("Unknown piece [%s]", parent.getCurrentPiece()));
    }
  }

  private class AlphaBeta {
    public int alpha;
    public int beta;

    public AlphaBeta(int alpha, int beta) {
      this.alpha = alpha;
      this.beta = beta;
    }
  }
}
