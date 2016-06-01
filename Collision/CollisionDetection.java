/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collision;

import java.util.ArrayList;
import movingboxpractice.boxLogic.Boxes;

/**
 *
 * @author kw
 */
public class CollisionDetection {

    CollisionResolution collider;
    int floor;

    public CollisionDetection(int floor, int bounce, int collidePadding) {
        collider = new CollisionResolution(bounce, collidePadding);
        this.floor = floor;
    }

    public void checkCollisions(ArrayList<Boxes> boxList, int frequency, int n) {
        if (n % frequency == 0) { //find better way to do frequency check
            for (int i = 0; i < boxList.size(); i++) {
                for (int x = 0; x < boxList.size(); x++) {
                    //System.out.println("checking: " + i + " vs " + x);
                    if (i != x
                            && (boxList.get(i).getY() != boxList.get(i).getPreviousY()
                            || boxList.get(i).getX() != boxList.get(i).getPreviousX())) {
                        checkCollide(boxList.get(i), boxList.get(x), x, boxList);
                        boxList.get(i).setPreviousX(boxList.get(i).getX());
                        boxList.get(i).setPreviousY(boxList.get(i).getY());
                    }
                }
            }
        }
    }

    private void checkLandOnCollisions(ArrayList<Boxes> boxList) {
        for (int i = 0; i < boxList.size(); i++) {
            for (int x = 0; x < boxList.size(); x++) {
                if (i != x) {
                    //System.out.println("land ons: " + i + " vs " + x);
                    //System.out.println("check land on: "+boxList.get(i).getY()+" vs "+boxList.get(x).getY());
                    if (boxList.get(i).getY() < boxList.get(x).getY() && checkAboutToLand(boxList.get(i), boxList.get(x))) {
                        System.out.println("land on collision");
                        boxList.get(i).setOnBox(true);
                        collider.setNewFloor(boxList.get(i), boxList.get(x));
                    }
                }
            }
        }
    }

    private void checkCollide(Boxes box1, Boxes box2, int x, ArrayList<Boxes> boxList) {
        checkOnBox(box1, box2, x, boxList); //check and turn off the onBox setting while riding on another box

        if (!box1.onBox() && checkFloorReset(box1, box2)) {
            box1.setFloor(floor);
        }
        checkLandOnCollisions(boxList);
        //check about to land on another
        //System.out.println("box1.getY() "+box1.getY());
        /*
        if (box1.getY() < box2.getY() && checkAboutToLand(box1, box2)) {
            box1.setOnBox(true);
            collider.setNewFloor(box1, box2);
        }
         */
        checkSideCollisions(box1, box2);
    }

    private boolean checkTop(Boxes box1, Boxes box2) {
        System.out.println("check top");
        return (box1.getX() + box1.getWidth() / 2 > box2.getX() - box2.getWidth() / 3
                && box1.getX() + box1.getWidth() / 2 < box2.getX() + box2.getWidth() + box2.getWidth() / 3 //within x axis
                && box1.getY() < box2.getY()
                && box1.getY() > box2.getY() - box2.getHeight() - 1); //-1 because landing check sets it on top

    }

    private boolean checkBottom(Boxes box1, Boxes box2) {
        return (box1.getX() + box1.getWidth() / 2 > box2.getX() //- box2.getWidth() / 3
                && box1.getX() + box1.getWidth() / 2 < box2.getX() + box2.getWidth() //+ box2.getWidth() / 3
                && box1.getY() + box1.getHeight() < box2.getY()
                && box1.getY() + box1.getHeight() < box2.getY() + box2.getHeight());
    }

    private boolean checkRight(Boxes box1, Boxes box2) {
        return (box1.getX() + box1.getWidth() > box2.getX()
                && box1.getX() + box1.getWidth() < box2.getX() + box2.getWidth() //within box 2 in x axis
                && box1.getY() + box1.getHeight() / 2 > box2.getY() - box2.getHeight() / 3
                && box1.getY() + box1.getHeight() / 2 < box2.getY() + box2.getHeight() + box2.getHeight() / 3); // middle of box1 within y axis
    }

