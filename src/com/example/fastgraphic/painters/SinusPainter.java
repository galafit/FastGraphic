package com.example.fastgraphic.painters;

import com.example.fastgraphic.painters.Painter;

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
        Point previousPoint = new Point(0, height / 2);
        for (int i = 0; i < width; i++) {
            double x = (totalFrameShift +i) * 2 * Math.PI / width;
            double y = Math.sin(x);
            Point currentPoint = new Point(i, (int) Math.round(height * (1 - y) / 2));
            g.drawLine(previousPoint.x, previousPoint.y, currentPoint.x, currentPoint.y);
            previousPoint.x = currentPoint.x;
            previousPoint.y = currentPoint.y;
        }
        //drawing X-axis  in the middle of the painting area
        g.drawLine(0, height / 2, width, height / 2);
        totalFrameShift +=frameShift;
    }
}
