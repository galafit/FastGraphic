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

        mainPanel.setLayout(new BorderLayout());

        final JPanel exampleSelectionPanel = new ExampleSelectionPanel();
        mainPanel.add(exampleSelectionPanel, BorderLayout.LINE_START);

        final JPanel optionsPanel = new OptionsPanel();
        mainPanel.add(optionsPanel,BorderLayout.LINE_END);

        final JButton startButton = new JButton("Start");
        mainPanel.add(startButton,BorderLayout.PAGE_END);

        f.pack();
        f.setVisible(true);
    }

}