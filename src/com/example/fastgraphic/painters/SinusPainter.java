package com.example.fastgraphic.painters;

import com.example.fastgraphic.painters.Painter;

import java.awt.*;

/**
 * Draw "running sinus". Every time we call paint-method  sinus is shifted "frameShift" pixels to the left
 */

public class SinusPainter implements Painter {

    float frameShift; //in pixels
    float totalFrameShift;//in pixels

    public SinusPainter(float frameShift) {
        this.frameShift = frameShift;
    }

    public void paint(Graphics g) {
        int width = g.getClipBounds().width;
        int height = g.getClipBounds().height-1;
        Point previousPoint=null;
        for (int i = 0; i < width; i++) {
            double x = (totalFrameShift +i) * 2 * Math.PI / width;
            double y = Math.sin(x);
            Point currentPoint = new Point(i, (int) Math.round(height * (1 - y) / 2));
            if(previousPoint==null)
            {
                previousPoint = new Point(currentPoint.x,currentPoint.y);
            }
            g.drawLine(previousPoint.x, previousPoint.y, currentPoint.x, currentPoint.y);
            previousPoint.x = currentPoint.x;
            previousPoint.y = currentPoint.y;
        }
        //drawing X-axis  in the middle of the painting area
        g.drawLine(0, height / 2, width, height / 2);
        totalFrameShift +=frameShift;
    }
}
