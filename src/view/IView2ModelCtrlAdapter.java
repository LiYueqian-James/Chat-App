package view;

/**
 * Adapter for view to model for Ballworld controls (clearing or loading balls).
 * 
 * @author James Li yl176, Alena Holbert ah72
 */
@SuppressWarnings("javadoc")
public interface IView2ModelCtrlAdapter<TItem, TItem2> {
	/**
	 * Clear all balls from the screen.
	 */
	void clearBalls();

	/**
	 * Load a strategy factory indicated by the given classname
	 * @param strategyName the name of the strategy
	 * @param <TItem> the type of the strategy factory
	 * @return a factory that generates the desired strategy
	 */
	public TItem loadStrategyFac(String strategyName);

	public TItem2 loadPaintStrategyFac(String paintName);

	/**
	 * Load a new strategy factory that combines the two strategies provided
	 * 
	 * @param <TItem> the type of the strategy factory
	 * @param s1 the first strategy factory
	 * @param s2 the second strategy factory
	 * @return the new combines strategy factory
	 */
	public TItem combineStrategyFac(TItem s1, TItem s2);

	/**
	 * Load the ball with the desired strategy according to the strategy factory
	 * @param strategyFac the factory that generates strategy to be used by the ball
	 */
	public void loadBall(TItem strategyFac, TItem2 paintFac);

	/**
	 * Make a new switcher ball.
	 */
	public void makeSwitcher(TItem2 paintFac);

	/**
	 * Updates the switcher strategy.
	 * 
	 * @param newStrategy strategy the switcher balls are updating to.
	 */
	public void updateSwitcherStrategy(TItem newStrategy);

}
