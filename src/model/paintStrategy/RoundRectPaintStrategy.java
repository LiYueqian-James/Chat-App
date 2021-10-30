/**
 * 
 */
package model.paintStrategy;

import java.awt.geom.AffineTransform;

import model.ShapeFactories.RoundRectShapeFactory;

/**
 * @author James Li
 * Round Rectangle
 *
 */
public class RoundRectPaintStrategy extends ShapePaintStrategy {
	/**
	 * No parameter constructor that creates a prototype rectangle that 
	 * has twice the width as height but an average radius of 1.
	 * An AffineTranform for internal use is instantiated.
	 */
	public RoundRectPaintStrategy() {
		this(new AffineTransform(), 0, 0, 4.0 / 3.0, 2.0 / 3.0);
	}

	/**
	 * Constructor that allows the specification of the location, x-radius and y-radius
	 * of the prototype rectangle.   The AffineTransform to use is given.
	 * @param at The AffineTransform to use for internal calculations
	 * @param x floating point x-coordinate of center of circle
	 * @param y floating point y-coordinate of center of circle
	 * @param width floating point width of the rectangle (rectangle)
	 * @param height floating point height of the rectangle (rectangle)
	 */
	public RoundRectPaintStrategy(AffineTransform at, double x, double y, double width, double height) {
		super(at, RoundRectShapeFactory.Singleton.makeShape(x, y, width, height));
	}
}
