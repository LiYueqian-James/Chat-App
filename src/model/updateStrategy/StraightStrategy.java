package model.updateStrategy;

import java.awt.Graphics;

import model.Ball;
import model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;
import model.IBallCmd;

/**
 * Strategy that makes ball travel in a straight path with no adjustments
 * to its size, color, or velocity.
 * 
 * @author James Li and Alena Holbert
 */
public class StraightStrategy implements IUpdateStrategy {

	@Override
	/**
	 * Do nothing -- all balls are, at base, a straight ball.
	 */
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
	}

}
