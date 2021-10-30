package model.paintStrategy;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import model.Ball;
import model.IModel2ViewAdapter;

/**
 * @author James Li Manan Bajaj
 * IPaintStrategy abstract class for strategies that will use affine transforms
 */
public abstract class APaintStrategy implements IPaintStrategy {

	/**
	 * The affine transform
	 */
	protected AffineTransform at;

	/**
	 * The adpatper
	 */
	protected IModel2ViewAdapter adptr;

	/**
	 * @param at The affine transform
	 */
	public APaintStrategy(AffineTransform at) {
		this.at = at;
	}

	@Override
	public void paint(Graphics g, Ball host) {
		double scale = host.getRadius();
		at.setToTranslation(host.getLocation().x, host.getLocation().y);
		at.scale(scale * 2, scale * 2);
		at.rotate(host.getVelocity().x, host.getVelocity().y);
		g.setColor(host.getColor());
		//paintCFG()
		paintCfg(g, host);
		paintXfrm(g, host, at);
	}

	/**
	 *  If additional processing, e.g. staying upright, is required before the actual painting takes place
	 * @param g the graphics to paint on
	 * @param host the ball being painted over
	 */
	protected void paintCfg(Graphics g, Ball host) {
		// Subclasses may or may not override this method
	}

	/**
	 * Where the actual painting takes place
	 * @param g the graphics object to paint on
	 * @param host the ball being painted over
	 * @param at the affine transform to apply to what is being painted
	 */
	public abstract void paintXfrm(Graphics g, Ball host, AffineTransform at);

}
