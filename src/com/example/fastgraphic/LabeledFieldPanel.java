package com.example.fastgraphic;


import javax.swing.*;


//Panel that join a Label to the Component
public class LabeledFieldPanel extends JPanel{
    
    LabeledFieldPanel(String label, JComponent field){
       add(new JLabel(label)); 
       add(field);
    }
    
}
