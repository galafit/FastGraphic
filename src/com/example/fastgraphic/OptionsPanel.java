package com.example.fastgraphic;

import javax.swing.*;
import java.awt.*;

/**
 *   Panel to set parameters like fps, dimension, etc.
 */
public class OptionsPanel extends JPanel{
    public OptionsPanel() {
        super(new GridLayout(3, 1));

        add(new JLabel("fps: "));
        JTextField fps = new JTextField("60");
        add(fps);

        add (new JLabel("x size: "));
        JTextField xSize = new JTextField("640");
        add(xSize);

        add (new JLabel("y size: "));
        JTextField ySize = new JTextField("480");
        add(ySize);

        setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("options"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
    }
}
