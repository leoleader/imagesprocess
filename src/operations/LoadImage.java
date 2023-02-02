package operations;

import java.io.IOException;

import model.FileModel;
import model.ImageModel;
import model.PPMImageModel;

/**
 * This class is a function object that creates the correct ImageModel, which when initialized
 * creates it's necessary data, loading it in.
 */
public class LoadImage implements ImageCommand {

  private String filepath;

  private String givenname;


  public LoadImage(String filepath, String givenname) {
    this.filepath = filepath;
    this.givenname = givenname;
  }

  @Override
  public ImageModel command() throws IOException {
    if (filepath.substring(filepath.length() - 3).equals("ppm")) {
      return new PPMImageModel(filepath, givenname);
    }
    else {
      return new FileModel(filepath);
    }
  }

}
