package model;

import java.awt.Image;

import provided.utils.displayModel.IATImage;
import provided.utils.displayModel.IDimension;

/**
 * Model expects view to repaint the screen.
 * 
 * @author James Li yl176, Alena Holbert ah72
 */
public interface IModel2ViewAdapter {
	/**
	 *  Model calls update method of view.
	 */
	public void update();

	/**
	 * Gets the dimensions of the canvas
	 * 
	 * @return canvas dimensions
	 */
	public IDimension getCanvasDims();

	/**
	 * A way for the model to get the image.
	 * @param image the image
	 * @return an IATImage
	 */
	public IATImage getIATImage(Image image);

	/**
	 * No-op null adapter
	 */
	public static final IModel2ViewAdapter NULL_OBJECT = new IModel2ViewAdapter() {
		@Override
		public void update() {
		};

		@Override
		public IDimension getCanvasDims() {
			return null;
		}

		@Override
		public IATImage getIATImage(Image image) {
			// TODO Auto-generated method stub
			return null;
		};
	};

}
