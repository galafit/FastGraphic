package com.example.fastgraphic;

import com.example.fastgraphic.painters.CompositePainter;
import com.example.fastgraphic.animator.PaintingArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

/**
 * Active Rendering example  using BufferStrategy class
 */
public class ActiveRenderingPaintingArea implements PaintingArea{

    public static final int TWO_BUFFERS = 2;
    private Parameters params;
    private JFrame frame = new JFrame();
    private CompositePainter painter;
    private Canvas paintingCanvas;
    private Controller controller;
    private BufferStrategy bs;


    public ActiveRenderingPaintingArea(Parameters params, Controller contrl) {
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
        paintingCanvas = new Canvas();
        paintingCanvas.setPreferredSize(new Dimension(params.getWidth(), params.getHeight()));
        paintingCanvas.setBackground(params.getBgColor().getColor());
        paintingCanvas.setForeground(params.getFgColor().getColor());
        frame.getRootPane().setDoubleBuffered(false);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);
        frame.add(paintingCanvas);
        frame.pack();
        frame.setVisible(true);
        paintingCanvas.createBufferStrategy(TWO_BUFFERS);
        bs = paintingCanvas.getBufferStrategy();
    }

    public void changeFrame() {
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        Rectangle paintingRectangle = paintingCanvas.getBounds();
        g.setClip(paintingRectangle);
        g.clearRect(paintingRectangle.x, paintingRectangle.y,paintingRectangle.width,paintingRectangle.height);
        painter.paint(g);
        g.dispose();
        bs.show();
        Toolkit.getDefaultToolkit().sync();
    }
}
