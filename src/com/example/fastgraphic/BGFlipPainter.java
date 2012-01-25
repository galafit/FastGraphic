package com.example.fastgraphic;

import java.awt.*;

/**
 *
 */
public class BGFlipPainter implements Painter {

    private boolean colorFlag;
    private Color firstColor = Color.RED;
    private Color secondColor = Color.BLUE;

    public void paint(Graphics g) {
        changeColor(g);
        int width = g.getClipBounds().width;
        int height = g.getClipBounds().height;
        g.fillRect(0,0,width,height);
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
