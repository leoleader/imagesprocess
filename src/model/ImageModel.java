package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import operations.ImageCommand;

/**
 * This is the interface for all the data model representations of images loaded into the program.
 */
public interface ImageModel {

  /**
   * Runs the given command.
   * @param cmd the command to be run.
   * @return the ImageModel created after the command is run on the given model.
   */
  ImageModel runCommand(ImageCommand cmd) throws IOException;

  /**
   * Gets the pixelList of the ImageModel.
   * @return the List of Integers that represents the individual RGB values of each pixel.
   */
  List<Integer> getImage();

  /**
   * Returns the height of the image.
   * @return height of the image.
   */
  int getHeight();

  /**
   * Returns the width of the image.
   * @return width of the image.
   */
  int getWidth();

  /**
   * Used when saving the file to distinguish between different ImageModels and save them correctly.
   * @return A String that represents the type of ImageModel.
   */
  String checkType();

  /**
   * Returns the Buffered Image of the ImageModel.
   * @return the BufferedImage.
   */
  BufferedImage getBuffImage();

  /**
   * brightens the image to create a new image.
   *
   */
  ImageModel brighten(int value) throws IOException;

  /**
   * horizontally flip the image to create a new image.
   *
   */
  ImageModel horizontalFlip() throws IOException;

  /**
   * vertically flip the image to create a new image.
   *
   */
  ImageModel verticalFlip() throws IOException;

  /**
   * This makes a greyscale image dependent on the given component.
   * @param component possible components are red, green, blue, luna, intensity, and value.
   * @return The greyscaled ImageModel.
   * @throws IOException if invalid inputs.
   */
  ImageModel compGrey(String component) throws IOException;

  /**
   * This applies a color transformation to the image.
   * @param type The type of color transformation to be applied.
   * @return The transformed ImageModel.
   * @throws IOException if inputs are invalid.
   */
  ImageModel colorTransform(String type) throws IOException;

  /**
   * Filters the image by applying a kernel to each channel of an image's pixels, the kernel
   * determined by the users input, the String filter.
   * @return A new ImageModel that has been filtered.
   * @throws IOException if inputs are invalid.
   */
  ImageModel filterImage(String filter) throws IOException;

  List<Integer> makeComponentList(String component);

  /**
   * Swaps the ImageModel between PPMImageModel and FileModel so that both functionalities can be
   * used and maintained.
   * @return A new ImageModel that has been converted.
   * @throws IOException if inputs are invalid.
   */
  ImageModel convertBetween() throws IOException;



}
