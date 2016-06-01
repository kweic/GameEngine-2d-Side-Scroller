package Movement;

import KeyListener.KeyboardListener;
import Sounds.JumpSound;
import java.util.ArrayList;
import movingboxpractice.boxLogic.Boxes;

public class MovementHandler {

    KeyboardListener keyListen;
    JumpSound jumpSound;
    Boxes box;
    ArrayList<Boxes> boxesList;
    //int speed;
    int gravity; //atleast 2
    int reducedGrav;
    int friction; //how fast to reduce momentum after stopping
    //int jumpSpeed; // jump power
    //int jumpMax; //jump height max
    //int accel; //run acceleration
    //int floorBase;
    int floorOriginal;
    int maxSpeed; //side to side speed

    //int jumpAccel;
    //int jumpHeight;

    //int fallSpeed;
    //boolean right;
    //boolean jumping;
    boolean canJump;
    //boolean falling;
    boolean extraJump;
    
    
    public MovementHandler(JumpSound jumpSound, KeyboardListener listener, Boolean musicOn, ArrayList<Boxes> boxList, int gravity, int floor, int friction, int maxSpeed) {
        this.jumpSound = jumpSound;
        this.keyListen = listener;
        this.box = boxList.get(0);
        boxesList = boxList;
        floorOriginal = floor;
        //this.speed = 0; // stores run speed
        this.gravity = gravity; //atleast 2
        reducedGrav = gravity /2;
        this.friction = friction; //1, how fast to reduce momentum after stopping
       // this.jumpSpeed = jumpSpeed; //40, jump power

        //this.jumpMax = jumpMax; //60, jump height max
        //this.accel = accel; //4, run acceleration
        //floorBase = box.getY();
        this.maxSpeed = maxSpeed; //12, side to side speed

        //jumpAccel = jumpSpeed;

        //jumpHeight = 0;
        //fallSpeed = 0;
        //right = false;
        //jumping = false;
        canJump = true;
        //falling = false;
        extraJump = false;
        if(musicOn){
        jumpSound.loopMP3("E:\\Downloads\\02_HHavok-main.wav");
        }

    }

    public void applyMovements() {
        //System.out.println("jumpSpeed: "+jumpSpeed);

        box = boxesList.get(0);
        applyGravity();
        jumpDeceleration(true);

        checkHoldingJump();
        checkGroundReset();
        checkJumpPress();
        applySideMotion();
        checkLeftRightMotions(true);
        //System.out.println("gravity "+gravity);
        //System.out.println("floor   "+box.getFloor());

        runNPCsimulations();

    }

    public void runNPCsimulations() {
        for (int i = 1; i < boxesList.size(); i++) {
            //do other boxes simulations
            box = boxesList.get(i);
            applyGravity();
            //jumpDeceleration(false);
            checkGroundReset();
            applySideMotion();
            checkLeftRightMotions(false);
            //applyFriction();
        }
    }

    public void jumpDeceleration(boolean player) {

        if (box.getJumping()) {
            box.setY(box.getY() - (int) box.getJumpAccel());
            if (extraJump && (keyListen.jumpPressed() && player)) { //jump pressed while already jumping
                //if (jumpHeight < jumpMax) {
                //working
                //jumpAccel -= gravity / 2; //lower decel while jumping and pressing jump
                box.adjustJumpAccel(-reducedGrav); //lower while pressing jump
                // jumpHeight += gravity / 2;
                //} else {
                //    extraJump = false;
                //}
            } else if (!extraJump) {
                //System.out.println("jumpAccel: " + jumpAccel);
                //jumpAccel -= gravity;
                box.adjustJumpAccel(-gravity);
                //jumpHeight += gravity;
            }
            if (box.getJumpAccel() <= 0) {
                extraJump = false;
                box.setJumping(false);
                box.setFalling(true);
            }
        }
    }

    public void checkHoldingJump() {
        if (extraJump && !keyListen.jumpPressed()) { //stop extra jumping from holding down spacebar
            extraJump = false;
            //add part here to reduce jump accel faster
            //make it so that even with bonus jumping the speed is decreasing but decrease faster without bonus jumping
            //jumpHeight = 0;
        }
    }

