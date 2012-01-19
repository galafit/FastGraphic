package com.example.fastgraphic;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OptionsDialog extends JDialog implements ActionListener {

    Parameters currentParameters = new Parameters();
    String toolNames[] = {"AWT", "SWING", "FullScreen", "OpenGL", "Video Player"};
    String chosenTool = toolNames[0];


    //save the name of chosen RadioButton in the var "chosenTool"
    public void actionPerformed(ActionEvent e) {
        chosenTool = e.getActionCommand();
    }

    public OptionsDialog() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Panel with Radio Buttons to chose Tool
        JPanel toolPanel = new JPanel();
        ButtonGroup toolChoice = new ButtonGroup();
        for (int i = 0; i < toolNames.length; i++) {
            JRadioButton radio = new JRadioButton(toolNames[i]);
            if (i == 0) {
                radio.setSelected(true);
            }
            radio.setActionCommand(toolNames[i]);
            radio.addActionListener(this);
            toolPanel.add(radio);
            toolChoice.add(radio);
        }

        // Panel with CheckBox Buttons  to choose graphics that will be painted
        JPanel graphPanel = new JPanel(new GridLayout(0, 2, 20, 5));
        graphPanel.setBorder(BorderFactory.createTitledBorder("Graphics"));
        JCheckBox sinusCheckBox = new JCheckBox("Sinus", true);
        JCheckBox lineCheckBox = new JCheckBox("Line");
        JCheckBox bgCheckBox = new JCheckBox("BG change");
        JCheckBox slowCheckBox = new JCheckBox("Slow Painting");

        graphPanel.add(sinusCheckBox);
        graphPanel.add(lineCheckBox);
        graphPanel.add(bgCheckBox);
        graphPanel.add(slowCheckBox);


        // Panel describing Frames Changing
        JPanel framePanel = new JPanel(new BorderLayout());
        framePanel.setBorder(BorderFactory.createTitledBorder("Frame Changing"));
        JTextField frameRate = new JTextField(String.valueOf(currentParameters.frameRate), 3);
        JTextField  frameShift= new JTextField(String.valueOf(currentParameters.frameShift), 3);
        JPanel frameRatePanel=new LabeledFieldPanel("Frame Rate   [perSec]",frameRate);
        JPanel frameShiftPanel=new LabeledFieldPanel("Frame Shift  [pixels]",frameShift);
        
        framePanel.add(frameRatePanel,BorderLayout.NORTH);
        framePanel.add(frameShiftPanel,BorderLayout.CENTER);


        // Panel with Painting Options
        JPanel optionsPanel = new JPanel(new FlowLayout());
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Painting Options"));
        JTextField widthField = new JTextField(String.valueOf(currentParameters.width),3);
        JTextField heightField = new JTextField(String.valueOf(currentParameters.height),3);
        JComboBox bgChoice = new JComboBox(Parameters.availableColors);
        JComboBox fgChoice = new JComboBox(Parameters.availableColors);
        bgChoice.setSelectedItem(currentParameters.bgColor);
        fgChoice.setSelectedItem(currentParameters.fgColor);
        // Join Labels with the Fields
        JPanel bgChoicePanel = new LabeledFieldPanel("BG Color", bgChoice);
        JPanel fgChoicePanel = new LabeledFieldPanel("FG Color", fgChoice);
        JPanel widthPanel = new LabeledFieldPanel("Width ", widthField);
        JPanel heightPanel = new LabeledFieldPanel("Height", heightField);

        JPanel colorPanel = new JPanel(new BorderLayout());
        colorPanel.add(fgChoicePanel, BorderLayout.NORTH);
        colorPanel.add(bgChoicePanel, BorderLayout.CENTER);

        JPanel sizePanel = new JPanel(new BorderLayout());
        sizePanel.add(widthPanel, BorderLayout.NORTH);
        sizePanel.add(heightPanel, BorderLayout.CENTER);

        optionsPanel.add(colorPanel);
        optionsPanel.add(sizePanel);

         // Panel to chose the mode of Painting.
         // For the Tools  "OpenGL" and "Video Player" it will be disabled
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,20));
        modePanel.setBorder(BorderFactory.createTitledBorder("Paint Mode"));
        String modes[] = {"Normal", "Double Buffering", "GraphCard Double Buffering"};
        JComboBox modeChoice = new JComboBox(modes);
        modePanel.add(modeChoice);

        JPanel containPanel = new JPanel();
        containPanel.setLayout(new GridLayout(2, 2, 20, 5));
        containPanel.add(graphPanel);
        containPanel.add(framePanel);
        containPanel.add(optionsPanel);
        containPanel.add(modePanel);

        JButton okButton = new JButton("OK");
        JPanel okPanel = new JPanel();
        okPanel.add(okButton);


        add(toolPanel, BorderLayout.NORTH);
        add(containPanel, BorderLayout.CENTER);
        add(okPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);

    }

    public static void main(String[] args) {
        new OptionsDialog();
    }

}

