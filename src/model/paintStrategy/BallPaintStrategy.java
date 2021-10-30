/**
 * 
 */
package model.paintStrategy;

import java.awt.Shape;
import java.awt.geom.AffineTransform;

import model.ShapeFactories.EllipseShapeFactory;

/**
 * @author James Li
 *
 */
public class BallPaintStrategy extends EllipsePaintStrategy {

	/**
	 * No parameter constructor that creates a prototype circle with radius of 1.
	 * An AffineTranform for internal use is instantiated.
	 */
	public BallPaintStrategy() {
		this(new AffineTransform(), 0.0d, 0.0d, 1.0d, 1.0d);
	}

	/**
	 * Constructor that allows the specification of the locationradius
	 * of the prototype circle.   The AffineTransform to use is given.
	 * @param at The AffineTransform to use for internal calculations
	 * @param x floating point x-coordinate of center of circle
	 * @param y floating point y-coordinate of center of circle
	 * @param width floating point x-radius of the circle (ellipse)
	 * @param height floating point y-radius of the circle (ellipse)
	 */
	public BallPaintStrategy(AffineTransform at, double x, double y, double width, double height) {
		super(at, x, y, width, height);
	}

}
