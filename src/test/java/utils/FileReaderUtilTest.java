package utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileReaderUtilTest {
  @Test public void testGetFile() {
    String actual = FileReaderUtil.getFile("utils/sample_board.txt");

    String expected =
        "00000000\n"
            + "0WB00000\n"
            + "00WB0000\n"
            + "000BWB00\n"
            + "0000BW00\n"
            + "00000000\n"
            + "00000000\n"
            + "00000000\n";

    assertEquals(expected, actual);
  }
}