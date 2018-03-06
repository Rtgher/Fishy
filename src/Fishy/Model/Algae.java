package Fishy.Model;

import java.awt.Color;
import java.awt.Graphics2D;

import Fishy.Constants;
import Fishy.View.Game;
import Fishy.View.LImages;
import utilities.Vector2D;

public class Algae extends GameObject {

	public Algae(Vector2D position, Vector2D velocity, double radius) {
		super(LImages.getRandAlgaeImg(),
				position, velocity,
				radius, radius);
	}
	public static Algae makeAlgae(){
		
		double x = Constants.FRAME_WIDTH  * Math.random();
		double y = Constants.FRAME_HEIGHT * Math.random();
		double vx = (Constants.MAX_SPEED /2) * Math.random();
		double vy = (Constants.MAX_SPEED /2) * Math.random();
		
		//make vectors
		Vector2D pos = new Vector2D(x,y);
		Vector2D vel = new Vector2D(vx,vy);
		int radius = (int) (10 * Math.random()) + 2;

		return new Algae(pos,vel,radius);
	}
	@Override
	public void hit(){
		super.hit();
		Game.makeNewAlgae = true;
	}

	@Override
	public void draw(Graphics2D g) {
		if(sprite!=null)
		sprite.draw(g);
		
	}

	public void collisionHandling(GameObject other){
		if (this.getClass() != other.getClass() && this.overlap(other)){
			if(other instanceof ShipFish)((ShipFish) other).mouth();
			this.hit();
			other.sprite.increase += 1;
			other.sprite.setSize();
		}
		
	}

}
