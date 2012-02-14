package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompositePainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * Standard Passive rendering using SWING components
 *
 */

public class SwingPaintingArea implements PaintingArea {

    private JFrame frame;
    private CompositePainter painter;
    private JPanel paintingPanel;
    private Controller controller;

    public SwingPaintingArea(boolean isSwingDoubleBuff, String title, int width, int height,
                             Color bgColor, Color fgColor, CompositePainter painter, Controller contrl) {
        frame = new JFrame();
        if(!isSwingDoubleBuff){
            // turn off Double Buffering
            frame.getRootPane().setDoubleBuffered(false);
            //just for the case
            RepaintManager.currentManager(frame).setDoubleBufferingEnabled(false);
        }
        controller = contrl;
        this.painter = painter;
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.stopAnimation();
                frame.dispose();
            }
        });
        paintingPanel = new PaintingPanel();
        paintingPanel.setPreferredSize(new Dimension(width, height));
        paintingPanel.setBackground(bgColor);
        paintingPanel.setForeground(fgColor);
        paintingPanel.setOpaque(true);
        frame.add(paintingPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public void changeFrame() {
        paintingPanel.repaint();
    }

    class PaintingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            painter.paint(g);
        }
    }

}


