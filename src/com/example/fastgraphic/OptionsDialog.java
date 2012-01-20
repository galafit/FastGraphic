package com.example.fastgraphic;


import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;


public class OptionsDialog extends JDialog {

    Parameters parameters = new Parameters();

    private final JCheckBox sinusCheckBox = new JCheckBox("Sinus");
    JCheckBox lineCheckBox = new JCheckBox("Line");
    JCheckBox bgCheckBox = new JCheckBox("BG change");
    JCheckBox slowCheckBox = new JCheckBox("Slow Painting");

    JFormattedTextField frameRate = new JFormattedTextField(parameters.getFrameRate());
    JFormattedTextField frameShift = new JFormattedTextField(parameters.getFrameShift());

    JFormattedTextField widthField = new JFormattedTextField(parameters.getWidth());
    JFormattedTextField heightField = new JFormattedTextField(parameters.getHeight());
    JComboBox bgChoice = new JComboBox(Parameters.availableColors);
    JComboBox fgChoice = new JComboBox(Parameters.availableColors);
    JComboBox bufferingChoice = new JComboBox(BufferingType.values());
    private ButtonGroup toolButtonGroup;

    private JPanel composeLabelField(String label, Component component) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
    }

    public OptionsDialog() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Panel with Radio Buttons to chose Tool
        JPanel toolPanel = new JPanel();
        toolButtonGroup = new ButtonGroup();
        for (GTool gTool : GTool.values()) {
            JRadioButton radio = new JRadioButton(gTool.getLabel());
            radio.setActionCommand(gTool.name());
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

        bgChoice.setSelectedItem(parameters.getBgColor());
        fgChoice.setSelectedItem(parameters.getFgColor());
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

        JButton okButton = new JButton("OK");
        JPanel okPanel = new JPanel();
        okPanel.add(okButton);


        add(toolPanel, BorderLayout.NORTH);
        add(containPanel, BorderLayout.CENTER);
        add(okPanel, BorderLayout.SOUTH);
        paramsToControls(parameters);
        pack();
        setVisible(true);
    }

    public void paramsToControls(Parameters params) {
        sinusCheckBox.setSelected(params.isUseSinusPinter());
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
        throw new UnsupportedOperationException("todo");
    }

    public static void main(String[] args) {
        new OptionsDialog();
    }

}


