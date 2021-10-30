/**
 * 
 */
package model.updateStrategy;

import java.awt.Color;

import model.Ball;
import model.IBallCmd;
import model.IInteractBehavior;
import model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;

/**
 * @author James Li
 *
 */
public class ColorExchangeBehaviorStrategy implements IUpdateStrategy {
	@Override
	public void init(Ball context) {
		context.setInteractStrategy(new MultiInteractStrategy(context.getInteractStrategy(), new IInteractBehavior() {
			@Override
			public IBallCmd interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
				Color color = target.getColor();
				return new IBallCmd() {

					@Override
					public void apply(Ball context, IDispatcher<IBallCmd> disp) {
						context.setColor(color);
					}

				};
			}

		}));
	}

	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		// TODO Auto-generated method stub

	}

}
