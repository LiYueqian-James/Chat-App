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
public abstract class AInteractStrategy implements IUpdateStrategy {
	/**
	 * The interaction criteria 
	 */
	protected IUpdateStrategy criteria;

	/**
	 * The interaction behavior
	 */
	protected IUpdateStrategy behavior;

	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		this.getCriteria().updateState(context, disp);

	}

	@Override
	public void init(Ball host) {
		this.getBehavior().init(host);
	}

	/**
	 * @return the criteria strategy
	 */
	protected IUpdateStrategy getCriteria() {
		return this.criteria;
	}

	/**
	 * @return the behavior strategy
	 */
	protected IUpdateStrategy getBehavior() {
		return this.behavior;
	}

}
