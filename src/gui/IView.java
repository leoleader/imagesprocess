package gui;

import java.awt.image.BufferedImage;

import model.ImageModel;

/**
 * Interface for our GUI view class with all the functionality necessary.
 */
public interface IView {

  /**
   * Updates the program's response text box to inform the user of necessary error/completion
   * information.
   * @param message The message to be displayed to the user.
   */
  void updateResponse(String message);

  /**
   * Adds the necessary features to the correct elements of the GUIView.
   * @param features the Controller with the features that will be inherited.
   */
  void addFeatures(Features features);

  /**
   * Displays the current Image being worked on in the GUIView.
   * @param buffImage the Image to be displayed.
   */
  void displayImage(BufferedImage buffImage);

  /**
   * Updates the Histogram JPanel to match the image after whatever operation has been performed.
   * @param model The ImageModel to update the Histogram from.
   */
  void updateHistogram(ImageModel model);

  /**
   * Allows the user to choose a file to load in as an image.
   * @return The file path of the chosen file.
   */
  String chooseFile();

  /**
   * Allows the user to choose a filepath to save the image as.
   * @return The chosen file path to save the image to.
   */
  String saveFile();

  /**
   * Creates a pop up dialog box that requests the user's input as a String.
   * @param message the question to be displayed when asking the user for input.
   * @return the input of the user.
   */
  String getInput(String message);

}
