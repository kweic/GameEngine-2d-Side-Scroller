/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sounds;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
//import javax.media.*;


/**
 *
 * @author kw
 */
public class JumpSound {
    Clip soundClip;
    File soundFile;
    
    public JumpSound(){
        
    }
    
    private void loadWavFile(){
        
        try{
            //System.out.println("trying to load");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            //System.out.println("audioIn done");
        soundClip = AudioSystem.getClip();
            //System.out.println("getclip done");
        soundClip.open(audioIn);
            //System.out.println("open done");
        
        }catch (Exception e){
            
        }
    }
    
    public void playJumpSound(String file){
        soundFile = new File(file);
        //System.out.println("soundfile is: "+file);
        loadWavFile();
        soundClip.start();
        
    }
    
    
    //public void loadMP3(){
    //    System.out.println("loading mp3");
    //    Player playMP3 = new Player();
        
    //}
    
    public void loopMP3(String file){
        soundFile = new File(file);
        //System.out.println("soundfile is: "+file);
        loadWavFile();
        FloatControl volume = (FloatControl)soundClip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-15.0f);
        
        soundClip.start();
        //soundClip.loop(1);
        soundClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
