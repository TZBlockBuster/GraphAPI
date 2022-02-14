/*
 * Copyright BlockWare Studios (c) 2022. All rights reserved
 */

package de.blockwarestudios.graphapi;

import java.util.ArrayList;

public class MathUtils {

    public static ArrayList<Double> getCollatzProblem(double seed) {
        ArrayList<Double> values = new ArrayList<>();
        double value = seed;
        while (value != 1) {
            values.add(value);
            if (value % 2 == 0) {
                value /= 2;
            } else {
                value = 3 * value + 1;
            }
        }
        values.add(1d);
        return values;
    }
}
