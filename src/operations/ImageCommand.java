package operations;

import java.io.IOException;

import model.ImageModel;

/**
 * This is the interface for an Image Command, which is tasked with running a specific operation
 * on the given ImageModel and returning a new ImageModel with the operation applied.
 */
public interface ImageCommand {

  /**
   * This will be the method that is run when the operation is to be applied.
   * @return A new updated ImageModel with the operation applied.
   */
  ImageModel command() throws IOException;

}
