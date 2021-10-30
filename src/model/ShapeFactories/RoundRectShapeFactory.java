/**
 * 
 */
package model.ShapeFactories;

import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Double;

/**
 * @author James Li
 * Generate a round rectangle prototype
 */
public class RoundRectShapeFactory implements IShapeFactory {

	/**
	 * Static generator
	 */
	public static RoundRectShapeFactory Singleton = new RoundRectShapeFactory();

	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		return new RoundRectangle2D.Double(x - xScale / 2.0d, y - yScale / 2.0d, xScale, yScale, 0.5d * xScale,
				0.5d * yScale);
	}

}
