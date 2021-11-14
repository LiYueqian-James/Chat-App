package hpn2_yl176.mini_mvc.controller;
import hpn2_yl176.mini_mvc.view.ChatRoomView;
import hpn2_yl176.mini_mvc.model.IMini2ViewAdptr;
import hpn2_yl176.mini_mvc.view.IView2ModelAdptr;
import hpn2_yl176.mini_mvc.model.MiniModel;
/**
 * @author James Li
 *
 */
public class MiniController {
	/**
	 * The view of a chat room.
	 */
	private ChatRoomView view;
	
	
	/**
	 * The model behind a chat room.
	 */
	private MiniModel model;
	
	/**
	 * Start the chat room
	 */
	public void start() {
		view.start();
		model.start();
	}
}
