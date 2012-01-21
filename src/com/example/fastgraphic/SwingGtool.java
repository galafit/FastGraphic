package com.example.fastgraphic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SwingGTool extends AbstractGTool {

    @Override
    protected Frame getFrame() {
        return new JFrame();
    }

    @Override
    protected PaintingArea getPaintingArea() {
        return new SwingPaintingArea();
    }

    public SwingGTool(Parameters params) {
        super(params);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        final JFrame f = new JFrame();
        ((JPanel)paintingArea).setOpaque(true);
        f.add((JPanel)paintingArea);
        f.pack();
        f.setVisible(true);
    }
}

class SwingPaintingArea extends JPanel implements PaintingArea{
     private java.util.List<Painter> painters = new ArrayList<Painter>();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Painter painter : painters) {
            painter.paint(g);
        }
    }
     public void addPainter(Painter painter) {
        painters.add(painter);
    }
}
