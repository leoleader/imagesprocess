package operations;

import java.io.IOException;
import java.util.Objects;

import model.ImageModel;

/**
 * This function object runs the operation which brightens/darkens the given ImageModels pixels
 * depending on the given integer value.
 */
public class BrightenImage implements ImageCommand {

  private ImageModel img;
  private int value;

  /**
   * The constructor for Brighten Image, initializes the img and value fields.
   * @param model The ImageModel that the operation will be applied to.
   * @param value The amount that the image will be brightened or darkened. 
   */
  public BrightenImage(ImageModel model, int value) {
    Objects.requireNonNull(model);
    this.img = model;
    this.value = value;
  }

  @Override
  public ImageModel command() throws IOException {
    return img.brighten(value);
  }

}