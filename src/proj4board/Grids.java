package proj4board;
import java.awt.*;
import java.awt.event.*;

public class Grids extends Canvas {
  int width, height, rows, columns;

  Grids(int w, int h, int r, int c) {
  setSize(width = w, height = h);
  rows = r;
  columns = c;
  }
  public void paint(Graphics g) {
  int k;
  width = getSize().width;
  height = getSize().height;

  int htOfRow = height / (rows);
  for (k = 0; k <= rows; k++)
    g.drawLine(0, k * htOfRow , width, k * htOfRow );
  
  int wdOfRow = width / (columns);
  for (k = 0; k <= columns; k++)
    g.drawLine(k * wdOfRow , 0, k * wdOfRow , height);
  
  g.drawLine(height-1, 0, height-1, height-1);
  g.drawLine(0, height-1, height-1, height-1);
  }
}
