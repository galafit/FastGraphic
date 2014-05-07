package com.example.fastgraphic;


import com.example.fastgraphic.painters.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.HashMap;


public class StartWindow extends JFrame {

    private final int INITIAL_WIDTH = 640;
    private final int INITIAL_HEIGHT = 480;
    private final int INITIAL_FRAME_RATE = 25;
    private final float INITIAL_FRAME_SHIFT = 0.5f;
    private final AvailableColors INITIAL_BG_COLOR = AvailableColors.BLACK;
    private final AvailableColors INITIAL_FG_COLOR = AvailableColors.GREEN;

    private  JCheckBox sinusCheckBox = new JCheckBox("Sinus");
    private JCheckBox lineCheckBox = new JCheckBox("Line");
    private JCheckBox bgCheckBox = new JCheckBox("BG change", true);
    private JCheckBox slowCheckBox = new JCheckBox("Slow Drawing");


    private JCheckBox isSwingDoubleBuffField = new JCheckBox("Double Buffering", true);
    private JCheckBox isFullScreenField = new JCheckBox("Full Screen", true);

    private JFormattedTextField frameRateField = new JFormattedTextField(INITIAL_FRAME_RATE);
    private JFormattedTextField frameShiftField = new JFormattedTextField(INITIAL_FRAME_SHIFT);

    private JFormattedTextField widthField = new JFormattedTextField(INITIAL_WIDTH);
    private JFormattedTextField heightField = new JFormattedTextField(INITIAL_HEIGHT);
    private JComboBox bgChoice = new JComboBox(AvailableColors.values());
    private JComboBox fgChoice = new JComboBox(AvailableColors.values());

