package model;

import java.awt.Graphics;

import provided.logger.ILogger;
import provided.logger.ILoggerControl;
import provided.logger.LogLevel;
import provided.utils.dispatcher.IDispatcher;
import model.IBallCmd;

/**
 * Interface for all ball strategies that can be applied to balls in ballworld.
 * 
 * @author James Li, Alena Holbert
 */
public interface IUpdateStrategy {

	/**
	 * Logger to print messages to console.
	 */
	final ILogger logger = ILoggerControl.makeLogger();

	/**
	 * Error strategy
	 */
	IUpdateStrategy ERROR = new IUpdateStrategy() {
		private boolean logged = false;

		@Override
		public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
			if (!logged) {
				logger.log(LogLevel.INFO, "ERROR: this is not a valid strategy");
				logged = true;
			}
		}
	};

	/**
	 * Initialize the paint strategy, if needed.
	 * @param host the ball to get info
	 */
	public default void init(Ball host) {

	}

	/**
	 * Null strategy
	 */
	IUpdateStrategy NULL = new IUpdateStrategy() {
		public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		}
	};

	/**
	 * Update the state/display of the ball on the GUI.
	 * 
	 * @param context 	ball being updated.
	 * @param disp		dispatcher that is updating the balls.
	 */
	void updateState(Ball context, IDispatcher<IBallCmd> disp);

}
