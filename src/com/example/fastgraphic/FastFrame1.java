package com.example.fastgraphic;


	 
	import java.awt.*;
	import java.awt.event.*;
	import java.awt.image.BufferStrategy;
	import java.util.Random;
	import javax.swing.*;
	 
	public class FastFrame1 extends JFrame implements Runnable {
	 
	    private long startTime = 0;
	    private long frames = 0;
	    private int width, height;
	    private GraphicsDevice device = null;
	    private boolean running = false;
	 
	    private FastFrame1(String title, int width, int height) {
	        super(title);
	        this.width = width;
	        this.height = height;
	 
	        // ��������� repaint � ������� ����������� swing-�����������,
	        // ������ ��� �� ����� �� ���� ��������������
	        setIgnoreRepaint(true);
	        getRootPane().setDoubleBuffered(false);
	        setResizable(false);
	        setVisible(true);
	        // ������������� ������� ���� ������, ����� ���������� ������� ���� ���� width x height
	        Insets ins = getInsets();
	        setSize(ins.left + ins.right + width, ins.top + ins.bottom + height);
	        setLocationRelativeTo(null);
	        init();
	    }
	 
	    private FastFrame1(String title, int width, int height, GraphicsDevice device) {
	        super(title, device.getDefaultConfiguration());
	        this.width = width;
	        this.height = height;
	        this.device = device;
	 
	        setIgnoreRepaint(true);
	        getRootPane().setDoubleBuffered(false);
	        setUndecorated(true);
	        // �������� ������������� �����
	        device.setFullScreenWindow(this);
	        if (device.isDisplayChangeSupported()) {
	            device.setDisplayMode(new DisplayMode(width, height, 32, DisplayMode.REFRESH_RATE_UNKNOWN));
	        }
	        init();
	    }
	 
	    private void init() {
	        // ����� ����� ��� �������� fps
	        startTime = System.currentTimeMillis();
	        // �������� ���������� ������� �����������
	        createBufferStrategy(2);
	 
	        addWindowListener(new WindowAdapter() {
	            @Override public void windowClosing(WindowEvent ev) {
	                close();
	            }
	        });
	        addKeyListener(new KeyAdapter() {
	            @Override public void keyPressed(KeyEvent e) {
	                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	                    close();
	                }
	            }
	        });
	 
	        Thread th = new Thread(this);
	        running = true;
	        th.start();
	    }
	 
	    private void close() {
	        running = false;
	        // ���������� fps
	        System.out.println("FPS: " + frames * 1000 / (System.currentTimeMillis() - startTime));
	        // ���� �� � ������������� ������, ������������ � �������� �����
	        if (device != null) {
	            device.setFullScreenWindow(null);
	        }
	        System.exit(0);
	    }
	 
	    public static FastFrame1 create(String title, int width, int height, boolean fullscreen) {
	        if (fullscreen) {
	            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	            GraphicsDevice device = env.getDefaultScreenDevice();
	            return new FastFrame1(title, width, height, device);
	        } else {
	            return new FastFrame1(title, width, height);
	        }
	    }
	 
	    public void run() {
	        BufferStrategy bs = getBufferStrategy();
	        while (running) {
	            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
	 
	            // �.�. ���������� ����� ��������� ��� ����� ���� �������,
	            // �� ��� ����������� ���������� ��������� ������ ����� � ��������� ����,
	            // ����� �������� ������ � ���������� ������� ����
	            Insets ins = getInsets();
	 
	            // ������� 1000 ��������� �����
	            g.clearRect(ins.left, ins.top, width, height);
	            Random rnd = new Random();
	            for (int i = 0; i < 1000; i++) {
	                int x1 = ins.left + rnd.nextInt(width);
	                int y1 = ins.top + rnd.nextInt(height);
	                int x2 = ins.left + rnd.nextInt(width);
	                int y2 = ins.top + rnd.nextInt(height);
	                g.drawLine(x1, y1, x2, y2);
	            }
	 
	            // �����������
	            g.dispose();
	            // ������� ����� �� �����
	            bs.show();
	            // ���� ���������� ���� ���������� ��������
	            Toolkit.getDefaultToolkit().sync();
	            frames++;
	 
	            // ����� ������ �� ��������� ���������, ���� �������� ��������� �����
	            try {
	                Thread.sleep(1);
	            } catch (InterruptedException e) {}
	            // ���� fps �������� � ���� ������ ��������,
	            // �� Thread.sleep() ���� �������� �� Thread.yield()
	 
	            // �� �������� ������ ���� ������ �� sleep() ���� ����� 400 fps (�������� CPU 1-2%),
	            // � yield() - 1300 fps (�������� CPU 100%)
	        }
	    }
	 
	    public static void main(String[] args) {
	        try {
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (Exception e) {}
	 
	        SwingUtilities.invokeLater(new Runnable() {
	 
	            public void run() {
	                FastFrame1.create("Fast Updates", 640, 480, false); // ������� �����
	//              FastFrame.create("Fast Updates", 1280, 1024, true); // ������������� �����
	            }
	        });
	    }
	}