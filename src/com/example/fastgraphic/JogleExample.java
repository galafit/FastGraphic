package com.example.fastgraphic;

import com.jogamp.opengl.util.FPSAnimator;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JogleExample implements GLEventListener {


    private boolean colorFlag;

    public JogleExample(int width, int height, int frameRate) {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);

        final Frame frame = new Frame("OpenGL Window Test");
        frame.setSize(width, height);
        frame.add(canvas);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });

        canvas.addGLEventListener(this);

        FPSAnimator animator = new FPSAnimator(canvas, frameRate);
        animator.add(canvas);
        animator.start();
    }

    public void display(GLAutoDrawable drawable) {
        update();
        render(drawable);
    }

    public void dispose(GLAutoDrawable drawable) {
    }

    public void init(GLAutoDrawable drawable) {
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
    }

    private void update() {
       //do nothing
    }

    private void render(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        if (colorFlag) {
            gl.glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
            colorFlag =!colorFlag;
        } else {
            gl.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
            colorFlag = !colorFlag;
        }
    }


}