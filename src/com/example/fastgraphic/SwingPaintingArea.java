package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompositePainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SwingPaintingArea implements PaintingArea {

    private Parameters params;
    private JFrame frame = new JFrame();
    private CompositePainter painter;
    private PaintingPanel paintingArea;
    private Controller controller;

    public SwingPaintingArea(Parameters params, Controller contrl) {
        this.controller = contrl;
        this.params = params;
        painter = new CompositePainter(params.getActivePainters());
        frame.setTitle(params.getGTool().getLabel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.stopAnimation();
                frame.dispose();
            }
        });
        paintingArea = new PaintingPanel();
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

    class PaintingPanel extends JPanel {
        PaintingPanel() {
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


