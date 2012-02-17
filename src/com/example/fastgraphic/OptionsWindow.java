package com.example.fastgraphic;


import com.example.fastgraphic.animator.Animator;
import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;


public class OptionsWindow extends JFrame {

    private int width = 640;
    private int height = 480;
    private int frameRate = 60;
    private float frameShift = 0.5f;
    private AvailableColors bgColor = AvailableColors.BLACK;
    private AvailableColors fgColor = AvailableColors.GREEN;
    private ApplicationType applicationType = ApplicationType.SWING;
    private boolean useLinePainter;
    private boolean useSinusPainter = true;
    private boolean useBgFlipPainter;
    private Painter painter;

    private boolean isSwingDoubleBuff = true;
    private boolean isFullScreen = false;

    private final JCheckBox sinusCheckBox = new JCheckBox("Sinus");
    JCheckBox lineCheckBox = new JCheckBox("Line");
    JCheckBox bgCheckBox = new JCheckBox("BG change");

    JCheckBox isSwingDoubleBuffField = new JCheckBox("Double Buffering");
    JCheckBox isFullScreenField = new JCheckBox("Full Screen");

    JFormattedTextField frameRateField = new JFormattedTextField(frameRate);
    JFormattedTextField frameShiftField = new JFormattedTextField(frameShift);

    JFormattedTextField widthField = new JFormattedTextField(width);
    JFormattedTextField heightField = new JFormattedTextField(height);
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

    public OptionsWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        widthField.setColumns(3);
        heightField.setColumns(3);
        frameRateField.setColumns(2);
        frameShiftField.setColumns(2);
        
        // Panel with Radio Buttons to chose Tool
        JPanel toolPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,15));
        toolPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        toolButtonGroup = new ButtonGroup();
        HashMap<ApplicationType,JPanel> radioButtonHashMap= new HashMap<ApplicationType,JPanel>();
        for (final ApplicationType appType : ApplicationType.values()) {
            JRadioButton radio = new JRadioButton(appType.getLabel());
            if(appType.equals(applicationType)){
                radio.setSelected(true);
            }
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
            buttonPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            buttonPanel.add(radio);
            radio.setActionCommand(appType.name());
            radio.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    applicationType=appType;
                }
            });
