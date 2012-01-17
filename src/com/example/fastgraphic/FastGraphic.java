package com.example.fastgraphic;


import javax.swing.*;
import java.awt.*;

public class FastGraphic {


    public static void main(String[] args) {

        JFrame f = new JFrame("Fast Graphic Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        f.add(mainPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        mainPanel.setLayout(new GridLayout(0,1));

        final JPanel optionsPanel = new OptionsPanel();
        mainPanel.add(optionsPanel);

        final JPanel exampleSelectionPanel = new ExampleSelectionPanel();
        mainPanel.add(exampleSelectionPanel);

        f.pack();
        f.setVisible(true);
    }

}