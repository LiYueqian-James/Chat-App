package model.ShapeFactories;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * @author James Li
 * generate a prototype ellipse.
 * 
 */
public class EllipseShapeFactory implements IShapeFactory {

	/**
	 * Static factory
	 */
	public static EllipseShapeFactory Singleton = new EllipseShapeFactory();

	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		return new Ellipse2D.Double(x - xScale / 2.0, y - yScale / 2.0, xScale, yScale);
	}

}
