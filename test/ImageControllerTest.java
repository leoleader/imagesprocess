import org.junit.Test;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import controller.ImageController;
import model.FileModel;
import model.ImageModel;
import model.PPMImageModel;

import static org.junit.Assert.assertEquals;

/**
 * These are the tests for the ImageController, testing that the outputs are as expected and that
 * the controller reads the inputs correctly, throws when expected.
 */
public class ImageControllerTest {

  /**
   * This tests that the welcome message plays at the beginning and that the farewell message
   * plays when the user immediately quits.
   */
  @Test
  public void test_quitProgram() {
    Readable read = new StringReader("q");
    Appendable app = new StringBuffer();
    ImageController cont = new ImageController(read, app);

    String expected = "Welcome to the image processing program!\n" +
            "Supported user commands are: \n" +
            "load image-path image-name: Load an image from the specified path and refer it to "
            + "henceforth in the program by the given image name.\n" +
            "save image-path image-name: Save the image with the given name to the specified path "
            + "which should include the name of the file.\n" +
            "blur image-name dest-image-name: Blur the image with the given name and save it as " +
            "the " + "designated name\n" +
            "sharpen image-name dest-image-name: Sharpen the image with the given name and save " +
            "it " + "as the designated name\n" +
            "sepia image-name dest-image-name: Make the image with the given name sepia and save " +
            "it as the designated name\n" +
            "greyscale image-name dest-image-name: Greyscale the image with the given name and " +
            "save it as the designated name\n" +
            "component component-type image-name dest-image-name: Create a greyscale image with " +
            "one of the following components of the image with the given name, and refer to it " +
            "henceforth in the program by the given destination name. Component types are: red, " +
            "green, blue, value, luma,  and intensity.\n" +
            "horizontal-flip image-name dest-image-name: Flip an image horizontally to " +
            "create a new image, referred to henceforth by the given destination name.\n" +
            "vertical-flip image-name dest-image-name: Flip an image vertically to create a " +
            "new image, referred to henceforth by the given destination name.\n" +
            "brighten increment image-name dest-image-name: brighten the image by the given " +
            "increment to create a new image, referred to henceforth by the given destination " +
            "name. The increment may be positive (brightening) or negative (darkening)\n" +
            "menu (Print supported instruction list)\n" +
            "q or quit (quit the program) \n" +
            "Thank you for using this program!";
    cont.runProgram();

    Assert.assertEquals(expected, app.toString());
  }

  /**
   * This tests that the welcome message plays at the beginning and that the farewell message
   * plays when the user immediately quits.
   */
  @Test
  public void test_scriptError() {
    Readable read = new StringReader("loab q");
    Appendable app = new StringBuffer();
    ImageController cont = new ImageController(read, app);

    String expected = "Welcome to the image processing program!\n" +
            "Supported user commands are: \n" +
            "load image-path image-name: Load an image from the specified path and refer it to " +
            "henceforth in the program by the given image name.\n" +
            "save image-path image-name: Save the image with the given name to the specified path "
            + "which should include the name of the file.\n" +
            "blur image-name dest-image-name: Blur the image with the given name and save it as " +
            "the designated name\n" + "sharpen image-name dest-image-name: Sharpen " +
            "the image with the given name and save it as the designated name\n" +
            "sepia image-name dest-image-name: Make the image with the given name sepia and " +
            "save it as the designated name\n" +
            "greyscale image-name dest-image-name: Greyscale the image with the given name and s" +
            "ave it as the designated name\n" +
            "component component-type image-name dest-image-name: Create a greyscale image with o" +
            "ne of the following components of the image with the given name, and refer to it " +
            "henceforth in the program by the given destination name. Component types are: red, " +
            "green, blue, value, luma,  and intensity.\n" +
            "horizontal-flip image-name dest-image-name: Flip an image horizontally to create a " +
            "new image, referred to henceforth by the given destination name.\n" +
            "vertical-flip image-name dest-image-name: Flip an image vertically to create a new " +
            "image, referred to henceforth by the given destination name.\n" +
            "brighten increment image-name dest-image-name: brighten the image by the given " +
            "increment to create a new image, referred to henceforth by the given destination " +
            "name. The increment may be positive (brightening) or negative (darkening)\n" +
            "menu (Print supported instruction list)\n" +
            "q or quit (quit the program) \n" +
            "Undefined instruction: loab\n" +
            "Thank you for using this program!";
    cont.runProgram();

    Assert.assertEquals(expected, app.toString());
  }

  /**
   * Tests that the two types of ImageModels load into the program correctly. It uses
   * the getImage method to check these things. It also tests the getHeight and getWidth methods.
   * @throws IOException if the inputs are invalid.
   */
  @Test
  public void testLoadIn() throws IOException {
    Readable read = new StringReader("load images/testing.ppm ppm "
            + "load images/testing.png png");
    Appendable app = new StringBuffer();
    ImageController cont = new ImageController(read, app);

    cont.runProgram();

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 0, 0, 0, 255, 255, 255, 0, 0, 0, 255, 255, 255, 10, 10, 10, 70, 70,
      70, 85, 95, 75, 0, 0, 0, 100, 200, 0};
    for (int i: intArray) {
      expected.add(i);
    }

