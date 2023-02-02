package gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.FileModel;
import model.ImageModel;
import operations.BrightenImage;
import operations.ColorTransformImage;
import operations.FilterImage;
import operations.GreyImage;
import operations.HorizontalFlipImage;
import operations.ImageCommand;
import operations.VerticalFlipImage;

/**
 * This is the controller for the GUI View, I made the design decision that the functions of the
 * GUIView were different enough to need its own controller.
 */
public class GUIController implements Features {

  private IView view;

  private ImageModel image;

  /**
   * This is the constructor for the GUIController, it requires a IView to send it's features to
   * and work with to build the GUI. 
   */
  public GUIController(IView view) {
    this.setView(view);
  }

  public void setView(IView view) {
    this.view = view;
    view.addFeatures(this);
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void loadImage() throws IOException {
    String file_path = view.chooseFile();
    System.out.println(file_path);
    this.image = new FileModel(file_path);
    view.displayImage(image.getBuffImage());
    view.updateHistogram(image);
    view.updateResponse("Loaded image " + file_path + "!");
  }

  @Override
  public void saveImage() throws IOException {
    String file_path = view.saveFile();
    String format = this.getFormat(file_path);
    ImageIO.write(image.getBuffImage(), format, new File(file_path));
    view.updateResponse("Saved image to " + file_path + "!");
  }

  @Override
  public void brightenImage() throws IOException {
    int ret = 0;
    String input = view.getInput("How much do you want to brighten the image?");
    try {
      ret = Integer.parseInt(input);
    } catch (IllegalArgumentException e) {
      view.updateResponse("That was not a valid value, try again!");
      return;
    }
    ImageCommand cmd = new BrightenImage(image, ret);
    image = image.runCommand(cmd);
    view.displayImage(image.getBuffImage());
    view.updateHistogram(image);
    view.updateResponse("Brightened by " + ret + "!");
  }

  @Override
  public void flipImage() throws IOException {
    String input = view.getInput("Enter 'vertical' for a vertical flip or 'horizontal' " +
            "for a horizontal flip ");
    ImageCommand cmd;
    if (input.equals("vertical")) {
      cmd = new VerticalFlipImage(image);
    }
    else if (input.equals("horizontal")) {
      cmd = new HorizontalFlipImage(image);
    }
    else {
      view.updateResponse("That was not a valid input, try again!");
      return;
    }
    image = image.runCommand(cmd);
    view.displayImage(image.getBuffImage());
    view.updateHistogram(image);
    view.updateResponse("Flipped image!");
  }

  @Override
  public void greyscaleImage() throws IOException {
    String input = view.getInput("Enter the component you want to greyscale with, either 'red'" +
            "'blue', 'green', 'luna', 'intensity', 'value'.");
    ImageCommand cmd = new GreyImage(image, input);
    image = image.runCommand(cmd);
    view.displayImage(image.getBuffImage());
    view.updateHistogram(image);
    view.updateResponse("Greyscaled the image!");

  }

  @Override
  public void filterImage() throws IOException {
    String input = view.getInput("Enter 'blur' for a blur or 'sharpen' " +
            "to sharpen the image ");
    ImageCommand cmd;
    if (input.equals("blur") || input.equals("sharpen")) {
      cmd = new FilterImage(image, input);
    }
    else {
      view.updateResponse("That was not a valid input, try again!");
      return;
    }
    image = image.runCommand(cmd);
    view.displayImage(image.getBuffImage());
    view.updateHistogram(image);
    view.updateResponse("Filtered image!");
  }

  @Override
  public void transformImage() throws IOException {
    String input = view.getInput("Enter 'greyscale' for a  greyscale or 'sepia' " +
            "to make the image sepia");
    ImageCommand cmd;
    if (input.equals("greyscale") || input.equals("sepia")) {
      cmd = new ColorTransformImage(image, input);
    }
    else {
      view.updateResponse("That was not a valid input, try again!");
      return;
    }
    image = image.runCommand(cmd);
    view.displayImage(image.getBuffImage());
    view.updateHistogram(image);
    view.updateResponse("Filtered image!");
  }


  private String getFormat(String filepath) {

    int i = filepath.lastIndexOf('.');
    if (i > 0) {
      return filepath.substring(i + 1);
    }
    else {
      return "";
    }
  }

}
