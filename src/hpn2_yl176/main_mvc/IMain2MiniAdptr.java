/**
 * 
 */
package hpn2_yl176.main_mvc;

import javax.swing.JPanel;

import common.receiver.INamedReceiver;

/**
 * @author James Li
 * The mini model doesn't know if the room is created by the current chat app instance
 * or if it is created via invitation.
 * 
 * Each adapter represents a chat room instance.
 */
public interface IMain2MiniAdptr {	
	/**
	 * @return the named receiver representing the stub of the chat room(the mini mvc)
	 */
	public INamedReceiver getNamedReceiver();
	
	/**
	 * @return the panel containing the view of a chat room.
	 */
	public JPanel getRoomPanel();
	
	/**
	 * Start the mini controller - start the chat room!
	 */
	public void start();
	
	/**
	 * Remove the person that has quit from the room.
	 * @param person the person who has quit.
	 */
	public void removeParticipant(INamedReceiver person);
	
	/**
	 * Quit the chat room.
	 */
	public void quit();
	
}
