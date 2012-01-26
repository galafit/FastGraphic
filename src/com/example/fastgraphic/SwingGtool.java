package com.example.fastgraphic;

import javax.swing.*;
import java.awt.*;

public class SwingGtool extends AbstractGtool{

    @Override
    protected Frame getFrame() {
        return new JFrame();
    }

    @Override
    protected PaintingArea getPaintingArea() {
        return new SwingPaintingArea();
    }

    public SwingGtool(Parameters params) {
        super(params);
        frame.add((Component) paintingArea);
        ((JPanel) paintingArea).setOpaque(true);
    }

    class SwingPaintingArea extends JPanel implements PaintingArea {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            painter.paint(g);
        }
    }
}


