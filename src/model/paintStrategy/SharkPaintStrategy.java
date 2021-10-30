/**
 * 
 */
package model.paintStrategy;

import java.awt.geom.AffineTransform;

/**
 * @author James Li
 * paint a sharks
 *
 */
public class SharkPaintStrategy extends ImagePaintStrategy {

	/**
	 * @param at the affine transform obj
	 */
	public SharkPaintStrategy(AffineTransform at) {
		super(at);
		this.loadImg("images/BlackTipReef.gif");
		this.fillFactor = 1.0d;
	}

	/**
	 * Default constructor.
	 */
	public SharkPaintStrategy() {
		this(new AffineTransform());
	}

}
