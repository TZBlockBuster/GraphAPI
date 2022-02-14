/*
 * Copyright BlockWare Studios (c) 2022. All rights reserved
 */

package de.blockwarestudios.graphapi;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int standardValue = 11;
                Graph graph = new Graph("Collatz Problem von 1 bis 100", MathUtils.getCollatzProblem(standardValue));
                for (int i = 0; i < 99; i++) {
                    Random rng = new Random();
                    graph.addValues(new Color(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255), 128), MathUtils.getCollatzProblem(i + 1));
                }
            }
        });
    }
}
