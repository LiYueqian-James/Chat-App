package model;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Point;

import model.paintStrategy.IPaintStrategy;
import provided.utils.dispatcher.IDispatcher;
import provided.utils.dispatcher.IObserver;
import provided.utils.displayModel.IDimension;

/**
 * Concrete Ball that represents a basic ball that can adopt different
 * IUpdateStrategy's that will tell it how to move/look.
 * 
 * @author James Li and Manan Bajaj
 */
public class Ball implements IObserver<IBallCmd> {
	/**
	 * Location of ball on the screen
	 */
	private Point location;
	/**
	 * Radius of ball
	 */
	private int radius;
	/**
	 * Color of ball
	 */
	private Color color;
	/**
	 * Velocity that the ball will move at on the canvas
	 */
	private Point velocity;
	/**
	 * Dimensions of canvas that the ball is painted onto
	 */
	private IDimension canvasDims;

	/**
	 * Strategy the ball uses to update its state
	 */
	private IUpdateStrategy strategy;

	/**
	 * Strategy the ball uses to paint
	 */
	private IPaintStrategy paintStrategy;

	/**
	 * Strategy the ball uses to interact, set to Null initially since a ball may not have any interactions
	 */
	private IInteractBehavior interactionStrategy = IInteractBehavior.NULL;

	/**
	 * The model2view adapter used by the model -- can be gotten as needed for certain paint strategies
	 */
	private IModel2ViewAdapter model2ViewAdapter;

	/**
	 * Initializes a ball at a location (l) with radius (r) color (c)
	 * velocity(v) and dimensions(dim)
	 * @param loc		initial location of the ball
	 * @param rad		initial radius of the ball
	 * @param c			initial color of the ball
	 * @param vel		initial velocity of the ball
	 * @param dim		initial dimensions of the ball
	 * @param strategy  strategy for this ball
	 * @param paintStrategy the paint strategy for this ball
	 * @param model2ViewAdapter the model2ViewAdapter for this ball to pass to paint strategies
	 */
	public Ball(Point loc, int rad, Color c, Point vel, IDimension dim, IUpdateStrategy strategy,
			IPaintStrategy paintStrategy, IModel2ViewAdapter model2ViewAdapter) {
		this.location = loc;
		this.radius = rad;
		this.color = c;
		this.velocity = vel;
		this.canvasDims = dim;
		this.model2ViewAdapter = model2ViewAdapter;
		this.setPaintStrategy(paintStrategy);
		this.setStrategy(strategy);
	}

	/**
	 * Updates the ball including position and color.
	 * 
	 * @param disp		dispatcher that system uses to communicate to a call
	 * @param g			graphics that is being updated
	 */
	public void update(IDispatcher<IBallCmd> disp, IBallCmd cmd) {
		cmd.apply(this, disp);
	}

	/**
	 * Takes the velocity defined and moves the ball by the X and Y amounts. 
	 */
	public void move() {
		location.translate(velocity.x, velocity.y);
	}

	/**
	 * Bounces the ball if a ball hits the wall of a container.
	 */
	public void bounce() {
		// Getting dimensions
		int width = canvasDims.getWidth();
		int height = canvasDims.getHeight();
		int x = location.x;
		int y = location.y;

		// Bouncing x direction
		if ((x + radius) > width) {
			x = 2 * (width - radius) - x;
			velocity.x = -1 * velocity.x;
		}
		if ((x - radius) < 0) {
			x = 2 * radius - x;
			velocity.x = -1 * velocity.x;
		}
		// Bouncing y direction
		if ((y + radius) > height) {
			y = 2 * (height - radius) - y;
			velocity.y = -1 * velocity.y;
		}
		if ((y - radius) < 0) {
			y = 2 * radius - y;
			velocity.y = -1 * velocity.y;
		}

		// Set the new location
		location.setLocation(x, y);

	}

	/**
	 * Paints the ball on the graphics object.
	 * 
	 * @param g		the graphics object being painted on
	 */
	public void paint(Graphics g) {
		paintStrategy.paint(g, this);
	}

	/**
	 * Updates the state of the ball. (Variant)
	 * 
	 * @param disp dispatcher object that system uses to communicate
	 */
	public void updateState(IDispatcher<IBallCmd> disp) {
		strategy.updateState(this, disp); // update the ball's state using strategy
	}

	/** 
	 * Sets the new center of the ball.
	 * 
	 * @param location		new location of the center
	 */
	public void setLocation(Point location) {
		this.location = location;
	}

	/**
	 * Gets the current location of the ball.
	 * 
	 * @return 	a Point representing the center of the ball.
	 */
	public Point getLocation() {
		return this.location;
	}

	/**
	 * Sets the new velocity of the ball.
	 * 
	 * @param velocity 		new velocity
	 */
	public void setVelocity(Point velocity) {
		this.velocity = velocity;
	}

	/**
	 * Returns the current velocity of the ball.
	 * 
	 * @return	a Point representing the current velocity of the ball
	 */
	public Point getVelocity() {
		return this.velocity;
	}

	/**
	 * Sets a new color to the ball.
	 * 
	 * @param c		the new color
	 */
	public void setColor(Color c) {
		this.color = c;
	}

	/**
	 * Returns the current color of the ball.
	 * 
	 * @return	the Color that the ball is
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Sets a new radius to the ball.
	 * 
	 * @param r		the new radius
	 */
	public void setRadius(int r) {
		this.radius = r;
	}

	/**
	 * Returns the current radius of the ball
	 * 
	 * @return	the radius off the ball
	 */
	public int getRadius() {
		return this.radius;
	}

	/**
	 * @param strategy - the new IUpdateStrategy to use
	 */
	public void setStrategy(IUpdateStrategy strategy) {
		this.strategy = strategy;
		this.strategy.init(this);
	}

	/**
	 * @return the current update strategy of this Ball
	 */
	public IUpdateStrategy getUpdateStrategy() {
		return strategy;
	}

	/**
	 * @return the current update strategy of this Ball
	 */
	public IPaintStrategy getPaintStrategy() {
		return this.paintStrategy;
	}

	/**
	 * @return the current update strategy of this Ball
	 */
	public IInteractBehavior getInteractStrategy() {
		return this.interactionStrategy;
	}

	/**
	 * @return the dimensions of this canvas/GUI
	 */
	public IDimension getCanvasDims() {
		return this.canvasDims;
	}

	/**
	 * 
	 * @return the model2ViewAdapter that was passed to this ball.
	 */
	public IModel2ViewAdapter getModel2ViewAdapter() {
		return this.model2ViewAdapter;
	}

	/**
	 * @param paintStrategy the paint strategy to set for this ball
	 */
	public void setPaintStrategy(IPaintStrategy paintStrategy) {
		this.paintStrategy = paintStrategy;
		this.paintStrategy.init(this);
	}

	/**
	 * @param interact the interact strategy
	 */
	public void setInteractStrategy(IInteractBehavior interact) {
		this.interactionStrategy = interact;
	}

	/**
	 * @param other the target ball
	 * @param disp the dispatched to be used to update other balls
	 */
	public IBallCmd interactWith(Ball other, IDispatcher<IBallCmd> disp) {
		return this.interactionStrategy.interact(this, other, disp);
	}

}
