package com.example.fastgraphic;


import javax.swing.*;
import java.awt.*;

public class FastGraphic {


    public static void main(String[] args) {

        JFrame f = new JFrame("Fast Graphic Test");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(640,480));
        f.add(jPanel);
        f.pack();
        f.setVisible(true);
    }

}