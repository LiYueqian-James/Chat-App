/**
 * 
 */
package model;

import provided.utils.dispatcher.IDispatcher;

/**
 * @author James Li
 *
 */
public interface IInteractBehavior {
	/**
	 * Null strategy
	 */
	IInteractBehavior NULL = new IInteractBehavior() {

		@Override
		public IBallCmd interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
			// TODO Auto-generated method stub
			return NULLCMD;
		}

	};

	/**
	 * null post cmd
	 */
	IBallCmd NULLCMD = new IBallCmd() {

		@Override
		public void apply(Ball context, IDispatcher<IBallCmd> disp) {
			// TODO Auto-generated method stub

		}

	};

	/**
	 * @param context the current ball
	 * @param target the target ball
	 * @param disp the dispatcher
	 * @return return the post interaction cmd
	 */
	IBallCmd interact(Ball context, Ball target, IDispatcher<IBallCmd> disp);
}
