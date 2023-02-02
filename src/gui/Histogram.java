package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JPanel;

import model.ImageModel;

/**
 * This is the Histogram for the RGB + intensity values of the image.
 */
public class Histogram extends JPanel {
  private static int max_value = 0;
  private static final int pref_width = 400;
  private static final int pref_height = 325;
  private static final int gap = 30;
  private static final int point_width = 12;
  private static final int y_line_count = 10;

  private List<Integer> red;

  private List<Integer> green;

  private List<Integer> blue;

  private List<Integer> intensity;

  /**
   * This is the constructor for the Histogram, it takes in an ImageModel and takes the necessary
   * information about the RGB values and uses it to make a Line Chart.
   * @param image the Image Model that is being used to make the Histogram.
   */
  public Histogram(ImageModel image) {
    List<Integer> red_init = image.makeComponentList("red");
    List<Integer> blue_init = image.makeComponentList("blue");
    List<Integer> green_init = image.makeComponentList("green");
    List<Integer> int_init = image.makeComponentList("intensity");
    this.red = this.getCount(red_init);
    this.blue = this.getCount(blue_init);
    this.green = this.getCount(green_init);
    this.intensity = this.getCount(int_init);
    this.max_value = this.calculateMax(red, green, blue);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    double xScale = ((double) getWidth() - 2 * gap) / (red.size() - 1);
    double yScale = ((double) getHeight() - 2 * gap) / (max_value - 1);

    // graph points for red
    List<Point> graphPoints = new ArrayList<Point>();
    for (int i = 0; i < red.size(); i++) {
      int x1 = (int) (i * xScale + gap);
      int y1 = (int) ((max_value - red.get(i)) * yScale + gap);
      graphPoints.add(new Point(x1, y1));
    }

    // graph points for blue
    List<Point> graphPointsblue = new ArrayList<Point>();
    for (int i = 0; i < blue.size(); i++) {
      int x1 = (int) (i * xScale + gap);
      int y1 = (int) ((max_value - blue.get(i)) * yScale + gap);
      graphPointsblue.add(new Point(x1, y1));
    }

    // graph points for green
    List<Point> graphPointsgreen = new ArrayList<Point>();
    for (int i = 0; i < green.size(); i++) {
      int x1 = (int) (i * xScale + gap);
      int y1 = (int) ((max_value - green.get(i)) * yScale + gap);
      graphPointsgreen.add(new Point(x1, y1));
    }

    // graph points for intensity
    List<Point> graphPointsint = new ArrayList<Point>();
    for (int i = 0; i < intensity.size(); i++) {
      int x1 = (int) (i * xScale + gap);
      int y1 = (int) ((max_value - intensity.get(i)) * yScale + gap);
      graphPointsint.add(new Point(x1, y1));
    }

    // create x and y axes
    g2.drawLine(gap, getHeight() - gap, gap, gap);
    g2.drawLine(gap, getHeight() - gap, getWidth() - gap, getHeight() - gap);

    // create hatch marks for y axis.
    for (int i = 0; i < y_line_count; i++) {
      int x0 = gap;
      int x1 = point_width + gap;
      int y0 = getHeight() - (((i + 1) * (getHeight() - gap * 2)) / y_line_count + gap);
      int y1 = y0;
      g2.drawLine(x0, y0, x1, y1);
    }

    // and for x axis
    for (int i = 0; i < red.size() - 1; i++) {
      int x0 = (i + 1) * (getWidth() - gap * 2) / (red.size() - 1) + gap;
      int x1 = x0;
      int y0 = getHeight() - gap;
      int y1 = y0 - point_width;
      g2.drawLine(x0, y0, x1, y1);
    }

    g2.setColor(Color.red);
    g2.setStroke(new BasicStroke(3f));
    for (int i = 0; i < graphPoints.size() - 1; i++) {
      int x1 = graphPoints.get(i).x;
      int y1 = graphPoints.get(i).y;
      int x2 = graphPoints.get(i + 1).x;
      int y2 = graphPoints.get(i + 1).y;
      g2.drawLine(x1, y1, x2, y2);
    }

    g2.setColor(Color.blue);
    for (int i = 0; i < graphPointsblue.size() - 1; i++) {
      int x1 = graphPointsblue.get(i).x;
      int y1 = graphPointsblue.get(i).y;
      int x2 = graphPointsblue.get(i + 1).x;
      int y2 = graphPointsblue.get(i + 1).y;
      g2.drawLine(x1, y1, x2, y2);
    }

    g2.setColor(Color.green);
    for (int i = 0; i < graphPointsgreen.size() - 1; i++) {
      int x1 = graphPointsgreen.get(i).x;
      int y1 = graphPointsgreen.get(i).y;
      int x2 = graphPointsgreen.get(i + 1).x;
      int y2 = graphPointsgreen.get(i + 1).y;
      g2.drawLine(x1, y1, x2, y2);
    }

    g2.setColor(Color.yellow);
    for (int i = 0; i < graphPointsint.size() - 1; i++) {
      int x1 = graphPointsint.get(i).x;
      int y1 = graphPointsint.get(i).y;
      int x2 = graphPointsint.get(i + 1).x;
      int y2 = graphPointsint.get(i + 1).y;
      g2.drawLine(x1, y1, x2, y2);
    }

  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(pref_width, pref_height);
  }

  private List<Integer> getCount(List<Integer> bruh) {
    List<Integer> red_count = new ArrayList<>();
    int count = 0;
    for (int i = 0; i < 256; i++) {
      for (int j = 0; j < bruh.size(); j++) {
        if (i == bruh.get(j)) {
          count = count + 1;
        }
      }
      red_count.add(count);
      count = 0;
    }
    return red_count;
  }

  private int calculateMax(List<Integer> r, List<Integer> g, List<Integer> b) {
    int bruh = 0;
    for (int i: r) {
      if (i > bruh) {
        bruh = i;
      }
    }
    for (int i: g) {
      if (i > bruh) {
        bruh = i;
      }
    }
    for (int i: b) {
      if (i > bruh) {
        bruh = i;
      }
    }
    return bruh;
  }


}
