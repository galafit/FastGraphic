package com.example.fastgraphic;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;


public class OptionsWindow extends JFrame {

    final Parameters params;

    private final JCheckBox sinusCheckBox = new JCheckBox("Sinus");
    JCheckBox lineCheckBox = new JCheckBox("Line");
    JCheckBox bgCheckBox = new JCheckBox("BG change");
    JCheckBox slowCheckBox = new JCheckBox("Slow Painting");

    JFormattedTextField frameRate = new JFormattedTextField(1000);
    JFormattedTextField frameShift = new JFormattedTextField(0.1f);

    JFormattedTextField widthField = new JFormattedTextField(1000);
    JFormattedTextField heightField = new JFormattedTextField(1000);
    JComboBox bgChoice = new JComboBox(AvailableColors.values());
    JComboBox fgChoice = new JComboBox(AvailableColors.values());
    JComboBox bufferingChoice = new JComboBox(BufferingType.values());
    private ButtonGroup toolButtonGroup;

    private JPanel composeLabelField(String label, Component component) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
    }

    public OptionsWindow(Parameters parameters) {
        params = parameters;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        widthField.setColumns(3);
        heightField.setColumns(3);
        frameRate.setColumns(2);
        frameShift.setColumns(2);
        // Panel with Radio Buttons to chose Tool
        JPanel toolPanel = new JPanel();
        toolButtonGroup = new ButtonGroup();
        for (GToolName gToolName : GToolName.values()) {
            JRadioButton radio = new JRadioButton(gToolName.getLabel());
            radio.setActionCommand(gToolName.name());
            toolPanel.add(radio);
            toolButtonGroup.add(radio);
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
        JPanel frameRatePanel = composeLabelField("Frame Rate   [perSec]", frameRate);
        JPanel frameShiftPanel = composeLabelField("Frame Shift  [pixels]", frameShift);
        framePanel.add(frameRatePanel, BorderLayout.NORTH);
        framePanel.add(frameShiftPanel, BorderLayout.CENTER);


        // Panel with Painting Options
        JPanel optionsPanel = new JPanel(new FlowLayout());
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Painting Options"));

        bgChoice.setSelectedItem(params.getBgColor());
        fgChoice.setSelectedItem(params.getFgColor());
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
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 20));
        modePanel.setBorder(BorderFactory.createTitledBorder("Paint Mode"));
        modePanel.add(bufferingChoice);

        JPanel containPanel = new JPanel();
        containPanel.setLayout(new GridLayout(2, 2, 20, 5));
        containPanel.add(graphPanel);
        containPanel.add(framePanel);
        containPanel.add(optionsPanel);
        containPanel.add(modePanel);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlsToParams(params);
                GToolFactory.getGTool(params).startAnimation();
            }
        });
        
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
                
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(exitButton);
        buttonPanel.add(startButton);


        add(toolPanel, BorderLayout.NORTH);
        add(containPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        paramsToControls(params);
        pack();
        setVisible(true);
    }

    public void paramsToControls(Parameters params) {
        sinusCheckBox.setSelected(params.isUseSinusPainter());
        lineCheckBox.setSelected(params.isUseLinePainter());
        bgCheckBox.setSelected(params.isUseBgFlipPainter());
        slowCheckBox.setSelected(params.isUseSlowPainter());
        frameRate.setValue(params.getFrameRate());
        frameShift.setValue(params.getFrameShift());
        widthField.setValue(params.getWidth());
        heightField.setValue(params.getHeight());
        bgChoice.setSelectedItem(params.getBgColor());
        fgChoice.setSelectedItem(params.getFgColor());
        bufferingChoice.setSelectedItem(params.getBufferingType());

        Enumeration<AbstractButton> buttonEnumeration = toolButtonGroup.getElements();
        while (buttonEnumeration.hasMoreElements()) {
            AbstractButton button =  buttonEnumeration.nextElement();
            if(button.getActionCommand().equals(params.getGTool().name())){
                button.setSelected(true);
            }
        }
    }

    public void controlsToParams(Parameters params) {
        params.setUseSinusPainter(sinusCheckBox.isSelected());
        params.setUseLinePainter(lineCheckBox.isSelected());
        params.setUseBgFlipPainter(bgCheckBox.isSelected());
        params.setUseSlowPainter(slowCheckBox.isSelected());

        params.setFrameRate((Integer)frameRate.getValue());
        params.setFrameShift((Float) frameShift.getValue());
        params.setWidth((Integer) widthField.getValue());
        params.setHeight((Integer) heightField.getValue());
        params.setBgColor((AvailableColors)bgChoice.getSelectedItem());
        params.setFgColor((AvailableColors)fgChoice.getSelectedItem());
        params.setBufferingType((BufferingType)bufferingChoice.getSelectedItem());

        Enumeration<AbstractButton> buttonEnumeration = toolButtonGroup.getElements();
        while (buttonEnumeration.hasMoreElements()) {
            AbstractButton button =  buttonEnumeration.nextElement();
            if(button.isSelected()){
                params.setGTool(GToolName.valueOf(button.getActionCommand()));
            }
        }
    }

    public static void main(String[] args) {
        new OptionsWindow(new Parameters());
    }

}


