package com.example.fastgraphic.painters;

import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;

/**
 *
 * CompoundPainter  allows  to add multiple painter implementations to a component.
 * The painters will be rendered in the order in which they are added to the CompoundPainter.
 *
 */

public class CompoundPainter implements Painter{

    private List<Painter> activePainters;

    public CompoundPainter(){
        activePainters = new ArrayList<Painter>();
    }


    public CompoundPainter(List<Painter> painters) {
        activePainters = painters;
    }

    public void addPainter(Painter painter){
        activePainters.add(painter);
    }

    public void paint(Graphics g){
        for (Painter activePainter : activePainters) {
             activePainter.paint(g);
        }

    }

}
