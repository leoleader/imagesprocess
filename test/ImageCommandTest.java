import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.FileModel;
import operations.BrightenImage;
import model.ImageModel;
import model.PPMImageModel;
import operations.ColorTransformImage;
import operations.FilterImage;
import operations.GreyImage;
import operations.HorizontalFlipImage;
import operations.ImageCommand;
import operations.VerticalFlipImage;

/**
 * These are the tests for all the various ImageCommands, brighten/darken, horizontal/vertical-flip,
 * the 6 different greyscales, and load image. Since these ImageCommands depend on their
 * corresponding ImageModel methods, examples being ImageModel.brighten() or ImageModel.redGrey(),
 * these tests also serve as tests for those methods, along with the private method groupPixels in
 * ImageModel because the two Flip ImageCommands are the only usages of that method and are
 * dependent on it working correctly.
 */
public class ImageCommandTest {
  /**
   * Tests the ImageCommand BrightenImage using both positive and negative values and
   * makes sure that rgb values cap at 0 and 225.
   */
  @Test
  public void test_BrightenImage() throws IOException {
    ImageModel model = new PPMImageModel("images/testing.ppm", "bruh");
    ImageCommand cmd = new BrightenImage(model, 10);

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 10, 10, 10,   255, 255, 255,  10, 10, 10,
      255, 255,  255, 20, 20, 20,  80, 80, 80,
      95, 105, 85,  10, 10, 10,  110, 210, 10, };
    for (int i: intArray) {
      expected.add(i);
    }

    Assert.assertEquals(cmd.command().getImage(), expected);

    ImageModel filemodel = new FileModel("images/testing.png");
    ImageCommand filecmd = new BrightenImage(filemodel, 10);

    List<Integer> expected_file = new ArrayList<Integer>();
    int[] intArray_file = new int[]{ 10, 10, 10,   255, 255, 255,  10, 10, 10,
      255, 255,  255, 20, 20, 20,  80, 80, 80,
      95, 105, 85,  10, 10, 10,  110, 210, 10, };
    for (int i: intArray_file) {
      expected_file.add(i);
    }

    Assert.assertEquals(filecmd.command().convertBetween().getImage(), expected);

    ImageModel model_dark = new PPMImageModel("images/testing.ppm", "bruhg");
    ImageCommand cmd_dark = new BrightenImage(model_dark, -10);

    List<Integer> expected_dark = new ArrayList<Integer>();
    int[] intArray_dark = new int[]{ 0, 0, 0,   245, 245, 245,  0, 0, 0,
      245, 245,  245, 0, 0, 0,  60, 60, 60,
      75, 85, 65,  0, 0, 0,  90, 190, 0 };
    for (int i: intArray_dark) {
      expected_dark.add(i);
    }

    Assert.assertEquals(cmd_dark.command().getImage(), expected_dark);

    ImageModel model_dark2 = new FileModel("images/testing.png");
    ImageCommand cmd_dark2 = new BrightenImage(model_dark2, -10);

    List<Integer> expected_dark2 = new ArrayList<Integer>();
    int[] intArray_dark2 = new int[]{ 0, 0, 0,   245, 245, 245,  0, 0, 0,
      245, 245,  245, 0, 0, 0,  60, 60, 60,
      75, 85, 65,  0, 0, 0,  90, 190, 0 };
    for (int i: intArray_dark2) {
      expected_dark2.add(i);
    }

