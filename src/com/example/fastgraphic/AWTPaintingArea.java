package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompositePainter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * Standard Passive rendering using AWT components
 *
 */

public class AWTPaintingArea implements PaintingArea{

        private Frame frame;
        private CompositePainter painter;
        private PaintingCanvas paintingCanvas;
        private Controller controller;

        public AWTPaintingArea(String title, int width, int height,
                               Color bgColor, Color fgColor, CompositePainter painter, Controller contrl) {
            controller = contrl;
            this.painter = painter;
            frame = new Frame();
            frame.setTitle(title);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    controller.stopAnimation();
                    frame.dispose();
                }
            });
            paintingCanvas = new PaintingCanvas();
            paintingCanvas.setPreferredSize(new Dimension(width, height));
            paintingCanvas.setBackground(bgColor);
            paintingCanvas.setForeground(fgColor);
            frame.add(paintingCanvas);
            frame.pack();
            frame.setVisible(true);
        }

        public void changeFrame() {
            paintingCanvas.repaint();
        }

        class PaintingCanvas extends Canvas {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                painter.paint(g);
            }
        }

}

