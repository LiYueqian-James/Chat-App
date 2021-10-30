package model.updateStrategy;

import java.awt.Graphics;

import model.Ball;
import model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;
import model.IBallCmd;

/**
 * Combining strategies.
 * 
 * @author James Li
 */
public class MultiStrategy implements IUpdateStrategy {

	/**
	 * The first strategy that is combined to form this multi-strategy.
	 */
	private IUpdateStrategy _s1;
	/**
	 * The second strategy that is combined to form this multi-strategy.
	 */
	private IUpdateStrategy _s2;

	/**
	 * Constructor for a combination of strategies
	 * 
	 * @param s1 the first strategy
	 * @param s2 the second strategy
	 */
	public MultiStrategy(IUpdateStrategy s1, IUpdateStrategy s2) {
		this._s1 = s1;
		this._s2 = s2;
	}

	@Override
	public void init(Ball host) {
		_s1.init(host);
		_s2.init(host);
	}

	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		this._s1.updateState(context, disp);
		this._s2.updateState(context, disp);
	}

}
