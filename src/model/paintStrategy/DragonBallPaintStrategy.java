package model.paintStrategy;

import java.awt.geom.AffineTransform;

/**
 * @author James Li
 * paint a Dragon Ball
 *
 */
public class DragonBallPaintStrategy extends ImagePaintStrategy {

	/**
	 * @param at the affine transform obj
	 */
	public DragonBallPaintStrategy(AffineTransform at) {
		super(at);
		this.loadImg("images/DragonBall.jpg");
		this.fillFactor = 1.0d;
	}

	/**
	 * Default constructor.
	 */
	public DragonBallPaintStrategy() {
		this(new AffineTransform());
	}

}
