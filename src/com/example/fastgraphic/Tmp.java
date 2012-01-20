package com.example.fastgraphic;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class Tmp {

    static final int X_SIZE = 700, Y_SIZE = 600;
    static final Color BACKGROUND_COLOR = Color.BLACK;
    static final Color FORGROUND_COLOR = Color.green;



    static final String[] modeNames = {"sinus", "line", "background", "slow"};

    static int timerDelay = 100; //milliseconds   dalay for Timer controling frame change
    static int paintingMode;
    static int repaintNum;
    static int graphShift;

    static void paint(Graphics g) {
        switch (paintingMode) {
            case 0:
                paintSinus(g);
                break;
            case 1:
                paintHorizontalLine(g);
                break;
            case 2:
                changeBackground(g);
                break;
            case 3:
                paintSlow(g);
                break;
            default:
                paintSinus(g);
                break;
        }
    }

    static void paintSinus(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int g_width = g.getClip().getBounds().width;
        int g_height = g.getClip().getBounds().height;
        Point previousPoint = new Point(0, g_height / 2);
        for (int i = 0; i < g_width; i++) {
            double x = 2 * Math.PI * (i + graphShift) / g_width;
            double y = Math.sin(x);
            Point currentPoint = new Point(i, (int) Math.round(g_height * (1 - y) / 2));
            int pixelY = (int) Math.round(g_height * (1 - y) / 2);
            g2d.drawLine(previousPoint.x, previousPoint.y, currentPoint.x, currentPoint.y);

            previousPoint.x = currentPoint.x;
            previousPoint.y = currentPoint.y;

        }

        g2d.drawLine(0, g_height / 2, g_width, g_height / 2);

    }

    static void paintHorizontalLine(Graphics g) {
        int g_height = g.getClip().getBounds().height;
//—É—Ä–µ–∑–∞–µ–º —Å–¥–≤–∏–≥ —á—Ç–æ–±—ã –ª–∏–Ω–∏—è –Ω–µ —É–±–µ–≥–∞–ª–∞ –∑–∞ –≥—Ä–∞–Ω–∏—Ü—ã –æ–±–ª–∞—Å—Ç–∏ —Ä–∏—Å–æ–≤–∞–Ω–∏—è
        int shift = graphShift - (graphShift / g_height) * g_height;
        g.drawLine(0, shift, g.getClip().getBounds().width, shift);
    }

    static void changeBackground(Graphics g) {
        if (repaintNum % 2 == 0) {
            g.setColor(FORGROUND_COLOR);
        } else {
            g.setColor(BACKGROUND_COLOR);
        }
        g.fillRect(0, 0, g.getClip().getBounds().width, g.getClip().getBounds().height);
    }

    static void paintSlow(Graphics g) {
        final int DELAY = 1000; //–∑–∞–¥–µ—Ä–∂–∫–∞ –º–µ–∂–¥—É –æ—Ç—Ä–∏—Å–æ–≤–∫–æ–π —ç–ª–µ–º–µ–Ω—Ç–æ–≤ –º—Å–µ–∫
        int g_width = g.getClip().getBounds().width;
        int g_height = g.getClip().getBounds().height;
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException x) {
        }
        g.setColor(Color.red);
        g.fillRect(0, 0, g_width / 2, g_height / 2);
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException x) {
        }
        g.setColor(Color.blue);
        g.fillOval(0, 0, g_width / 4, g_height / 4);
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException x) {
        }
        g.setColor(Color.YELLOW);
        g.drawString("Slow Painting", 10, 10);
    }

}
