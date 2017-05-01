import model.Node;

public class EvaluationEngine {
  public final static int DIMENSION = 8;

  public static boolean isEndOfGame(boolean skipBlack, boolean skipWhite) {
    if (skipBlack && skipWhite) {
      return true;
    } else {
      return false;
    }
  }

  public static String winnerIs(Node endNode) {
    int blackCounter = 0;
    int whiteCounter = 0;
    for (int i = 0; i < DIMENSION; i++) {
      for (int j = 0; j < DIMENSION; j++) {
        if (endNode.board.getBoard().get(i).get(j).toString() == "1") {
          blackCounter++;
        } else {
          whiteCounter++;
        }
      }
    }

    if (blackCounter > whiteCounter) {
      return "BLACK";
    } else if (whiteCounter > blackCounter) {
      return "WHITE";
    } else {
      return "DRAW";
    }
  }

  public static int evaluationMethod(Node node) {
    return 0;
  }
}
