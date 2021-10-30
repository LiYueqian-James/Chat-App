package model.paintStrategy;

import java.awt.geom.AffineTransform;

/**
 * @author James Li Manan Bajaj
 * Paint the Mushroom.
 *
 */
public class MushroomPaintStrategy extends MultiPaintStrategy {

	/**
	 * @param at affine transform
	 * @param paintStrategies the composee strategies
	 */
	public MushroomPaintStrategy(AffineTransform at, APaintStrategy... paintStrategies) {
		super(at, paintStrategies);
	}

	/**
	 * Default constructor
	 */
	public MushroomPaintStrategy() {
		super(new AffineTransform(), new EllipsePaintStrategy(),
				new RectanglePaintStrategy(new AffineTransform(), 0, 1.0 / 3.0, 3.0 / 4.0, 2.0 / 3.0));

	}

}
