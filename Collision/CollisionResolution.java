/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collision;

import Sounds.JumpSound;
import movingboxpractice.boxLogic.Boxes;

/**
 *
 * @author kw
 */
public class CollisionResolution {

    int bounce;
    //int damper;
    int extraSeperate;
    JumpSound sounds;
    String collideSound;

    public CollisionResolution(int bounce, int extraSeperate) {
        this.bounce = bounce;
        //this.damper = 2; //min 1 or div by 0 error
        this.extraSeperate = extraSeperate;
        sounds = new JumpSound();
        collideSound = "F:\\Programming\\Java\\MovingBoxPractice\\sounds\\SFX_Jump_42_collide.wav";
    }

    public void setNewFloor(Boxes box1, Boxes box2) {
        //System.out.println("test floor");

        //System.out.println("box2.getY()-box2.getHeight() != box1.getFloor() "+(box2.getY()-box2.getHeight() != box1.getFloor()));
        if (box2.getY() - box2.getHeight() != box1.getFloor()) {
            //System.out.println("floor set");

            box1.setFloor((box2.getY() - box2.getHeight()));
        }
    }

    public int fixSameDirectionBoxes(Boxes box1, Boxes box2) {
        if (box2.getSpeed() > 0 && box1.getSpeed() > 0) {
            //System.out.println("fix");
            return -box2.getSpeed();
        } else if (box2.getSpeed() < 0 && box1.getSpeed() < 0) {
            //System.out.println("fix 2");
            return -box2.getSpeed();
        }
        return box2.getSpeed();
    }

    public void collideRight(Boxes box1, Boxes box2) {
        //int box1gain = fixSameDirectionBoxes(box1, box2);
        int box1gain = box2.getSpeed()/box1.getWeight();
        int box2gain = box1.getSpeed()/box2.getWeight();
        
        separateBoxesRight(box1, box2);
        
        if (bounce != 0 && box1.getSpeed() > box1.getMaxSpeed() / 2) { //box1 is going right, bounce will be negative
            box1gain -= bounce;
        }
        if (bounce != 0 && box2.getSpeed() > box2.getMaxSpeed() / 2) { //box1 is going right, bounce will be negative
            box2gain += bounce;
            //System.out.println("-bounce b2");
        }

        finalizeCollisionRez(box1, box2, box1gain, box2gain);
    }

    public void finalizeCollisionRez(Boxes box1, Boxes box2, int b1gain, int b2gain) {
        setBoxRights(box1, b1gain);
        setBoxRights(box2, b2gain);

        collideSound(box1);
        box1.setSpeed(b1gain);
        box2.setSpeed(b2gain);
    }

    public void collideLeft(Boxes box1, Boxes box2) {
        int box1gain = fixSameDirectionBoxes(box1, box2);
        int box2gain = box1.getSpeed();
        
        separateBoxesLeft(box1, box2);
        
        if (bounce != 0 && box1.getSpeed() < box1.getMaxSpeed() / 2) { //box1 is going right, bounce will be negative
            box1gain += bounce;
        }
        if (bounce != 0 && box2.getSpeed() < box2.getMaxSpeed() / 2) { //box1 is going right, bounce will be negative
            box2gain -= bounce;
        }
        
        finalizeCollisionRez(box1, box2, box1gain, box2gain);
    }

    public void collideSound(Boxes box) {
        if(box.isPlayer()){
        sounds.playJumpSound(collideSound);
        }
    }

    static void setBoxRights(Boxes box, int gain) { //fixes the sliding wrong way problem after a bounce
        if (gain > 0) {
            box.setRight(true);
        } else {
            box.setRight(false);
        }
    }

    public void collideTopBottom(Boxes box1, Boxes box2) {
        if (!box2.getFalling() || !box2.getJumping()) {
            //land on top
            //System.out.println("new floor set: "+(box2.getY()-box2.getHeight()));
            box1.setFloor(box2.getY() - box2.getHeight());
            box1.setY(box1.getFloor());
           // collideSound(box1);
        }
    }

    public void separateBoxesLeft(Boxes box1, Boxes box2) { //stop chance of locking together on collide
        if (Math.abs(box1.getSpeed()) > Math.abs(box2.getSpeed())) {
            //box 1 is faster so give box2 most of the separation
            //if(box)
            box2.setX(box1.getX() - (box1.getWidth()+extraSeperate)); // box1 on left
        } else {
            box1.setX(box2.getX() + box1.getWidth()+extraSeperate); // box1 on left
        }
    }

    public void separateBoxesRight(Boxes box1, Boxes box2) { //stop chance of locking together on collide
        if (Math.abs(box1.getSpeed()) > Math.abs(box2.getSpeed())) {
            //box 1 is faster so give box2 most of the separation
            //if(box)
            box2.setX(box1.getX() + box1.getWidth()+extraSeperate); // box1 on left
        } else {
            box1.setX(box2.getX() - (box1.getWidth()+extraSeperate)); // box1 on left
        }
    }
}
