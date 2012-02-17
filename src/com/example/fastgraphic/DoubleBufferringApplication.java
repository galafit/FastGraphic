package com.example.fastgraphic;

import com.example.fastgraphic.animator.Animator;
import com.example.fastgraphic.animator.PaintingArea;
import com.example.fastgraphic.painters.CompoundPainter;
import com.example.fastgraphic.painters.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Active rendering which implements simple Double Buffering with OffScreen back buffer stored in the system memory.
 *
 * Instead of drawing directly to the screen Double buffering involves first drawing to an offscreen image,
 * and then coping that image to the screen.
 *
 */

public class DoubleBufferringApplication implements PaintingArea {

    private Canvas paintingCanvas;
    private Rectangle paintingClip; //Painting Clip for Graphics object should be set manually for Active Rendering
    private Painter painter;  // implements all painting routine
    private Animator animator; // that will invoke repaintFrame() method in loop
    private Image offScreenImage; // back  buffer for Double Buffering

    public DoubleBufferringApplication(Painter painter, int width, int height,Color bgColor, Color fgColor){
        this.painter = painter;
        paintingClip = new Rectangle(0,0,width,height);
        final Frame frame = new Frame();  // Application Main Frame
        frame.setTitle(ApplicationType.DOUBLE_BUFF.getLabel());
        // add Window Listener to the frame which will stop animation and dispose the frame on closing
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                animator.stopAnimation();
                frame.dispose();
                //create a Window with information about real average FrameRate
                JOptionPane.showMessageDialog(frame, animator.getFrameRateReport());
            }
        });
        paintingCanvas = new Canvas();
        paintingCanvas.setPreferredSize(new Dimension(width, height));
        paintingCanvas.setBackground(bgColor);
        paintingCanvas.setForeground(fgColor);
        frame.add(paintingCanvas);
        frame.pack();
        frame.setVisible(true);
        // put the window at the screen center
        frame.setLocationRelativeTo(null);

        // create Off-Screen Image Buffer
        offScreenImage = createBufferedImage();
    }

    public void setAnimator(Animator animator){
        this.animator = animator;
    }

    public void repaintFrame() {
        // get Off-Screen Graphics
        Graphics offScreenG = offScreenImage.getGraphics();
        // rendering to the off-Screen
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

        // The call to Toolkit.sync( ) after drawImage( ) ensures that the display is promptly updated.
        // This is required for Linux, which doesn't automatically flush its display buffer. Without the sync( ) call,
        // the animation may be only partially updated, creating a " tearing " effect.
        // "Killer Game Programming in Java" By Andrew Davison
        Toolkit.getDefaultToolkit().sync();

    }

    /**
     * For the OffScreen back buffer we need to create an image as close as possible to the Graphics Configuration
     * in terms of its format, with a data layout and color model compatible with the graphics configuration it is drawing to.
     * We can do it by using method createImage(width,height) of any Component
     * However, in order to create an image in this way, the component  must be displayable (or the return value may be null)
     * The most efficient way to create an image is through the Graphics Configuration of the graphics device
     * where the program is running:
     * <pre>
     *  GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
     *  GraphicsDevice defaultScreenDevice=ge.getDefaultScreenDevice();
     *  GraphicsConfiguration graphicsConfiguration = defaultScreenDevice.getDefaultConfiguration();
     *  graphicsConfiguration.createCompatibleImage(width,height);
     * </pre>
     *
     */
    private Image createBufferedImage(){
// Returns a BufferedImage (Subclass of Image) with a data layout and color model compatible with this graphicsConfiguration.
        return paintingCanvas.createImage(paintingClip.width,paintingClip.height);
    }
}
