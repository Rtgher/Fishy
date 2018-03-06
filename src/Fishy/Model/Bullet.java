package Fishy.Model;

import Fishy.View.Game;
import utilities.Vector2D;

import java.awt.*;

import static Fishy.Constants.*;
import static utilities.Calculations.between;

/**
 * Created by Rtgher on 17/03/2016.
 */
public class Bullet extends GameObject {
    public static int nr_fired=0;

    public Bullet(Image img, Vector2D position, Vector2D velocity) {
        super(img, position, velocity, Game.player.sprite.width/2, Game.player.sprite.height/2);
        nr_fired++;
    }

    @Override
    public void hit(){
        this.dead = true;
        Game.player.bullet = null;
    }
    public void update(){

        velocity.addScaled(velocity, DT );
        //velocity.addScaled(new Vector2D(velocity), -DRAG/2);
        sprite.position.addScaled(velocity, DT);
        sprite.setBox(getPosition());

        if(!between(getPosition().x,0,FRAME_WIDTH))
            if(!between(getPosition().y,0,FRAME_HEIGHT)){
                this.hit();}
    }

    @Override
    public void draw(Graphics2D g) {
        if(sprite!=null)
            sprite.draw(g);
    }
}
