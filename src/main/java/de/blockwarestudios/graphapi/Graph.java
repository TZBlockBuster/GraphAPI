/*
 * Copyright BlockWare Studios (c) 2022. All rights reserved
 */

package de.blockwarestudios.graphapi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graph {

    private GraphPanel graphPanel;


    /**
     * @param name The name that is used for the Window that will open
     * @param values The values displayed in the Window that will open
     */
    public Graph(String name, ArrayList<Double> values) {
        graphPanel = new GraphPanel(values);
        graphPanel.setPreferredSize(new Dimension(800, 600));

        JFrame frame = new JFrame(name);
        frame.setCursor(Cursor.getPredefinedCursor(1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(graphPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * @return Returns the GraphPanel which is used to make more "Settings" for more advanced users
     */
    public GraphPanel getGraphPanel() {
        return graphPanel;
    }

    public void setGraphPanel(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
    }

    public void addValues(Color color, ArrayList<Double> scores) {
        graphPanel.addScores(new GraphContent(color, scores));
    }
}
