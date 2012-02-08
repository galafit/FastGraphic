package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompositePainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: galafit
 * Date: 05/02/12
 * Time: 22:48
 * To change this template use File | Settings | File Templates.
 */
public class ActiveRenderingDoubleBufferring implements PaintingArea {
    public static final int TWO_BUFFERS = 2;
    private Parameters params;
    private JFrame frame = new JFrame();
    private CompositePainter painter;
    private Canvas paintingCanvas;
    private Controller controller;


    public ActiveRenderingDoubleBufferring(Parameters params, Controller contrl) {
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
    }

    public void changeFrame() {
        Rectangle paintingRectangle = paintingCanvas.getBounds();
        BufferedImage bufferedImage = new BufferedImage(paintingRectangle.width,paintingRectangle.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setClip(paintingRectangle);
        //g.clearRect(paintingRectangle.x, paintingRectangle.y,paintingRectangle.width,paintingRectangle.height);
        painter.paint(g);
        Graphics2D frameG = (Graphics2D) paintingCanvas.getGraphics();
        frameG.drawImage(bufferedImage,paintingRectangle.x,paintingRectangle.y, null);
        Toolkit.getDefaultToolkit().sync();
        frameG.dispose();
        g.dispose();
    }
}
