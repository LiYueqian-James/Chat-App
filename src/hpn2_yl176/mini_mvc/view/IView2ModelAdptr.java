/**
 * 
 */
package hpn2_yl176.mini_mvc.view;

/**
 * @author James Li
 *
 */
public interface IView2ModelAdptr {
	
	/**
	 * Send a string message to the chat room.
	 * @param msg the msg to be sent.
	 */
	public void sendMsg(String msg);
	
	/**
	 * Leave this chat room
	 */
	public void leave();
	
	/**
	 * 
	 */
	public void sendBallWorld();
	
}
