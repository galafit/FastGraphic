package com.example.fastgraphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to display animation using AWT
 */
public class AWTGTool implements GTool {

    private Parameters param;
    private final AWTCanvas canvas = new AWTCanvas();
    private final Frame frame = new Frame();
    private Timer changeFrameTimer;

    public AWTGTool(Parameters param) {
        this.param = param;
        canvas.setPreferredSize(new Dimension(param.getWidth(), param.getHeight()));
        canvas.setBackground(param.getBgColor().getColor());
        canvas.setForeground(param.getFgColor().getColor());
        setPainters();

        // close this window
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                changeFrameTimer.stop();
            }
        });
        frame.setTitle(param.getGTool().getLabel());
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    private void setPainters() {
        if (param.isUseBgFlipPainter()) {
            canvas.addPainter(new BGFlipPainter());
        }
        if (param.isUseLinePainter()) {
            canvas.addPainter(new LinePainter(param.getFrameShift()));
        }
        if (param.isUseSinusPainter()) {
            canvas.addPainter(new SinusPainter(param.getFrameShift()));
        }
        if (param.isUseSlowPainter()) {
            canvas.addPainter(new SlowPainter());
        }

    }

    public void startAnimation() {
        int delay = 1000 / param.getFrameRate();

        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                canvas.repaint();
            }
        };
        changeFrameTimer = new Timer(delay, taskPerformer);
        changeFrameTimer.start();
    }
}

class AWTCanvas extends Canvas {

    private List<Painter> painters = new ArrayList<Painter>();

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Painter painter : painters) {
            painter.paint(g);
        }
    }

    public void addPainter(Painter painter) {
        painters.add(painter);
    }
}
