/**
 * 
 */
package model.updateStrategy;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;

/**
 * @author James Li
 *
 */
public class TogetherStrategy implements IUpdateStrategy {
	/**
	 * Criteria is if the ball has overlapped
	 */
	private IUpdateStrategy criteria = new OverlapCriteriaStrategy();

	/**
	 * Behavior is the ball move up together
	 */
	private IUpdateStrategy behavior = new TogetherInteractStrategy();

	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		criteria.updateState(context, disp);

	}

	@Override
	public void init(Ball host) {
		behavior.init(host);
	}

}
