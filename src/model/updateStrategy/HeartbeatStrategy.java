package model.updateStrategy;

import java.awt.Graphics;

import model.Ball;
import model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;
import model.IBallCmd;

/**
 * Strategy that a ball will change its size to mimic a heartbeat.
 * 
 * @author James Li and Alena Holbert
 */
public class HeartbeatStrategy implements IUpdateStrategy {
	/**
	 * Counter to keep track of where in the heartbeat cycle the ball is.
	 */
	private int counter = 0;
	/**
	 * Ball that is adopting this strategy. If there are multiple balls adopting
	 * the same instance of a Heartbeat strategy (i.e. in the case of switchers),
	 * let this be a single Ball representative of all balls employing that same instance.
	 */
	Ball representativeBall;
	/**
	 * Boolean to track whether a representative ball has been set.
	 */
	boolean initialized = false;

	/**
	 * Updates ball's radius depending on where it is in the heartbeat cycle.
	 * 
	 * @param context	Ball that the context is applied to
	 * @param disp 		Dispatcher that updates the GUI
	 */
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		if (!initialized) {
			representativeBall = context;
			initialized = true;
		}
		if (counter == 0 || counter == 5) {
			context.setRadius((int) (context.getRadius() * 1.5)); // grow
		} else if (counter == 2 || counter == 7) {
			context.setRadius((int) (context.getRadius() / 1.5)); // shrink
		}
		if (this.representativeBall == context) {
			counter++;
			counter %= 20;
		}
	}
}