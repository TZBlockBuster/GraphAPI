/*
 * Copyright BlockWare Studios (c) 2022. All rights reserved
 */

package de.blockwarestudios.graphapi;

import java.awt.*;
import java.util.ArrayList;

public class GraphContent {

    private Color color;
    private ArrayList<Double> values;

    /**
     * @param color The Color that is used to display the Graph
     * @param values The Values of the Graph
     */
    public GraphContent(Color color, ArrayList<Double> values) {
        this.color = color;
        this.values = values;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<Double> getValues() {
        return values;
    }

    public void setValues(ArrayList<Double> values) {
        this.values = values;
    }
}