    private boolean checkLeft(Boxes box1, Boxes box2) {
        return (box1.getX() < box2.getX() + box2.getWidth()
                && box1.getX() > box2.getX()
                && box1.getY() + box1.getHeight() / 2 > box2.getY() - box2.getHeight() / 3
                && box1.getY() + box1.getHeight() / 2 < box2.getY() + box2.getHeight() + box2.getHeight() / 3);
    }

    private void checkOnBox(Boxes box1, Boxes box2, int x, ArrayList<Boxes> boxList) {
        if (box1.onBox() && !rideCheck(x, boxList)) { //finds if box is no longer standing on another
            box1.setOnBox(false);
        }
    }

    private boolean checkFloorReset(Boxes box1, Boxes box2) {
        return (box1.getX() > box2.getX() + box2.getWidth() // too far right
                || box1.getX() + box1.getWidth() < box2.getX() // too far left
                || box1.getY() > box2.getY() + box2.getHeight() //box is too far below
                || box1.getY() + box1.getHeight() * 4 < box2.getY()); //too far above
    }

    private boolean checkAboutToLand(Boxes box1, Boxes box2) {
        //System.out.println("box1.getY() " + box1.getY());

        /*
        if (box1.getY() != 600) {
            System.out.println("box1.getY() " + box1.getY());
            System.out.println("box1.getX() + box1.getWidth() > box2.getX() " + (box1.getX() + box1.getWidth() > box2.getX()));
            //System.out.println("box1 x: "+box1.getX()+" b2 x: "+box2.getX());
            System.out.println("box1.getX() < box2.getX() + box2.getWidth() " + (box1.getX() < box2.getX() + box2.getWidth()));
            System.out.println("box1.getY() < box2.getY() " + (box1.getY() < box2.getY()));
            System.out.println("box1Y: " + box1.getY() + " b2 Y: " + box2.getY());
            System.out.println("box1.getY() > box2.getY() - box2.getHeight() * 2 " + (box1.getY() > box2.getY() - box2.getHeight() * 2));
            System.out.println("");
        }
         */
        System.out.println("checking about to land");
        return (box1.getX() + box1.getWidth() > box2.getX() //not too far to the left
                && box1.getX() < box2.getX() + box2.getWidth() //not too far right
                && box1.getY() + box1.getHeight() > box2.getY() - (box2.getHeight() * 2)); // bottom of box is greater than height of box2 - some
    }

    private void checkSideCollisions(Boxes box1, Boxes box2) {
        //box is on the right
        if (box1.getX() < box2.getX() && checkRight(box1, box2)) {
            //System.out.println("collide right");
            collider.collideRight(box1, box2);

            return;
            //box is on the left
        } else if (box1.getX() > box2.getX() && checkLeft(box1, box2)) {
            //System.out.println("collide left");
            collider.collideLeft(box1, box2);
            return;
        }
        //box is above
        if (box1.getY() < box2.getY() && checkTop(box1, box2)) {
            //System.out.println("collide top");
            collider.collideTopBottom(box1, box2);
            //box is below
        } else if (box1.getY() > box2.getY() && checkBottom(box1, box2)) {
            //System.out.println("collide bottom");
            collider.collideTopBottom(box1, box2);
        }
    }

    public boolean checkRiding(Boxes box1, Boxes box2) {
        //return true if something is close under
        return (box1.getX() + box1.getWidth() > box2.getX() //- box2.getWidth() / 3
                && box1.getX() < box2.getX() + box2.getWidth() //+ box2.getWidth() / 3
                && box1.getY() <= box2.getY() + box2.getHeight()
                && box1.getY() > box2.getY() - box2.getHeight() * 2);

    }

    public boolean rideCheck(int x, ArrayList<Boxes> boxList) {
        //System.out.println("ride check");
        for (int i = 1; i < boxList.size(); i++) {
            //find if something is close under box0, if it is return true if this is true, return
            if (checkRiding(boxList.get(0), boxList.get(i))) {
                //System.out.println("box is riding");
                return true;
            }
        }
        //System.out.println("box NOT riding");
        return false; //not riding anything
    }
}
