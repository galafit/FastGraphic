package com.example.fastgraphic.painters;

import java.util.List;
import java.awt.Graphics;

/**
 * Paint all selected graphics (sinus, line, etc)  together
 */
public class CompositePainter {

    private List<Painter> activePainters;

    public CompositePainter(List<Painter> activePainters) {
        this.activePainters = activePainters;
    }

    public void paint(Graphics g){
        for (Painter activePainter : activePainters) {
             activePainter.paint(g);
        }

    }

}
