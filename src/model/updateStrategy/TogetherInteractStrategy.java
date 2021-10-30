/**
 * 
 */
package model.updateStrategy;

import java.awt.Point;
import java.awt.geom.Point2D;

import model.Ball;
import model.IBallCmd;
import model.IInteractBehavior;
import model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;

/**
 * @author James Li
 *
 */
public class TogetherInteractStrategy implements IUpdateStrategy {
	@Override
	public void init(Ball context) {
		context.setInteractStrategy(new MultiInteractStrategy(context.getInteractStrategy(), new IInteractBehavior() {
			@Override
			public IBallCmd interact(Ball context, Ball target, IDispatcher<IBallCmd> disp) {
				//				double cmass = context.getRadius()*context.getRadius()*Math.PI;
				//				double tmass = target.getRadius()*target.getRadius()*Math.PI;
				//				double rmass = reducedMass(cmass, tmass);	
				//				
				double dist = context.getLocation().distance(target.getLocation());
				Point2D.Double uVect = calcUnitVec(context.getLocation(), target.getLocation(), dist);
				//				Point2D.Double cimpulse = impulse(uVect, context.getVelocity(), target.getVelocity(), rmass);
				Point nudge = calcNudgeVec(uVect, context.getRadius() + target.getRadius(), dist);
				return new IBallCmd() {

					@Override
					public void apply(Ball context, IDispatcher<IBallCmd> disp) {
						//						updateVelocity(target, rmass, new Point2D.Double(-cimpulse.getX(), -cimpulse.getY()));
						context.setLocation(new Point((int) (context.getLocation().getX() + nudge.getX()),
								(int) (context.getLocation().getY() + nudge.getY())));
						updateVelocity(context);
					}

				};

			}

		}));
	}

	/**
	 * Calculate the unit vector (normalized vector) from the location of the source ball to the location of the target ball.
	 * @param lSource Location of the source ball
	 * @param lTarget Location of the target ball
	 * @param distance Distance from the source ball to the target ball
	 * @return A double-precision vector (point)
	 */
	Point2D.Double calcUnitVec(Point lSource, Point lTarget, double distance) {

		// Calculate the normalized vector, from source to target
		double nx = ((double) (lTarget.x - lSource.x)) / distance;
		double ny = ((double) (lTarget.y - lSource.y)) / distance;

		return new Point2D.Double(nx, ny);
	}

	/**
	 * The multiplicative factor to increase the separation distance to insure that the two balls
	 * are beyond collision distance
	 */
	private static final double NudgeFactor = 1;

	/**
	 * Calculate the vector to add to the source ball's location to "nudge" it out of the way of the target ball.
	 * @param normalVec  The unit vector (normalized vector) from the location of the source ball to the location of the target ball.
	 * @param minSeparation The minimum allowed non-colliding separation between the centers of the balls = maximum allowed colliding separation.
	 * @param distance The actual distance between the centers of the balls.
	 * @return A Point object which is the amount to "nudge" the source ball away from the target ball.
	 */
	Point calcNudgeVec(Point2D.Double normalVec, double minSeparation, double distance) {
		// The minimum allowed separation(sum of the ball radii) minus the actual separation(distance between ball centers). Should be a
		// positive value.  This is the amount of overlap of the balls as measured along the line between their centers.
		double deltaR = minSeparation - distance;

		// Calc the amount to move the source ball beyond collision range of the target ball, along
		// the normal direction.
		return new Point((int) Math.ceil(-normalVec.getX() * deltaR * NudgeFactor),
				(int) Math.ceil(-normalVec.getY() * deltaR * NudgeFactor));

	}

	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		// TODO Auto-generated method stub

	}

	/**
	 * Updates the velocity of a ball, given an impulse vector. The change in velocity is the 
	 * impulse divided by the ball's mass. 
	 * 
	 * @param aBall
	 *            The ball whose velocity needs to be modified by the impulse
	 * @param mass 
	 *            The "mass" of the ball
	 * @param impulseVec 
	 *            The impulse vector for the ball
	 */
	protected void updateVelocity(Ball aBall) {
		aBall.getVelocity().setLocation(0, 5);
		;
	}

}
