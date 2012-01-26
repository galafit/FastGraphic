package com.example.fastgraphic;

import com.example.fastgraphic.animator.Animator;
import com.example.fastgraphic.animator.PaintingArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Class to display animation using AWT
 */
public class AWTGTool  implements PaintingArea{

    private Parameters params;
        private Frame frame = new Frame();
        private CompositePainter painter;
        private AWTPaintingArea paintingArea;

        public AWTGTool(Parameters params) {
            this.params = params;
            painter = new CompositePainter(params);
            frame.setTitle(params.getGTool().getLabel());
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    frame.dispose();
                }
            });
            paintingArea = new AWTPaintingArea();
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

        class AWTPaintingArea extends Canvas {
            AWTPaintingArea() {
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

