package model;

/**
 * Factory that will take a given strategy, and create an instance of it.
 *
 * @author James Li, Alena Holbert
 */
public interface IStrategyFac {

	/**
	 * @return an IUpdateStrategy instance
	 */
	public IUpdateStrategy make();

	/**
	 * Factory that makes a null IUpdateStrategy
	 */
	public static final IStrategyFac NULL = new IStrategyFac() {
		@Override
		public IUpdateStrategy make() {
			return IUpdateStrategy.NULL;
		}
	};

	/**
	 * Factory that makes an error IUpdateStrategy
	 */
	public static final IStrategyFac ERROR = new IStrategyFac() {
		@Override
		public IUpdateStrategy make() {
			return IUpdateStrategy.ERROR;
		}

		public String toString() {
			return "Error Strategy";
		}
	};
}
