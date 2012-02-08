package com.example.fastgraphic.painters;

import com.example.fastgraphic.Controller;
import com.example.fastgraphic.Parameters;
import com.example.fastgraphic.animator.PaintingArea;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: galafit
 * Date: 05/02/12
 * Time: 23:51
 * To change this template use File | Settings | File Templates.
 */
public class DoubleBufferingFullScreen implements PaintingArea {
    public static final int TWO_BUFFERS = 2;
    private CompositePainter painter;
    private Controller controller;
    private GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice device = env.getDefaultScreenDevice();
    final Frame frame;


    public DoubleBufferingFullScreen(Parameters params, Controller contrl) {
        controller = contrl;
        frame = new Frame();
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
        device.setFullScreenWindow(frame);
        painter = new CompositePainter(params.getActivePainters());

    }

    public void changeFrame() {
        Rectangle paintingRectangle = new Rectangle(0,0,device.getDisplayMode().getWidth(),device.getDisplayMode().getHeight());
        BufferedImage bufferedImage = device.getDefaultConfiguration().createCompatibleImage(paintingRectangle.width,paintingRectangle.height);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setClip(paintingRectangle);
        g.setColor(Color.green);
        //g.clearRect(paintingRectangle.x, paintingRectangle.y,paintingRectangle.width,paintingRectangle.height);
        painter.paint(g);
        Graphics2D frameG = (Graphics2D) frame.getGraphics();
        frameG.drawImage(bufferedImage,paintingRectangle.x,paintingRectangle.y, null);
        Toolkit.getDefaultToolkit().sync();
        frameG.dispose();
        g.dispose();
    }
}
