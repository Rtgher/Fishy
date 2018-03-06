package Fishy.Model;
import static Fishy.Constants.FRAME_HEIGHT;
import static Fishy.Constants.FRAME_WIDTH;
import static Fishy.Constants.DT;

import java.awt.*;

import utilities.*;

public abstract class GameObject {
	//VARIABLES;
	public Sprite sprite;
	public boolean dead;
	public Vector2D velocity;
	
	public GameObject(Vector2D velocity){
		this.dead = false;
		this.velocity = velocity;
	}
	public GameObject(Image img, Vector2D position, Vector2D velocity, double width, double height){
		this(velocity);
		sprite = new Sprite(img, width, height, position);
	}
	public double size(){
		return this.sprite.size;
	}
	public abstract void draw(Graphics2D g);

	protected void hit(){
		this.dead = true;
	}
	///// Movement methods
	public void rotate(double angle){
		//rotates the sprite by angle;
		this.sprite.direction.rotate(angle);
	}
	public void update(){
		sprite.update(velocity);
	}

	public void changeDirection(Vector2D way){
		//shortcup to the sprite change direction for typing sense.
		this.sprite.changeDirection(way);
	}
	public Vector2D getDirection(){
		//returns copy of the sprite direction for typing sense.
		return this.sprite.direction.clone();
	}
	public Vector2D getPosition(){
		//returns copy of position of GameObejct sprite for typing sense.
		return this.sprite.position.clone();
	}

	///////collision handling
	protected boolean overlap(GameObject other){
		//simple overlap detection based on bounding boxes2d
		if(other.sprite.box == null)return false;
		if (this.sprite.box.intersects(other.sprite.box)) {
			return true;}
		
		return false;
		
	}
	
}
