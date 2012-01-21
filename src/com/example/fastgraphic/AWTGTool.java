package com.example.fastgraphic;

import javax.swing.*;
import java.awt.*;

/**
 * Class to display animation using AWT
 */
public class AWTGTool extends AbstractGtool {

    @Override
    protected PaintingArea getPaintingArea() {
        return new AWTPaintingArea();
    }

    public AWTGTool(Parameters param) {
        super(param);
    }

    @Override
    protected Frame getFrame() {
        return new JFrame();
    }

    class AWTPaintingArea extends Canvas implements PaintingArea{
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            painter.paint(g);
        }
    }
}

