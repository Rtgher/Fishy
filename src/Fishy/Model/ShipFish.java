package Fishy.Model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Fishy.Controller.BasicController;
import Fishy.View.Game;
import Fishy.View.ImageManager;
import Fishy.View.LImages;
import Fishy.View.SoundManager;
import utilities.*;

import javax.swing.*;

import static Fishy.Constants.*;

public class ShipFish extends GameObject implements ActionListener {
	///FINAL VARIABLES
	// rotation velocity in radians per second 
    public static final double STEER_RATE = 0.8 * Math.PI;
	public static int lives;
    public static final Color COLOR = Color.cyan;
    //NON-FINAL VARIABLES
    private BasicController ctrl;
    public Vector2D direction;
	public Bullet bullet;
	private Timer animation_timer;
	private boolean invincible = false;
	private int timer_time = 600;

	public ShipFish(BasicController ctrl) {
		super(LImages.player[0], new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2), new Vector2D(0,0),10,10);
		this.ctrl = ctrl;
		animation_timer = new Timer(timer_time,this);
		bullet = null;
		direction = new Vector2D(10,0);
		this.sprite.direction = this.direction;
        this.sprite.setSize(30,30);
		animation_timer.start();
	}

	public void mouth(){
		animation_timer.stop();
		this.sprite.image = LImages.player[2];
		animation_timer.restart();
	}
	
	@Override
	public void update() {
		// turn commands
    	if(ctrl.action().turn == -1 ){
    		direction.rotate( -STEER_RATE *DT );
			velocity.rotate(velocity.angle(direction));
			velocity.addScaled(velocity, -DRAG);
			sprite.update(velocity);
    	}
    	if(ctrl.action().turn == 1 ){
    		direction.rotate( STEER_RATE *DT );
			velocity.rotate(velocity.angle(direction));
			velocity.addScaled(velocity, -DRAG);
			sprite.update(velocity);
    	}

    	//thrust commands
    	if (ctrl.action().thrust == 1){
			SoundManager.startThrust();
			if(velocity.mag() < MAX_SHIP_SPEED - size()* 0.8){
				if(ctrl.action().turn == 0 ){
					velocity.addScaled(direction.normalise(), DT );
					velocity.addScaled(velocity.clone(), (DRAG -DT));}
				else{velocity.addScaled(velocity.clone(), (DRAG -DT));}
				sprite.update(velocity);
			}
    	}
    	else if(ctrl.action().thrust == -1){
    		velocity.addScaled( velocity, -DRAG/2 );
    	}
    	else{
			SoundManager.stopThrust();
    		velocity.addScaled( velocity, -DRAG/6 );
    	}
		//fire commands;
		if(ctrl.action().shoot)
			if(bullet == null)
			{	System.out.println("pew");//debug
				Fishy.View.SoundManager.fire();
				this.bullet = new Bullet( LImages.bullet,//img
						getPosition().add(getDirection()),//position vector
						velocity.clone().mult(2) );//velocity vector
			}
    	
    	// directional addition.
    	sprite.position.addScaled(velocity, DT);
    	sprite.position.wrap(FRAME_WIDTH , FRAME_HEIGHT);
		sprite.setBox(sprite.position);
    	
    }
    @Override
    protected void hit(){
        playerHit();
    }

	protected void playerHit(){
		if(!invincible){
		animation_timer.stop();
		Game.score += (int)this.size()-14;//adds score based on size. subtracts starting size
		this.sprite.image = LImages.player[3];
		if(lives==0) {
			this.dead = true;
		}
		else{
		this.lives--;
			JOptionPane.showMessageDialog(Game.gameframe, "You were eaten once. But you still have "+(lives+1)+ " lives.");
		Game.player = new ShipFish(Game.ctrl);}
		}
	}
	@Override
	public void draw(Graphics2D g) {
		if(sprite!=null) {
			g.setColor(Color.black);
			//g.drawRect((int) sprite.box.getMinX(), (int) sprite.box.getMinY(), (int) sprite.box.getWidth(), (int) sprite.box.getHeight());//debug
			sprite.draw(g);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		//time animation
		if(sprite.image == LImages.player[0])sprite.image=LImages.player[1];
		else sprite.image = LImages.player[0];
		if(invincible)invincible=!invincible;//turns off temporary invincibility
	}
}
