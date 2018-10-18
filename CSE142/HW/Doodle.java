/*
 * Brian Kang
 * 1/21/2018
 * CSE142
 * TA: Kyle Ian Williams-Smith
 * Assignment #3: Doodle.java
 *
 * This program will draw the Olympic Rings
 */

import java.awt.*;

public class Doodle {
    public static void main(String[] args) {
        DrawingPanel p = new DrawingPanel(200, 100);
        Graphics g = p.getGraphics();

        p.setBackground(Color.WHITE);
        g.setColor(Color.BLUE);
        g.drawOval(10,10,50,50);
        g.setColor(Color.BLACK);
        g.drawOval(70,10,50,50);
        g.setColor(Color.RED);
        g.drawOval(130,10,50,50);
        g.setColor(Color.YELLOW);
        g.drawOval(35,35,50,50);
        g.setColor(Color.GREEN);
        g.drawOval(95,35,50,50);
    }
}
