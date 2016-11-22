package engine;

import model.Coordinate;
import model.Piece;
import org.junit.Before;
import org.junit.Test;
import strategy.GreedyStrategy;

import static org.junit.Assert.assertEquals;

public class GameEngineTest {

  @Test public void testPlacePieceByHumanSkipBlack() {
    GameEngine gameEngine =
        new GameEngine(new GreedyStrategy(), "engine/skip_black_piece_board.txt");

    String expected =
        "00BBBBB0\n"
            + "00BBBB0B\n"
            + "BBBBBBBB\n"
            + "BBBBBBWB\n"
            + "BBBWBBWB\n"
            + "BBWBWWBB\n"
            + "BWWWWW0B\n"
            + "0BBBBBBB\n";

    gameEngine.placePieceByHuman(new Coordinate(6, 7), Piece.BLACK);

    String actual = gameEngine.getBoardLayout();

    assertEquals(expected, actual);
  }

  @Test public void testPlacePieceByHumanSkipWhite() {
    GameEngine gameEngine =
        new GameEngine(new GreedyStrategy(), "engine/skip_white_piece_board.txt");

    String expected =
        "WWWWW000\n"
            + "WWWW0000\n"
            + "WWWWWWW0\n"
            + "BBBBBB00\n"
            + "WWWWWWWW\n"
            + "WWWWWWW0\n"
            + "WWWWWW00\n"
            + "WWWWWW00\n";

    gameEngine.placePieceByHuman(new Coordinate(3, 5), Piece.BLACK);

    String actual = gameEngine.getBoardLayout();

    assertEquals(expected, actual);
  }

  @Test public void testPlacePieceByHumanLastStep() {
    GameEngine gameEngine =
        new GameEngine(new GreedyStrategy(), "engine/last_step_board.txt");
    gameEngine.placePieceByHuman(new Coordinate(7, 0), Piece.BLACK);

    String expected =
        "WWWWWWWW\n"
            + "BWBBBBBB\n"
            + "BWWBWBBB\n"
            + "BWBWBWWB\n"
            + "BWWBWWWB\n"
            + "BWBWWWWB\n"
            + "BWWWWWWB\n"
            + "BWWWWWWB\n";

    String actual = gameEngine.getBoardLayout();
    assertEquals(expected, actual);
  }

  @Test public void testPlacePieceByHumanInvalid() {
    GameEngine gameEngine =
        new GameEngine(new GreedyStrategy(), "engine/last_step_board.txt");
    gameEngine.placePieceByHuman(new Coordinate(9, 9), Piece.BLACK);

    String expected =
        "WWWWWWWW\n"
            + "BWBBBBBB\n"
            + "WWWBWWBB\n"
            + "WWBWWWWB\n"
            + "WWWWWWWB\n"
            + "WWWWWWWB\n"
            + "WWWWWWWB\n"
            + "00WWWWWB\n";

    String actual = gameEngine.getBoardLayout();
    assertEquals(expected, actual);
  }

  @Test public void testPlacePieceByRobotInvalid() {
    GameEngine gameEngine =
        new GameEngine(new GreedyStrategy(), "engine/skip_white_piece_board.txt");
    gameEngine.placePieceByRobot(Piece.WHITE);

    String expected =
        "WWWWW000\n"
            + "WWWW0000\n"
            + "WWWWWWW0\n"
            + "BWWWW000\n"
            + "WWWWWWWW\n"
            + "WWWWWWW0\n"
            + "WWWWWW00\n"
            + "WWWWWW00\n";

    String actual = gameEngine.getBoardLayout();
    assertEquals(expected, actual);
  }
}
