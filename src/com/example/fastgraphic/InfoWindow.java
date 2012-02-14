package com.example.fastgraphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Window to show Text information. 
 */
public class InfoWindow extends JFrame{
    public InfoWindow(String title, ArrayList<String> textStrings) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(title);
        JTextArea textArea = new JTextArea(10,50);
// Set Line Wrap. If set to true the lines will be wrapped if they are too long to fit within the allocated width.
// If set to false, the lines will always be unwrapped.
// By default this property is false.
        textArea.setLineWrap(true);
// Set Wrap Style Word.
// If set to true the lines will be wrapped at word boundaries (whitespace) if they are too long to fit within the allocated width.
// If set to false, the lines will be wrapped at character boundaries.
// By default this property is false.
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JPanel textPanel = new JPanel();        
// Add Scroll Panel to the text Area. By default it does not have it.       
        textPanel.add(new JScrollPane(textArea));
// Add text to the TextArea. Every new String begin with the new Line and separated with empty line
        for(String currentString : textStrings){
            textArea.append("\n  "+currentString+"\n");
        }

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);

        getRootPane().setLayout(new BorderLayout());
        getRootPane().add(textPanel,BorderLayout.NORTH);
        getRootPane().add(buttonPanel,BorderLayout.SOUTH);
        pack();
        setVisible(true);
// put the window at the screen center
        setLocationRelativeTo(null);
    }
}
