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
public class CollideStrategy extends AInteractStrategy {

	/**
	 * Initialize the criteria and the behavior
	 */
	public CollideStrategy() {
		this.criteria = new OverlapCriteriaStrategy();
		this.behavior = new CollideBehaviorStrategy();
	}

}
