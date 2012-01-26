package com.example.fastgraphic.painters;

import com.example.fastgraphic.BGFlipPainter;
import com.example.fastgraphic.Parameters;
import com.example.fastgraphic.painters.LinePainter;
import com.example.fastgraphic.painters.Painter;
import com.example.fastgraphic.painters.SinusPainter;
import com.example.fastgraphic.painters.SlowPainter;

import java.util.ArrayList;
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
