/**
 * 
 */
package model.updateStrategy;

/**
 * @author James Li
 *
 */
public class ColorExchangeStrategy extends AInteractStrategy {

	/**
	 * Initialize the behavior and the criteria
	 */
	public ColorExchangeStrategy() {
		this.criteria = new OverlapCriteriaStrategy();
		this.behavior = new MultiStrategy(new CollideBehaviorStrategy(), new ColorExchangeBehaviorStrategy());
	}

}
