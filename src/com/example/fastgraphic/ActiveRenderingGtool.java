package com.example.fastgraphic;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;


public class ActiveRenderingGtool extends AbstractGtool {

    public ActiveRenderingGtool(Parameters params) {
        super(params);
        ((JPanel) paintingArea).setOpaque(true);
    }

    @Override
    protected Frame getFrame() {
        JFrame frame = new JFrame();
        frame.setIgnoreRepaint(true);
        frame.getRootPane().setDoubleBuffered(false);
        frame.setResizable(false);
        return frame;
    }

    @Override
    protected PaintingArea getPaintingArea() {
        return new ActiveRenderingPaintingArea();
    }

    class ActiveRenderingPaintingArea extends JPanel implements PaintingArea {

        BufferStrategy bs;
        CompositePainter painter = new CompositePainter(params);

        ActiveRenderingPaintingArea() {
            this.createBufferStrategy(2);
            bs = getBufferStrategy();
        }

        @Override
        public void repaint() {
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.setClip(getRootPane().getBounds());
            g.clearRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
            painter.paint(g);
            g.dispose();
            bs.show();
            Toolkit.getDefaultToolkit().sync();
        }
    }
}
