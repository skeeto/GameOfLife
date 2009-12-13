import java.awt.*;
import java.util.*;

public abstract class Cell {
    // Adds adjacent cells for polling
    abstract public void addAdj(Vector<Cell> cells);

    // Return current cell state
    abstract public int getState();

    // Get the color corresponding to the current cell state
    abstract public Color getColor();

    // Poll adjacent cells and update the next state
    abstract public void update();

    // Advance cell to the previously calculated next state
    abstract public void sync();
}
