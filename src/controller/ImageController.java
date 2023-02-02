package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import model.ImageModel;
import operations.BrightenImage;
import operations.ColorTransformImage;
import operations.FilterImage;
import operations.GreyImage;
import operations.HorizontalFlipImage;
import operations.ImageCommand;
import operations.LoadImage;
import operations.VerticalFlipImage;

/**
 * This is the controller for our image processing program, it interacts between the users inputs
 * and runs commands to create new image models and outputs responses to the user.
 */
public class ImageController {
  private Readable readable;
  private Appendable appendable;

  /**
   * This field is where all the images loaded in the program are going to be stored.
   */
  private TreeMap<String, ImageModel> bank;

  /**
   * This is the constructor for the controller, checks that the inputs aren't null and initializes
   * the "bank".
   * @param readable This is what the controller will be reading input from.
   * @param appendable This is what the controller will be sending output to.
   */
  public ImageController(Readable readable, Appendable appendable) {
    if ((readable == null) || (appendable == null)) {
      throw new IllegalArgumentException("Readable or appendable is null");
    }
    this.appendable = appendable;
    this.readable = readable;
    this.bank = new TreeMap<String, ImageModel>();

  }

  /**
   * The main method that relinquishes control of the application to the controller.
   * @throws IllegalStateException if the controller is unable to transmit output
   */
  public void runProgram() throws IllegalStateException {
    Scanner sc = new Scanner(readable);
    boolean quit = false;


    //print the welcome message
    this.welcomeMessage();

    while (!quit && sc.hasNext()) { //continue until the user quits
      String userCommand = sc.next(); //take an instruction name
      if (userCommand.equals("quit") || userCommand.equals("q")) {
        quit = true;
      }
      else {
        processCommand(userCommand, sc);
      }
    }

    //after the user has quit, print farewell message
    this.farewellMessage();

  }

