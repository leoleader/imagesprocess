package gui;

import java.io.IOException;

/**
 * These are the features that will be implemented in the GUIController.
 */
public interface Features {

  /**
   * Allows the user to exit the program.
   */
  void exitProgram();

  /**
   * Loads and creates the ImageModel and then tells the view to display the correct information.
   * @throws IOException if inputs are invalid.
   */
  void loadImage() throws IOException;

  /**
   * Saves the ImageModel and then tells the view to display the correct information.
   * @throws IOException if inputs are invalid.
   */
  void saveImage() throws IOException;

  /**
   * Brightens the ImageModel and then tells the view to display the correct information.
   * @throws IOException if inputs are invalid.
   */
  void brightenImage() throws IOException;

  /**
   * Flips the ImageModel and then tells the view to display the correct information.
   * @throws IOException if inputs are invalid.
   */
  void flipImage() throws IOException;

  /**
   * Greyscales the ImageModel and then tells the view to display the correct information.
   * @throws IOException if inputs are invalid.
   */
  void greyscaleImage() throws IOException;

  /**
   * Filters the ImageModel and then tells the view to display the correct information.
   * @throws IOException if inputs are invalid.
   */
  void filterImage() throws IOException;

  /**
   * Color transforms the ImageModel and then tells the view to display the correct information.
   * @throws IOException if inputs are invalid.
   */
  void transformImage() throws IOException;

}
