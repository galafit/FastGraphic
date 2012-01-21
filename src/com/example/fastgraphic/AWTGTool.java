package com.example.fastgraphic;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Class to display animation using AWT
 */
public class AWTGTool implements GTool {

    private Parameters param;
    private final Canvas canvas = new Canvas();;
    private final  Frame frame = new Frame();

    public AWTGTool(Parameters param) {
        this.param = param;
        canvas.setPreferredSize(new Dimension(param.getWidth(), param.getHeight()));
        canvas.setBackground(param.getBgColor().getColor());
        canvas.setForeground(param.getFgColor().getColor());


        // close this window
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
        frame.setTitle(param.getGTool().getLabel());
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void startAnimation() {

    }
}
