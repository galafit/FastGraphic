package com.example.fastgraphic;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;


public class OptionsWindow extends JFrame {

    final Parameters params;
    private final Controller controller = new Controller();

    private final JCheckBox sinusCheckBox = new JCheckBox("Sinus");
    JCheckBox lineCheckBox = new JCheckBox("Line");
    JCheckBox bgCheckBox = new JCheckBox("BG change");
    JCheckBox slowCheckBox = new JCheckBox("Slow Painting");

    JCheckBox isSwingDoubleBuff = new JCheckBox("Double Buffering");
    JCheckBox isFullScreen = new JCheckBox("Full Screen");

    JFormattedTextField frameRate = new JFormattedTextField(1000);
    JFormattedTextField frameShift = new JFormattedTextField(0.1f);

    JFormattedTextField widthField = new JFormattedTextField(1000);
    JFormattedTextField heightField = new JFormattedTextField(1000);
    JComboBox bgChoice = new JComboBox(AvailableColors.values());
    JComboBox fgChoice = new JComboBox(AvailableColors.values());

    private ButtonGroup toolButtonGroup;

    private JPanel composeLabelField(String label, Component component) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
    }
    
    private JPanel titlePanel (String title, JPanel panel){
        JPanel titledPanel =  new JPanel(new FlowLayout(FlowLayout.CENTER,5,10));
        titledPanel.setBorder(BorderFactory.createTitledBorder(title));
        titledPanel.add(panel);
        return titledPanel;
    }

    public OptionsWindow(Parameters parameters) {
        params = parameters;
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        widthField.setColumns(3);
        heightField.setColumns(3);
        frameRate.setColumns(2);
        frameShift.setColumns(2);
        
        // Panel with Radio Buttons to chose Tool
        JPanel toolPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,15));
        toolPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        toolButtonGroup = new ButtonGroup();
        HashMap<PaintingAreaName,JPanel> radioButtonHashMap= new HashMap<PaintingAreaName,JPanel>();
        for (PaintingAreaName paintingAreaName : PaintingAreaName.values()) {
            JRadioButton radio = new JRadioButton(paintingAreaName.getLabel());
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
            buttonPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            buttonPanel.add(radio);
            radio.setActionCommand(paintingAreaName.name());
// in Panel of SWING radio button we add the checkBox "isSwingDoubleBuff" to permit to chose whether the SWING Application
// will work with Double Buffering "On" or "Off".
// The ActionListeners control that this checkbox will be enabled only when SWING mode is chosen
//
// in Panel of PAGE_FLIPPING radio button we add the checkBox "isFullScreen" to permit to chose whether the PageFlipping Application
// will work with en Full Screen or Window mode.
// The ActionListeners control that this checkbox will be enabled only when PAGE_FLIPPING mode is chosen
            if(paintingAreaName.equals(PaintingAreaName.SWING)){
                buttonPanel.add(isSwingDoubleBuff);
                radio.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                       isSwingDoubleBuff.setSelected(true);
                       isSwingDoubleBuff.setEnabled(true);

                        isFullScreen.setSelected(false);
                        isFullScreen.setEnabled(false);
                    }
                });
            }
            if(paintingAreaName.equals(PaintingAreaName.PAGE_FLIPPING)){
                buttonPanel.add(isFullScreen);
                radio.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        isFullScreen.setSelected(true);
                        isFullScreen.setEnabled(true);

                        isSwingDoubleBuff.setSelected(false);
                        isSwingDoubleBuff.setEnabled(false);
                    }
                });
            }
            else{
                radio.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        isFullScreen.setSelected(false);
                        isFullScreen.setEnabled(false);
                        isSwingDoubleBuff.setSelected(false);
                        isSwingDoubleBuff.setEnabled(false);
                    }
                });
            }

            toolButtonGroup.add(radio);
            radioButtonHashMap.put(paintingAreaName,buttonPanel);
        }

        JPanel passiveRenderingPanel = new JPanel(new BorderLayout(0,3));
        passiveRenderingPanel.add(radioButtonHashMap.get(PaintingAreaName.AWT),BorderLayout.SOUTH);
        passiveRenderingPanel.add(radioButtonHashMap.get(PaintingAreaName.SWING),BorderLayout.NORTH);

        JPanel activeRenderingPanel = new JPanel(new BorderLayout(0,3));
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        groupPanel.add(radioButtonHashMap.get(PaintingAreaName.DIRECT));
        groupPanel.add(radioButtonHashMap.get(PaintingAreaName.DOUBLE_BUFF));
        groupPanel.add(radioButtonHashMap.get(PaintingAreaName.ACCELERATED_BUFF));
        activeRenderingPanel.add(groupPanel,BorderLayout.SOUTH);
        activeRenderingPanel.add(radioButtonHashMap.get(PaintingAreaName.PAGE_FLIPPING),BorderLayout.NORTH);
        
        JPanel examplePanel = new JPanel(new BorderLayout(0,3));
        examplePanel.add(radioButtonHashMap.get(PaintingAreaName.OPEN_GL),BorderLayout.NORTH);
        examplePanel.add(radioButtonHashMap.get(PaintingAreaName.VLCJ),BorderLayout.SOUTH);
        
        toolPanel.add(titlePanel("Passive Rendering",passiveRenderingPanel));
        toolPanel.add(titlePanel("Active Rendering",activeRenderingPanel));
        toolPanel.add(titlePanel("Additional",examplePanel));


        // Panel describing Frames Changing
        JPanel framePanel = new JPanel(new BorderLayout());
        framePanel.setBorder(BorderFactory.createTitledBorder("Frame Changing"));
        JPanel frameRatePanel = composeLabelField("Frame Rate   [perSec]", frameRate);
        JPanel frameShiftPanel = composeLabelField("Frame Shift  [pixels]", frameShift);
        framePanel.add(frameRatePanel, BorderLayout.NORTH);
        framePanel.add(frameShiftPanel, BorderLayout.CENTER);

        // Panel with CheckBox Buttons  to choose graphics that will be painted
        JPanel graphPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        graphPanel.setBorder(BorderFactory.createTitledBorder("Graphics"));
        graphPanel.add(sinusCheckBox);
        graphPanel.add(lineCheckBox);
        graphPanel.add(bgCheckBox);
        graphPanel.add(slowCheckBox);

        // Panel with Painting Options
        JPanel paintingOptionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paintingOptionsPanel.setBorder(BorderFactory.createTitledBorder("Painting Options"));

        bgChoice.setSelectedItem(params.getBgColor());
        fgChoice.setSelectedItem(params.getFgColor());

        JPanel colorPanel = new JPanel(new BorderLayout());
        colorPanel.add(composeLabelField("FG Color", fgChoice), BorderLayout.NORTH);
        colorPanel.add(composeLabelField("BG Color", bgChoice), BorderLayout.CENTER);

        JPanel sizePanel = new JPanel(new BorderLayout());
        sizePanel.add(composeLabelField("Width ", widthField), BorderLayout.NORTH);
        sizePanel.add(composeLabelField("Height", heightField), BorderLayout.CENTER);

        paintingOptionsPanel.add(colorPanel);
        paintingOptionsPanel.add(sizePanel);

        // Panel that join all Graphics and Painting Options
        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.add(graphPanel,BorderLayout.WEST);
        optionsPanel.add(framePanel,BorderLayout.CENTER);
        optionsPanel.add(paintingOptionsPanel,BorderLayout.EAST);

        // Panel with buttons "Start" and "Exit"
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controlsToParams(params);
                controller.start(params);
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

        // Root Panel of the OptionsWindow
        getRootPane().setLayout(new BorderLayout(0, 20));
        getRootPane().add(toolPanel, BorderLayout.NORTH);
        getRootPane().add(optionsPanel, BorderLayout.CENTER);
        getRootPane().add(buttonPanel, BorderLayout.SOUTH);

        paramsToControls(params);
        pack();
        setVisible(true);
        // put the window at the screen center
        setLocationRelativeTo(null);
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

        isSwingDoubleBuff.setSelected(params.isSwingDoubleBuff());
        isFullScreen.setSelected(params.isFullScreen());

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

        params.setSwingDoubleBuff(isSwingDoubleBuff.isSelected());
        params.setFullScreen(isFullScreen.isSelected());

        Enumeration<AbstractButton> buttonEnumeration = toolButtonGroup.getElements();
        while (buttonEnumeration.hasMoreElements()) {
            AbstractButton button =  buttonEnumeration.nextElement();
            if(button.isSelected()){
                params.setGTool(PaintingAreaName.valueOf(button.getActionCommand()));
            }
        }
    }

    public static void main(String[] args) {
        new OptionsWindow(new Parameters());
    }

}


