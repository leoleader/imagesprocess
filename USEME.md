

UPDATED FOR HW6:
How to use the program:
Open up the res folder in the terminal and run the following command:
java -jar hw6.jar
Which will open the GUI version of the program.


Commands Supported by this Program:

IMPORTANT NOTE: the load command must be done before any of the other commands
(except quit, which can be done at any time) because the other commands reference an image
already loaded into the program.

load - click the load button and select a file to load

save - saves the current image to whatever folder and file path you choose

filter - click the filter button, two options can be typed in the pop up dialog box, either 'blur' 
to blur the image or 'sharpen' to sharpen the image.

flip - click the flip button, two options can be typed in the pop up dialog box, either 'vertical'
or 'horizontal' and will complete the corresponding flip of the image. 

greyscale - click the greyscale button and specify which of the 6 components you want to use in
the transformation by typing in the pop up dialog box, will then greyscale the image appropriately.

brighten- click the brighten image and in the popup dialog box specify how much you want to increase
or decrease the values of the image.

transform - click the transform button, two options of 'greyscale' or 'sepia', that apply their
respective color transforms on the image. 

exit - to exit the program simply click the exit button. 




//OLD HW5 USEME INFO

How to use the program: 
Open up the res folder in the terminal and run the following command:
java -jar hw5.jar -file script.txt


Commands Supported by this Program:

IMPORTANT NOTE: the load command must be done before any of the other commands 
(except menu and quit, which can be done at any time) becaue the other commands reference an image 
already loaded into the program.

load image-path image-name
"Load an image from the specified path and refer to it henceforth in the program by the given 
image name."
Example - load res/duck.jpg ducky

save image-path image-name
"Save the image with the given image-name to the specified path which should include the name of 
the file."
Example - save images/done.jpg ducky

blur image-name dest-image-name 
"Blur the image with the given name and save it in the program as the designated name"
Example - blur ducky blurry

sharpen image-name dest-image-name 
"Sharpen the image with the given name and save it in the program as the designated name"
Example - sharpen ducky sharp

sepia image-name dest-image-name 
"Give the image with the given name a sepia color transformation and save it in the program as the 
designated name"
Example - sepia ducky sepia

greyscale image-name dest-image-name 
"Make the image with the given name greyscale and save it in the program as the designated name"
Example - greyscale ducky grey

component component-type image-name dest-image-name
"Create a greyscale image using one of the following components of the image with the given name, 
and refer to it henceforth in the program by the given destination name. Component types are: red, 
green, blue, value, luma, and intensity."
Examples - greyscale blue ducky blue
         - greyscale luna ducky luna

horizontal-flip image-name dest-image-name
"Flip an image horizontally to create a new image, referred to henceforth by the given destination 
name"
Example - horizontal-flip ducky flipped_ducky

vertical-flip image-name dest-image-name
"Flip an image vertically to create a new image, referred to henceforth by the given destination 
name"
Example - vertical-flip ducky flipped_ducky

brighten increment image-name dest-image-name: 
"Brighten the image by the given increment to create a new image, referred to henceforth by the 
given destination name. The increment may be positive (brightening) or negative (darkening)"
Example - brighten 50 ducky brighter

menu 
"Prints the supported commands list"
Example - menu

quit
"Quits the program"
Examples - q
         - quit

