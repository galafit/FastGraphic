package com.example.fastgraphic;


import java.awt.*;

public class NamedColor extends Color {

    static final NamedColor red = new NamedColor("red", Color.red);
    static final NamedColor black = new NamedColor("black", Color.black);
    static final NamedColor green = new NamedColor("green", Color.green);
    static final NamedColor gray = new NamedColor("gray", Color.gray);

    private String name;

    public NamedColor(String name, Color color){
        super(color.getRGB()) ;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
