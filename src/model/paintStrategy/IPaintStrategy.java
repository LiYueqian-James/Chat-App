package model.paintStrategy;

import java.awt.Graphics;
import java.awt.Point;

import model.Ball;

/**
 * @author James Li Manan Bajaj
 * The top-level interface that the Ball uses. 
 * 
 */
public interface IPaintStrategy {

	/**
	 * Paints the ball on the graphics object.
	 * 
	 * @param g		the graphics object being painted on
	 * @param host the context to pull relevant information
	 */
	public void paint(Graphics g, Ball host);
	// should be left unimplemented I think

	/**
	 * Initialize the paint strategy, if needed.
	 * @param host the ball to get info
	 */
	public void init(Ball host);

	/**
	 * Null strategy
	 */
	IPaintStrategy NULL = new IPaintStrategy() {
		public void paint(Graphics g, Ball host) {

		}

		public void init(Ball host) {

		}
	};
}
