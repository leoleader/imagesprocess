package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.imageio.ImageIO;

import operations.ImageCommand;

/**
 * This is the data model representation for image files that are not ppms, specifically jpg, png
 * bpm, and any other file formats supported by javaIO.
 */
public class FileModel implements ImageModel {

  private final BufferedImage image;

  private final int width;

  private final int height;

  private final double[] blur =
          new double[]{.0625, .125, .0625, .125, .25, .125, .0625, .125, .0625};

  private final double[] sharpen =
          new double[]{-.125, -.125, -.125, -.125, -.125,
                          -.125, .25, .25, .25, -.125, -.125, .25, 1, .25, -.125,
                          -.125, .25, .25, .25, -.125, -.125, -.125, -.125, -.125, -.125};

  /**
   * This is the constructor for when a FileModel is provided a String for the filepath.
   * @param fileName the path that the file is located at.
   * @throws IOException if the inputs are invalid.
   */
  public FileModel(String fileName) throws IOException {
    File file = new File(fileName);
    BufferedImage first_try = ImageIO.read(file);
    this.width = first_try.getWidth();
    this.height = first_try.getHeight();
    BufferedImage real = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    int[] pixels = new int[width * height];
    first_try.getRGB(0, 0, width, height, pixels, 0, width);
    real.setRGB(0, 0, width, height, pixels, 0, width);

    this.image = real;

  }

  /**
   * This is the constructor for FileImage when it is provided a BufferedImage.
   * @param image the BufferedImage to create a model for
   */
  public FileModel(BufferedImage image) {
    this.image = image;
    this.width = image.getWidth();
    this.height = image.getHeight();
  }

  @Override
  public ImageModel convertBetween() {
    int[] pixels = new int[width * height];
    List<Integer> pixelList = new ArrayList<>();
    image.getRGB(0, 0, width, height, pixels, 0, width);
    for (int i: pixels) {
      pixelList.add((i >> 16) & 0xFF);
      pixelList.add((i >> 8) & 0xFF);
      pixelList.add(i  & 0xFF);
    }
    return new PPMImageModel("n/a", "n/a", height, width, 255, pixelList);
  }

  @Override
  public ImageModel runCommand(ImageCommand cmd) throws IOException {
    return cmd.command();
  }

  @Override
  public List<Integer> getImage() {
    return this.convertBetween().getImage();
  }

  @Override
  public int getHeight() {
    return image.getHeight();
  }

  @Override
  public int getWidth() {
    return image.getWidth();
  }

  @Override
  public ImageModel colorTransform(String type) throws IOException {
    BufferedImage transformed = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    double red;
    double green;
    double blue;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        int r = this.image.getRGB(i, j) >> 16 & 0xFF;
        int g = this.image.getRGB(i, j) >> 8 & 0xFF;
        int b = this.image.getRGB(i, j) & 0xFF;
        if (type.equals("sepia")) {
          red = (0.393 * r) + (0.769 * g) + (0.189 * b);
          green = (0.349 * r) + (0.686 * g) + (0.168 * b);
          blue = (0.272 * r) + (0.534 * g) + (0.131 * b);
        }
        else {
          double grey = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);
          red = grey;
          green = grey;
          blue = grey;
        }

