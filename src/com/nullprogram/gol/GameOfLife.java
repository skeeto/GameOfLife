/* Copyright (c) 2009 Christopher Wellons <mosquitopsu@gmail.com>
 *
 * Permission to use, copy, modify, and distribute this software for
 * any purpose with or without fee is hereby granted, provided that
 * the above copyright notice and this permission notice appear in all
 * copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL
 * WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE
 * AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR
 * CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS
 * OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN
 * CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package com.nullprogram.gol;

import javax.swing.JFrame;

public class GameOfLife {

    static int width = 50;
    static int height = 50;
    static int unitSize = 10;

    public static void main(String args[]) {
        /* Fix for poor OpenJDK performance. */
        System.setProperty("sun.java2d.pmoffscreen", "false");

        /* Parse command line arguments */
        Cell cellType = new BasicCell();
        if (args.length >= 1) {
            if (args[0].equals("growth"))
                cellType = new GrowthCell();
            else if (args[0].equals("basic"))
                cellType = new BasicCell();
            else if (args[0].equals("random"))
                cellType = new RandomFactory();
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

        /* Initialize GUI */
        JFrame frame = new JFrame("Game of Life");
        Board board = new Board(unitSize, width, height, cellType);
        frame.add(board);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
