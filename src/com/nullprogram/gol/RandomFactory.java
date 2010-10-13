package com.nullprogram.gol;

import java.awt.*;
import java.util.*;

/**
 * Spawns off random cells on divide().
 *
 * This cell type doesn't actually do anything itself.
 */
public class RandomFactory extends Cell {

    interface Factory {
        Cell create();
    }
    Factory[] types = null;

    public RandomFactory() {
        types = new Factory[2];
        types[0] = new Factory() {
            public Cell create() {
                return new BasicCell();
            }
        };
        types[1] = new Factory() {
            public Cell create() {
                return new GrowthCell();
            }
        };
        curState = nextState = 0;
    }

    public Color getColor() {
        return new Color(255, 0, 0);
    }

    public void update() {
        /* Nothing */
    }

    public Cell divide() {
        return types[rand.nextInt(types.length)].create();
    }

    public Cell divide(int state) {
        return types[rand.nextInt(types.length)].create();
    }
}