    public void checkGroundReset() {
        if (box.getFalling() && box.getY() >= box.getFloor() && !keyListen.jumpPressed()) { //hit ground reset
            //System.out.println("       hit ground reset");
            //falling = false;
            box.setFalling(false);
            // jumpHeight = 0;

            canJump = true;
            
            //fallSpeed = 0;
            //jumpAccel = jumpSpeed; //jump speed reset on landing
            box.resetJumpAccel();
            jumpSound.playJumpSound("F:\\Programming\\Java\\MovingBoxPractice\\sounds\\SFX_Jump_09_landing.wav");
        }
    }

    public void checkJumpPress() {
        if (keyListen.jumpPressed() && !box.getFalling() && canJump) { //jump pressed
            extraJump = true;
            box.setJumping(true);
            canJump = false;
            //jumpSound.loadFile();
            jumpSound.playJumpSound("F:\\Programming\\Java\\MovingBoxPractice\\sounds\\SFX_Jump_22_jump.wav");
        }
    }

    public void applySideMotion() {
        box.setX(box.getX() + box.getSpeed());
    }

    public void checkLeftRightMotions(boolean player) {
        if (player && keyListen.leftPressed()) {
            if (box.getRight() && !box.getFalling() && !box.getJumping()) {
                //System.out.println("speed changed from :"+speed);
                box.setSpeed(0);
                //System.out.println("to: "+speed);
                box.setRight(false);
            } else if (box.getRight() && (box.getFalling() || box.getJumping())) { //changed to left from right
                box.setSpeed((int) (-(box.getSpeed() * 1.0) * .5)); //50% speed loss when changing direction in air
                box.setRight(false);
            }
            box.setSpeed(getSpeed(box.getSpeed(), maxSpeed, box.getRight(), box.getAccel()));
        } else if (player && keyListen.rightPressed()) {
            if (!box.getRight() && !box.getFalling() && !box.getJumping()) {
                //speed = 0;
                box.setSpeed(0);
                box.setRight(true);
            } else if (!box.getRight() && (box.getFalling() || box.getJumping())) { //changed to right from left
                //speed = (int) (-(speed * 1.0) * .5); //50% speed loss when changing direction in air
                box.setSpeed((int) (-(box.getSpeed() * 1.0) * .5));
                box.setRight(true);
            }
            box.setSpeed(getSpeed(box.getSpeed(), maxSpeed, box.getRight(), box.getAccel()));
        } else { // right or left not pressed, slow down
            applyFriction();
        }

    }

    public void applyFriction() {
        if (!box.getJumping() && !box.getFalling() && box.getSpeed() > 0) { // NO key, apply friction
            if (box.getSpeed() - friction < 0) {
                //speed = 0;
                box.setSpeed(0);
            } else {
                //speed -= friction;
                box.setSpeed(box.getSpeed() - friction);
            }

        } else if (!box.getJumping() && !box.getFalling() && box.getSpeed() < 0) { //slow down for going left and not pressing anything
            if (box.getSpeed() + friction > 0) {
                //speed = 0;
                box.setSpeed(0);
            } else {
                //speed += friction;
                box.setSpeed(box.getSpeed() + friction);
            }
        }

    }

    public void applyGravity() {
        if (box.getY() < box.getFloor()) { //gravity to apply always when not on the ground
            //box.setFalling(true);
            if (box.getFallSpeed() + box.getY() > box.getFloor()) { //modify last fall distance so no falling past the ground
                box.setFallSpeed(box.getFloor()- box.getY());
            }
            
            box.setY(box.getY() + box.getFallSpeed()); //falling           

            if (box.getJumping()) {
                //fallSpeed = gravity;
                box.setFallSpeed(gravity);

            } else if (box.getFallSpeed() < box.getJumpSpeed()) { //not jumping
                //fallSpeed += gravity; //accelerate fall speed
                box.setFallSpeed(box.getFallSpeed()+gravity);
            } else {
                //fallSpeed = jumpSpeed;
                box.setFallSpeed(box.getJumpSpeed());
            }

        } else if (box.getY() > box.getFloor()) {
            box.setY(box.getFloor()); //pop up to floor level if accidentally went through
            box.setFallSpeed(maxSpeed);
        }
    }

    private int getSpeed(int speed, int max, boolean right, int accel) {
        if (box.getJumping() || box.getFalling() && Math.abs(speed) > 0) {
            //System.out.println("returning "+speed);
            return speed;
        }
        if (Math.abs(speed) < max) {
            if (right) {
                return speed + accel;
            } else {
                return speed - accel;
            }
        }
        return speed;
    }

}
