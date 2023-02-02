import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

import controller.ImageController;
import gui.GUIController;
import gui.GUIView;
import gui.IView;

/**
 * This class is so that the program can be run in the terminal and the user can input commands
 * and edit images.
 */
public final class ImageProcessor {

  /**
   * This is the main method that allows the program to be run in the terminal.
   * @param args The incoming arguments.
   */
  public static void main(String[] args) {
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;

    if (args.length > 1) {
      System.out.println(args[0] + " " + args[1]);
      Scanner sc;

      try {
        sc = new Scanner(new FileInputStream(args[1]));
      }
      catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Sorry that is an invalid file, try again.");
      }

      String bruh = "";

      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        bruh = bruh + (s + System.lineSeparator());
      }
      ImageController control = new ImageController(new StringReader(bruh), ap);
      control.runProgram();
    }

    else if (args.length == 1) {
      ImageController control = new ImageController(rd, ap);
      control.runProgram();
    }

    //This is where the GUIController will go wahahha
    else if (args.length == 0) {
      IView view = new GUIView("Image Processing Program");
      GUIController controller = new GUIController(view);
    }

  }
}
