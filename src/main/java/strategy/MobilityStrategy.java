package strategy;

import model.Board;
import model.Piece;

public class MobilityStrategy implements Strategy {

  @Override
  public int getBoardHeuristicValue(Board board, Piece piece) {
    int blackValidMoves = board.getValidMoves(Piece.BLACK).size();
    int whiteValidMoves = board.getValidMoves(Piece.WHITE).size();

    if (blackValidMoves + whiteValidMoves == 0) {
      return 0;
    }

    return 100 * (blackValidMoves - whiteValidMoves) / (blackValidMoves + whiteValidMoves);
  }
}
