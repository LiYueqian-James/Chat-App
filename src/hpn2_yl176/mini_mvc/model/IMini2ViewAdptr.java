/**
 * 
 */
package hpn2_yl176.mini_mvc.model;

import provided.pubsubsync.IPubSubSyncManager;

/**
 * @author James Li
 *
 */
public interface IMini2ViewAdptr {
	
	/**
	 * @param msg the message to be displayed
	 */
	public void displayMsg(String msg);
	
	
	/**
	 * @param status the status message to be displayed
	 */
	public void displayStatus(String status);
	
	/**
	 * Get the pubsubsync manager to create data channels
	 * @return
	 */
	public IPubSubSyncManager getPubSubSyncManager();
	

}
