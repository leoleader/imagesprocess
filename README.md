README.md

UPDATES FOR HW6:
- Abstracted the sepia and greyscale methods into a general color transform method
- Created Features and IView interfaces for GUIController and GUIView classes to make the
GUI for the program, where using the program is much more user-friendly and straight forward. 
- Created a Histogram class that extends JPanel to build a representation of the RGB and intensity 
values for the GUI as a Line Chart.


Welcome! This is an image processing program that is made up of three main components: a model, 
a controller and function objects. 

The 2 models PPMImageModel and FileModel implement an interface called ImageModel, 
which is basically a data representation of a chosen image and the methods necessary to change 
different attributes of the image. The PPMImageModel works with ppm file types and the FileModel
works with other commonly used file types like jpg, png, and bpm. 

The interface ImageCommand represents an operation, for example brightening the image, to be run on 
a given ImageModel to create a new one with the operation applied. The classes 
BrightenImage, GreyImage, GreyScaleImage, HorizontalFlipImage, VerticalFlipImage, LoadImage, 
SepiaImage, and FilterImage all implement this interface. 

The controller is a class called ImageController and is responsible for communicating between the 
user and the models by reading and interpreting user inputs, applying image commands, outputting 
useful information, etc. 

There are 2 test classes, ImageCommandTest and ImageControllerTest  which test their respective 
classes along with the ImageModel methods because they are heavily used and dependent on them.

The class ImageProcessor has the main() method, which is responsible for figuring out where to
receive command inputs from and giving the controller the right information. 

DESIGN ELEMENT UPDATES
- Added convertBetween method to ImageModel to support functionality of switching between ppm
  and other file types. This is necessary for saving files in different file types than they started
  and for applying file type specific operations on other file types. For example instead of figurin
  out how to vertical-flip a FileImage and writing a ton of code, I use convertBetween() to
  momentarily change it to a PPMImageModel, apply the operation, and then convert it back.
- Moved saveFile method from ImageModel to ImageController because IO functionality should be
  handled by the controller.
- Combined the six different grey component methods in ImageModel into one method called
  compGrey(String component) to reduce code duplication and simplify the GreyImage ImageCommand.

IMAGE CITATIONS
Added duck.jpg in res folder, which is a free to use stock image downloaded from 
https://www.pexels.com/photo/duckling-on-black-soil-during-daytime-162140/
The example image, sample.ppm and an image used for testing, testing.ppm, both
in the images folder, were made by us by randomly generating values for their RGBs. So they are ours
and are authorized for use in this project. 

//HW5 Update - Use the jar file and script file to run the program as described in the USEME file.
SCRIPT OF COMMANDS
run the program using the ImageProcessor and then paste this script of commands (the resulting 
image will be in the res folder called cat_flipped.ppm and will be the original leo picture
flipped horizontally and vertically):

load images/sample.ppm john
horizontal-flip john flipped
vertical-flip flipped superflip
save res/superflipped.ppm superflip
quit
