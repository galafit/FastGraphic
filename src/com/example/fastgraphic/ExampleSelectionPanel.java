package com.example.fastgraphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * RadioButtons panel to select the example to start
 */
public class ExampleSelectionPanel extends JPanel implements ActionListener{

    public ExampleSelectionPanel() {
        super(new GridLayout(0, 1));

//        setPreferredSize(new Dimension(300, 500));

        JRadioButton simpleButton = new JRadioButton("Simple");
        simpleButton.setActionCommand("simple");
        simpleButton.setSelected(true);

        JRadioButton simpleDubBufButton = new JRadioButton("Simple double buffering");
        simpleDubBufButton.setMnemonic(KeyEvent.VK_C);
        simpleDubBufButton.setActionCommand("simple__dubbuf");

        JRadioButton activeRendering = new JRadioButton("Active rendering");
        activeRendering.setMnemonic(KeyEvent.VK_D);
        activeRendering.setActionCommand("active_rendering");

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(simpleButton);
        group.add(simpleDubBufButton);
        group.add(activeRendering);

        //Register a listener for the radio buttons.
        simpleButton.addActionListener(this);
        simpleDubBufButton.addActionListener(this);
        activeRendering.addActionListener(this);

        add(simpleButton);
        add(simpleDubBufButton);
        add(activeRendering);

        setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("test to start"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println(e);
    }
}
