package com.example.fastgraphic;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class AbstractGTool {
    protected Parameters params;
    protected final PaintingArea paintingArea = getPaintingArea();
    protected Frame frame = getFrame();

    protected abstract Frame getFrame();

    protected abstract PaintingArea getPaintingArea();

    protected Timer changeFrameTimer;

    public AbstractGTool(Parameters params) {
        this.params = params;
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                changeFrameTimer.stop();
            }
        });
        paintingArea.setPreferredSize(new Dimension(params.getWidth(), params.getHeight()));
        paintingArea.setBackground(params.getBgColor().getColor());
        paintingArea.setForeground(params.getFgColor().getColor());
        setPainters();
    }

    protected void setPainters() {
        if (params.isUseBgFlipPainter()) {
            paintingArea.addPainter(new BGFlipPainter());
        }
        if (params.isUseLinePainter()) {
            paintingArea.addPainter(new LinePainter(params.getFrameShift()));
        }
        if (params.isUseSinusPainter()) {
            paintingArea.addPainter(new SinusPainter(params.getFrameShift()));
        }
        if (params.isUseSlowPainter()) {
            paintingArea.addPainter(new SlowPainter());
        }

    }

    public void startAnimation() {
        int delay = 1000 / params.getFrameRate();
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                paintingArea.repaint();
            }
        };
        changeFrameTimer = new Timer(delay, taskPerformer);
        changeFrameTimer.start();
    }
}
