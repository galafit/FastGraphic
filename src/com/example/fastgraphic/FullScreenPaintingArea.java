package com.example.fastgraphic;

import com.example.fastgraphic.painters.CompositePainter;
import com.example.fastgraphic.animator.PaintingArea;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

/**
 * Active rendering in Full Screen mode
 */
public class FullScreenPaintingArea implements PaintingArea{
    public static final int TWO_BUFFERS = 2;
    private BufferStrategy bs;
    private CompositePainter painter;
    private Controller controller;
    private GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice device = env.getDefaultScreenDevice();


    public FullScreenPaintingArea(Parameters params, Controller contrl) {
        controller = contrl;
        final Frame frame = new Frame();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.stopAnimation();
                frame.dispose(); 
            }
        });
        frame.setBackground(params.getBgColor().getColor());
        frame.setForeground(params.getFgColor().getColor());
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.createBufferStrategy(TWO_BUFFERS);
        bs = frame.getBufferStrategy();
        device.setFullScreenWindow(frame);
        painter = new CompositePainter(params.getActivePainters());

    }

    public void changeFrame() {
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        Rectangle paintingRectangle = new Rectangle(0,0,device.getDisplayMode().getWidth(),device.getDisplayMode().getHeight());
        g.setClip(paintingRectangle);
        g.clearRect(paintingRectangle.x, paintingRectangle.y,paintingRectangle.width,paintingRectangle.height);
        painter.paint(g);
        g.dispose();
        bs.show();
        Toolkit.getDefaultToolkit().sync();
    }
}
