import java.awt.*;
import java.awt.event.*;

public class GameOfLife {
    static int width = 50;
    static int height = 50;
    static int unitSize = 10;

    public static void main(String args[]) {
        Frame frame = new Frame("Game of Life");
        Board board = new Board(unitSize, width, height);
        frame.add(board);
        frame.setSize(width*unitSize+12, height*unitSize+30);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }
}
