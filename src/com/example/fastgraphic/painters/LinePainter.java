package com.example.fastgraphic.painters;

import java.awt.*;

/**
 * Draw "running vertical line". Every time we call this method Line is shifted "shift" pixels to the left
 */

public class LinePainter implements Painter {
    float shift;
    float totalShift;

    public LinePainter(float shift) {
        this.shift = shift;
    }

    public void paint(Graphics g) {
        int g_height = g.getClip().getBounds().height;
        int g_width = g.getClip().getBounds().width;
        totalShift += shift;
        int intTotalShift = (int) totalShift;
        if (intTotalShift > g_width) {
            totalShift = 0;
        }
        g.drawLine(intTotalShift, 0, intTotalShift, g_height);
    }
}
