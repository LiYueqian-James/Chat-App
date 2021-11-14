/**
 * 
 */
package hpn2_yl176.main_mvc;

import javax.swing.JPanel;

import common.receiver.INamedReceiver;

/**
 * @author James Li
 * Main wants the mini to...
 */
public interface IMain2MiniAdptr {
	/**
	 * Make a runnable mini controller
	 * @return an IMain2MiniAdptr to interact with it
	 */
	public IMain2MiniAdptr make();
	
	/**
	 * @return the named receiver representing the stub of the chat room(the mini mvc)
	 */
	public INamedReceiver getNamedReceiver();
	
	/**
	 * @return the panel containing the view of a chat room.
	 */
	public JPanel getRoomPanel();
}
