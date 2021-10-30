package model.updateStrategy;

import java.awt.Graphics;
import java.awt.Point;

import model.Ball;
import model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;
import provided.utils.valueGenerator.impl.Randomizer;
import model.IBallCmd;

/**
 * Strategy that gives a ball a 10% chance of changing its velocity and color
 * to a new random value/color.
 * 
 * @author James Li and Alena Holbert
 */
public class SpontaneousStrategy implements IUpdateStrategy {
	/**
	 * A random number/color generator
	 */
	protected Randomizer randomizer = Randomizer.Singleton;

	/**
	 * Possibility of changing.
	 */
	private int alpha = 10;

	@Override
	/**
	 * Randomly update the velocity and color of the context ball.
	 * 
	 * @param context	Ball that the context is applied to
	 * @param disp 		Dispatcher that updates the GUI
	 */
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		if (this.randomizer.randomInt(0, this.alpha) == 1) {
			context.setVelocity(new Point(this.randomizer.randomInt(-8, 8), this.randomizer.randomInt(-6, 6)));
			context.setColor(this.randomizer.randomColor());
		}

	}

}
