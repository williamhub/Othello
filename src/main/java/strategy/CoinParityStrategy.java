package strategy;

import model.Board;
import model.Piece;

/**
 * This component of the utility function captures
 * the difference in coins between the max player
 * and min player.
 */
public class CoinParityStrategy implements Strategy {

  @Override public int getBoardHeuristicValue(Board board, Piece piece) {
    int blackCoins = board.countPieces(Piece.BLACK);
    int whiteCoins = board.countPieces(Piece.WHITE);
    return 100 * (blackCoins - whiteCoins) / (blackCoins + whiteCoins);
  }
}
