package controller;

import java.awt.*;
import model.*;
import model.paintStrategy.IPaintStrategyFac;
import view.*;
import provided.utils.displayModel.IATImage;
import provided.utils.displayModel.IDimension;

/**
 * The Controller for Ballworld, organized by MVC Architecture, which sets up the
 * system and instantiates the model and view.
 * Uses event handlers to respond to user input and notifies the model and the view to update accordingly.
 * Uses a Timer object to update the model and the view at a regular time slice.
 * 
 * @author James Li yl176, Manan Bajaj
 */
public class BallWorldController {

	/**
	 * view in use
	 */
	private BallWorldView<IStrategyFac, IPaintStrategyFac> view;
	/**
	 * model in use
	 */
	private BallModel model;

	/**
	 * Constructor for BallWorldController. Instantiates BallWorldView GUI with the required
	 * adapters for making a ball, clearing the screen, or painting the balls. Starts the timer.
	 */
	public BallWorldController() {
		view = new BallWorldView<IStrategyFac, IPaintStrategyFac>(
				(new IView2ModelCtrlAdapter<IStrategyFac, IPaintStrategyFac>() {

					@Override
					public void clearBalls() {
						model.clearBalls();
					}

					@Override
					public IStrategyFac loadStrategyFac(String strategyName) {
						return model.makeStrategyFac(strategyName);
					}

					public void makeSwitcher(IPaintStrategyFac paintFac) {
						model.makeBall(model.getSwitcherStrategy(), paintFac.make());
					}

					public void updateSwitcherStrategy(IStrategyFac newStrategy) {
						if (newStrategy == null)
							model.switchSwitcherStrategy(IStrategyFac.NULL.make());
						else
							model.switchSwitcherStrategy(newStrategy.make());
					}

					@Override
					public IStrategyFac combineStrategyFac(IStrategyFac s1, IStrategyFac s2) {
						return model.makeStrategyFac(s1, s2);
					}

					@Override
					public IPaintStrategyFac loadPaintStrategyFac(String paintName) {
						return model.makePaintStrategyFac(paintName);

					}

					@Override
					public void loadBall(IStrategyFac strategyFac, IPaintStrategyFac paintFac) {
						model.makeBall(strategyFac.make(), paintFac.make());

					}

				}), new IView2ModelPaintAdapter() {
					// anonymous inner class for view2model paint adapter
					@Override
					public void paintBalls(Graphics g) {
						model.paint(g);
					}
				});

		model = new BallModel(new IModel2ViewAdapter() {
			// anonymous inner class for model2view adapter
			@Override
			public void update() {
				view.update();
			}

			@Override
			public IDimension getCanvasDims() {
				return new IDimension() {
					@Override
					public int getWidth() {
						return view.getCanvas().getWidth();
					}

					@Override
					public int getHeight() {
						return view.getCanvas().getHeight();
					}
				};
			}

			@Override
			public IATImage getIATImage(Image image) {
				return IATImage.FACTORY.apply(image, view.getCanvas());
			}
		});
	}

	/**
	 * Run the system. Start model and view
	 */
	public void start() {
		model.start();
		view.start();
	}

	/**
	 * Instantiate BallWorldController object which will subsequently build/start the program.
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					(new BallWorldController()).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
