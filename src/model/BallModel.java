package model;

import model.updateStrategy.*;

import model.paintStrategy.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.Timer;
import provided.utils.dispatcher.IDispatcher;
import provided.utils.dispatcher.impl.SequentialDispatcher;
import provided.utils.loader.IObjectLoader;
import provided.utils.loader.impl.ObjectLoaderPath;
import provided.utils.valueGenerator.IRandomizer;
import provided.utils.valueGenerator.impl.Randomizer;

/**
 * Models all the balls
 * 
 * @author James Li yl176, Manan Bajaj
 *
 */
public class BallModel {
	/**
	 *  Model to view adapter
	 */
	private IModel2ViewAdapter model2ViewAdpt = IModel2ViewAdapter.NULL_OBJECT;
	/**
	 *  IDispatcher contains all the balls added
	 */
	private IDispatcher<IBallCmd> myDispatcher = new SequentialDispatcher<IBallCmd>();
	/**
	 * Update Strategy Loader
	 */
	private IObjectLoader<IUpdateStrategy> strategyLoader = new ObjectLoaderPath<IUpdateStrategy>(
			(params) -> IUpdateStrategy.ERROR, "model.updateStrategy.");

	/**
	 * Paint Strategy Loader
	 */
	private IObjectLoader<IPaintStrategy> paintLoader = new ObjectLoaderPath<>((params) -> IPaintStrategy.NULL,
			"model.paintStrategy.");
	/**
	 * IUpdateStrategy that the switcher balls are using
	 */
	private IUpdateStrategy switchStrategy = IUpdateStrategy.NULL;

	/**
	 * Wrapper around the switcher ball strategy.
	 */
	private IUpdateStrategy switcher = new IUpdateStrategy() {
		@Override
		public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
			switchStrategy.updateState(context, null);
		}
	};
	/**
	 * Delay between events on timer (in milliseconds)
	 */
	private int tickTime = 50;
	/**
	 * Timer by which the model is updated
	 */
	private Timer timer = new Timer(tickTime, (e) -> model2ViewAdpt.update());
	/**
	 * Used to randomize location, size, etc
	 */
	IRandomizer rand = Randomizer.Singleton;
	/**
	 * Minimum radius for balls to be randomly generated with
	 */
	private int _InitMinRad = 15;
	/**
	 * Maximum radius for balls to be randomly generated with
	 */
	private int _InitMaxRad = 30;
	/**loadIns
	 * Maximum speed for balls to be randomly generated with
	 */
	private int _InitMaxSpeed = 20;
	/**
	 * Maximum velocity for balls to be randomly generated with
	 */
	private Rectangle _InitMaxVel = new Rectangle(-_InitMaxSpeed, -_InitMaxSpeed, _InitMaxSpeed, _InitMaxSpeed);

	/**
	 *  The constructor for the model
	 *  
	 * @param model2ViewAdpt The adapter to the view
	 */
	public BallModel(IModel2ViewAdapter model2ViewAdpt) {
		this.model2ViewAdpt = model2ViewAdpt;
	}

	/**
	 * Update the balls' painted locations by painting all the balls onto the graphics object.
	 * 
	 * @param g The Graphics object from the view's paingComponent() call.
	 */
	public void paint(final Graphics g) {
		// myDispatcher.updateAll(g); // Graphics object given to all the balls (Observers)

		myDispatcher.updateAll(new IBallCmd() {
			public void apply(Ball context, IDispatcher<IBallCmd> disp) {
				context.move();
				context.updateState(disp);
				context.bounce();
				context.paint(g);
			}
		}); // Graphics object given to all the balls (Observers)
	}

	/**
	 * Creates a ball of type classname
	 * 
	 * @param strategy		strategy that this new ball will adopt
	 */
	public void makeBall(IUpdateStrategy strategy, IPaintStrategy paintStrategy) {
		myDispatcher.addObserver(new Ball(
				rand.randomLoc(new Dimension(model2ViewAdpt.getCanvasDims().getWidth(),
						model2ViewAdpt.getCanvasDims().getHeight())),
				rand.randomInt(_InitMinRad, _InitMaxRad),
				//				25, 
				rand.randomColor(), rand.randomVel(_InitMaxVel), model2ViewAdpt.getCanvasDims(), strategy,
				paintStrategy, this.model2ViewAdpt));
	}

	/**
	 * @param classname the name of the paint strategy
	 * @return the paint factory
	 */
	public IPaintStrategyFac makePaintStrategyFac(final String classname) {
		if (null == classname) {
			// Add this handling in later
		}

		return new IPaintStrategyFac() {

			public IPaintStrategy make() {
				if (classname.contains("PaintStrategy"))
					return paintLoader.loadInstance(classname);
				else
					return paintLoader.loadInstance(classname + "PaintStrategy");
			}

			public String toString() {
				return classname;
			}

		};
	}

	/**
	 * Returns an IStrategyFac that can be used to make the IUpdateStrategy
	 * specified by classname. If classname is null, return an error factory.
	 * Will accept both the strategy abbreviation, and full strategy name.
	 * 
	 * @param classname name of strategy's class
	 * @return an IStrategyFac to make that strategy
	 */
	public IStrategyFac makeStrategyFac(final String classname) {
		if (null == classname) {
			return IStrategyFac.ERROR;
		}
		return new IStrategyFac() {
			public IUpdateStrategy make() {
				if (classname.contains("Strategy"))
					return strategyLoader.loadInstance(classname);
				else
					return strategyLoader.loadInstance(classname + "Strategy");
			}

			public String toString() {
				return classname;
			}
		};
	}

	/**
	 * Overload the make method, when two factories are provided, combine them and
	 * return a new factory. 
	 * @param s1 one factory.
	 * @param s2 another factory.
	 * @return a new factory that generates the combined strategy.
	 */
	public IStrategyFac makeStrategyFac(IStrategyFac s1, IStrategyFac s2) {
		return new IStrategyFac() {
			public IUpdateStrategy make() {
				return new MultiStrategy(s1.make(), s2.make());
			}

			public String toString() {
				return s1.toString() + "-" + s2.toString();
			}
		};
	}

	/**
	 * Clears all the balls on the screen
	 */
	public void clearBalls() {
		myDispatcher.removeAllObservers();
	}

	/**
	 * Start the model
	 */
	public void start() {
		timer.start();
	}

	/**
	 * @return the current switcher strategy.
	 */
	public IUpdateStrategy getSwitcherStrategy() {
		return switcher;
	}

	/**
	 * @param newStrategy	the new strategy to apply to the switcher balls.
	 */
	public void switchSwitcherStrategy(IUpdateStrategy newStrategy) {
		this.switchStrategy = newStrategy;
	}

}
