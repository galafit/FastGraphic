package com.example.fastgraphic;

import java.awt.*;

/**
 * Paint all selected graphics (sinus, line, etc)  together
 */
public class CompositePainter {
    private Parameters params;
    LinePainter linePainter;
    SinusPainter sinusPainter;
    BGFlipPainter bgFlipPainter;
    SlowPainter slowPainter;

    public CompositePainter(Parameters params) {
        this.params = params;
        linePainter = new LinePainter(params.getFrameShift());
        sinusPainter = new SinusPainter(params.getFrameShift());
        bgFlipPainter = new BGFlipPainter();
        slowPainter = new SlowPainter();
    }

    public void paint(Graphics g){
        if (params.isUseBgFlipPainter()) {
            bgFlipPainter.paint(g);
        }
        if (params.isUseLinePainter()) {
            linePainter.paint(g);
        }
        if (params.isUseSinusPainter()) {
            sinusPainter.paint(g);
        }
        if (params.isUseSlowPainter()) {
            slowPainter.paint(g);
        }

    }

}
