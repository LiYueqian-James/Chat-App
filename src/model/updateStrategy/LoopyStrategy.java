package model.updateStrategy;

import java.awt.Graphics;

import model.Ball;
import model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;
import model.IBallCmd;

/**
 * Strategy that makes the ball make loops as it continues on general path.
 * 
 * @author James Li and Alena Holbert
 */
public class LoopyStrategy implements IUpdateStrategy {

	/**
	 * Degree of angle that the ball is at in the loop.
	 */
	private int theta = 0;

	/**
	 * Updates the ball's location to a new position on the relative circle
	 * that its traveling in.
	 * 
	 * @param context	Ball that the context is applied to
	 * @param disp 		Dispatcher that updates the GUI
	 */
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		double originalRelX = 30 * Math.cos(theta * 0.0174533);
		double originalRelY = 30 * Math.sin(theta * 0.0174533);

		theta += 45;
		theta %= 360;

		double newRelX = 30 * Math.cos(theta * 0.0174533);
		double newRelY = 30 * Math.sin(theta * 0.0174533);

		double xShift = newRelX - originalRelX;
		double yShift = newRelY - originalRelY;

		context.getLocation().translate((int) xShift, (int) yShift);
	}
}
