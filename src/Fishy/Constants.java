package Fishy;

import java.awt.Dimension;

public class Constants {
	public static final int FRAME_HEIGHT = 600;
	public static final int FRAME_WIDTH = 800;
	public static final Dimension FRAME_SIZE = new Dimension(
			Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
	// sleep time between two frames in milliseconds
	public static final int DELAY = 30;
	// estimate for time passed between two frames in seconds 
	public static final double DT = DELAY / 3300.0;
	public static final double MAX_SPEED = 60;
	public static final double MAX_SHIP_SPEED = 150;
	public static final int MODEL_SPEED = 20;
	public static final int MODEL_DURATION = 3;
	// acceleration when thrust is applied
	public static final double MAG_ACC = 100;
	// constant speed loss factor
	public static final double DRAG = 0.015;
}