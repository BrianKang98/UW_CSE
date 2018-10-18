import java.awt.*;

public class Ball {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(1000, 1000);
        panel.setBackground(Color.YELLOW);
        Graphics pen = panel.getGraphics();

        //draw initial ball
        pen.fillOval(500,500, 20 ,20);

        double xDisplacement =  20.0;
        double ydisplacement = 5.0;

        double x = 500.0;
        double y = 500.0;

        for (int i = 1; i <= 100; i++) {
            //change the x location of the ball
            //change the y location of the ball
            x = x + xDisplacement;
            y = y +ydisplacement;

            pen.fillOval((int) x, (int) y, 20, 20);

            System.out.println("pause");
            //panel.sleep(1000);
        }
    }
}
