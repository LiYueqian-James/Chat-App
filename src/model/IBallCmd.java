/**
 * 
 */
package model;

import provided.utils.dispatcher.IDispatcher;

/**
 * @author James Li
 * The cmd interface.
 *
 */
public interface IBallCmd {
	/**
	 * The method run by the ball's update method which is called when the ball is updated by the dispatcher.
	 * @param context The ball that is calling this method.   The context under which the command is to be run.
	 * @param disp The Dispatcher that sent the command out.
	 */
	public abstract void apply(Ball context, IDispatcher<IBallCmd> disp);
}
