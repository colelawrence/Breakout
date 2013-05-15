package cole.breakout;

import java.applet.AudioClip;

public class Audio {
	private AudioClip wallhit;
	private AudioClip brickhit;
	private AudioClip brickbreak;
	private AudioClip paddlehit;
	private AudioClip lose;
	private AudioClip win;

	public Audio(Execution ex) {
		wallhit = ex.getAudioClip(ex.getCodeBase(), "wallhit.wav");
		brickhit = ex.getAudioClip(ex.getCodeBase(), "brickhit.wav");
		brickbreak = ex.getAudioClip(ex.getCodeBase(), "brickbreak.wav");
		paddlehit = ex.getAudioClip(ex.getCodeBase(), "paddlehit.wav");
		lose = ex.getAudioClip(ex.getCodeBase(), "lose.wav");
		win = ex.getAudioClip(ex.getCodeBase(), "win.wav");
	}

	public void playWallHit() {
		wallhit.play();
	}

	public void playBrickHit() {
		brickhit.play();
	}

	public void playBrickBreak() {
		brickbreak.play();
	}

	public void playPaddleHit() {
		paddlehit.play();
	}

	public void playLose() {
		lose.play();
	}

	public void playWin() {
		win.play();
	}
}
