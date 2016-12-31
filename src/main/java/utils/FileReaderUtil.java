package utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Reading file util.
 */
public class FileReaderUtil {

  /**
   * Read file from resource folder.
   */
  public static String getFile(String fileName) {

    StringBuilder result = new StringBuilder("");

    ClassLoader classLoader = FileReaderUtil.class.getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());

    try (Scanner scanner = new Scanner(file)) {

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        result.append(line).append("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return result.toString();
  }
}
