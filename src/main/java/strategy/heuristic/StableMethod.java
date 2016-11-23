package strategy.heuristic;

import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Board;
import model.GameResult;
import model.Piece;
import model.TreeNode;

public class StableMethod implements HeuristicMethod {

  private final static List<List<Integer>> STABLE_STRATEGY_METRIC = new ArrayList<>();

  static {
    STABLE_STRATEGY_METRIC.add(Arrays.asList(4, -3, 2, 2, 2, 2, -3, 4));
    STABLE_STRATEGY_METRIC.add(Arrays.asList(-3, -4, -1, -1, -1, -1, -4, -3));
    STABLE_STRATEGY_METRIC.add(Arrays.asList(2, -1, 1, 0, 0, 1, -1, 2));
    STABLE_STRATEGY_METRIC.add(Arrays.asList(2, -1, 0, 1, 1, 0, -1, 2));
    STABLE_STRATEGY_METRIC.add(Arrays.asList(2, -1, 0, 1, 1, 0, -1, 2));
    STABLE_STRATEGY_METRIC.add(Arrays.asList(2, -1, 1, 0, 0, 1, -1, 2));
    STABLE_STRATEGY_METRIC.add(Arrays.asList(-3, -4, -1, -1, -1, -1, -4, -3));
    STABLE_STRATEGY_METRIC.add(Arrays.asList(4, -3, 2, 2, 2, 2, -3, 4));
  }

  @Override
  public int getResult(TreeNode leafNode) {
    Board board = leafNode.getBoard();
    if (board.isOver()) {
      GameResult gameResult = board.getWinner();
      switch (gameResult) {
        case BLACK:
          return Integer.MAX_VALUE;
        case WHITE:
          return Integer.MIN_VALUE;
        case TIE:
          return 0;
        default:
          throw new IllegalArgumentException(
              String.format("Unknown game result [%s]", gameResult));
      }
    }

    int whiteResult = 0;
    int blackResult = 0;

    for (int row = 0; row < STABLE_STRATEGY_METRIC.size(); row++) {
      for (int col = 0; col < STABLE_STRATEGY_METRIC.get(0).size(); col++) {
        Optional<Piece> pieceOptional =
            board.getBoardCell(row, col).getPiece();

        if (!pieceOptional.isPresent()) {
          continue;
        }
        Piece piece = pieceOptional.get();

        switch (piece) {
          case WHITE:
            whiteResult += STABLE_STRATEGY_METRIC.get(row).get(col);
            break;
          case BLACK:
            blackResult += STABLE_STRATEGY_METRIC.get(row).get(col);
            break;
          default:
            throw new IllegalArgumentException(
                String.format("Unknown piece [%s]", piece));
        }
      }
    }

    int result = blackResult - whiteResult;
    leafNode.setHeuristicScore(result);
    return result;
  }
}
