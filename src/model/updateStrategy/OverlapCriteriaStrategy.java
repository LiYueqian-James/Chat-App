/**
 * 
 */
package model.updateStrategy;

import java.awt.Point;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;

/**
 * @author James Li
 *
 */
public class OverlapCriteriaStrategy implements IUpdateStrategy {

	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		disp.updateAll(new IBallCmd() {

			@Override
			public void apply(Ball other, IDispatcher<IBallCmd> disp) {

				if (context != other) {
					if ((context.getRadius() + other.getRadius()) > context.getLocation()
							.distance(other.getLocation())) {
						IBallCmd cPost = context.interactWith(other, disp);
						IBallCmd oPost = other.interactWith(context, disp);

						context.update(disp, cPost);

						other.update(disp, oPost);
					}
				}
			}

		});

	}

}
