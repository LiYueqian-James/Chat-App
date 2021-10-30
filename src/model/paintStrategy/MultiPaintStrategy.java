package model.paintStrategy;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import model.Ball;

/**
 * @author James Li Manan Bajaj
 * Multiple paint strategies composed together.
 *
 */
public class MultiPaintStrategy extends APaintStrategy {

	/**
	 * Different strategies to be painted
	 */
	private APaintStrategy[] paintStrategies;

	/**
	 * @param at afffine transform.
	 * @param paintStrategies the paint strategies.
	 */
	public MultiPaintStrategy(AffineTransform at, APaintStrategy... paintStrategies) {
		super(at);
		this.paintStrategies = paintStrategies;
	}

	@Override
	public void init(Ball host) {
		for (APaintStrategy paintStrategy : paintStrategies) {
			paintStrategy.init(host);
		}

	}

	@Override
	public void paintXfrm(Graphics g, Ball host, AffineTransform at) {
		for (APaintStrategy paintStrategy : paintStrategies) {
			paintStrategy.paintXfrm(g, host, this.at);
		}
	}

	protected void paintCfg(Graphics g, Ball host) {
		super.paintCfg(g, host);
		if (Math.abs(Math.atan2(host.getVelocity().y, host.getVelocity().x)) > Math.PI / 2.0) {
			at.scale(1.0, -1.0);
		}
	}
}
