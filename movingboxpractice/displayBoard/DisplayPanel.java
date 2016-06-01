/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingboxpractice.displayBoard;

import KeyListener.KeyboardListener;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JPanel;
import movingboxpractice.boxLogic.Boxes;

/**
 *
 * @author kw
 */
public class DisplayPanel {

    private JPanel panel;
    ArrayList<Boxes> boxList;

    public DisplayPanel(JPanel panel, ArrayList<Boxes> boxList) {
        this.panel = panel;
        this.boxList = boxList;
    }
    

    public void placeComponents() {
        panel.removeAll();
        for (Boxes box : boxList) {
            panel.add(box.getBoxPanel());
        }
    }
    
    public JPanel getPanel(){
        return panel;
    }
    
    public void refreshBoard(){
        //System.out.println("refresh "+System.currentTimeMillis()/10);
        panel.repaint();
    }

}
