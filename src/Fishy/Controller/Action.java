package Fishy.Controller;

public class Action {
	  public int thrust; // 0 = off, 1 = on -1 = reverse;
	  public int turn; // -1 = left turn, 0 = no turn, 1 = right turn 
	  public boolean shoot; 
	  public Action(){
		  thrust=0;
		  turn=0;
		  shoot=false;
	  }
	}