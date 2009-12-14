import java.awt.*;
import java.awt.event.*;

public class GameOfLife {
    static int width = 50;
    static int height = 50;
    static int unitSize = 10;

    public static void main(String args[]) {
        // Parse command line arguments
        Cell cellType = new BasicCell();
        if (args.length >= 1) {
            if (args[0].equals("growth"))
                cellType = new GrowthCell();
            else if (args[0].equals("basic"))
                cellType = new BasicCell();
            else {
                System.out.println("error: unknown cell type " + args[0]);
                System.exit(-1);
            }
        }
        if (args.length >= 3) {
            width = Integer.parseInt(args[1]);
            height = Integer.parseInt(args[2]);
        }
        if (args.length >= 4) {
            unitSize = Integer.parseInt(args[3]);
        }

        // Initialize GUI
        Frame frame = new Frame("Game of Life");
        Board board = new Board(unitSize, width, height, cellType);
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
