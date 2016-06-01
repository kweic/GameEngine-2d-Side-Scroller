/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KeyListener;

//import Movement.JumpHandler;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import movingboxpractice.boxLogic.Boxes;
import movingboxpractice.displayBoard.DisplayPanel;

/**
 *
 * @author kw
 */
public class KeyboardListener implements KeyListener {

    //private Boxes box;
    //private JFrame frame;
    //private JumpHandler jumpHandler;
    //private int floor;
    private boolean right;
    private boolean left;
    private boolean jump;
    //private int jumpHeight;
    //private DisplayPanel panel;
    //ArrayList<Boxes> boxList;
    int boxIndex;

    public KeyboardListener() {
        //this.panel = panel;
        //jumpHandler = new JumpHandler(displayPanel);
        //this.frame = frame;
        //this.boxList = boxList;
        //this.boxIndex = boxIndex;
        right = false;
        left = false;
        jump = false;
        //this.box = box;
        //floor = 200;
        //this.jumpHeight = jumpHeight;
    }

    public boolean rightPressed() {
        return right;
    }

    public boolean leftPressed() {
        return left;
    }

    public boolean jumpPressed() {
        return jump;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("key pressed "+e.paramString());

        if (e.getKeyCode() == 68) { // D
            if (!right) {
                //System.out.println("right");
                right = true;
                left = false;
            }
            //System.out.println("right pressed");
        } else if (e.getKeyCode() == 65) { // A
            if (!left) {
                //System.out.println("left");
                left = true;
                right = false;
            }
            //System.out.println("left pressed");
        } else if (e.getKeyCode() == 32 || e.getKeyCode() == 87) { //spacebar
            System.out.println("jump");
            jump = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 68) { // D
            if (!left) {
                //System.out.println("Right stop");
                right = false;
            }
        } else if (e.getKeyCode() == 65) { // A
            if (!right) {
                //System.out.println("Left stop");
                left = false;
            }
        } else if (e.getKeyCode() == 32 || e.getKeyCode() == 87) { //spacebar
            jump = false;
        }
    }

}
