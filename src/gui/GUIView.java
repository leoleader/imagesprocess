package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.ImageModel;

/**
 * This is the View for the GUI version of the program, uses Java Swing to build necessary buttons,
 * panels, etc.
 */
public class GUIView extends JFrame implements IView {

  private JLabel display;
  private JLabel text_response;

  private JButton loadButton;
  private JButton exitButton;
  private JButton saveButton;
  private JButton flipButton;
  private JButton brightenButton;
  private JButton greyscaleButton;
  private JButton filterButton;
  private JButton transformButton;

  private JPanel histogram;

  /**
   * This is the constructor for the GUIView, it is responsible for building and initializing the
   * view.
   * @param title What the title of the program will be.
   */
  public GUIView(String title) {
    super(title);

    setSize(700, 700);
    setLocation(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setMinimumSize(new Dimension(800,800));

    this.setLayout(new FlowLayout());


    JPanel mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    JPanel imagePanel = new JPanel();
    //for elements to be arranged vertically within this panel
    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    JScrollPane imageScrollPane = new JScrollPane(imagePanel);
    add(imageScrollPane);

    //exit button
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit Button");
    mainPanel.add(exitButton);

    //load button
    loadButton = new JButton("Load");
    loadButton.setActionCommand("Load Button");
    mainPanel.add(loadButton);

    //save button
    saveButton = new JButton("Save");
    saveButton.setActionCommand("Save Button");
    mainPanel.add(saveButton);

    //brighten button
    brightenButton = new JButton("Brighten");
    brightenButton.setActionCommand("Brighten Button");
    mainPanel.add(brightenButton);

    //flip button
    flipButton = new JButton("Flip");
    flipButton.setActionCommand("Flip Button");
    mainPanel.add(flipButton);

    //greyscale button
    greyscaleButton = new JButton("Greyscale");
    greyscaleButton.setActionCommand("Save Button");
    mainPanel.add(greyscaleButton);

    //filter button
    filterButton = new JButton("Filter");
    filterButton.setActionCommand("Filter Button");
    mainPanel.add(filterButton);

    //transform button
    transformButton = new JButton("Color Transform");
    transformButton.setActionCommand("Transform Button");
    mainPanel.add(transformButton);

    //displays the image that is currently being worked on
    display = new JLabel("Image to be displayed");
    imagePanel.add(display);

    //program text response after each action
    text_response = new JLabel("Load an image to start!");
    this.add(text_response);

    pack();
    setVisible(true);
  }

  @Override
  public void updateResponse(String message) {
    text_response.setText(message);
  }

  @Override
  public void addFeatures(Features features) {
    exitButton.addActionListener(evt -> features.exitProgram());
    brightenButton.addActionListener(evt -> {
      try {
        features.brightenImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    flipButton.addActionListener(evt -> {
      try {
        features.flipImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    greyscaleButton.addActionListener(evt -> {
      try {
        features.greyscaleImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    filterButton.addActionListener(evt -> {
      try {
        features.filterImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    transformButton.addActionListener(evt -> {
      try {
        features.transformImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    loadButton.addActionListener(evt -> {
      try {
        features.loadImage();
      } catch (IOException e) {
        System.out.println("bruh");
        throw new RuntimeException(e);
      }
    });
    saveButton.addActionListener(evt -> {
      try {
        features.saveImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });


  }

  @Override
  public void displayImage(BufferedImage buffImage) {
    display.setIcon(new ImageIcon(buffImage));
    display.setText("");
  }

  @Override
  public void updateHistogram(ImageModel model) {
    if (histogram != null) {
      this.remove(histogram);
    }
    histogram = new Histogram(model);
    this.add(histogram);
  }

  @Override
  public String chooseFile() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Images", "jpg", "png", "ppm", "bmp");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(GUIView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    else {
      return "";
    }
  }

  @Override
  public String saveFile() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(GUIView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    return "";
  }

  @Override
  public String getInput(String message) {
    return (JOptionPane.showInputDialog(message));
  }


}
