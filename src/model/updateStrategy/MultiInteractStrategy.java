/**
 * 
 */
package model.updateStrategy;

import model.Ball;
import model.IBallCmd;
import model.IInteractBehavior;
import model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;

/**
 * @author James Li
 *
 */
public class MultiInteractStrategy implements IInteractBehavior {
	/**
	 * The first strategy that is combined to form this multi-strategy.
	 */
	private IInteractBehavior _s1;
	/**
	 * The second strategy that is combined to form this multi-strategy.
	 */
	private IInteractBehavior _s2;

	/**
	 * Constructor for a combination of strategies
	 * 
	 * @param s1 the first strategy
	 * @param s2 the second strategy
	 */
	public MultiInteractStrategy(IInteractBehavior s1, IInteractBehavior s2) {
		this._s1 = s1;
		this._s2 = s2;
	}

	@Override
	public IBallCmd interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
		IBallCmd post1 = this._s1.interact(context, target, disp);
		IBallCmd post2 = this._s2.interact(context, target, disp);
		return new IBallCmd() {

			@Override
			public void apply(Ball context, IDispatcher<IBallCmd> disp) {
				context.update(disp, post1);
				context.update(disp, post2);
			}

		};
	}

}
