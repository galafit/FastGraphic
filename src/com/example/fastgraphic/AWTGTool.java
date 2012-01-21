package com.example.fastgraphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to display animation using AWT
 */
public class AWTGTool extends AbstractGTool {

    @Override
    protected PaintingArea getPaintingArea() {
        return new AWTPaintingArea();
    }

    public AWTGTool(Parameters param) {
        super(param);
        frame.setTitle(param.getGTool().getLabel());
        frame.add((Canvas)paintingArea);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected Frame getFrame() {
        return new JFrame();
    }
}

class AWTPaintingArea extends Canvas implements PaintingArea{

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
