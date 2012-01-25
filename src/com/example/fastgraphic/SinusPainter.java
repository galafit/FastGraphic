package com.example.fastgraphic;

import java.awt.*;

/**
 *   Draw sinus
 */
public class SinusPainter implements Painter {

    float frameShift; //in pixels
    float totalFrameShift=0;//in pixels

    public SinusPainter(float frameShift) {
        this.frameShift = frameShift;
    }

    public void paint(Graphics g) {
        int width = g.getClipBounds().width;
        int height = g.getClipBounds().height;
        int prevPixelX = 0, prevPixelY = width / 2;
        for (int i = 0; i < width; i++) {
            double x = (totalFrameShift +i) * 2 * Math.PI / width;
            double y = Math.sin(x);

            int pixelY = (int) Math.round(height * (1 - y) / 2);
            g.drawLine(prevPixelX, prevPixelY, i, pixelY);
            prevPixelX = i;
            prevPixelY = pixelY;
        }
        g.drawLine(0, height / 2, width, height / 2);
        totalFrameShift +=frameShift;
    }
}
