// GrowthCell.java - custom cell that grows over time
import java.awt.*;
import java.util.*;

public class GrowthCell extends Cell {
    private Color[] colors = null;

    public GrowthCell() {
        this(rand.nextInt(4));
    }

    public GrowthCell(int state) {
        curState = nextState = state;
        colors = new Color[4];
        colors[0] = new Color(255, 255, 255);
        colors[1] = new Color(0, 255, 0);
        colors[2] = new Color(0, 127, 255);
        colors[3] = new Color(0, 0, 255);
    }

    // Get the color corresponding to the current cell state
    public Color getColor() {
        return colors[curState];
    }

    // Poll adjacent cells and update the next state
    public void update() {
        int liveCount = 0;
        Iterator<Cell> it = adj.iterator();
        while (it.hasNext()) {
            Cell cell = it.next();
            if (cell.getState() > 0)
                liveCount = liveCount + 1;
        }

        // Like the original, but if we remain alive, we grow
        if (curState > 0 && (liveCount == 2 || liveCount == 3))
            nextState = curState + 1;
        else if (curState == 0 && liveCount == 3)
            nextState = 1;
        else
            nextState = curState - Math.min(Math.abs(liveCount - 2), 3);

        if (nextState > 3)
            nextState = 3;
        if (nextState < 0)
            nextState = 0;
    }

    // Built-in factory: create a new cell with a random state
    public Cell divide() {
        return divide(rand.nextInt(2));
    }

    // Built-in factory: create a new cell with given state
    public Cell divide(int state) {
        return new GrowthCell(state);
    }
}
