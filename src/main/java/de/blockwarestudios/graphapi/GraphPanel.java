/*
 * Copyright BlockWare Studios (c) 2022. All rights reserved
 */

package de.blockwarestudios.graphapi;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class GraphPanel extends JPanel {

    private int width = 800;
    private int height = 400;
    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private ArrayList<GraphContent> scores = new ArrayList<>();

    public GraphPanel(ArrayList<Double> scores) {
        this.scores.add(new GraphContent(lineColor, scores));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (getScoreSize() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y-axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (getScoreSize() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0f) / numberYDivisions)) * 100)) / 100.0f + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x-axis
        for (int i = 0; i < getScoreSize(); i++) {
            if (getScoreSize() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (getScoreSize() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((getScoreSize() / 20.0f)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // create x and y axes
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        for (int con = 0; con < scores.size(); con++) {

            ArrayList<Point> graphPoints = new ArrayList<>();
            for (int i = 0; i < scores.get(con).getValues().size(); i++) {
                int x1 = (int) (i * xScale + padding + labelPadding);
                int y1 = (int) ((getMaxScore() - scores.get(con).getValues().get(i)) * yScale + padding);
                graphPoints.add(new Point(x1, y1));
            }

            Stroke oldStroke = g2.getStroke();
            g2.setColor(scores.get(con).getColor());
            g2.setStroke(GRAPH_STROKE);
            for (int i = 0; i < graphPoints.size() - 1; i++) {
                int x1 = graphPoints.get(i).x;
                int y1 = graphPoints.get(i).y;
                int x2 = graphPoints.get(i + 1).x;
                int y2 = graphPoints.get(i + 1).y;
                g2.drawLine(x1, y1, x2, y2);
            }

            g2.setStroke(oldStroke);
            g2.setColor(pointColor);
            for (int i = 0; i < graphPoints.size(); i++) {
                int x = graphPoints.get(i).x - pointWidth / 2;
                int y = graphPoints.get(i).y - pointWidth / 2;
                int ovalW = pointWidth;
                int ovalH = pointWidth;
                g2.fillOval(x, y, ovalW, ovalH);
            }
        }
    }


    /**
     * @return Returns the lowest Score from all Graphs
     */
    private double getMinScore() {
        ArrayList<Double> sortedList = new ArrayList<>();
        for (GraphContent graphContent : scores) {
            sortedList.addAll(graphContent.getValues());
        }
        Collections.sort(sortedList);
        return sortedList.get(0);
    }

    /**
     * @return Returns the highest Score from all Graphs
     */
    private double getMaxScore() {
        ArrayList<Double> sortedList = new ArrayList<>();
        for (GraphContent graphContent : scores) {
            sortedList.addAll(graphContent.getValues());
        }
        Collections.sort(sortedList);
        return sortedList.get(sortedList.size() - 1);
    }

    /**
     * @return Returns the Longest Set of Scores
     */
    private int getScoreSize() {
        ArrayList<Integer> sortedList = new ArrayList<>();
        for (GraphContent graphContent : scores) {
            sortedList.add(graphContent.getValues().size());
        }
        Collections.sort(sortedList);
        return sortedList.get(sortedList.size() - 1);
    }

    public void setScores(int index, ArrayList<Double> scores) {
        this.scores.set(index, new GraphContent(this.scores.get(index).getColor(), scores));
        invalidate();
        this.repaint();
    }

    public void addScores(GraphContent content){
        this.scores.add(content);
        invalidate();
        this.repaint();
    }

    public ArrayList<Double> getScores(int index) {
        return scores.get(index).getValues();
    }
}
