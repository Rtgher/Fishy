package Fishy.Model;

import com.sun.javafx.geom.*;
import javafx.geometry.BoundingBox;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static Fishy.Constants.DT;
import static Fishy.Constants.FRAME_HEIGHT;
import static Fishy.Constants.FRAME_WIDTH;
import static Fishy.Constants.MAG_ACC;
import static Fishy.Constants.DRAG;

/**
 * Created by Rtgher on 12/03/2016.
 */

public class Sprite {
    public Image image;
    BoundingBox box;
    public Vector2D position;
    public Vector2D direction;
    public double width, height;
    public double size;//to handle who eats who
    public double increase;//increases size if object ate another

    public Sprite(double width, double height){
        this.width = width;
        this.height = height;
        this.direction = new Vector2D(10,0);
        size = Math.sqrt( (Math.pow(width,2) + Math.pow(height,2)) );
        increase = 0;
    }

    public Sprite(Image image, double width, double height){
        this(width,height);
        this.image =  image;
    }
    public Sprite(Image image, double width, double height, Vector2D position){
        this(image, width, height);
        this.position = position;
        setBox(position);
    }
    public Sprite(Image image, double width, double height, Vector2D position, Vector2D direction){
        this(image,width,height,position);
        this.direction = direction;
    }

    public Sprite update(Vector2D velocity){
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH,FRAME_HEIGHT);
        setBox(position);
        return this;
    }

    public void changeDirection(Vector2D way){
        this.direction = way;
    }
    protected void setBox(Vector2D position){
        try {
            box = new BoundingBox((position.x + width / 4), (position.y + height / 4), width* 0.75, height * 0.60);
        }catch(NullPointerException npe){System.out.println("Failed to set position of object " + this.getClass().getName() + ". \n Null pointer exception found.");}
    }
    public void setSize(double width, double height){
        //resizes the sprite.
        this.width = width + increase;
        this.height = height + increase;
        size = Math.sqrt( (Math.pow(width,2) + Math.pow(height,2)) );
        increase = 0;
    }
    public void setSize(){
        this.width += increase;
        this.height += increase;
        size = Math.sqrt( (Math.pow(width,2) + Math.pow(height,2)) );
        increase = 0;
    }

    public void draw(Graphics2D g) {
        double imW = image.getWidth(null);
        double imH = image.getHeight(null);
        AffineTransform t = new AffineTransform();
        t.rotate(direction.angle());
        t.scale(width / imW, height / imH);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x + width/2, position.y+ height/2);
        g.drawImage(image, t, null);
        g.setTransform(t0);
    }
}