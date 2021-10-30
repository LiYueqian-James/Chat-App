/**
 * 
 */
package view;

import java.awt.Graphics;

/**
 * Adapter for view to model for Ballworld painting
 * 
 * @author Shreya Mohanselvan sm126, Alena Holbert ah72
 */
public interface IView2ModelPaintAdapter {

	/**
	 * Paint balls/update ballworld
	 * 
	 * @param g the Graphics object to paint on
	 */
	void paintBalls(Graphics g);

	/**
	 * Null adapter
	 */
	public static final IView2ModelPaintAdapter NULL = new IView2ModelPaintAdapter() {
		@Override
		public void paintBalls(Graphics g) {
		}
	};

}
