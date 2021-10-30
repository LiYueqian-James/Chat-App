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
public class KillBehaviorStrategy implements IUpdateStrategy {
	@Override
	public void init(Ball context) {
		context.setInteractStrategy(new MultiInteractStrategy(context.getInteractStrategy(), new IInteractBehavior() {
			@Override
			public IBallCmd interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
				disp.removeObserver(target);
				return IInteractBehavior.NULLCMD;
			}

		}));
	}

	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {

	}

}
