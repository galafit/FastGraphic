package com.example.fastgraphic;

import java.awt.*;

/**
 * Paint running Vertical Line
 */

public class LinePainter implements Painter {
    Parameters param;
    float totalShift;

    public LinePainter(Parameters param) {
        this.param = param;
    }

    public void paint(Graphics g) {
        int g_height = g.getClip().getBounds().height;
        int g_width = g.getClip().getBounds().width;
        totalShift += param.getFrameShift();
        int intTotalShift = (int) totalShift;
        if (intTotalShift > g_width) {
            totalShift = 0;
        }
        g.drawLine(intTotalShift, 0, intTotalShift, g_height);
    }
}
