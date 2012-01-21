package com.example.fastgraphic;

import javax.swing.*;
import java.awt.*;

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
        ((JPanel) paintingArea).setOpaque(true);
    }

    class SwingPaintingArea extends JPanel implements PaintingArea {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Painter painter : painters) {
                painter.paint(g);
            }
        }
    }
}


