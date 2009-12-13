import java.awt.*;
import java.util.*;

// This is the basic cell that implements Conway's Game of Life
public class BasicCell extends Cell {
    private Color live, dead;

    public BasicCell() {
        this(rand.nextInt(2));
    }

    public BasicCell(int initState) {
        curState = nextState = initState;
        live = new Color(0, 0, 0);
        dead = new Color(255, 255, 255);
    }

    public Color getColor() {
        if (curState == 1)
            return live;
        return dead;
    }

    public void update() {
        int liveCount = 0;
        Iterator<Cell> it = adj.iterator();
        while (it.hasNext()) {
            Cell cell = it.next();
            if (cell.getState() > 0)
                liveCount = liveCount + 1;
        }

        //   1. Any live cell with fewer than two live neighbours
        //      dies, as if caused by underpopulation.
        //   2. Any live cell with more than three live neighbours
        //      dies, as if by overcrowding.
        //   3. Any live cell with two or three live neighbours lives
        //      on to the next generation.
        //   4. Any dead cell with exactly three live neighbours
        //      becomes a live cell.
        if (curState == 1 && (liveCount == 2 || liveCount == 3))
            nextState = 1;
        else if (curState == 0 && liveCount == 3)
            nextState = 1;
        else
            nextState = 0;
    }

    public Cell divide() {
        return divide(rand.nextInt(2));
    }

    public Cell divide(int state) {
        return new BasicCell(state);
    }
}
