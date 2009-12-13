import java.awt.*;
import java.util.*;

public abstract class Cell {
    protected Vector<Cell> adj = null;
    protected int curState, nextState;
    protected static Random rand = new Random();

    public Cell() {
        adj = new Vector<Cell>();
    }

    // Adds adjacent cells for polling
    public void addAdj(Vector<Cell> cells) {
        adj.addAll(cells);
    }

    // Return current cell state
    public int getState() {
        return curState;
    }

    // Get the color corresponding to the current cell state
    abstract public Color getColor();

    // Poll adjacent cells and update the next state
    abstract public void update();

    // Built-in factory: create a new cell with a random state
    abstract public Cell divide();

    // Built-in factory: create a new cell with given state
    abstract public Cell divide(int state);

    // Advance cell to the previously calculated next state
    public void sync() {
        curState = nextState;
    }
}
