package hpn2_yl176.mini_mvc.controller;
import hpn2_yl176.mini_mvc.view.ChatRoomView;
import hpn2_yl176.mini_mvc.IMini2MainAdptr;
import hpn2_yl176.mini_mvc.model.IModel2ViewAdptr;
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
	 * Construct a new mini controller.
	 * @param adptr the m2m adapter
	 */
	public MiniController(IMini2MainAdptr adptr) {
		this.model = new MiniModel(new IModel2ViewAdptr() {

			@Override
			public void displayMsg(String msg) {
				view.appendMessage(adptr.getUserName(), msg);
				
			}

			@Override
			public void displayStatus(String status) {
				view.appendStatus(status);
				
			}
			
		});
		
		this.view = new ChatRoomView(new IView2ModelAdptr() {

			@Override
			public void sendMsg(String msg) {
				model.sendMsg(msg);
				
			}

			@Override
			public void leave() {
				adptr.removeRoom();
				
			}

			@Override
			public void sendBallWorld() {
				model.sendBallWorld();
				
			}
			
		});
	}
	
	/**
	 * Start the chat room
	 */
	public void start() {
		view.start();
		model.start();
	}
}
