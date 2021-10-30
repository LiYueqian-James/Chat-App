package model.updateStrategy;

import java.awt.Color;
import java.awt.Graphics;

import model.Ball;
import model.IUpdateStrategy;
import provided.utils.dispatcher.IDispatcher;
import model.IBallCmd;

/**
 * Strategy that makes a ball rotate through different color schemes, where each
 * color scheme consists of two colors representing one of Rice's residential colleges.
 * 
 * @author alenaholbert
 */
public class CollegeColorsStrategy implements IUpdateStrategy {
	/**
	 * Array of each college's two main colors.
	 */
	private static Color[][] colors = { { new Color(245, 212, 66), Color.BLACK }, // Wiess - Goldenrod + Black
			{ new Color(245, 212, 66), Color.BLUE }, // Hanszen - Gold + Blue
			{ Color.RED, Color.BLACK }, //Baker - Red + Black
			{ new Color(24, 35, 128), new Color(245, 212, 66) }, // Lovett - Gold + Dark Blue
			{ new Color(128, 24, 24), new Color(255, 177, 8) }, // Will Rice - Red/Orange + Gold
			{ new Color(245, 212, 66), new Color(255, 81, 0) }, // Sid Rich - Red/Orange + Gold
			{ new Color(76, 21, 112), Color.LIGHT_GRAY }, // McMurtry - Purple + Gray
			{ new Color(201, 161, 0), new Color(25, 82, 30) }, // Duncan - 
			{ new Color(23, 133, 32), new Color(114, 23, 194) }, //Jones
			{ new Color(171, 0, 65), new Color(31, 0, 171) }, // Martel
			{ new Color(245, 212, 66), new Color(110, 16, 51) } //Brown - Gold + Maroon
	};
	/**
	 * Integer corresponding to which college the ball is representing.
	 */
	private int collegeNum = 0;
	/**
	 * Integer corresponding to which of the two colors for any college the ball is.
	 */
	private int colorNum = 0;

	@Override
	/**
	 * Updates the color of the ball, changing to a different college's colors if the ball bounces
	 * on a wall.
	 * 
	 * @param context	Ball that the context is applied to
	 * @param disp 		Dispatcher that updates the GUI
	 */
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		int width = context.getCanvasDims().getWidth();
		int height = context.getCanvasDims().getHeight();
		int x = context.getLocation().x;
		int y = context.getLocation().y;

		int radius = context.getRadius();

		// If it hits a side
		if (x + radius > width || (x - radius) < 0 || ((y + radius) > height) || (y - radius) < 0) {
			collegeNum++;
			collegeNum %= 11;
			colorNum = 0;
		}

		context.setColor(colors[collegeNum][colorNum / 8]);

		colorNum++;
		colorNum %= 16;
	}
}
