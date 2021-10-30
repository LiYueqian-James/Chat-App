package model.paintStrategy;

import java.awt.Graphics;

import model.Ball;

/**
 * @author James Li
 * Paint a hollow rectangle.
 *
 */
public class Rect3DPaintStrategy implements IPaintStrategy {
	/**
	 * @param g the graphics to paint on
	 * @param host the ball to get info
	 */
	public void paint(Graphics g, Ball host) {
		int halfSide = host.getRadius();
		g.setColor(host.getColor());
		g.draw3DRect(host.getLocation().x - halfSide, host.getLocation().y - halfSide, 2 * halfSide, 2 * halfSide,
				true);
	}

	@Override
	public void init(Ball host) {

	}
}
