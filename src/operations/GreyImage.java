package operations;

import java.io.IOException;
import java.util.Objects;

import model.ImageModel;

/**
 * This function object runs the greyscale method in the ImageModel using the given component.
 */
public class GreyImage implements ImageCommand {

  private ImageModel img;
  private String component;

  /**
   * The constructor for GreyImage, initializes the img and component fields.
   * @param img The ImageModel that the operation will be applied to.
   * @param component The type of component to be used for the greyscale operation.
   */
  public GreyImage(ImageModel img, String component) {
    Objects.requireNonNull(img);
    this.component = component;
    this.img = img;
  }

  @Override
  public ImageModel command() throws IOException {
    return img.compGrey(component);
  }
}
