/**
 * 
 */
package hpn2_yl176.mini_mvc.model;

import java.util.Set;

import common.receiver.INamedReceiver;

/**
 * @author James Li
 *
 */
public interface IMini2ViewAdptr {
	
	/**
	 * @param msg the message to be displayed.
	 */
	public void displayMsg(String msg);
	
	
	/**
	 * @param status the status message to be displayed.
	 */
	public void displayStatus(String status);
	
	/**
	 * @return all the INamedReceivers in the room.
	 */
	public Set<INamedReceiver> getRoomRoster();
	

}
