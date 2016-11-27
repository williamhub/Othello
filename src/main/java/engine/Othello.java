package engine;

import java.util.Scanner;
import model.Coordinate;
import model.Piece;
import strategy.CoinParityMinMaxTreeStrategy;

public class Othello {
  public final static int ROBOT_TIME_LIMIT_SECONDS = 10;
  public final static int HUMAN_TIME_LIMIT_SECONDS = 60;

  public static void main(String[] args) {
    GameEngine gameEngine = new GameEngine(new CoinParityMinMaxTreeStrategy());
    System.out.println(gameEngine.getBoardLayout());

    Scanner scanner = new Scanner(System.in);

    long startingTime = System.currentTimeMillis();
    while (scanner.hasNextLine()) {
      long interval = System.currentTimeMillis() - startingTime;

      String line = scanner.nextLine();
      Scanner lineScanner = new Scanner(line);
      int row = lineScanner.nextInt();
      int col = lineScanner.nextInt();

      startingTime = System.currentTimeMillis();
      if (interval > HUMAN_TIME_LIMIT_SECONDS * 1000) {
        System.err.println("Out of time, and you are skipped for one step!");
        gameEngine.placePieceByRobot(Piece.WHITE);
        System.out.println(gameEngine.getBoardLayout());
        continue;
      }

      gameEngine.placePieceByHuman(new Coordinate(row, col), Piece.BLACK);
      System.out.println(gameEngine.getBoardLayout());
    }
  }
}
