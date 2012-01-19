package com.example.fastgraphic;

import javax.swing.*;
import java.awt.*;

//hello sasha

/**
 * Panel to set parameters like fps, dimension, etc.
 */
public class OptionsPanel extends JPanel {
    public OptionsPanel() {
        GridLayout gl = new GridLayout(3,4);
        gl.setHgap(15);
        setLayout(gl);

        add(new JLabel("delay: "));
        add(new JFormattedTextField(16));

        add(new JLabel("change colors"));
        add(new JCheckBox());

        add(new JLabel("x size : "));
        add(new JFormattedTextField(640));

        add(new JLabel("display sinus"));
        add(new JCheckBox());

        add(new JLabel("y size: "));
        add(new JFormattedTextField(480));

        add(new JLabel("dX:"));
        add(new JFormattedTextField(0.5f));


        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("options"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }
}