    Assert.assertEquals(cmd_dark2.command().convertBetween().getImage(), expected_dark2);


  }

  /**
   * Tests the ImageCommand VerticalFlipImage, making sure that the list of pixels created is
   * as expected.
   */
  @Test
  public void test_VerticalFlip() throws IOException {
    ImageModel model = new PPMImageModel("images/testing.ppm", "bruh");
    ImageCommand cmd = new VerticalFlipImage(model);

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 85, 95, 75, 0, 0, 0, 100, 200, 0, 255, 255, 255, 10, 10, 10, 70,
      70, 70, 0, 0, 0, 255, 255, 255, 0, 0, 0};
    for (int i: intArray) {
      expected.add(i);
    }

    Assert.assertEquals(cmd.command().getImage(), expected);

    ImageModel model2 = new FileModel("images/testing.png");
    ImageCommand cmd2 = new VerticalFlipImage(model2);

    List<Integer> expected2 = new ArrayList<Integer>();
    int[] intArray2 = new int[]{ 85, 95, 75, 0, 0, 0, 100, 200, 0, 255, 255, 255, 10, 10, 10, 70,
      70, 70, 0, 0, 0, 255, 255, 255, 0, 0, 0};
    for (int i: intArray2) {
      expected2.add(i);
    }

    Assert.assertEquals(cmd2.command().convertBetween().getImage(), expected2);

  }

  /**
   * Tests the ImageCommand HorizontalFlipImage, making sure that the list of pixels created is
   * as expected.
   */
  @Test
  public void test_HorizontalFlip() throws IOException {
    ImageModel model = new PPMImageModel("images/testing.ppm", "bruh");
    ImageCommand cmd = new HorizontalFlipImage(model);

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 0, 0, 0, 255, 255, 255, 0, 0, 0, 70, 70, 70, 10, 10, 10, 255,
      255, 255, 100, 200, 0, 0, 0, 0, 85, 95, 75};
    for (int i: intArray) {
      expected.add(i);
    }

    Assert.assertEquals(cmd.command().getImage(), expected);

    ImageModel model2 = new FileModel("images/testing.png");
    ImageCommand cmd2 = new HorizontalFlipImage(model2);

    List<Integer> expected2 = new ArrayList<Integer>();
    int[] intArray2 = new int[]{ 0, 0, 0, 255, 255, 255, 0, 0, 0, 70, 70, 70, 10, 10, 10, 255,
      255, 255, 100, 200, 0, 0, 0, 0, 85, 95, 75};
    for (int i: intArray2) {
      expected2.add(i);
    }

    Assert.assertEquals(cmd2.command().convertBetween().getImage(), expected2);

  }

  /**
   * Tests the ImageCommand GreyImage, specifically that when the user selects the "red" component
   * that it generates the correct list of pixel values.
   */
  @Test
  public void test_RedGrey() throws IOException {
    ImageModel model = new PPMImageModel("images/testing.ppm", "bruh");
    ImageCommand cmd = new GreyImage(model, "red");

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 0, 0, 0, 255, 255, 255, 0, 0, 0, 255, 255, 255, 10, 10, 10, 70,
      70, 70, 85, 85, 85, 0, 0, 0, 100, 100, 100};
    for (int i: intArray) {
      expected.add(i);
    }

    Assert.assertEquals(cmd.command().getImage(), expected);
  }

  /**
   * Tests the ImageCommand GreyImage, specifically that when the user selects the "blue" component
   * that it generates the correct list of pixel values.
   */
  @Test
  public void test_BlueGrey() throws IOException {
    ImageModel model = new PPMImageModel("images/testing.ppm", "bruh");
    ImageCommand cmd = new GreyImage(model, "blue");

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 0, 0, 0, 255, 255, 255, 0, 0, 0, 255, 255, 255, 10, 10, 10, 70,
      70, 70, 75, 75, 75, 0, 0, 0, 0, 0, 0};
    for (int i: intArray) {
      expected.add(i);
    }

    Assert.assertEquals(cmd.command().getImage(), expected);
  }

  /**
   * Tests the ImageCommand GreyImage, specifically that when the user selects the "green" component
   * that it generates the correct list of pixel values.
   */
  @Test
  public void test_GreenGrey() throws IOException {
    ImageModel model = new PPMImageModel("images/testing.ppm", "bruh");
    ImageCommand cmd = new GreyImage(model, "green");

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 0, 0, 0, 255, 255, 255, 0, 0, 0, 255, 255, 255, 10, 10, 10, 70,
      70, 70, 95, 95, 95, 0, 0, 0, 200, 200, 200};
    for (int i: intArray) {
      expected.add(i);
    }

    Assert.assertEquals(cmd.command().getImage(), expected);
  }

  /**
   * Tests the ImageCommand GreyImage, specifically that when the user selects the "value" component
   * that it generates the correct list of pixel values.
   */
  @Test
  public void test_ValueGrey() throws IOException {
    ImageModel model = new PPMImageModel("images/testing.ppm", "bruh");
    ImageCommand cmd = new GreyImage(model, "value");

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 0, 0, 0, 255, 255, 255, 0, 0, 0, 255, 255, 255, 10, 10, 10, 70,
      70, 70, 95, 95, 95, 0, 0, 0, 200, 200, 200};
    for (int i: intArray) {
      expected.add(i);
    }

    Assert.assertEquals(cmd.command().getImage(), expected);
  }

  /**
   * Tests the ImageCommand GreyImage, specifically that when the user selects the "intensity"
   * component that it generates the correct list of pixel values.
   */
  @Test
  public void test_IntensityGrey() throws IOException {
    ImageModel model = new PPMImageModel("images/testing.ppm", "bruh");
    ImageCommand cmd = new GreyImage(model, "intensity");

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 0, 0, 0, 255, 255, 255, 0, 0, 0, 255, 255, 255, 10, 10, 10, 70,
      70, 70, 85, 85, 85, 0, 0, 0, 100, 100, 100};
    for (int i: intArray) {
      expected.add(i);
    }

    Assert.assertEquals(cmd.command().getImage(), expected);
  }

  /**
   * Tests the ImageCommand GreyImage, specifically that when the user selects the "luna" component
   * that it generates the correct list of pixel values.
   */
  @Test
  public void test_LunaGrey() throws IOException {
    ImageModel model = new PPMImageModel("images/testing.ppm", "bruh");
    ImageCommand cmd = new GreyImage(model, "luna");

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 0, 0, 0, 255, 255, 255, 0, 0, 0, 255, 255, 255, 10, 10, 10, 70,
      70, 70, 91, 91, 91, 0, 0, 0, 164, 164, 164};
    for (int i: intArray) {
      expected.add(i);
    }

    Assert.assertEquals(cmd.command().getImage(), expected);
  }


  /**
   * Tests the ImageCommand GreyScaleImage,
   * that it generates the correct list of pixel values.
   */
  @Test
  public void test_GreyScaleImage() throws IOException {
    ImageModel model = new FileModel("images/sample.png");
    ImageCommand cmd = new ColorTransformImage(model, "greyscale");

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 65, 65, 65, 124, 124, 124, 199, 199, 199, 170, 170, 170,
      119, 119, 119, 67, 67, 67, 130, 130, 130, 96, 96, 96, 211, 211, 211, 90, 90, 90,
      134, 134, 134, 140, 140, 140, 163, 163, 163, 64, 64, 64, 80, 80, 80, 203, 203, 203,
      103, 103, 103, 181, 181, 181, 104, 104, 104, 201, 201, 201, 160, 160, 160, 142, 142,
      142, 126, 126, 126, 23, 23, 23, 96, 96, 96 };

    for (int i: intArray) {
      expected.add(i);
    }

    Assert.assertEquals(cmd.command().convertBetween().getImage(), expected);

    ImageModel model2 = new PPMImageModel("images/sample.ppm", "bruh");
    ImageCommand cmd2 = new ColorTransformImage(model2, "greyscale");

    List<Integer> expected2 = new ArrayList<Integer>();
    int[] intArray2 = new int[]{ 65, 65, 65, 124, 124, 124, 199, 199, 199, 170, 170, 170,
      119, 119, 119, 67, 67, 67, 130, 130, 130, 96, 96, 96, 211, 211, 211, 90, 90, 90,
      134, 134, 134, 140, 140, 140, 163, 163, 163, 64, 64, 64, 80, 80, 80, 203, 203, 203,
      103, 103, 103, 181, 181, 181, 104, 104, 104, 201, 201, 201, 160, 160, 160, 142, 142, 142,
      126, 126, 126, 23, 23, 23, 96, 96, 96 };

    for (int i: intArray2) {
      expected2.add(i);
    }

    Assert.assertEquals(cmd2.command().getImage(), expected2);
  }


  /**
   * Tests the ImageCommand SepiaScaleImage,
   * that it generates the correct list of pixel values.
   */
  @Test
  public void test_SepiaImage() throws IOException {
    ImageModel model = new FileModel("images/sample.png");
    ImageCommand cmd = new ColorTransformImage(model, "sepia");

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 85, 76, 59, 168, 150, 117, 255, 233, 181, 222, 198, 154, 161, 143,
      111, 106, 94, 73, 154, 137, 107, 130, 116, 90, 255, 255, 199, 109, 97, 75, 170, 151, 118,
      168, 150, 117, 218, 194, 151, 109, 97, 75, 147, 130, 102, 255, 238, 185, 160, 142, 110,
      249, 221, 172, 158, 141, 110, 249, 222, 173, 185, 165, 128, 171, 153, 119, 177, 157, 123,
      36, 32, 25, 160, 143, 111};

    for (int i: intArray) {
      expected.add(i);
    }

    Assert.assertEquals(cmd.command().convertBetween().getImage(), expected);

    ImageModel model2 = new PPMImageModel("images/sample.ppm", "bruh");
    ImageCommand cmd2 = new ColorTransformImage(model2, "sepia");

    List<Integer> expected2 = new ArrayList<Integer>();
    int[] intArray2 = new int[]{ 85, 76, 59, 168, 150, 117, 255, 233, 181, 222, 198, 154, 161,
      143, 111, 106, 94, 73, 154, 137, 107, 130, 116, 90, 255, 255, 199, 109, 97, 75, 170, 151,
      118, 168, 150, 117, 218, 194, 151, 109, 97, 75, 147, 130, 102, 255, 238, 185, 160, 142, 110,
      249, 221, 172, 158, 141, 110, 249, 222, 173, 185, 165, 128, 171, 153, 119, 177, 157,
      123, 36, 32, 25, 160, 143, 111};

    for (int i: intArray2) {
      expected2.add(i);
    }

    Assert.assertEquals(cmd2.command().getImage(), expected2);
  }

  /**
   * Tests the ImageCommand FilterImage,
   * that it generates the correct list of pixel values depending on the given filter type.
   */
  @Test
  public void test_FilterImage() throws IOException {
    ImageModel model = new FileModel("images/sample.png");
    ImageCommand cmd = new FilterImage(model, "blur");

    List<Integer> expected = new ArrayList<Integer>();
    int[] intArray = new int[]{ 52, 47, 43, 103, 90, 47, 130, 121, 70, 101, 126, 119, 41, 82, 104,
      57, 78, 83, 95, 131, 92, 146, 146, 105, 151, 136, 149, 81, 87, 124, 88, 105, 76, 107,
      145, 120, 135, 134, 147, 161, 105, 160, 115, 71, 123, 108, 126, 67, 144, 148, 120, 147,
      123, 154, 145, 99, 161, 113, 81, 122, 56, 104, 47, 100, 115, 66, 110, 82, 78, 93, 60,
      101, 75, 50, 94};

    for (int i: intArray) {
      expected.add(i);
    }

    Assert.assertEquals(cmd.command().convertBetween().getImage(), expected);

    ImageModel modelppm = new PPMImageModel("images/sample.ppm", "bruh");
    ImageCommand cmdppm = new FilterImage(modelppm, "blur");

    List<Integer> expectedppm = new ArrayList<Integer>();
    int[] intArrayppm = new int[]{ 52, 47, 43, 103, 90, 47, 130, 121, 70, 101, 126, 119, 41, 82,
      104, 57, 78, 83, 95, 131, 92, 146, 146, 105, 151, 136, 149, 81, 87, 124, 88, 105, 76,
      107, 145, 120, 135, 134, 147, 161, 105, 160, 115, 71, 123, 108, 126, 67, 144, 148, 120,
      147, 123, 154, 145, 99, 161, 113, 81, 122, 56, 104, 47, 100, 115, 66, 110, 82, 78, 93,
      60, 101, 75, 50, 94 };

    for (int i: intArrayppm) {
      expectedppm.add(i);
    }

    Assert.assertEquals(cmdppm.command().getImage(), expectedppm);

    ImageCommand cmd2 = new FilterImage(model, "sharpen");

    List<Integer> expected2 = new ArrayList<Integer>();
    int[] intArray2 = new int[]{ 53, 52, 67, 236, 141, 26, 255, 255, 78, 184, 255, 249, 23, 191,
      255, 43, 105, 181, 108, 255, 141, 255, 235, 60, 255, 255, 255, 89, 137, 221, 106, 150,
      113, 127, 255, 219, 160, 165, 250, 255, 128, 255, 222, 50, 252, 255, 255, 96, 255, 255,
      246, 244, 197, 255, 255, 161, 255, 200, 175, 225, 60, 225, 79, 210, 255, 108, 218, 81, 70,
      126, 102, 187, 153, 74, 223 };

    for (int i: intArray2) {
      expected2.add(i);
    }

    Assert.assertEquals(cmd2.command().convertBetween().getImage(), expected2);
  }

}
