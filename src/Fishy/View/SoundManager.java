package Fishy.View;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import java.io.File;

// SoundManager for Asteroids

public class SoundManager {

	static int nBullet = 0;
	static boolean thrusting = false;

	// this may need modifying
	final static String path = System.getProperty("user.dir") + "/bin/Fishy/Assets/Sounds/";

	// note: having too many clips open may cause
	// "LineUnavailableException: No Free Voices"

	public final static Clip bullet  = getClip("pew");
	public final static Clip bangMedium = getClip("burp");
	public final static Clip thrust = getClip("thrust");
	public final static Clip[] clips = { bangMedium, thrust, bullet};


	// methods which do not modify any fields

	public static void play(Clip clip) {
		clip.setFramePosition(0);
		clip.start();
	}

	private static Clip getClip(String filename) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path
					+ filename + ".wav"));
			clip.open(sample);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clip;
	}

	// methods which modify (static) fields
	public static void fire() {
		// fire the n-th bullet and increment the index
		Clip clip = bullet;
		clip.setFramePosition(0);
		clip.start();
	}

	public static void startThrust() {
		if (!thrusting) {
			thrust.loop(-1);
			thrusting = true;
		}
	}

	public static void stopThrust() {
		thrust.loop(0);
		thrusting = false;
	}

	// Custom methods playing a particular sound
	// Please add your own methods below

	public static void ate_fish() {
		play(bangMedium);
	}

}
