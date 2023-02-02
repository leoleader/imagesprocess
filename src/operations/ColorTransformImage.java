package operations;

import java.io.IOException;
import java.util.Objects;

import model.ImageModel;

/**
 * Applies a chosen color transformation to the image.
 */
public class ColorTransformImage implements ImageCommand {

  private ImageModel img;

  private String filter;

  /**
   * The constructor for ColorTransformImage, initializes the img and transforms fields.
   * @param model The ImageModel that the operation will be applied to.
   * @param filter The chosen type of transform to be applied.
   */
  public ColorTransformImage(ImageModel model, String filter) {
    Objects.requireNonNull(model);
    this.img = model;
    this.filter = filter;
  }

  @Override
  public ImageModel command() throws IOException {
    return img.colorTransform(filter);
  }

}
