package Fishy.View;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Rtgher on 12/03/2016.
 */
public class LImages {
    /* This class holds all loaded images
    called by game class. Returns an image if needed.
     */
    private final static int nr_playerSprites = 4;
    private final static int nr_fishSprites = 2;
    private final static int nr_algaeSprites = 3;
    public static Image[] player =  new Image[nr_playerSprites];
    public static Image[] fish =  new Image[nr_fishSprites];
    public static Image[] algae = new Image[nr_algaeSprites];
    public static Image bullet;
    public static int fishint = 0, algaeint =0;

    public static boolean loadAll(){
        /* This method loads all relevant images into memory.
        assignment of images is done separetly.
        returns true if succeeded, false if not.
         */
        try {
            for (int i = 0; i < nr_playerSprites-1; i++) {
                player[i] = ImageManager.loadImage("player" + i);
            }
            player[3] = ImageManager.loadImage("player_dead");
            for(int i = 0; i < nr_algaeSprites; i++){
                algae[i] = ImageManager.loadImage("algae" + i);
            }
            for(int i=0; i < nr_fishSprites; i++){
                fish[i] = ImageManager.loadImage("fish" + i);
            }
            bullet = ImageManager.loadImage("bullet");


            //add more image loads
        }catch (java.io.IOException io){System.out.println("Could not load image files.");
        return false;}

        return true;
    }
    public static Image getRandFishImg(){
        try{
            if(fishint == nr_fishSprites)fishint = 0;
            fishint++;
            return fish[fishint-1];
        }catch(IndexOutOfBoundsException ioobe){fishint--;}

        return null;
    }

    public static Image getRandAlgaeImg(){
            try{
                if(algaeint == nr_algaeSprites)algaeint = 0;
                algaeint++;
                return algae[algaeint-1];
            }catch(IndexOutOfBoundsException ioobe){algaeint--;}

        return null;
    }
}
