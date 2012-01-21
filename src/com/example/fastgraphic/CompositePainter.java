package com.example.fastgraphic;

import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;

/**
 * Paint all selected graphics (sinus, line, etc)  together
 */
public class CompositePainter {
    private Parameters params;
    private List<Painter> activePainters = new ArrayList<Painter>();

    public CompositePainter(Parameters params) {
        this.params = params;
        if (params.isUseBgFlipPainter()) {
           activePainters.add(new BGFlipPainter());
        }
        if (params.isUseLinePainter()) {
             activePainters.add(new LinePainter(params.getFrameShift()));
        }
        if (params.isUseSinusPainter()) {
             activePainters.add(new SinusPainter(params.getFrameShift()));
        }
        if (params.isUseSlowPainter()) {
             activePainters.add(new SlowPainter());
        }
    }

    public void paint(Graphics g){
        for (Painter activePainter : activePainters) {
             activePainter.paint(g);
        }

    }

}
