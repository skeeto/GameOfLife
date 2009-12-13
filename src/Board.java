import java.awt.*;
import java.util.*;

public class Board extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    private int unitSize, width, height;
    private Cell[][] grid = null;
    private int sleepTime;

    public Board(int unitSize, int width, int height, Cell starter) {
        super();
        this.unitSize = unitSize;
        this.width = width;
        this.height = height;
        this.setSize(width*unitSize, height*unitSize);
        sleepTime = 200;

        grid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = starter.divide();
            }
        }

        // Wire up the cells
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vector<Cell> cells = new Vector<Cell>();
                for (int x = i-1; x <= i+1; x++)
                    for (int y = j-1; y <= j+1; y++) {
                        if (x == i && y == j)
                            continue;
                        int xx = x;
                        int yy = y;
                        if (xx >= width)
                            xx -= width;
                        if (yy >= height)
                            yy -= height;
                        if (xx < 0)
                            xx += width;
                        if (yy < 0)
                            yy += height;
                        cells.add(grid[xx][yy]);
                    }
                grid[i][j].addAdj(cells);
            }
        }

        (new Thread(this)).start();
    }

    public void paint(Graphics g) {
        int unitWidth  = width*unitSize;
        int unitHeight = height*unitSize;
        g.drawLine(0, unitHeight, unitWidth, unitHeight);
        g.drawLine(unitWidth, 0, unitWidth, unitHeight);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                drawCell(g, i, j);
            }
        }
    }

    private void drawCell(Graphics g, int x, int y) {
        g.setColor(grid[x][y].getColor());
        g.fillRect(x * unitSize, y * unitSize, unitSize, unitSize);
    }

    public void iterate() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j].update();
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j].sync();
            }
        }
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                return;
            }
            iterate();
            paint(this.getGraphics());
        }
    }
}
