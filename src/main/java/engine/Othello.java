package engine;

import java.util.Scanner;
import model.Coordinate;
import model.Piece;
import strategy.GreedyStrategy;

public class Othello {

  public static void main(String[] args) {
    GameEngine gameEngine = new GameEngine(new GreedyStrategy());
    System.out.println(gameEngine.getBoardLayout());

    Scanner scanner = new Scanner(System.in);

    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      Scanner lineScanner = new Scanner(line);
      int row = lineScanner.nextInt();
      int col = lineScanner.nextInt();

      gameEngine.placePieceByHuman(new Coordinate(row, col), Piece.BLACK);
      System.out.println(gameEngine.getBoardLayout());
    }
  }
}
