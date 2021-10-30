package model.paintStrategy;

import java.awt.Shape;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import model.Ball;

/**
 * @author James Li Manan Bajaj
 * paint a simple shape.
 *
 */
public class ShapePaintStrategy extends APaintStrategy {

	/**
	 * The prototype shape
	 */
	private Shape prototypeShape;

	/**
	 * Constructor
	 * @param prototypeShape the prototype
	 */
	public ShapePaintStrategy(Shape prototypeShape) {
		super(new AffineTransform());
		this.prototypeShape = prototypeShape;
	}

	/**
	 * Constructor with at
	 * @param at the affine transform
	 * @param prototypeShape the protype shape
	 */
	public ShapePaintStrategy(AffineTransform at, Shape prototypeShape) {
		super(at);
		this.prototypeShape = prototypeShape;
	}

	@Override
	public void init(Ball host) {

	}

	@Override
	public void paintXfrm(Graphics g, Ball host, AffineTransform at) {
		((Graphics2D) g).fill(at.createTransformedShape(prototypeShape));
	}

}
