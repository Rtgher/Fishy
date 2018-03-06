package Fishy.Controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerKeys extends KeyAdapter implements BasicController {
	  Action action;
	  
	  public PlayerKeys() {
	    action = new Action();
	  }

	  public Action action() {
	    // this is defined to comply with the standard interface
	    return action;
	  }

	public void keyPressed(KeyEvent e) {
	    int key = e.getKeyCode();
	    switch (key) {
	    case KeyEvent.VK_UP:
	      action.thrust = +1;
	      break;
	    case KeyEvent.VK_DOWN:
	      action.thrust = -1;
	      break;
	    case KeyEvent.VK_LEFT:
	      action.turn = -1;
	      break;
	    case KeyEvent.VK_RIGHT:
	      action.turn = +1;
	      break;
	    case KeyEvent.VK_SPACE:
	      action.shoot = true;
	      //System.out.println("Pew");
	      break;
	    }
	  }

	  public void keyReleased(KeyEvent e) {
		  int key = e.getKeyCode();
		    switch (key) {
		    case KeyEvent.VK_UP:
		      action.thrust = 0;
		      break;
		    case KeyEvent.VK_DOWN:
		      action.thrust = 0;
		      break;
		    case KeyEvent.VK_LEFT:
		      action.turn = 0;
		      break;
		    case KeyEvent.VK_RIGHT:
		      action.turn = 0;
		      break;
		    case KeyEvent.VK_SPACE:
		      action.shoot = false;
		      break;
		    }
	  }
	}