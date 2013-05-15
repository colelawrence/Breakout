package cole.breakout;

import java.applet.Applet;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Execution extends Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4429345609602509190L;

	private Image bufferImage;
	public static Graphics b;
	public static Action a;
	public static Execution instance;
	public static Audio audio;

	public void init() {
		this.setSize(600, 600);
		// Sounds
		audio = new Audio(this);

		instance = this;
		bufferImage = createImage(this.getWidth(), this.getHeight());
		b = bufferImage.getGraphics();
		a = new Action();
		a.init();
		addMouseMotionListener(a);
		Timer painter = new Timer();
		TimerTask toPaint = new TimerTask() {
			@Override
			public void run() {
				Execution.instance.repaint();
			}
		};
		painter.scheduleAtFixedRate(toPaint, 17, 17);
	}

	public void paint(Graphics g) {
		b.setColor(new Color(230, 230, 230));
		b.fillRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < a.bricks.size(); i++) {
			Rectangle r = a.bricks.get(i);
			Color brickColor;
			switch (a.btypes.get(i)) {
			case 2:
				brickColor = new Color(255, 110, 0);
				break;
			case 1:
				brickColor = new Color(255, 60, 0);
				break;
			case 0:
				brickColor = new Color(255, 20, 0);
				break;
			default:
				brickColor = new Color(0, 160, 255);
			}
			b.setColor(new Color(255, 160, 0));
			b.fillRect(r.x, r.y, r.width, r.height);
			b.setColor(brickColor);
			b.fillRect(r.x + 4, r.y + 4, r.width - 8, r.height - 8);
		}
		b.setColor(new Color(35, 35, 35));
		b.fillRect(a.ball.x, a.ball.y, a.ball.width, a.ball.height);
		b.fillRect(a.paddle.x, a.paddle.y, a.paddle.width, a.paddle.height);
		g.drawImage(bufferImage, 0, 0, Color.BLACK, this);
	}

	public void update(Graphics g) {
		a.act();
		paint(g);
	}
}
