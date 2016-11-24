package strategy;

import java.util.List;
import model.Board;
import model.Coordinate;
import model.GameResult;
import model.Piece;
import model.TreeNode;
import strategy.heuristic.HeuristicMethod;

public class MinMaxTree {
  public final static int LEVELS = 3;

  private TreeNode root;

  private HeuristicMethod heuristicMethod;

  public MinMaxTree(Board board, Piece piece, HeuristicMethod heuristicMethod) {
    this.root = new TreeNode(board, piece);
    this.heuristicMethod = heuristicMethod;
  }

  public TreeNode getRoot() {
    return this.root;
  }

  public TreeNode getNextNode() {
    getNextNode(getRoot(), LEVELS, Integer.MIN_VALUE, Integer.MAX_VALUE);
    return getRoot().getChosenChild();
  }

  private int getNextNode(TreeNode parent, final int restLevels, int alpha, int beta) {
    if (restLevels == 0) {
      return getHeuristicValueLeafNode(parent);
    }

    if (parent.getBoard().isOver()) {
      return getHeuristicValueNonLeafNode(parent);
    }

    List<Coordinate> childes = parent.getValidBoardMoves();

    /* No available moves for current board */
    if (childes.isEmpty()) {
      TreeNode copiedNode = new TreeNode(parent.getBoard(), parent.getCurrentPiece().getOpposite());
      return getNextNode(copiedNode, restLevels - 1, alpha, beta);
    }

    switch (parent.getCurrentPiece()) {
      case BLACK:
        TreeNode maxChild = null;

        for (Coordinate coordinate : childes) {
          TreeNode childNode = parent.getValidChild(coordinate);
          int heuristicScore = getNextNode(childNode, restLevels - 1, alpha, beta);

          if (maxChild == null) {
            maxChild = childNode;
          } else {
            if (maxChild.getHeuristicScore() < childNode.getHeuristicScore()) {
              maxChild = childNode;
            }
          }

          if (heuristicScore > alpha) {
            alpha = heuristicScore;
          }
          if (alpha >= beta) {
            // beta cut-off
            break;
          }
        }

        parent.setChosenChild(maxChild);
        parent.setHeuristicScore(maxChild.getHeuristicScore());
        return alpha;
      case WHITE:
        TreeNode minChild = null;

        for (Coordinate coordinate : childes) {
          TreeNode childNode = parent.getValidChild(coordinate);
          int heuristicScore = getNextNode(childNode, restLevels - 1, alpha, beta);

          if (minChild == null) {
            minChild = childNode;
          } else {
            if (minChild.getHeuristicScore() > childNode.getHeuristicScore()) {
              minChild = childNode;
            }
          }

          if (heuristicScore < beta) {
            beta = heuristicScore;
          }
          if (alpha >= beta) {
            // alpha cut-off
            break;
          }
        }

        parent.setChosenChild(minChild);
        parent.setHeuristicScore(minChild.getHeuristicScore());
        return beta;
      default:
        throw new IllegalArgumentException(
            String.format("Unknown piece [%s]", parent.getCurrentPiece()));
    }
  }

  private int getHeuristicValueLeafNode(TreeNode treeNode) {
    int result = 0;

    Board board = treeNode.getBoard();
    if (board.isOver()) {
      result = getBoardHeuristicValue(board);
    } else {
      result = heuristicMethod.getResult(treeNode);
    }
    treeNode.setHeuristicScore(result);

    return result;
  }

  private int getHeuristicValueNonLeafNode(TreeNode treeNode) {
    int result = getBoardHeuristicValue(treeNode.getBoard());
    treeNode.setHeuristicScore(result);
    return result;
  }

  private int getBoardHeuristicValue(Board board) {
    GameResult gameResult = board.getWinner();
    switch (gameResult) {
      case BLACK:
        return 10000000;
      case WHITE:
        return -10000000;
      case TIE:
        return 0;
      default:
        throw new IllegalArgumentException(
            String.format("Unknown game result [%s]", gameResult));
    }
  }
}
