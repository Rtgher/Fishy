package Fishy.View;

import java.util.ArrayList;

import static Fishy.Constants.MODEL_SPEED;
import static Fishy.Constants.MODEL_DURATION;

import Fishy.Model.Algae;
import Fishy.Model.Fish;
import Fishy.Controller.PlayerKeys;
import Fishy.Model.ShipFish;
import utilities.JEasyFrame;

import javax.swing.*;


public class Game {
  public static final int max_fish = 5;
  public static final int nr_algae = 10;
  public static ArrayList<Fish> fish;
  public static ArrayList<Algae> algae;
  public static ShipFish player;
  public static PlayerKeys ctrl;
  public static boolean makeNewFish = false;
  public static boolean makeNewAlgae = false;
    public static int score=0;
    public static JEasyFrame gameframe;

  public Game(PlayerKeys ctrl) {
	  this.ctrl = ctrl;
      score=0;
      //load images
      if(
      LImages.loadAll()) {
          //make all objects
          player = new ShipFish(ctrl);
          player.lives = 3;
          player.sprite.image = LImages.player[0];
          fish = new ArrayList<Fish>();
          for (int i = 0; i < max_fish; i++) {
              fish.add(Fish.makeFish());
              fish.get(i).sprite.image = LImages.getRandFishImg();
          }
          algae = new ArrayList<Algae>();
          for (int i = 0; i < nr_algae; i++) {
              algae.add(Algae.makeAlgae());
              algae.get(i).sprite.image = LImages.getRandAlgaeImg();
          }
      }
    
  }

  public static void main(String[] args) throws Exception {
	ctrl = new PlayerKeys();
	Game game = new Game(ctrl);
    View view = new View(game);
    //frame setup
    gameframe = new JEasyFrame(view, "Fishy");
    gameframe.addKeyListener(ctrl);
      JOptionPane.showMessageDialog(gameframe, "Something feels fishy. Time to swim!");
    gameframe.requestFocusInWindow();
    gameframe.requestFocus();
    //gameloop
          int i = 0;
          while (!player.dead) {
              long time0 = System.currentTimeMillis();
              game.update();
              i++;
              if (i == MODEL_SPEED) {
                  view.repaint();
                  i = 0;
              }
              Thread.sleep(Math.max(0,
                      MODEL_DURATION + time0 - System.currentTimeMillis()));
          }
          JOptionPane.showMessageDialog(gameframe, "You lost. There's always a bigger fish.");
      JOptionPane.showMessageDialog(gameframe,"You managed to score: "+score);

  }
  public static void FoundCollision(){
      //main collision find call
	  if (makeNewAlgae){
		  algae.add(Algae.makeAlgae());
		  makeNewAlgae = false;
	  }
	  for(Algae alga : algae)
		  if(!alga.dead){
			  alga.collisionHandling(player);//check collision between algae and player
              for(Fish fishy : fish)
                  if(!fishy.dead) {
                      alga.collisionHandling(fishy);//check collision between algae and fish
                      fishy.collisionHandling(player);//check collision between fish and player
                      if(player.bullet!=null)
                          fishy.collisionHandling(player.bullet);
                  }

		  }
	  if(makeNewFish){
		  fish.add(Fish.makeFish());
		  makeNewFish = false;
	  }

  }

  public void update() {
      if(player.bullet!=null)player.bullet.update();
        for (Fish fishy : fish) {
            fishy.update();
        }
        for(Algae alg : algae){
            alg.update();
        }
        player.update();

        FoundCollision();
  }
}