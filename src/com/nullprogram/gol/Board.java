package com.nullprogram.gol;

import java.util.Vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * Sets up the cell grid and iterates the automata.
 */
public class Board extends JPanel
            implements Runnable, MouseMotionListener, MouseListener {

    private static final long serialVersionUID = 1L;

    private int unitSize, width, height;
    private Cell[][] grid = null;
    private int sleepTime;
    private volatile boolean enabled;
    private int button;

    public Board(int unitSize, int width, int height, Cell starter) {
        super();
        this.unitSize = unitSize;
        this.width = width;
        this.height = height;
        sleepTime = 200;
        Dimension size = new Dimension(width * unitSize, height * unitSize);
        setMinimumSize(size);
        setPreferredSize(size);
        addMouseListener(this);
        addMouseMotionListener(this);

        /* Create grid with cells */
        grid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = starter.divide();
            }
        }

        /* Wire up the cells */
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

        enabled = false;
        start();
    }

    /* Run the iteration thread */
    public void start() {
        if (!enabled) {
            enabled = true;
            (new Thread(this)).start();
        }
    }

    /* Stop the iteration thread. */
    public void stop() {
        enabled = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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

    /* Perform a single iteration of the automata */
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
        while (enabled) {
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
                return;
            }
            iterate();
            repaint();
        }
    }

    /** {@inheritDoc} */
    public final void mouseDragged(final MouseEvent e) {
        int state;
        if (button == MouseEvent.BUTTON1) {
            state = 1;
        } else {
            state = 0;
        }
        //System.out.println("drag " + e.getButton());
        Point p = e.getPoint();
        int x = (int) (p.getX() / unitSize);
        int y = (int) (p.getY() / unitSize);
        if ((x < width) && (x >= 0) && (y < height) && (y >= 0)) {
            grid[x][y].setState(state);
        }
        repaint();
    }

    /** {@inheritDoc} */
    public void mousePressed(final MouseEvent e) {
        button = e.getButton();
        if (button == MouseEvent.BUTTON2) {
            if (enabled) {
                stop();
            } else {
                start();
            }
        }
    }

    /** {@inheritDoc} */
    public void mouseMoved(final MouseEvent e) {
        /* Do nothing */
    }

    /** {@inheritDoc} */
    public void mouseExited(final MouseEvent e) {
        /* Do nothing */
    }

    /** {@inheritDoc} */
    public void mouseEntered(final MouseEvent e) {
        /* Do nothing */
    }

    /** {@inheritDoc} */
    public void mouseClicked(final MouseEvent e) {
        /* Do nothing */
    }

    /** {@inheritDoc} */
    public final void mouseReleased(final MouseEvent e) {
        /* Do nothing */
    }
}
