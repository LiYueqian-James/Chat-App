package model.paintStrategy;

import java.awt.geom.AffineTransform;

/**
 * @author James Li
 * paint a WaddleDee 
 *
 */
public class WaddleDeePaintStrategy extends ImagePaintStrategy {

	/**
	 * @param at the affine transform obj
	 */
	public WaddleDeePaintStrategy(AffineTransform at) {
		super(at);
		this.loadImg("images/WaddleDee.jpg");
		this.fillFactor = 1.2d;
	}

	/**
	 * Default constructor.
	 */
	public WaddleDeePaintStrategy() {
		this(new AffineTransform());
	}

}
