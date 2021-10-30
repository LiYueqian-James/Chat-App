/**
 * 
 */
package model.paintStrategy;

import java.awt.geom.AffineTransform;

/**
 * @author James Li
 * paint a saturn.
 *
 */
public class SaturnPaintStrategy extends ImagePaintStrategy {
	/**
	 * @param at the affine transform obj
	 */
	public SaturnPaintStrategy(AffineTransform at) {
		super(at);
		this.loadImg("images/Saturn.png");
		this.fillFactor = 1.0d;
	}

	/**
	 * Default constructor.
	 */
	public SaturnPaintStrategy() {
		this(new AffineTransform());
	}
}
