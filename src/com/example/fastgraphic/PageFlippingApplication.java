package com.example.fastgraphic;

import com.example.fastgraphic.painters.Painter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Active Rendering using BufferStrategy class. That is the best way to implement double buffering in Java.
 * BufferStrategy chooses the best buffering method based on the capabilities of the system
 * and if possible uses the hardware-accelerated memory and realizes Page Flipping Strategy.
 * <p/>
 * Also it is supposed that it waits for the monitor refresh to be finished before performing any page flip to avoid tearing
 * (when the part of the old buffer can be displayed at the same time as the part of the new buffer)
 * <p/>
 * Application can work in two modes: Full Screen Mode or Window Mode
 * <p/>
 * http://docs.oracle.com/javase/tutorial/extra/fullscreen/index.html
 */

public class PageFlippingApplication extends AbstractGraphicsApplication {

    // BufferStrategy can use more then 2 Buffers but 2 is the most common
    public static final int TWO_BUFFERS = 2;
    private GraphicsDevice screenDevice;
    private BufferStrategy bufferStrategy;
    private Rectangle paintingClip;


    public PageFlippingApplication(int frameRate, boolean isFullScreen, Painter painter, int width, int height, Color bgColor, Color fgColor) {
        super(painter);
        screenDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        paintingClip = new Rectangle(0, 0, width, height);
        Frame mainFrame = new Frame();
        setDefaultCloseOperation(mainFrame);
        setSizeAndColors(mainFrame, width, height, bgColor, fgColor);
        // turn off paint events dispatched from the operating system since we are doing active rendering
        mainFrame.setIgnoreRepaint(true);
        // Full-screen exclusive applications should not be resizable,
        // since resizing a full-screen application can cause unpredictable (or possibly dangerous) behavior.
        // In windowed mode it is also better to use a fixed size Window
        mainFrame.setResizable(false);
        // Most full-screen exclusive applications are better suited to use undecorated windows.
        mainFrame.setUndecorated(true);
        // place the window to the screen center
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        // createBufferStrategy has to be done AFTER the element (mainFrame or canvas) is set visible!
        mainFrame.createBufferStrategy(TWO_BUFFERS);
        bufferStrategy = mainFrame.getBufferStrategy();

        if (isFullScreen) {
            enterFullScreenMode(mainFrame);
        }
        startAnimation(frameRate);
    }

    private void enterFullScreenMode(Frame mainFrame) {
        if (screenDevice.isFullScreenSupported()) {
            paintingClip = new Rectangle(0, 0, screenDevice.getDisplayMode().getWidth(), screenDevice.getDisplayMode().getHeight());

            screenDevice.setFullScreenWindow(mainFrame);
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Full Screen Exclusive Mode is NOT Supported");
        }
    }

    /**
     * Buffers in BufferStrategy are usually of the type VolatileImage and therefore they may become lost.
     * So usually drawing to the off-screen buffer and coping the image to the screen done in do-while loop.
     * This ensure that if something was wrong (rendering data was lost or Graphics settings was changed) and
     * on the screen appears "damaged" image, painting will be repeated till we see intact image.
     * See: http://docs.oracle.com/javase/6/docs/api/java/awt/image/BufferStrategy.html
     *
     * In our case of quickly-changing frames it is not necessary  -
     * instead of repainting the damaged frame the next frame simply will be painted
     */
    public void repaintFrame() {
        // Get a new graphics context every time to make sure the strategy is validated
        Graphics graphics = bufferStrategy.getDrawGraphics();

        // Render off-screen
        graphics.setClip(paintingClip);
        graphics.clearRect(paintingClip.x, paintingClip.y, paintingClip.width, paintingClip.height);
        painter.paint(graphics);

        // Dispose the graphics
        graphics.dispose();

        // Display the off-screen image
        bufferStrategy.show();

        // The call to Toolkit.sync( ) after drawImage( ) ensures that the display is promptly updated.
        // This is required for Linux, which does not automatically flush its display buffer. Without the sync( ) call,
        // the animation may be only partially updated, creating a "tearing" effect.
        // "Killer Game Programming in Java" By Andrew Davison
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder();
        report.append(super.getReport());
        report.append("\nThe Buffer Strategy uses page flipping [isPageFlipping] - "
                + bufferStrategy.getCapabilities().isPageFlipping());
        report.append("\nPage Flipping is only available in Full Screen mode [isFullScreenRequired]  - "
                + bufferStrategy.getCapabilities().isFullScreenRequired());
        report.append("\nPage Flipping can be performed using more than two buffers [isMultiBufferAvailable] - "
                + bufferStrategy.getCapabilities().isMultiBufferAvailable());
        return report.toString();
    }

}
