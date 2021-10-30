/**
 * 
 */
package model.updateStrategy;

/**
 * @author James Li
 *
 */
public class KillStrategy extends AInteractStrategy {

	/**
	 * Initialize the behavior and the criteria
	 */
	public KillStrategy() {
		this.criteria = new OverlapCriteriaStrategy();
		this.behavior = new KillBehaviorStrategy();
	}

}
