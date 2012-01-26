package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SwingGtool implements PaintingArea {

    private Parameters params;
    private JFrame frame = new JFrame();
    private CompositePainter painter;
    private SwingGtool.SwingPaintingArea paintingArea;
    private Controller controller;

    public SwingGtool(Parameters params, Controller contrl) {
        this.controller = contrl;
        this.params = params;
        painter = new CompositePainter(params);
        frame.setTitle(params.getGTool().getLabel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.stopAnimation();
                frame.dispose();
            }
        });
        paintingArea = new SwingPaintingArea();
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

    class SwingPaintingArea extends JPanel {
        SwingPaintingArea() {
            setPreferredSize(new Dimension(params.getWidth(), params.getHeight()));
            setBackground(params.getBgColor().getColor());
            setForeground(params.getFgColor().getColor());
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            painter.paint(g);
        }
    }

}


