package Fishy.View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rtgher on 12/03/2016.
 */
public class ImageManager {
    final static String path = System.getProperty("user.dir") + "/bin/Fishy/Assets/Sprites/";
    final static String ext = ".png";
    static Map<String, Image> images = new HashMap<String, Image>();

    public static Image getImage(String s) {
        return images.get(s);
    }
    public static Image loadImage(String fname)
            throws IOException {
        try{
            System.out.println("Loading: " + path + fname + ext);
            images.put(fname, ImageIO.read(new File(path + fname + ext)));
        }catch (IOException exc){System.out.println("Failed to load img: " + fname + ext);}

        return images.get(fname);
    }
    public static Image loadImage(String imName, String fname)
            throws IOException {
        try{
            images.put(imName, ImageIO.read(new File(path + fname + ext)));
        }catch (IOException exc){System.out.println("Failed to load img: " + fname + ext);}

        return images.get(imName);
    }

    public static void loadImages(String[] fNames)
            throws IOException {
        for(String fName: fNames){
            try{
                images.put(fName, ImageIO.read(new File(path + fName + ext)));
            }catch (IOException exc){System.out.println("Failed to load img: " + fName + ext);}

        }
    }
    public static void loadImages(Iterable<String> fNames)
            throws IOException {
        for(String fName: fNames){
            try{
                images.put(fName, ImageIO.read(new File(path + fName + ext)));
            }catch (IOException exc){System.out.println("Failed to load img: " + fName + ext);}
        }
    }
}
