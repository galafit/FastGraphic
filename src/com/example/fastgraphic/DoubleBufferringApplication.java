package com.example.fastgraphic;

import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompositePainter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

/**
 * Active rendering that implements simple Double Buffering with the OffScreen back buffer stored in the system memory
 *
 * Instead of drawing directly to the screen Double buffering involves first drawing to an offscreen image,
 * and then coping that image to the screen.
 *


 More importantly, however, by creating a compatible image, you create an image as close as possible to the graphics configuration
 in terms of its format, with a data layout and color model compatible with the graphics configuration it is drawing to.
 This can have a large speed impact when rendering images to, for example, the window running under this graphics configuration,
 cutting down on any pixel color mapping calculations required during the drawing process.
 Note
 You may also retain the graphics configuration from the component,
 such as our JFrame, using the method component.getGraphicsConfiguration, and create the compatible image from here.
 Note also that if the component has not been assigned a specific graphics configuration and has not been added
 to another component container, then component.getGraphicsConfiguration will return null.
 If the component has been added to a containment hierarchy, the graphics configuration associated with the top-level container
 (e.g., Frame, JFrame, JApplet, etc.) will be returned instead.

 */

public class DoubleBufferringApplication implements PaintingArea {

    private Frame frame;
    private CompositePainter painter;
    private Controller controller;
    private Canvas paintingCanvas;
    private Image offScreenImage;
    private Rectangle paintingClip;
    private Color backgroundColor;
    private Color foregroundColor;


    public DoubleBufferringApplication(String title, int width, int height,
                                       Color bgColor, Color fgColor, CompositePainter painter, Controller contrl) {
        frame = new Frame();
        controller = contrl;
        this.painter = painter;
        paintingClip = new Rectangle(0,0,width,height);
        backgroundColor = bgColor;
        foregroundColor = fgColor;
        frame.setTitle(title);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.stopAnimation();
                frame.dispose();
            }
        });
        paintingCanvas = new Canvas();
        paintingCanvas.setPreferredSize(new Dimension(width, height));
// turn off paint events dispatched from the operating system since we are doing active rendering
        frame.setIgnoreRepaint(true);
// for simplicity we use  a fixed size Frame
        frame.setResizable(false);
        frame.add(paintingCanvas);
        frame.pack();
        frame.setVisible(true);

// create Off-Screen Image Buffer
        offScreenImage = createBufferedImage();
    }

    public void changeFrame() {
        // get Off-Screen Graphics
        Graphics2D offScreenG = (Graphics2D)offScreenImage.getGraphics();
        // rendering to the off-Screen
        offScreenG.setBackground(backgroundColor);
        offScreenG.setColor(foregroundColor);
        offScreenG.setClip(paintingClip);
        offScreenG.clearRect(paintingClip.x,paintingClip.y,paintingClip.width,paintingClip.height);
        painter.paint(offScreenG);
        // Dispose the off-Screen graphics
        offScreenG.dispose();
        //get Graphics object for the onscreen window
        Graphics screenG = paintingCanvas.getGraphics();
        // copy of-Screen Image to the Screen
        screenG.drawImage(offScreenImage,paintingClip.x,paintingClip.y, null);
        // Dispose the on-screen graphics
        screenG.dispose();

// The call to “Toolkit.getDefaultToolkit().sync()” exists to fix event queue problems on Linux
// On many systems, this method does nothing.
        Toolkit.getDefaultToolkit().sync();

    }


// For the OffScreen back buffer we need to create an image as close as possible to the Graphics Configuration
// in terms of its format, with a data layout and color model compatible with the graphics configuration it is drawing to.
// We can do it by using method createImage(width,height) of any Component
// However, in order to create an image in this way, the component  must be displayable (or the return value may be null)
// The most efficient way to create an image is through the Graphics Configuration of the graphics device
// on which the program is running.
    
    private Image createBufferedImage(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreenDevice=ge.getDefaultScreenDevice();
        GraphicsConfiguration graphicsConfiguration = defaultScreenDevice.getDefaultConfiguration();
// Returns a BufferedImage (Subclass of Image) with a data layout and color model compatible with this graphicsConfiguration.
        return graphicsConfiguration.createCompatibleImage(paintingClip.width,paintingClip.height);  
    }
}