    assertEquals(cont.getBank().get("ppm").getHeight(), 3);
    assertEquals(cont.getBank().get("ppm").getWidth(), 3);
    assertEquals(cont.getBank().get("png").getWidth(), 3);
    assertEquals(cont.getBank().get("png").getHeight(), 3);

    assertEquals(expected, cont.getBank().get("ppm").getImage());
    assertEquals(expected, cont.getBank().get("png").convertBetween().getImage());
    assertEquals(cont.getBank().get("png").convertBetween().getImage(),
        cont.getBank().get("ppm").getImage());
  }

  /**
   * tests the command line to brighten an image, that controller properly parses input and creates
   * the expected image.
   */
  @Test
  public void testBrightenCommandLine() {
    Readable read = new StringReader("load images/testing.ppm tester "
            + "brighten 10 tester tester-brighter");
    Appendable app = new StringBuffer();
    ImageController cont = new ImageController(read, app);

    cont.runProgram();

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 10, 10, 10,   255, 255, 255,  10, 10, 10,
      255, 255,  255, 20, 20, 20,  80, 80, 80, 95, 105, 85,  10, 10, 10,
      110, 210, 10};
    for (int i: intArray) {
      expected.add(i);
    }

    assertEquals(expected, cont.getBank().get("tester-brighter").getImage());
  }

  /**
   * tests the command line to vertically flip an image.
   */
  @Test
  public void testVerticalFlipCommandLine() {
    Readable read = new StringReader("load images/testing.ppm tester "
            + "vertical-flip tester tester-vertical q");
    Appendable app = new StringBuffer();
    ImageController cont = new ImageController(read, app);

    cont.runProgram();

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 85, 95, 75, 0, 0, 0, 100, 200, 0, 255, 255, 255, 10, 10, 10, 70,
      70, 70, 0, 0, 0, 255, 255, 255, 0, 0, 0};
    for (int i: intArray) {
      expected.add(i);
    }

    assertEquals(expected, cont.getBank().get("tester-vertical").getImage());
  }

  /**
   * tests the command line to horizontally flip an image.
   */
  @Test
  public void testHorizontalFlipCommandLine() {
    Readable read = new StringReader("load images/testing.ppm tester "
            + "horizontal-flip tester tester-horizontal");
    Appendable app = new StringBuffer();
    ImageController cont = new ImageController(read, app);

    cont.runProgram();

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 0, 0, 0, 255, 255, 255, 0, 0, 0, 70, 70, 70, 10, 10, 10, 255,
      255, 255, 100, 200, 0, 0, 0, 0, 85, 95, 75};
    for (int i: intArray) {
      expected.add(i);
    }

    assertEquals(expected, cont.getBank().get("tester-horizontal").getImage());
  }

  /**
   * tests the command line to greyscale an image with red.
   */
  @Test
  public void testCompGreyCommandLine() {
    Readable read = new StringReader("load images/testing.ppm tester "
            + "component red tester tester-red");
    Appendable app = new StringBuffer();
    ImageController cont = new ImageController(read, app);

    cont.runProgram();

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 0, 0, 0, 255, 255, 255, 0, 0, 0, 255, 255, 255, 10, 10, 10, 70,
      70, 70, 85, 85, 85, 0, 0, 0, 100, 100, 100};
    for (int i: intArray) {
      expected.add(i);
    }

    assertEquals(expected, cont.getBank().get("tester-red").getImage());
  }


  /**
   * This tests to see that the saveFile method saves the given ImageModel correctly and to the
   * right place and is able to save to different file extensions. This test depends on and tests
   * the method convertBetween() as well to convert the file types.
   */
  @Test
  public void test_saveFile() throws IOException {
    Readable read = new StringReader("load images/sample.png bruh save images/sample.bmp bruh " +
            "load images/sample.ppm samp save images/switch.png samp q");
    Appendable app = new StringBuffer();
    ImageController cnt = new ImageController(read, app);
    cnt.runProgram();

    ImageModel model1 = new FileModel("images/sample.png");
    ImageModel model2 = new FileModel("images/sample.bmp");
    Assert.assertEquals(model1.getImage(), model2.getImage());
    File second = new File("images/sample.bmp");
    second.delete();

    ImageModel ppm = new PPMImageModel("images/sample.ppm", "samp");
    ImageModel png = new FileModel("images/switch.png");
    Assert.assertEquals(ppm.getImage(), png.convertBetween().getImage());
    File cleanup = new File("images/switch.png");
    cleanup.delete();

  }

  @Test
  public void test_nullThrows() {
    Readable read = new StringReader("load images/sample.png bruh save images/sample.bmp bruh " +
            "load images/sample.ppm samp save images/switch.png samp q");
    Appendable app = new StringBuffer();
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new ImageController(read, null));
    Assert.assertThrows(IllegalArgumentException.class,
        () -> new ImageController(null, app));

  }

}
