package Fishy.View;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import javax.swing.JComponent;

import Fishy.Model.Algae;
import Fishy.Constants;
import Fishy.Model.Fish;

public class View extends JComponent {
	// background colour
	public static final Color BG_COLOR = Color.blue;

	private Game game;

	public View(Game game) {
		this.game = game;
	    this.setFocusable(true);
	    this.requestFocusInWindow();
	    
	}

	@Override
	public void paintComponent(Graphics g0) {
		Graphics2D g = (Graphics2D) g0;
		try{
		Image background = ImageManager.loadImage("background");
			double imW = background.getWidth(null);
			double imH = background.getHeight(null);
			AffineTransform t = new AffineTransform();
			t.scale(Constants.FRAME_WIDTH / imW, Constants.FRAME_HEIGHT / imH);
			AffineTransform t0 = g.getTransform();
			g.drawImage(background, t, null);
			g.setTransform(t0);
		}catch(IOException io){
			// paint the background using fill if image fails to load
			g.setColor(BG_COLOR);
			g.fillRect(0, 0, getWidth(), getHeight());}

		synchronized (Game.class) {
			//for draw loop
			for (Fish fishy : game.fish) if (!fishy.dead) fishy.draw(g);
			for (Algae alga : game.algae) if (!alga.dead) alga.draw(g);
			if (!game.player.dead) game.player.draw(g);
			if(game.player.bullet!=null)game.player.bullet.draw(g);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return Constants.FRAME_SIZE;
	}
}