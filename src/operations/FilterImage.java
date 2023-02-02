package operations;

import java.io.IOException;
import java.util.Objects;

import model.ImageModel;

/**
 * This function object runs the operation which filters the pixels of the ImageModel to make it
 * blurred, sharpened, depending on the filter input.
 */
public class FilterImage implements ImageCommand {

  private ImageModel img;

  private String filter;

  /**
   * The constructor for SepiaImage, initializes the img and filter fields.
   * @param model The ImageModel that the operation will be applied to.
   * @param filter The chosen type of filter to be applied.
   */
  public FilterImage(ImageModel model, String filter) {
    Objects.requireNonNull(model);
    this.img = model;
    this.filter = filter;
  }

  @Override
  public ImageModel command() throws IOException {
    return img.filterImage(filter);
  }


}
