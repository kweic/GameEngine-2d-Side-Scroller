/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingboxpractice.displayBoard;

import KeyListener.KeyboardListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import movingboxpractice.boxLogic.Boxes;

/**
 *
 * @author kw
 */
public class DisplayBoard implements Runnable {

    private JFrame frame;
    private int height;
    private int width;
    private JPanel panel;
    private KeyboardListener listener;
    DisplayPanel displayPanel;


    public DisplayBoard(DisplayPanel displayPanel, int height, int width, JPanel panel, KeyboardListener listener) {
        this.displayPanel = displayPanel;
        this.listener = listener;
        this.panel = panel;
        this.height = height;
        this.width = width;
    }

    @Override
    public void run() {
        System.out.println("started running UI");
        frame = new JFrame();
        //preppedPanel = new JPanel();
        frame.setPreferredSize(new Dimension(height, width));
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //fullscreen

        //frame.setUndecorated(true); //no icons
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addKeyListener(listener);
        createComponents(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);

        System.out.println("frame size: " + frame.getWidth() + " " + frame.getHeight());
        System.out.println("finished running UI");

    }

    public void refreshPanel() {
        frame.dispose();
        run();
    }

    private void createComponents(Container container) {
        //JPanel panel = new JPanel();
        panel.setBounds(0, 0, width, height);
        panel.setBackground(Color.lightGray);


        container.add(panel);
    }

    public int getWidth() {
        return frame.getWidth();
    }

    public int getHeight() {
        return frame.getHeight();
    }

    public JFrame getFrame() {
        return frame;
    }
}