        if (red > 255) {
          red = 255;
        }
        if (blue > 255) {
          blue = 255;
        }
        if (green > 255) {
          green = 255;
        }
        transformed.setRGB(i, j, ((int) red << 16 | (int) green << 8 |
                (int) blue));
      }
    }
    return new FileModel(transformed);
  }


  @Override
  public ImageModel filterImage(String filter) throws IOException {
    BufferedImage filtered = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    double[] matrix;
    if (filter.equals("blur")) {
      matrix = blur;
    }
    else {
      matrix = sharpen;
    }
    int dim = (int) Math.sqrt(matrix.length + 1);
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        filtered.setRGB(i, j, this.calcRGB(dim, i, j, matrix));
      }
    }
    return new FileModel(filtered);

  }

  /**
   * Creates a nxn matrix of pixel values based on the given dimensions and origin point.
   * @return a List of Integers that represents the list of values.
   */
  private List<Integer> makePixelMatrix(int dim, int originx, int originy, String component) {
    List<Integer> bruh = new ArrayList<>();
    for (int i = originx - ((dim - 1) / 2); i < (originx + ((dim + 1) / 2)); i++) {
      for (int j = originy - ((dim - 1) / 2); j < (originy + ((dim + 1) / 2)); j++) {
        if (-1 < i && i < width && -1 < j && j < height) {
          bruh.add(image.getRGB(i, j));
        }
        else {
          bruh.add(0);
        }
      }
    }
    if (component.equals("red")) {
      List<Integer> red = new ArrayList<>();
      for (int i: bruh) {
        red.add((i >> 16) & 0xFF);
      }
      return red;
    }
    if (component.equals("green")) {
      List<Integer> green = new ArrayList<>();
      for (int i: bruh) {
        green.add((i >> 8) & 0xFF);
      }
      return green;
    }
    if (component.equals("blue")) {
      List<Integer> blue = new ArrayList<>();
      for (int i: bruh) {
        blue.add((i) & 0xFF);
      }
      return blue;
    }
    else {
      return bruh;
    }
  }

  /**
   * Calculates the RGB value of the pixel at originx, originy after applying the given filter.
   * @param dim the size of the filter to be applied.
   * @param originx the x coordinate of the pixel.
   * @param originy the y coordinate of the pixel.
   * @param filter the array of values that will be used as a filter.
   * @return the RGB value after application.
   */
  private int calcRGB(int dim, int originx, int originy, double[] filter) {
    List<Integer> red = this.makePixelMatrix(dim, originx, originy, "red");
    double red_value = 0;
    for (int i = 0; i < red.size(); i++) {
      red_value = red_value + (red.get(i) * filter[i]);
    }
    List<Integer> blue = this.makePixelMatrix(dim, originx, originy, "blue");
    double blue_value = 0;
    for (int i = 0; i < blue.size(); i++) {
      blue_value = blue_value + (blue.get(i) * filter[i]);
    }
    List<Integer> green = this.makePixelMatrix(dim, originx, originy, "green");
    double green_value = 0;
    for (int i = 0; i < green.size(); i++) {
      green_value = green_value + (green.get(i) * filter[i]);
    }
    if (red_value > 255) {
      red_value = 255;
    }
    if (red_value < 0) {
      red_value = 0;
    }
    if (blue_value > 255) {
      blue_value = 255;
    }
    if (blue_value < 0) {
      blue_value = 0;
    }
    if (green_value > 255) {
      green_value = 255;
    }
    if (green_value < 0) {
      green_value = 0;
    }
    return ((int) red_value << 16 | (int) green_value << 8 | (int) blue_value);
  }

  @Override
  public BufferedImage getBuffImage() {
    return this.image;
  }

  @Override
  public ImageModel brighten(int value) throws IOException {
    ImageModel medium = this.convertBetween();
    ImageModel filtered = medium.brighten(value);
    return filtered.convertBetween();
  }

  @Override
  public ImageModel horizontalFlip() throws IOException {
    ImageModel medium = this.convertBetween();
    ImageModel filtered = medium.horizontalFlip();
    return filtered.convertBetween();
  }

  @Override
  public ImageModel verticalFlip() throws IOException {
    ImageModel medium = this.convertBetween();
    ImageModel filtered = medium.verticalFlip();
    return filtered.convertBetween();
  }

  @Override
  public ImageModel compGrey(String component) throws IOException {
    ImageModel medium = this.convertBetween();
    ImageModel filtered = medium.compGrey(component);
    return filtered.convertBetween();
  }

  @Override
  public String checkType() {
    return "file";
  }

  @Override
  public List<Integer> makeComponentList(String component) {
    List<Integer> bruh = new ArrayList<>();
    for (int i = 0; i < this.getWidth(); i++) {
      for (int j = 0; j < this.getHeight(); j++) {
        bruh.add(image.getRGB(i, j));
      }
    }
    if (component.equals("red")) {
      List<Integer> red = new ArrayList<>();
      for (int i: bruh) {
        red.add((i >> 16) & 0xFF);
      }
      return red;
    }
    if (component.equals("green")) {
      List<Integer> green = new ArrayList<>();
      for (int i: bruh) {
        green.add((i >> 8) & 0xFF);
      }
      return green;
    }
    if (component.equals("blue")) {
      List<Integer> blue = new ArrayList<>();
      for (int i: bruh) {
        blue.add((i) & 0xFF);
      }
      return blue;
    }
    if (component.equals("intensity")) {
      List<Integer> intensity = new ArrayList<>();
      for (int i: bruh) {
        int blue = i & 0xFF;
        int green = i >> 8 & 0xFF;
        int red = i >> 16 & 0xFF;
        intensity.add(blue + green + red / 3);
      }
      return intensity;
    }
    else {
      return bruh;
    }
  }

}
