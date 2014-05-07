package com.example.fastgraphic.painters;

import java.awt.*;

/**
 * Draw word "HELLO" slowly letter by letter
 * It is convenient to visually see whether Double Buffering is on or off
 */

public class SlowPainter implements Painter {
    private final int DELAY = 100; //delay between drawing operations in milliseconds
    int shift = 40;
    int totalShift;

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void paint(Graphics g) {
        int g_height = g.getClip().getBounds().height;
        int g_width = g.getClip().getBounds().width;
        totalShift += shift;
        if (totalShift > g_width - 6*shift) {
            totalShift = shift;
        }
        int string_x = totalShift;
        int string_y = g_height / 2;

        g.setFont(new Font("Helvetica", Font.BOLD, 30));
        g.drawString("H", string_x, string_y);
        sleep(DELAY);
        string_x += shift;
        g.drawString("E", string_x, string_y);
        sleep(DELAY);
        string_x += shift;
        g.drawString("L", string_x, string_y);
        sleep(DELAY);
        string_x += shift;
        g.drawString("L", string_x, string_y);
        sleep(DELAY);
        string_x += shift;
        g.drawString("O", string_x, string_y);
        sleep(DELAY);
    }
}
