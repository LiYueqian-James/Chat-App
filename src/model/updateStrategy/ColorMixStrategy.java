package model.updateStrategy;

/**
 * A strategy that mixes the color of two balls upon contact.
 */
public class ColorMixStrategy extends AInteractStrategy {

	/**
	 * Initialize the behavior and the criteria.
	 */
	public ColorMixStrategy() {
		this.criteria = new OverlapCriteriaStrategy();
		this.behavior = new MultiStrategy(new CollideBehaviorStrategy(), new ColorMixBehaviorStrategy());
	}

}
