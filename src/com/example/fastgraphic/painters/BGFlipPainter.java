package com.example.fastgraphic.painters;

import com.example.fastgraphic.painters.Painter;

import java.awt.*;

/**
 * Change background color RED-BLUE-RED-BLUE... every time we call paint-method
 */

public class BGFlipPainter implements Painter {

    private boolean colorFlag;
    private Color firstColor = Color.RED;
    private Color secondColor = Color.BLUE;

    public void paint(Graphics g) {
        Color initialColor = g.getColor();
        changeColor(g);
        int width = g.getClipBounds().width;
        int height = g.getClipBounds().height;
        g.fillRect(0,0,width,height);
        g.setColor(initialColor);
    }

    private void changeColor(Graphics g) {
        colorFlag = !colorFlag;
        if(colorFlag){
            g.setColor(firstColor);
        } else {
            g.setColor(secondColor);
        }
    }

}
