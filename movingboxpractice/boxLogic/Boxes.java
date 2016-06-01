/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingboxpractice.boxLogic;

import java.awt.Color;
import javax.swing.JPanel;

/**
 *
 * @author kw
 */
public class Boxes {

    //private Color color;
    private int x;
    private int y;
    int previousX;
    int previousY;
    private int width;
    private int height;

    JPanel boxPanel;
    boolean jumpable;

    int speed; //current x speed
    int jumpSpeed; //jump power
    //int jumpMax; //max height, may not need
    int accel; //run accel
    int maxSpeed; //max run speed
    int jumpAccel; //current jump
    int fallSpeed; //current fall speed, maybe disuse this and just inverse jumpAccel
    int floorBase;
    int weight;

    
    
    boolean right; //player pressing right or not
    //boolean left; //player pressing left or not
    boolean jumping;
    boolean canJump;
    boolean falling;
    boolean extraJump;
    boolean onBox;
    boolean player;
    Color color;
    //int jumpHeight;

    public Boxes(Color color, int x, int y, int width, int height, int accel, int floorBase, boolean isPlayer, int weight, int jumpSpeed) {
        this.player = isPlayer;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.jumpSpeed = jumpSpeed;
        jumpAccel = jumpSpeed;
        JPanel boxPanel = new JPanel();
        boxPanel.setSize(width, height);
        this.boxPanel = boxPanel;
        boxPanel.setBackground(color);
        this.color = color;
        this.x = x;
        this.y = y;
        previousX = x;
        previousY = y;
        this.accel = accel;
        jumpable = true;
        falling = false;
        right = false;
        this.floorBase = floorBase;
        onBox = false;
        // left = false;
    }
    
    public void resetJumpAccel(){
        jumpAccel = jumpSpeed;
    }
    
    public int getJumpAccel(){
        return jumpAccel;
    }
    public int getJumpSpeed(){
        return jumpSpeed;
    }
    
    public void adjustJumpAccel(int adjust){
        jumpAccel += adjust;
    }

    public void setPreviousX(int preX) {
        previousX = preX;
    }

    public void setPreviousY(int preY) {
        previousY = preY;
    }

    public int getPreviousX() {
        return previousX;
    }

    public int getPreviousY() {
        return previousY;
    }

    public int getWeight() {
        return weight;
    }

    public int getFallSpeed() {
        return fallSpeed;
    }

    public void setFallSpeed(int fallSpeed) {
        this.fallSpeed = fallSpeed;
    }

    public boolean isPlayer() {
        return player;
    }

    public boolean onBox() {
        return onBox;
    }

    public void setOnBox(boolean onBox) {
        this.onBox = onBox;
    }

    public void printColor() {
        System.out.println(color.toString());
    }

    public int getFloor() {
        return floorBase;
    }

    public void setFloor(int floor) {
        floorBase = floor;
    }

    public boolean getRight() {
        return right;
    }

    //public boolean getLeft(){
    //    return left;
    //}
    public void setRight(boolean right) {
        this.right = right;
    }

    //public void setLeft(boolean left){
    //    this.left = left;
    //}
    public boolean getFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean getJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public int getSpeed() {
        return speed;
    }


    public int getAccel() {
        return accel;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //public int getFall
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public JPanel getBoxPanel() {
        boxPanel.setLocation(x, y);
        return boxPanel;
    }

    public Color getColor() {
        return color;
    }

    public void setX(int x) {
        this.x = x;
        //printPosition();
    }

    public void setY(int y) {
        this.y = y;
        //printPosition();
    }

    public void setJumpable(boolean jumpable) { //ability to turn on or off jumping based on where they are
        this.jumpable = jumpable;
    }

    public boolean onJumpableSurface() {
        return jumpable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void printPosition() {
        System.out.println("x: " + x + " y:" + y);
    }
}
