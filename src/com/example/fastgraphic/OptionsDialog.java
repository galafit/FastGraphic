package com.example.fastgraphic;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class OptionsDialog extends JDialog implements ActionListener {

    Parameters currentParameters = new Parameters();
    GTool chosenTool;

    private final JCheckBox sinusCheckBox = new JCheckBox("Sinus");
    JCheckBox lineCheckBox = new JCheckBox("Line");
    JCheckBox bgCheckBox = new JCheckBox("BG change");
    JCheckBox slowCheckBox = new JCheckBox("Slow Painting");

    JFormattedTextField frameRate = new JFormattedTextField(currentParameters.frameRate);
    JFormattedTextField  frameShift= new JFormattedTextField(currentParameters.frameShift);

   JFormattedTextField widthField = new JFormattedTextField(currentParameters.width);
    JFormattedTextField heightField = new JFormattedTextField(currentParameters.height);
    JComboBox bgChoice = new JComboBox(Parameters.availableColors);
    JComboBox fgChoice = new JComboBox(Parameters.availableColors);
    JComboBox bufferingChoice = new JComboBox(BufferingType.values());


    private JPanel composeLabelField(String label, Component component) {
        JPanel panel=new  JPanel();
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
        
    }

    //save the name of chosen RadioButton in the var "chosenTool"
    public void actionPerformed(ActionEvent e) {
       chosenTool = GTool.valueOf(e.getActionCommand());
    }

    public OptionsDialog() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Panel with Radio Buttons to chose Tool
        JPanel toolPanel = new JPanel();
        ButtonGroup toolChoice = new ButtonGroup();
        for (GTool gTool : GTool.values()) {
            JRadioButton radio = new JRadioButton(gTool.getLabel());
            radio.setActionCommand(gTool.name());
            radio.addActionListener(this);
            toolPanel.add(radio);
            toolChoice.add(radio);
        }

        // Panel with CheckBox Buttons  to choose graphics that will be painted
        JPanel graphPanel = new JPanel(new GridLayout(0, 2, 20, 5));
        graphPanel.setBorder(BorderFactory.createTitledBorder("Graphics"));
        graphPanel.add(sinusCheckBox);
        graphPanel.add(lineCheckBox);
        graphPanel.add(bgCheckBox);
        graphPanel.add(slowCheckBox);


        // Panel describing Frames Changing
        JPanel framePanel = new JPanel(new BorderLayout());
        framePanel.setBorder(BorderFactory.createTitledBorder("Frame Changing"));
        JPanel frameRatePanel=composeLabelField("Frame Rate   [perSec]", frameRate);
        JPanel frameShiftPanel=composeLabelField("Frame Shift  [pixels]",frameShift);
        framePanel.add(frameRatePanel,BorderLayout.NORTH);
        framePanel.add(frameShiftPanel,BorderLayout.CENTER);


        // Panel with Painting Options
        JPanel optionsPanel = new JPanel(new FlowLayout());
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Painting Options"));

        bgChoice.setSelectedItem(currentParameters.bgColor);
        fgChoice.setSelectedItem(currentParameters.fgColor);
        // Join Labels with the Fields
        JPanel bgChoicePanel = composeLabelField("BG Color", bgChoice);
        JPanel fgChoicePanel = composeLabelField("FG Color", fgChoice);
        JPanel widthPanel = composeLabelField("Width ", widthField);
        JPanel heightPanel = composeLabelField("Height", heightField);

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
        modePanel.add(bufferingChoice);

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