  protected void processCommand(String userCommand, Scanner sc) {

    switch (userCommand) {
      case "load": //loads the file as an ImageModel
        try {
          String filename = sc.next();
          String givenname = sc.next();
          ImageCommand cmd = new LoadImage(filename, givenname);
          ImageModel img_to_load = cmd.command();
          bank.put(givenname, img_to_load);
          writeMessage("Loaded image " + givenname + System.lineSeparator());
        } catch (IllegalArgumentException e) {
          writeMessage("Error: " + e.getMessage() + System.lineSeparator());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        break;
      case "save": //saves an ImageModel as a file
        try {
          String filename = sc.next();
          String givenname = sc.next();
          try {
            ImageModel img_to_save = bank.get(givenname);
            this.saveFile(filename, img_to_save);
            writeMessage("Saved image " + givenname + " to " + filename +
                    System.lineSeparator());
          } catch (NullPointerException e) {
            writeMessage("Try again with correct filepath and chosen loaded image"
                    + System.lineSeparator());
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        } catch (IllegalArgumentException e) {
          writeMessage("Error: " + e.getMessage() + System.lineSeparator());
        }
        break;
      case "brighten":
        try {
          int brighten_value = sc.nextInt();
          String givenname = sc.next();
          String newname = sc.next();
          ImageModel img_to_brighten = bank.get(givenname);
          ImageCommand cmd = new BrightenImage(img_to_brighten, brighten_value);
          bank.put(newname, img_to_brighten.runCommand(cmd));
          writeMessage("Brightened image " + givenname + " by " + brighten_value +
                  " and named it " + newname + System.lineSeparator());
        } catch (InputMismatchException e) {
          writeMessage("That was not valid, try again.");
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        break;
      case "menu": //print the menu of supported instructions
        welcomeMessage();
        break;
      case "horizontal-flip":
        try {
          String givenname = sc.next();
          String newname = sc.next();
          ImageModel img_to_flip = bank.get(givenname);
          ImageCommand cmd = new HorizontalFlipImage(img_to_flip);
          bank.put(newname, img_to_flip.runCommand(cmd));
          writeMessage("Flipped image " + givenname + " horizontally and named it "
                  + newname + System.lineSeparator());
        } catch (InputMismatchException e) {
          writeMessage("That was not valid, try again.");
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        break;
      case "vertical-flip":
        try {
          String givenname = sc.next();
          String newname = sc.next();
          ImageModel img_to_flip = bank.get(givenname);
          ImageCommand cmd = new VerticalFlipImage(img_to_flip);
          bank.put(newname, cmd.command());
          writeMessage("Flipped image " + givenname + " vertically and named it "
                  + newname + System.lineSeparator());
        } catch (InputMismatchException e) {
          writeMessage("That was not valid, try again.");
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        break;
      case "component":
        try {
          String component = sc.next();
          String givenname = sc.next();
          String newname = sc.next();
          ImageModel img = bank.get(givenname);
          ImageCommand cmd = new GreyImage(img, component);
          bank.put(newname, cmd.command());
          writeMessage("Made " + givenname + " greyscale using " + component +
                  " and named it " + newname + System.lineSeparator());
        } catch (InputMismatchException e) {
          writeMessage("That was not valid, try again.");
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        break;
      case "blur":
        try {
          String givenname = sc.next();
          String newname = sc.next();
          ImageModel img = bank.get(givenname);
          ImageCommand cmd = new FilterImage(img, "blur");
          bank.put(newname, cmd.command());
          writeMessage("Blurred " + givenname +
                  " and named it " + newname + System.lineSeparator());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        break;
      case "sharpen":
        try {
          String givenname = sc.next();
          String newname = sc.next();
          ImageModel img = bank.get(givenname);
          ImageCommand cmd = new FilterImage(img, "sharpen");
          bank.put(newname, cmd.command());
          writeMessage("Sharpened " + givenname +
                  " and named it " + newname + System.lineSeparator());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        break;
      case "sepia":
        try {
          String givenname = sc.next();
          String newname = sc.next();
          ImageModel img = bank.get(givenname);
          ImageCommand cmd = new ColorTransformImage(img, "sepia");
          bank.put(newname, cmd.command());
          writeMessage("Made " + givenname + " sepia" +
                  " and named it " + newname + System.lineSeparator());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        break;
      case "greyscale":
        try {
          String givenname = sc.next();
          String newname = sc.next();
          ImageModel img = bank.get(givenname);
          ImageCommand cmd = new ColorTransformImage(img, "greyscale");
          bank.put(newname, cmd.command());
          writeMessage("Made " + givenname + " greyscale" +
                  " and named it " + newname + System.lineSeparator());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        break;
      default: //error due to unrecognized instruction
        writeMessage("Undefined instruction: " + userCommand + System.lineSeparator());
    }
  }

  /**
   * Saves the image by determining what kind of file it is and what kind of file it is being
   * saved as.
   * @param filepath The filepath that the image will be saved to.
   * @param model The ImageModel being saved.
   * @throws IOException if inputs are invalid.
   */
  private void saveFile(String filepath, ImageModel model) throws IOException {
    boolean convert = true;
    if (filepath.endsWith("ppm") && model.checkType().equals("ppm")) {
      try {
        FileWriter myWriter = new FileWriter(filepath);
        myWriter.write("P3" + "\n");
        myWriter.write(model.getWidth() + " " + model.getHeight() + "\n");
        myWriter.write(255 + "\n");
        for (int i = 0; i < model.getImage().size(); i++) {
          myWriter.write(model.getImage().get(i) + " ");
        }
        myWriter.close();
        System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      convert = false;
    }
    if (!(filepath.endsWith("ppm")) && model.checkType().equals("file")) {
      String format = this.getFormat(filepath);
      ImageIO.write(model.getBuffImage(), format, new File(filepath));
      System.out.println("Successfully wrote to the file.");
      convert = false;
    }
    else if (convert) {
      ImageModel converted = model.convertBetween();
      this.saveFile(filepath, converted);
    }
  }

  /**
   * Determines the file extension of the given filepath.
   * @param filepath the filepath of the file as a String.
   * @return a String that is the file extension of the file.
   */
  private String getFormat(String filepath) {

    int i = filepath.lastIndexOf('.');
    if (i > 0) {
      return filepath.substring(i + 1);
    }
    else {
      return "";
    }
  }


  protected void writeMessage(String message) throws IllegalStateException {
    try {
      appendable.append(message);

    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  protected void printMenu() throws IllegalStateException {
    writeMessage("Supported user commands are: " + System.lineSeparator());
    writeMessage("load image-path image-name: Load an image from the specified path and refer it "
            + "to henceforth in the program by the given image name."
            + System.lineSeparator());
    writeMessage("save image-path image-name: Save the image with the given name to" +
            " the specified path which should include the name of the file."
            + System.lineSeparator());
    writeMessage("blur image-name dest-image-name: Blur the image with the given name and" +
            " save it as the designated name"
            + System.lineSeparator());
    writeMessage("sharpen image-name dest-image-name: Sharpen the image with the given name and" +
            " save it as the designated name"
            + System.lineSeparator());
    writeMessage("sepia image-name dest-image-name: Make the image with the given name sepia and" +
            " save it as the designated name"
            + System.lineSeparator());
    writeMessage("greyscale image-name dest-image-name: Greyscale the image with the given name and"
            + " save it as the designated name" + System.lineSeparator());
    writeMessage("component component-type image-name dest-image-name: Create a greyscale image " +
            "with " + "one of the following components of the image with the given name, and " +
            "refer to it henceforth " + "in the program by the given destination name. " +
            "Component types are: red, green, blue, " + "value, luma,  " +
            "and intensity." + System.lineSeparator());
    writeMessage("horizontal-flip image-name dest-image-name: Flip an image horizontally " +
            "to create a new image, referred to henceforth by the given destination name."
            + System.lineSeparator());
    writeMessage("vertical-flip image-name dest-image-name: Flip an image vertically " +
            "to create a new image, referred to henceforth by the given destination name."
            + System.lineSeparator());
    writeMessage("brighten increment image-name dest-image-name: brighten the " +
            "image by the given increment to create a new image, referred to henceforth " +
            "by the given destination name. The increment may be positive (brightening)" +
            " or negative (darkening)" + System.lineSeparator());
    writeMessage("menu (Print supported instruction list)" + System.lineSeparator());
    writeMessage("q or quit (quit the program) " + System.lineSeparator());
  }

  protected void welcomeMessage() throws IllegalStateException {
    writeMessage("Welcome to the image processing program!" + System.lineSeparator());
    printMenu();
  }

  protected void farewellMessage() throws IllegalStateException {
    writeMessage("Thank you for using this program!");
  }

  /**
   * This method is just for testing purposes.
   * @return the bank of the ImageController.
   */
  public TreeMap<String, ImageModel> getBank() {
    return this.bank;
  }


}
