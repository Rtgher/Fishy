package Fishy.Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import Fishy.Constants;
import Fishy.View.Game;
import Fishy.View.LImages;
import Fishy.View.SoundManager;
import utilities.Vector2D;

import javax.swing.*;

import static Fishy.Constants.*;
import static utilities.Calculations.cointoss;

public class Fish extends GameObject implements ActionListener{

    private double slowed=0;
	private Timer chase_timer; //timer used for chaseing.
	private boolean chaseing = false;
	private int sign = 1;

	public Fish(Vector2D position,Vector2D velocity, double width, double height){
		super(LImages.getRandFishImg(),
				position, velocity,
				width, height);
		this.sprite.direction =  new Vector2D(-10,0);
		this.chase_timer = new Timer(300, this);
	}
    private void slow(){
        //handles behaviour when hit by bullet.
        slowed = 40;
    }

	@Override
	public void hit(){
		super.hit();
		Game.makeNewFish = true;
	}
	public static Fish makeFish(){
		//makes a bigger Fish by checking player size.
		double x = cointoss(0,FRAME_WIDTH-5);
		double y = cointoss(0,FRAME_HEIGHT-5);
		double vx = Constants.MAX_SPEED * Math.random();
		double vy = Constants.MAX_SPEED * Math.random();
		Vector2D pos = new Vector2D(x,y);
		Vector2D vel = new Vector2D(vx,vy);
		double width = Game.player.sprite.width + (int) (Math.random() * 5);
		double height = Game.player.sprite.height + (int) (Math.random() * 5);

		return new Fish(pos,vel, width,height);
	}
	public void chase(GameObject other, int way){
        /// the chase method should change the direction of the sprite in order to
        // try and chase the other or run away from it.
		if(!chaseing){

			Vector2D facingVec = getDirection();
			if(way == 1) {
				facingVec = other.getPosition().add(this.getDirection());
				facingVec.rotate(facingVec.angle());
				sign *= -1;
			}
			else{
				facingVec = other.getPosition().subtract(this.getDirection());
			}

			this.changeDirection(facingVec);
			this.slow();
			this.update();
			this.chaseing = true;
			chase_timer.start();
		}
	}
	@Override
	protected boolean overlap(GameObject other){
		//this calls the chase() method for fish;
		//uses basic circle overlap to check if valid;
		double dx = this.sprite.position.x - other.sprite.position.x;
		double dy = this.sprite.position.y - other.sprite.position.y;
		double distance = Math.sqrt(dx * dx + dy * dy);
		if(distance < this.size() * 1.5  + other.size() )
			if(this.getClass() ==  Fish.class ){
				if(this.size()<= other.size())this.chase(other,-1);
				else this.chase(other,1);
			}
        //calls to see if collision actually happened.
		return super.overlap(other);
	}
	@Override
	public void draw(Graphics2D g) {
		if(sprite!=null)
        sprite.draw(g);
	}

    @Override
    public void update(){
        if( slowed >0 )slowed -= 0.005;//decrease slow to simmulate effect wearing off.
        velocity.addScaled(getDirection().normalise(), ((MAX_SPEED-size())-slowed) * DT );
        velocity.addScaled(new Vector2D(velocity), -DRAG/2);
        sprite.update(velocity);
    }

	public void collisionHandling(GameObject other){
        //checks for collision
		if(other!=null){
            if (this.overlap(other))
            {
                if(other instanceof ShipFish){
                    if(this.size()<= other.size()){
                        this.hit();
						SoundManager.ate_fish();
						((ShipFish) other).mouth();
                        other.sprite.increase+=2;
						((ShipFish)other).sprite.setSize();
                    }
                    if(this.size()>other.size()){
						((ShipFish)other).hit();
                        this.sprite.increase+=2;
                        this.sprite.setSize();
                    }
                    return;
                }

            //handles for collision with bullet.
            if (other instanceof Bullet) {
                other.hit();
                this.slow();
                }
            }
        }

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		chaseing=false;
		chase_timer.stop();
	}
}
