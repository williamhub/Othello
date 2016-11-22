package strategy.heuristic;

import com.google.common.base.Optional;
import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.Coordinate;
import model.Piece;
import model.TreeNode;
import utils.FileReaderUtil;

public class HeuristicMethod {

  private final static String STABLE_STRATEGY_METRIC_PATH = "strategy/stable_strategy.txt";

  public int getResult(TreeNode leafNode) {
    int whiteResult = 0;
    int blackResult = 0;
    List<List<Integer>> matrix = loadMatrix(STABLE_STRATEGY_METRIC_PATH);
    Board board = leafNode.getBoard();

    for (int row = 0; row < matrix.size(); row++) {
      for (int col = 0; col < matrix.get(0).size(); col++) {
        Optional<Piece> pieceOptional =
            board.getBoardCell(new Coordinate(row, col)).get().getPiece();

        if (!pieceOptional.isPresent()) {
          continue;
        }
        Piece piece = pieceOptional.get();

        switch (piece) {
          case WHITE:
            whiteResult += matrix.get(row).get(col);
            break;
          case BLACK:
            blackResult += matrix.get(row).get(col);
            break;
          default:
            throw new IllegalArgumentException(
                String.format("Cannot use [%s] piece", piece));
        }
      }
    }

    int result = blackResult - whiteResult;
    leafNode.setHeuristicValue(result);
    return result;
  }

  private List<List<Integer>> loadMatrix(String fileName) {
    String matrixRaw = FileReaderUtil.getFile(fileName);

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