    private JPanel composeLabelField(String label, Component component) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
    }

    private JPanel titlePanel(String title, JPanel panel) {
        JPanel titledPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        titledPanel.setBorder(BorderFactory.createTitledBorder(title));
        titledPanel.add(panel);
        return titledPanel;
    }

    public StartWindow() {
        setTitle("Graphic Test");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        widthField.setColumns(3);
        heightField.setColumns(3);
        frameRateField.setColumns(2);
        frameShiftField.setColumns(2);

        // Panel with  Buttons to choose Application
        JPanel toolPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));

        HashMap<ApplicationType, Component> buttonHashMap = new HashMap<ApplicationType, Component>();
        for (final ApplicationType appType : ApplicationType.values()) {
            JButton appButton = new JButton(appType.getLabel());
            appButton.setActionCommand(appType.name());
            appButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    startApplication(appType);
                }
            });
            if (appType.equals(ApplicationType.SWING)) {
                JPanel panel = new JPanel(new FlowLayout());
                panel.setBorder(BorderFactory.createLineBorder(Color.gray));
                panel.add(appButton);
                panel.add(isSwingDoubleBuffField);
                buttonHashMap.put(appType, panel);
            } else if (appType.equals(ApplicationType.PAGE_FLIPPING)) {
                JPanel panel = new JPanel(new FlowLayout());
                panel.setBorder(BorderFactory.createLineBorder(Color.gray));
                panel.add(appButton);
                panel.add(isFullScreenField);
                buttonHashMap.put(appType, panel);
            } else buttonHashMap.put(appType, appButton);
        }

        JPanel passiveRenderingPanel = new JPanel(new BorderLayout(0, 3));
        passiveRenderingPanel.add(buttonHashMap.get(ApplicationType.AWT), BorderLayout.SOUTH);
        passiveRenderingPanel.add(buttonHashMap.get(ApplicationType.SWING), BorderLayout.NORTH);

        JPanel activeRenderingPanel = new JPanel(new BorderLayout(0, 3));
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        groupPanel.add(buttonHashMap.get(ApplicationType.DIRECT));
        groupPanel.add(buttonHashMap.get(ApplicationType.DOUBLE_BUFF));
        groupPanel.add(buttonHashMap.get(ApplicationType.ACCELERATED_BUFF));
        activeRenderingPanel.add(groupPanel, BorderLayout.SOUTH);
        activeRenderingPanel.add(buttonHashMap.get(ApplicationType.PAGE_FLIPPING), BorderLayout.NORTH);


        toolPanel.add(titlePanel("Passive Rendering", passiveRenderingPanel));
        toolPanel.add(titlePanel("Active Rendering", activeRenderingPanel));


        // Panel describing Frames Changing
        JPanel framePanel = new JPanel(new BorderLayout());
        framePanel.setBorder(BorderFactory.createTitledBorder("Animation Settings"));
        JPanel frameRatePanel = composeLabelField("Frame Rate  [perSec]", frameRateField);
        JPanel frameShiftPanel = composeLabelField("Frame Shift  [pixels]", frameShiftField);
        framePanel.add(frameRatePanel, BorderLayout.NORTH);
        framePanel.add(frameShiftPanel, BorderLayout.CENTER);

        // Panel with CheckBox Buttons to choose graphics that will be painted
        JPanel graphPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        graphPanel.setBorder(BorderFactory.createTitledBorder("Active Graphs"));
        graphPanel.add(bgCheckBox);
        graphPanel.add(sinusCheckBox);
        graphPanel.add(slowCheckBox);
        graphPanel.add(lineCheckBox);

        // Panel with Painting Settings
        JPanel paintingSettingsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paintingSettingsPanel.setBorder(BorderFactory.createTitledBorder("Painting Settings"));

        bgChoice.setSelectedItem(INITIAL_BG_COLOR);
        fgChoice.setSelectedItem(INITIAL_FG_COLOR);

        JPanel colorPanel = new JPanel(new BorderLayout());
        colorPanel.add(composeLabelField("FG Color", fgChoice), BorderLayout.NORTH);
        colorPanel.add(composeLabelField("BG Color", bgChoice), BorderLayout.CENTER);

        JPanel sizePanel = new JPanel(new BorderLayout());
        sizePanel.add(composeLabelField("Width ", widthField), BorderLayout.NORTH);
        sizePanel.add(composeLabelField("Height", heightField), BorderLayout.CENTER);

        paintingSettingsPanel.add(colorPanel);
        paintingSettingsPanel.add(sizePanel);

        // Panel joining Graphics and Painting Options
        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.add(paintingSettingsPanel, BorderLayout.CENTER);
        optionsPanel.add(graphPanel, BorderLayout.EAST);
        optionsPanel.add(framePanel, BorderLayout.WEST);

        // Panel with the buttons "Exit" and "About"
        JPanel buttonPanel = new JPanel();
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        JButton aboutButton = new JButton("About");
        aboutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/Alvw/FastGraphic/wiki"));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonPanel.add(exitButton);
        buttonPanel.add(aboutButton);

        // Root Panel of the StartWindow
        add(toolPanel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        // place the window to the screen center
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void startApplication(ApplicationType applicationType) {
        if (applicationType == ApplicationType.AWT) {
            new AWTApplication(getFrameRate(), createCompoundPainter(), getWidth(), getHeight(), getBgColor(), getFgColor());
        }
        if (applicationType == ApplicationType.SWING) {
            new SwingApplication(getFrameRate(), isSwingDoubleBuffered(), createCompoundPainter(), getWidth(), getHeight(), getBgColor(), getFgColor());
        }
        if (applicationType == ApplicationType.DOUBLE_BUFF) {
            new DoubleBufferringApplication(getFrameRate(), createCompoundPainter(), getWidth(), getHeight(), getBgColor(), getFgColor());
        }
        if (applicationType == ApplicationType.ACCELERATED_BUFF) {
            new AcceleratedDoubleBufferingApplication(getFrameRate(), createCompoundPainter(), getWidth(), getHeight(), getBgColor(), getFgColor());
        }
        if (applicationType == ApplicationType.DIRECT) {
            new DirectPaintingApplication(getFrameRate(), createCompoundPainter(), getWidth(), getHeight(), getBgColor(), getFgColor());
        }
        if (applicationType == ApplicationType.PAGE_FLIPPING) {
            new PageFlippingApplication(getFrameRate(), isFullScreen(), createCompoundPainter(), getWidth(), getHeight(), getBgColor(), getFgColor());
        }
    }

    public int getFrameRate() {
        return (Integer) frameRateField.getValue();
    }

    public float getFrameShift() {
        return (Float) frameShiftField.getValue();
    }

    public int getWidth() {
        return (Integer) widthField.getValue();
    }

    public int getHeight() {
        return (Integer) heightField.getValue();
    }

    public Color getBgColor() {
        return ((AvailableColors) bgChoice.getSelectedItem()).getColor();
    }

    public Color getFgColor() {
        return ((AvailableColors) fgChoice.getSelectedItem()).getColor();
    }

    public boolean isFullScreen() {
        return isFullScreenField.isSelected();
    }

    public boolean isSwingDoubleBuffered() {
        return isSwingDoubleBuffField.isSelected();
    }

    public Painter createCompoundPainter() {
        CompoundPainter compoundPainter = new CompoundPainter();
        if (bgCheckBox.isSelected()) {
            compoundPainter.addPainter(new BGFlipPainter());
        }
        if (slowCheckBox.isSelected()) {
            compoundPainter.addPainter(new SlowPainter());
        }
        if (lineCheckBox.isSelected()) {
            compoundPainter.addPainter(new LinePainter(getFrameShift()));
        }
        if (sinusCheckBox.isSelected()) {
            compoundPainter.addPainter(new SinusPainter(getFrameShift()));
        }
        return compoundPainter;
    }

    public static void main(String[] args) {
        new StartWindow();
    }

}


