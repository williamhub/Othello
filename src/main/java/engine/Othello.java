package engine;

import com.google.common.base.Optional;
import delegate.BoardDelegate;
import java.util.Scanner;
import model.Board;
import model.Coordinate;
import model.Piece;

public class Othello {

  public static void main(String[] args) {
    GameEngine gameEngine = new GameEngine();
    System.out.println(gameEngine.getBoardLayout());

    Scanner scanner = new Scanner(System.in);

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      Scanner lineScanner = new Scanner(line);
      int row = lineScanner.nextInt();
      int col = lineScanner.nextInt();

      gameEngine.placePiece(new Coordinate(row, col), Piece.BLACK);
      System.out.println(gameEngine.getBoardLayout());
    }
  }
}
