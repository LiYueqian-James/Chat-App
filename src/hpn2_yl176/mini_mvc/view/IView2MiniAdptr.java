/**
 * 
 */
package hpn2_yl176.mini_mvc.view;

import java.util.Set;

/**
 * @author James Li
 *
 */
public interface IView2MiniAdptr {

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
	public void sendHeartMsg();
	
	/**
	 * 
	 */
	public void sendTab();

	/**
	 * @return
	 */
	public Set<String> getRoomRoster();

}
