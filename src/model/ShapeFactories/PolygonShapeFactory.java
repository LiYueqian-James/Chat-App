/**
 * 
 */
package model.ShapeFactories;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.Point;
import java.awt.Polygon;

/**
 * @author James Li
 * generate a prototype triangle
 */
public class PolygonShapeFactory implements IShapeFactory {

	/**
	 * The local at
	 */
	AffineTransform at = new AffineTransform();
	/** 
	 * the scale factor
	 */
	double scaleFactor;
	/**
	 * the local polygon
	 */
	Polygon poly;

	/**
	 * Static factory
	 */

	public static PolygonShapeFactory Singleton = new PolygonShapeFactory(new AffineTransform(), 1);

	/**
	 * The constructor of the factory
	 * @param at the affine transform
	 * @param scaleFactor the scale factor
	 * @param pts the points
	 */
	public PolygonShapeFactory(AffineTransform at, double scaleFactor, Point... pts) {
		this.at = at;
		this.scaleFactor = scaleFactor;
		this.poly = new Polygon();
		for (Point pt : pts) {
			this.poly.addPoint(pt.x, pt.y);
		}
	}

	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		this.at.setToTranslation(x, y);
		this.at.scale(xScale * this.scaleFactor, yScale * this.scaleFactor);
		return at.createTransformedShape(poly);
	}
}
