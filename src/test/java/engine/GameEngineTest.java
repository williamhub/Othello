package engine;

import model.Coordinate;
import model.Piece;
import org.junit.Test;
import strategy.GreedyStrategy;

import static org.junit.Assert.assertEquals;

public class GameEngineTest {

  @Test public void testPlacePieceByHumanSkipBlack() {
    GameEngine gameEngine =
        new GameEngine(new GreedyStrategy(), "engine/skip_black_piece_board.txt");

    String expected =
        "(\n"
            + "(00BBBBB0)\n"
            + "(00BBBB0B)\n"
            + "(BBBBBBBB)\n"
            + "(BBBBBBWB)\n"
            + "(BBBWBBWB)\n"
            + "(BBWBWWBB)\n"
            + "(BWWWWW0B)\n"
            + "(0BBBBBBB)\n"
            + ")\n";

    gameEngine.placePieceByHuman(new Coordinate(6, 7), Piece.BLACK);

    String actual = gameEngine.getBoardLayout();

    assertEquals(expected, actual);
  }

  @Test public void testPlacePieceByHumanSkipWhite() {
    GameEngine gameEngine =
        new GameEngine(new GreedyStrategy(), "engine/skip_white_piece_board.txt");

    String expected =
        "(\n"
            + "(WWWWW000)\n"
            + "(WWWW0000)\n"
            + "(WWWWWWW0)\n"
            + "(BBBBBB00)\n"
            + "(WWWWWWWW)\n"
            + "(WWWWWWW0)\n"
            + "(WWWWWW00)\n"
            + "(WWWWWW00)\n"
            + ")\n";

    gameEngine.placePieceByHuman(new Coordinate(3, 5), Piece.BLACK);

    String actual = gameEngine.getBoardLayout();

    assertEquals(expected, actual);
  }

  @Test public void testPlacePieceByHumanLastStep() {
    GameEngine gameEngine =
        new GameEngine(new GreedyStrategy(), "engine/last_step_board.txt");
    gameEngine.placePieceByHuman(new Coordinate(7, 0), Piece.BLACK);

    String expected =
        "(\n"
            + "(WWWWWWWW)\n"
            + "(BWBBBBBB)\n"
            + "(BWWBWBBB)\n"
            + "(BWBWBWWB)\n"
            + "(BWWBWWWB)\n"
            + "(BWBWWWWB)\n"
            + "(BWWWWWWB)\n"
            + "(BWWWWWWB)\n"
            + ")\n";

    String actual = gameEngine.getBoardLayout();
    assertEquals(expected, actual);
  }

  @Test public void testPlacePieceByHumanInvalidStep() {
    GameEngine gameEngine =
        new GameEngine(new GreedyStrategy(), "engine/last_step_board.txt");
    gameEngine.placePieceByHuman(new Coordinate(9, 9), Piece.BLACK);
    gameEngine.placePieceByHuman(new Coordinate(1, 1), Piece.BLACK);
    gameEngine.placePieceByHuman(new Coordinate(7, 0), Piece.WHITE);

    String expected =
        "(\n"
            + "(WWWWWWWW)\n"
            + "(BWBBBBBB)\n"
            + "(WWWBWWBB)\n"
            + "(WWBWWWWB)\n"
            + "(WWWWWWWB)\n"
            + "(WWWWWWWB)\n"
            + "(WWWWWWWB)\n"
            + "(00WWWWWB)\n"
            + ")\n";

    String actual = gameEngine.getBoardLayout();
    assertEquals(expected, actual);
  }
}
