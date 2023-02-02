package operations;

import java.io.IOException;
import java.util.Objects;

import model.ImageModel;

/**
 * This function object runs the operation which flips the images pixels horizontally.
 */
public class HorizontalFlipImage implements ImageCommand {

  private ImageModel img;

  /**
   * The constructor for HorizontalFlipImage, initializes the img field.
   * @param model the Image Model that the operation will be applied to.
   */
  public HorizontalFlipImage(ImageModel model) {

    Objects.requireNonNull(model);
    this.img = model;
  }

  @Override
  public ImageModel command() throws IOException {
    return img.horizontalFlip();
  }

}
