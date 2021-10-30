/**
 * 
 */
package model.paintStrategy;

import java.awt.geom.AffineTransform;

import model.ShapeFactories.TriangleShapeFactory;

/**
 * @author James Li
 * Triangle
 *
 */
public class TrianglePaintStrategy extends ShapePaintStrategy {

	/**
	 * No parameter constructor that creates a prototype triangle
	 * An AffineTranform for internal use is instantiated.
	 */
	public TrianglePaintStrategy() {
		this(new AffineTransform(), 0, 0, 2, 1);
	}

	/**
	 * Constructor that allows the specification of the location, x-width and y-height
	 * @param at the affinetransform
	 * @param x the x coord
	 * @param y the y coord
	 * @param width the width
	 * @param height the height
	 */
	public TrianglePaintStrategy(AffineTransform at, double x, double y, double width, double height) {
		super(at, TriangleShapeFactory.Singleton.makeShape(x, y, width, height));
	}
}
