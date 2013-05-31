package cole.breakout;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Action implements MouseMotionListener, MouseListener {
	Rectangle ball = new Rectangle(0, 0, 20, 20);
	Rectangle paddle = new Rectangle(0, 0, 100, 20);
	ArrayList<Rectangle> bricks = new ArrayList<Rectangle>();
	ArrayList<Integer> btypes = new ArrayList<Integer>();
	double dX = 0;
	double dY = 0;
	double locX = 0;
	double locY = 0;
	double maxSpeed = 13;
	int width = 600;
	int height = 540;
	int paddleX = 100;
	int paddleWidth = 100;
	boolean lost = false;
	boolean start = false;
	boolean won = false;
	final int side = ball.width;

	public void init() {
		populateBricks();
		width = Execution.instance.getWidth();
		height = Execution.instance.getHeight();
		paddle.y = height - paddle.height * 3;
		locX = (width - side) / 2;
		locY = (height - side) / 2;
		dX = Math.random() * 10 + 3;
		dY = Math.random() * 10 + 3;
		ball.setLocation((int) (locX), (int) (locY));
	}

	public void act() {
		paddle.x = paddleX;
		if (won || lost || !start)
			return;

		int cX1, cX2, cX3;
		int cY1, cY2, cY3;

		cX1 = (int) (locX + (dX < 0 ? 0 : side));
		cY1 = (int) (locY + side);
		cX2 = (int) (locX + (dX < 0 ? 0 : side));
		cY2 = (int) (locY + 0);
		cY3 = (int) (locY + (dY > 0 ? 0 : side));
		cX3 = (int) (locX + (dX > 0 ? 0 : side));
		// Check Collisions of bricks at new destination
		for (int index = 0; index < bricks.size(); index++)
			if (bricks.get(index).intersectsLine(cX1, cY1, cX1 + dX, cY1 + dY)
					|| bricks.get(index).intersectsLine(cX2, cY2, cX2 + dX,
							cY2 + dY)
					|| bricks.get(index).intersectsLine(cX3, cY3, cX3 + dX,
							cY3 + dY))
				collide(index);

		if (paddle.intersectsLine(cX1, cY1, cX1 + dX, cY1 + dY)
				|| paddle.intersectsLine(cX2, cY2, cX2 + dX, cY2 + dY)
				|| paddle.intersectsLine(cX3, cY3, cX3 + dX, cY3 + dY)) {
			double offsetX = (ball.x + ball.width / 2)
					- (paddle.x + paddle.width / 2);
			offsetX /= paddle.width;
			dX = Math.sqrt(dX * dX + dY * dY) * offsetX;
			dY = -Math.abs(dY);
			System.out.println(offsetX);
			Execution.audio.playPaddleHit();
		}

		// Check Wall Collisions
		if (locX + side + dX > width || 0 > locX + dX) {
			dX *= (dX > maxSpeed || dX < -maxSpeed) ? -1 : -1.05;
			Execution.audio.playWallHit();
		}
		if (0 > locY + dY) {
			dY *= (dY > maxSpeed || dY < -maxSpeed) ? -1 : -1.05;
			Execution.audio.playWallHit();
		}

		if (locY + side + dY > height + 30 && !lost) {
			Execution.audio.playLose();
			lost = true;
		}

		locX += dX;
		locY += dY;
		ball.setLocation((int) (locX), (int) (locY));
	}

	public void collide(int brickIndex) {
		Rectangle r = ball.intersection(bricks.get(brickIndex));
		if (r.width > r.height) {
			dY *= (dY > maxSpeed || dY < -maxSpeed) ? -1 : -1.05;
		} else {
			dX *= (dX > maxSpeed || dX < -maxSpeed) ? -1 : -1.05;
		}
		int type = btypes.get(brickIndex);
		if (type == 0) {
			bricks.remove(brickIndex);
			btypes.remove(brickIndex);
			Execution.audio.playBrickBreak();
			if (btypes.size() == 0) {
				Execution.audio.playWin();
				won = true;
			}

		} else {
			btypes.set(brickIndex, type - 1);
			Execution.audio.playBrickHit();
		}
	}

	public void populateBricks() {
		bricks.clear();
		int alt = 1;
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 6; i++) {
				bricks.add(new Rectangle(i * 100, j * 30, 100, 30));
				alt = (alt == 1) ? 2 : 1;
				btypes.add(alt);
			}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		paddleX = e.getX() - paddleWidth / 2;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.print("Started");
		if (won || lost) {
			init();
			start=false;
			won=false;
			lost=false;
		} else {
			start=true;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
