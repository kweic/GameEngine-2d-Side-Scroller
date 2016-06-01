/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingboxpractice;

import Collision.CollisionDetection;
import KeyListener.KeyboardListener;
import Movement.MovementHandler;
import Sounds.JumpSound;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JPanel;
import movingboxpractice.boxLogic.Boxes;
import movingboxpractice.displayBoard.DisplayBoard;
import movingboxpractice.displayBoard.DisplayPanel;

public class MovingBoxPractice {

    //goal, make that box able to jump on another box
    //goal, make a pit that can easily be placed and extends the ground lower
    public static void main(String[] args) {
        int gravity = 5; //atleast 2, 5
        int friction = 1; //how fast to reduce momentum after stopping, default 1
        int jumpSpeed = 40; // jump power, 40
        //int jumpMax = 60; //jump height max, 60
        int accel = 4; //run acceleration, 4
        int maxSpeed = 12; //side to side speed, 12
        int floor = 600;
        boolean musicOn = false;
        int collisionCheckFrequency = 3; //1 == check on each game loop

        //collision
        int bounce = 0;
        int collidePadding = 1;
        //end collision

        ArrayList<Boxes> boxesList = makeBoxes(accel, floor);
        DisplayPanel panelDisplay = makeDisplayPanel(boxesList); //DisplayPanel handles updating location of boxes

        KeyboardListener keyListen = new KeyboardListener();
        DisplayBoard board = new DisplayBoard(panelDisplay, 1800, 800, panelDisplay.getPanel(), keyListen);

        JumpSound jumpSound = new JumpSound();

        MovementHandler movement = new MovementHandler(jumpSound, keyListen, musicOn, boxesList, gravity, floor, friction, maxSpeed);

        CollisionDetection collisionDetector = new CollisionDetection(floor, bounce, collidePadding);

        board.run();

        int i = 0;
        while (true) {
            try {
                i += 2;
                Thread.sleep(17);
                movement.applyMovements();
                
                testBoxMovements(i, boxesList);
                collisionDetector.checkCollisions(boxesList, collisionCheckFrequency, i);

                panelDisplay.placeComponents();
                panelDisplay.refreshBoard();
                if (i > 1000) {
                    i = 0;
                }
            } catch (InterruptedException ex) {
                System.out.println("Main loop fail");
                break;
            }
        }

    }

    static void testBoxMovements(int i, ArrayList<Boxes> boxesList) {
        int moveSpeed = 5;
        if (i > 1000) {
            boxesList.get(1).setX(0);
        }
        
        //if (i % 3 == 0) {
            //boxesList.add(new Boxes(Color.blue, 500, 0, 75, 75, accel, floor, false));
           // boxesList.get(1).setX(boxesList.get(1).getX() + moveSpeed);
            boxesList.get(1).setSpeed(moveSpeed);
            //System.out.println("testBoxes: "+boxesList.size());
        //}
    }

    static DisplayPanel makeDisplayPanel(ArrayList<Boxes> boxesList) {
        JPanel panel = new JPanel();//display board
        panel.setLayout(null);
        return new DisplayPanel(panel, boxesList);
    }

    static ArrayList<Boxes> makeBoxes(int accel, int floor) {
        ArrayList<Boxes> boxesList = new ArrayList();
        int box1Weight = 1;
        int box2Weight = 4;
        int jumpSpeed = 40;
        Boxes box = new Boxes(Color.DARK_GRAY, 330, floor, 75, 75, accel, floor, true, box1Weight, jumpSpeed); //main test box
        Boxes box2 = new Boxes(Color.red, 900, floor, 75, 75, accel, floor, false, box2Weight, jumpSpeed); //test box 1
        //Boxes box3 = new Boxes(Color.black, 330, floor, 75, 75, accel, floor, false, 1); //text box 2
        //Boxes box4 = new Boxes(Color.black, 800, floor, 75, 75, accel, floor);

        boxesList.add(box);
        boxesList.add(box2);
        //boxesList.add(box3);
        //boxesList.add(box4);
        return boxesList;
    }

}
