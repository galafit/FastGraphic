package com.example.fastgraphic;

import com.example.fastgraphic.painters.CompositePainter;
import com.example.fastgraphic.animator.PaintingArea;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 *  Active rendering using BufferStrategy class.
 *  BufferStrategy chooses the best buffering method based on the capabilities of the system
 *  and if possible uses hardware-accelerated memory and realizes Page Flipping Strategy
 *
 *  Also supposed that it waits on the monitor refresh to finish before performing any page flip to avoid tearing
 *  (when part of the old buffer can display at the same time as part of the new buffer)
 *
 *   http://docs.oracle.com/javase/tutorial/extra/fullscreen/index.html
 */
public class PageFlippingApplication implements PaintingArea {

// BufferStrategy can use more then 2 Buffers but 2 is the most common
    public static final int TWO_BUFFERS = 2;

    private CompositePainter painter;
    private Controller controller;

    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice screenDevice = graphicsEnvironment.getDefaultScreenDevice();
    private BufferStrategy bufferStrategy;
    private Rectangle paintingClip;
    private Color backgroundColor;
    private Color foregroundColor;
    private String applicationTitle;

// Array of Strings to store some additional information that could be interesting for users
// and that will be displayed after finishing of that Application in InfoWindow
// When displayed every Strings element will begin with the new Line and be separated with empty line
    ArrayList<String> infoStrings = new ArrayList<String>();
    Frame frame;

    public PageFlippingApplication(boolean isFullScreen, String title, int width, int height,
                                   Color bgColor, Color fgColor, CompositePainter painter, Controller contrl) {
        controller = contrl;
        this.painter = painter;
        paintingClip = new Rectangle(0,0,width,height);
        backgroundColor = bgColor;
        foregroundColor = fgColor;
        applicationTitle = title;
        frame = new Frame();
// turn off paint events dispatched from the operating system since we are doing active rendering
        frame.setIgnoreRepaint(true);
// Full-screen exclusive applications should not be resizable,
// since resizing a full-screen application can cause unpredictable (or possibly dangerous) behavior.
// In windowed mode it is also better to use a fixed size Window
        frame.setResizable(false);
        if (isFullScreen) {
            enterFullScreenMode();
        }else {
            enterWindowMode(applicationTitle);
        }
    }

    private void enterFullScreenMode(){
        if(screenDevice.isFullScreenSupported()) {
            paintingClip = new Rectangle(0, 0, screenDevice.getDisplayMode().getWidth(), screenDevice.getDisplayMode().getHeight());
// Most full-screen exclusive applications are better suited to use undecorated windows.
            frame.setUndecorated(true);
            frame.setBackground(backgroundColor);
            frame.setForeground(foregroundColor);
// Add Kay Listener to exit from the Full Screen Mode on pressing any button
            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    controller.stopAnimation();
                    frame.dispose();
// switch back from Full Screen to Window mode
                    screenDevice.setFullScreenWindow(null);
// Add some info to infoStrings and create a Window that shows that info
                    addInfo();
                    new InfoWindow("Page Flipping Application Info",infoStrings);
                }
            });

            frame.setVisible(true);
//Important!!! createBufferStrategy have to be done AFTER  the element (frame or canvas) is set visible
            frame.createBufferStrategy(TWO_BUFFERS);
            bufferStrategy = frame.getBufferStrategy();
            screenDevice.setFullScreenWindow(frame);
            infoStrings.add("Full Screen Exclusive Mode is Supported");
        } else {
            infoStrings.add("Full Screen Exclusive Mode is NOT Supported");
// On systems where full-screen exclusive mode is not available, it is probably better
// to run an application in windowed mode with a fixed size rather than setting a full-screen window
            enterWindowMode(applicationTitle + " - Full Screen Exclusive Mode is not Supported");
        }
    }

    private void enterWindowMode(String frameTitle){
        frame.setTitle(frameTitle);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.stopAnimation();
                frame.dispose();
// Add some info to infoStrings and create a Window that shows that info
                addInfo();
                new InfoWindow("Page Flipping Application Info",infoStrings);
            }
        });
        Canvas paintingCanvas = new Canvas();
        paintingCanvas.setPreferredSize(new Dimension(paintingClip.width, paintingClip.height));
        paintingCanvas.setBackground(backgroundColor);
        paintingCanvas.setForeground(foregroundColor);
        frame.add(paintingCanvas);
        frame.pack();
        frame.setVisible(true);
// put the window at the screen center
        frame.setLocationRelativeTo(null);
//Important!!! createBufferStrategy have to be done AFTER  the element (frame or canvas) is set visible
        paintingCanvas.createBufferStrategy(TWO_BUFFERS);
        bufferStrategy = paintingCanvas.getBufferStrategy();
    }

// Since the buffers in a buffer strategy are usually type VolatileImage they may become lost.
// So rendering to OffScreen buffer of BufferStrategy and  putting the image from the back buffer on the screen have to be don
// within do-while loop (likewise in the  case  of VolatileImage class )

// Render single frame
    public void changeFrame() {
        do {
            renderOffscreen();
            // Display the back buffer
            bufferStrategy.show();
            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());

// The call to “Toolkit.getDefaultToolkit().sync()” exists to fix event queue problems on Linux
// On many systems, this method does nothing.
        Toolkit.getDefaultToolkit().sync();
    }


// rendering to the off-screen back  buffer  of Buffer Strategy
    private void renderOffscreen() {
        do {
            // Get a new graphics context every time through the loop
            // to make sure the strategy is validated
            Graphics graphics = bufferStrategy.getDrawGraphics();
            graphics.setClip(paintingClip);
            graphics.clearRect(paintingClip.x, paintingClip.y, paintingClip.width, paintingClip.height);
            // Rendering
            painter.paint(graphics);
            // Dispose the graphics
            graphics.dispose();
            // Repeat the rendering if the drawing buffer contents were restored
        } while (bufferStrategy.contentsRestored());
    }

// add to infoStrings some info about BufferCapabilities that could be interesting to users
    private void addInfo(){
        infoStrings.add("Whether the Buffer Strategy uses page flipping [isPageFlipping] - "
                + bufferStrategy.getCapabilities().isPageFlipping());
        infoStrings.add("Whether Page Flipping is only available in Full Screen mode [isFullScreenRequired]  - "
                + bufferStrategy.getCapabilities().isFullScreenRequired());
        infoStrings.add("Whether Page Flipping can be performed using more than two buffers [isMultiBufferAvailable] - "
                + bufferStrategy.getCapabilities().isMultiBufferAvailable());
    }
}
