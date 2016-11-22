package strategy.minmaxtree;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.Piece;
import model.TreeNode;
import utils.FileReaderUtil;

public class StableStrategyTree extends MinMaxTree {

  private final static String METRIC_PATH = "strategy/stable_strategy.txt";

  public static MinMaxTree newInstance(Board board, Piece piece) {
    TreeNode treeNode = new TreeNode(null, board, piece);
    StableStrategyTree stableStrategyTree = new StableStrategyTree();
    stableStrategyTree.root = treeNode;

    stableStrategyTree.initialize();

    return stableStrategyTree;
  }

  private StableStrategyTree() {
    // prevent initialization
  }

  @Override public int optimal(TreeNode treeNode) {
    int whiteResult = 0;
    int blackResult = 0;
    List<List<Integer>> metrix = loadMatrix();
    Board board = treeNode.getBoard();

    for (int row = 0; row < metrix.size(); row++) {
      for (int col = 0; col < metrix.get(0).size(); col++) {
        Piece piece = board.getBoardCell(row, col).get().getPiece().get();

        switch (piece) {
          case WHITE:
            whiteResult += metrix.get(row).get(col);
            break;
          case BLACK:
            blackResult += metrix.get(row).get(col);
            break;
          default:
            throw new IllegalArgumentException(
                String.format("Cannot use [%s] piece", piece));
        }
      }
    }

    int result = blackResult - whiteResult;
    treeNode.setHeuristicValue(result);
    return result;
  }

  private List<List<Integer>> loadMatrix() {
    String matrixRaw = FileReaderUtil.getFile(METRIC_PATH);

    List<List<Integer>> metric = new ArrayList<>();
    String[] matricRows = matrixRaw.split("\n");

    for (String metricRow : matricRows) {
      String[] rowRaw = metricRow.split(",");
      List<Integer> rowMetric = new ArrayList<>();
      for (String rawValue : rowRaw) {
        rowMetric.add(Integer.parseInt(rawValue));
      }
      metric.add(rowMetric);
    }

    return metric;
  }
}
