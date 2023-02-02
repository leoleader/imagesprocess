package operations;

import java.io.IOException;
import java.util.Objects;

import model.ImageModel;

/**
 * This function object runs the operation which flips the image's pixels vertically.
 */
public class VerticalFlipImage implements ImageCommand {

  private ImageModel img;

  public VerticalFlipImage(ImageModel model) {
    Objects.requireNonNull(model);
    this.img = model;
  }

  @Override
  public ImageModel command() throws IOException {
    return img.verticalFlip();
  }

}

