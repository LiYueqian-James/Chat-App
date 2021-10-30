package model.updateStrategy;

import java.awt.Color;

import model.Ball;
import model.IBallCmd;
import model.IInteractBehavior;
import model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;

/**
 * A strategy that gets the average RGB value of two balls upon contact, thus 'mixing' their colors.
 */
public class ColorMixBehaviorStrategy implements IUpdateStrategy {

	@Override
	public void init(Ball context) {
		context.setInteractStrategy(new MultiInteractStrategy(context.getInteractStrategy(), new IInteractBehavior() {
			@Override
			public IBallCmd interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
				Color targetColor = target.getColor();
				return new IBallCmd() {

					@Override
					public void apply(Ball context, IDispatcher<IBallCmd> disp) {
						Color contextColor = context.getColor();
						Color newColor = new Color((int) (targetColor.getRed() + contextColor.getRed()) / 2,
								(int) (targetColor.getGreen() + contextColor.getGreen()) / 2,
								(int) (targetColor.getBlue() + contextColor.getBlue()) / 2, 255);

						target.setColor(newColor);
						context.setColor(newColor);
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
