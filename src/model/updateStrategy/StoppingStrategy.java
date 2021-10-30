package model.updateStrategy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import model.Ball;
import model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;
import provided.utils.valueGenerator.impl.Randomizer;
import model.IBallCmd;

/**
 * Strategy that gives the ball a 2% chance of turning red and freezing for 1 second, and then
 * returing back to original color and velocity.
 * 
 * @author James Li and Alena Holbert
 */
public class StoppingStrategy implements IUpdateStrategy {

	/**
	 * A random number/color generator
	 */
	protected Randomizer randomizer = Randomizer.Singleton;

	@Override
	/**
	 * Randomly stop the ball for 1 second, and turn it red to indicate that it's stopped.
	 * 
	 * @param context	Ball that the context is applied to
	 * @param disp 		Dispatcher that updates the GUI
	 */
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		if (this.randomizer.randomInt(0, 50) == 1) { // 2% chance
			Point originalVelocity = context.getVelocity();
			if (originalVelocity.getX() == 0 && originalVelocity.getY() == 0) {
				return;
			}
			Color originalColor = context.getColor();

			context.setVelocity(new Point(0, 0));
			context.setColor(Color.RED);

			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					context.setColor(originalColor);
					context.setVelocity(originalVelocity);
				}
			}, 1000);
		}
	}
}
