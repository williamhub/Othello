import model.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {
  @Test
  public void testToString() {
    Board board = Board.newInstance();
    String expected = "00000000\n"
        + "00000000\n"
        + "00000000\n"
        + "000WB000\n"
        + "000BW000\n"
        + "00000000\n"
        + "00000000\n"
        + "00000000\n";
    String actual = board.toString();

    assertEquals(expected, actual);
  }
}
