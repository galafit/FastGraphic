package com.example.fastgraphic;


import javax.swing.*;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGTool {
    protected Parameters params;
    protected final PaintingArea paintingArea = getPaintingArea();
    protected Frame frame = getFrame();
    protected List<Painter> painters = new ArrayList<Painter>();

    protected abstract Frame getFrame();

    protected abstract PaintingArea getPaintingArea();

    protected Timer changeFrameTimer;

    public AbstractGTool(Parameters params) {
        this.params = params;
        frame.setTitle(params.getGTool().getLabel());
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
        frame.add((Component) paintingArea);
        frame.pack();
        frame.setVisible(true);
    }


    protected void setPainters() {
        if (params.isUseBgFlipPainter()) {
            painters.add(new BGFlipPainter());
        }
        if (params.isUseLinePainter()) {
            painters.add(new LinePainter(params.getFrameShift()));
        }
        if (params.isUseSinusPainter()) {
            painters.add(new SinusPainter(params.getFrameShift()));
        }
        if (params.isUseSlowPainter()) {
            painters.add(new SlowPainter());
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