// in Panel of SWING radio button we add the checkBox "isSwingDoubleBuffField" to permit to chose whether the SWING Application
// will work with Double Buffering "On" or "Off".
// The ActionListeners control that this checkbox will be enabled only when SWING mode is chosen
//
// in Panel of PAGE_FLIPPING radio button we add the checkBox "isFullScreenField" to permit to chose whether the PageFlipping Application
// will work with en Full Screen or Window mode.
// The ActionListeners control that this checkbox will be enabled only when PAGE_FLIPPING mode is chosen
            if(appType.equals(ApplicationType.SWING)){
                buttonPanel.add(isSwingDoubleBuffField);
                radio.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        isSwingDoubleBuffField.setSelected(true);
                       isSwingDoubleBuffField.setEnabled(true);

                        isFullScreenField.setSelected(false);
                        isFullScreenField.setEnabled(false);
                    }
                });
            }
            if(appType.equals(ApplicationType.PAGE_FLIPPING)){
                buttonPanel.add(isFullScreenField);
                radio.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        isFullScreenField.setSelected(true);
                        isFullScreenField.setEnabled(true);

                        isSwingDoubleBuffField.setSelected(false);
                        isSwingDoubleBuffField.setEnabled(false);
                    }
                });
            }
            else{
                radio.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        isFullScreenField.setSelected(false);
                        isFullScreenField.setEnabled(false);
                        isSwingDoubleBuffField.setSelected(false);
                        isSwingDoubleBuffField.setEnabled(false);
                    }
                });
            }

            toolButtonGroup.add(radio);
            radioButtonHashMap.put(appType,buttonPanel);
        }

        JPanel passiveRenderingPanel = new JPanel(new BorderLayout(0,3));
        passiveRenderingPanel.add(radioButtonHashMap.get(ApplicationType.AWT),BorderLayout.SOUTH);
        passiveRenderingPanel.add(radioButtonHashMap.get(ApplicationType.SWING),BorderLayout.NORTH);

        JPanel activeRenderingPanel = new JPanel(new BorderLayout(0,3));
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        groupPanel.add(radioButtonHashMap.get(ApplicationType.DIRECT));
        groupPanel.add(radioButtonHashMap.get(ApplicationType.DOUBLE_BUFF));
        groupPanel.add(radioButtonHashMap.get(ApplicationType.ACCELERATED_BUFF));
        activeRenderingPanel.add(groupPanel,BorderLayout.SOUTH);
        activeRenderingPanel.add(radioButtonHashMap.get(ApplicationType.PAGE_FLIPPING),BorderLayout.NORTH);
        
        JPanel examplePanel = new JPanel(new BorderLayout(0,3));
        examplePanel.add(radioButtonHashMap.get(ApplicationType.OPEN_GL),BorderLayout.NORTH);
        examplePanel.add(radioButtonHashMap.get(ApplicationType.VLCJ),BorderLayout.SOUTH);
        
        toolPanel.add(titlePanel("Passive Rendering",passiveRenderingPanel));
        toolPanel.add(titlePanel("Active Rendering",activeRenderingPanel));
        toolPanel.add(titlePanel("Additional",examplePanel));


        // Panel describing Frames Changing
        JPanel framePanel = new JPanel(new BorderLayout());
        framePanel.setBorder(BorderFactory.createTitledBorder("Frame Changing"));
        JPanel frameRatePanel = composeLabelField("Frame Rate   [perSec]", frameRateField);
        JPanel frameShiftPanel = composeLabelField("Frame Shift  [pixels]", frameShiftField);
        framePanel.add(frameRatePanel, BorderLayout.NORTH);
        framePanel.add(frameShiftPanel, BorderLayout.CENTER);

        // Panel with CheckBox Buttons  to choose graphics that will be painted
        JPanel graphPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        graphPanel.setBorder(BorderFactory.createTitledBorder("Graphics"));
        graphPanel.add(bgCheckBox);
        graphPanel.add(sinusCheckBox);
        graphPanel.add(new Label("")); // just empty position
        graphPanel.add(lineCheckBox);


        // Panel with Painting Options
        JPanel paintingOptionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paintingOptionsPanel.setBorder(BorderFactory.createTitledBorder("Painting Options"));

        bgChoice.setSelectedItem(bgColor);
        fgChoice.setSelectedItem(fgColor);

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
        optionsPanel.add(paintingOptionsPanel,BorderLayout.WEST);
        optionsPanel.add(graphPanel,BorderLayout.CENTER);
        optionsPanel.add(framePanel,BorderLayout.EAST);

        // Panel with buttons "Start" and "Exit"
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startApplication();
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

        pack();
        setVisible(true);
        // put the window at the screen center
        setLocationRelativeTo(null);
    }

  
    private void startApplication(){

        controlsToParams();
        if (applicationType == ApplicationType.AWT) {
            AWTApplication application = new AWTApplication(painter,width,height,bgColor.getColor(),fgColor.getColor());
            Animator  animator = new Animator(frameRate);
            animator.addPaintingArea(application);
            application.setAnimator(animator);
            animator.startAnimation();

        }
        if (applicationType  == ApplicationType.SWING) {
            SwingApplication application = new SwingApplication(isSwingDoubleBuff,painter,width,height,bgColor.getColor(),fgColor.getColor());
            Animator  animator = new Animator(frameRate);
            animator.addPaintingArea(application);
            application.setAnimator(animator);
            animator.startAnimation();
        }
        if (applicationType == ApplicationType.DOUBLE_BUFF) {
            DoubleBufferringApplication application = new DoubleBufferringApplication(painter,width,height,bgColor.getColor(),fgColor.getColor());
            Animator  animator = new Animator(frameRate);
            animator.addPaintingArea(application);
            application.setAnimator(animator);
            animator.startAnimation();
        }
        if (applicationType == ApplicationType.ACCELERATED_BUFF) {
            AcceleratedDoubleBufferingApplication application = new AcceleratedDoubleBufferingApplication(painter,width,height,bgColor.getColor(),fgColor.getColor());
            Animator  animator = new Animator(frameRate);
            animator.addPaintingArea(application);
            application.setAnimator(animator);
            animator.startAnimation();
        }
        if (applicationType == ApplicationType.DIRECT) {
            DirectPaintingApplication application = new DirectPaintingApplication(painter,width,height,bgColor.getColor(),fgColor.getColor());
            Animator  animator = new Animator(frameRate);
            animator.addPaintingArea(application);
            application.setAnimator(animator);
            animator.startAnimation();
        }
        if (applicationType == ApplicationType.PAGE_FLIPPING) {
            PageFlippingApplication application = new PageFlippingApplication(isFullScreen,painter,width,height,bgColor.getColor(),fgColor.getColor());
            Animator  animator = new Animator(frameRate);
            animator.addPaintingArea(application);
            application.setAnimator(animator);
            animator.startAnimation();
        }
    }
    

    public void controlsToParams() {
  

        frameRate = (Integer)frameRateField.getValue();
        frameShift = (Float) frameShiftField.getValue();
        width = (Integer) widthField.getValue();
        height = (Integer) heightField.getValue();
        bgColor = (AvailableColors)bgChoice.getSelectedItem();
        fgColor = (AvailableColors)fgChoice.getSelectedItem();
   
        painter = createCompoundPainter();
        if(isFullScreenField.isSelected()){
            isFullScreen = true;
        }
        if(isSwingDoubleBuffField.isSelected()){
            isSwingDoubleBuff = true;
        }
    }


        private  Painter createCompoundPainter() {
            CompoundPainter compoundPainter = new CompoundPainter();
            if (bgCheckBox.isSelected()) {
                compoundPainter.addPainter(new BGFlipPainter());
            }
            if (lineCheckBox.isSelected()) {
                compoundPainter.addPainter(new LinePainter((Float)frameShiftField.getValue()));
            }
            if (sinusCheckBox.isSelected()) {
                compoundPainter.addPainter(new SinusPainter((Float)frameShiftField.getValue()));
            }
            return compoundPainter;
        }

    public static void main(String[] args) {
        new OptionsWindow();
    }

}


