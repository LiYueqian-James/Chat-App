package model.paintStrategy;

/**
 * @author James Li
 * The factory that generates paint strategies.
 * 
 */
public interface IPaintStrategyFac {

	/**
	 * @return the paint strategy.
	 */
	public IPaintStrategy make();

	/**
	 * Null factory
	 */
	public static final IPaintStrategyFac NULL = new IPaintStrategyFac() {
		@Override
		public IPaintStrategy make() {
			return IPaintStrategy.NULL;
		}
	};

}
