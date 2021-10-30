/**
 * 
 */
package model.ShapeFactories;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * @author James Li
 * Generate a prototype rectangle.
 *
 */
public class RectangleShapeFactory implements IShapeFactory {
	/**
	 * Static factory
	 */
	public static RectangleShapeFactory Singleton = new RectangleShapeFactory();

	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		return new Rectangle2D.Double(x - xScale / 2.0, y - yScale / 2.0, xScale, yScale);
	}

}
