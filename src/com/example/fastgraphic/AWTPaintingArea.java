package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompositePainter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Class to display animation using AWT
 */
public class AWTPaintingArea implements PaintingArea{

    private Parameters params;
        private Frame frame = new Frame();
        private CompositePainter painter;
        private PaintingCanvas paintingArea;
        private Controller controller;

        public AWTPaintingArea(Parameters params, Controller contrl) {
            controller = contrl;
            this.params = params;
            painter = new CompositePainter(params.getActivePainters());
            frame.setTitle(params.getGTool().getLabel());
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    controller.stopAnimation();
                    frame.dispose();
                }
            });
            paintingArea = new PaintingCanvas();
            frame.add(paintingArea);
            frame.pack();
            frame.setVisible(true);
        }

        public void changeFrame() {
            paintingArea.repaint();
        }

        public Frame getMainWindow() {
           return frame;
        }

        class PaintingCanvas extends Canvas {
            PaintingCanvas() {
                setPreferredSize(new Dimension(params.getWidth(), params.getHeight()));
                setBackground(params.getBgColor().getColor());
                setForeground(params.getFgColor().getColor());
            }

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                painter.paint(g);
            }
        }

}

