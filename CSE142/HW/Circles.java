/*
 * Brian Kang
 * 1/21/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #3: Circles.java
 *
 * This program will draw a specific figure of grids of concentric circles.
 */

import java.awt.*;  // so that I can use Graphics

public class Circles {
    public static void main(String[] args) {
        DrawingPanel p = new DrawingPanel(500, 350);
        Graphics g = p.getGraphics();

        p.setBackground(Color.CYAN);
        Subfigure(g, 0, 0, 100, 10);
        Subfigure(g, 130, 25, 100, 10);
        Subfigure(g, 260, 0, 60, 6);
        Subfigure(g, 360, 50, 80, 4);

        Grid(g, 3, 10, 170, 48, 4);
        Grid(g, 5, 180, 200, 24, 2);
        Grid(g, 2, 330, 170, 72, 9);
    }

    //Draws concentric circle subfigures composed of
    //one set of yellow and black concentric circles
    //parameters needed:
    //  g = allows to draw specific figures
    //  xCoord = the horizontal location
    //  yCoord = the vertical location
    //  Size = the width and height (of the circle?)
    //  subNum = the number of circle subfigures
    public static void Subfigure(Graphics g, int xCoord,
                                 int yCoord, int Size, int subNum) {
        g.setColor(Color.YELLOW);
        g.fillOval(xCoord, yCoord, Size, Size);
        g.setColor(Color.BLACK);
        g.drawOval(xCoord, yCoord, Size, Size);

        for (int i = 1; i <= subNum; i++) {
            g.drawOval(xCoord + (Size / (subNum * 2)) * i,
                    yCoord + (Size / (subNum * 2)) * i,
                    Size - (Size / subNum) * i,
                    Size - (Size / subNum ) * i);
        }
    }

    //Draws square grid for the concentric circle subfigures
    //parameters needed:
    //  g = allows to draw specific figures
    //  rowCol = the number of rows and columns of circles on grid
    //  xCoord = the horizontal location
    //  yCoord = the vertical location
    //  Size = the width and height (of the circle?)
    //  subNum = the number of circle subfigures
    public static void Grid(Graphics g, int rowCol, int xCoord,
                            int yCoord, int Size, int subNum) {
        g.setColor(Color.GREEN);
        g.fillRect(xCoord, yCoord, Size * rowCol, Size * rowCol);

        for (int i = 1; i <= rowCol; i++) {
            for (int j = 1; j <= rowCol; j++) {
                Subfigure(g, xCoord + Size * (i - 1),
                        yCoord + Size * (j - 1), Size, subNum);
            }
        }
        g.setColor(Color.BLACK);
        g.drawRect(xCoord, yCoord, Size * rowCol, Size * rowCol);
        g.drawLine(xCoord, yCoord, xCoord + Size * rowCol, yCoord + Size * rowCol);
        g.drawLine(xCoord, yCoord + Size * rowCol, xCoord + Size * rowCol, yCoord);
    }
}