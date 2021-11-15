/**
 * 
 */
package hpn2_yl176.mini_mvc.controller;

import java.util.Set;

import common.receiver.INamedReceiver;
import provided.logger.ILogger;
import provided.pubsubsync.IPubSubSyncManager;

/**
 * @author James Li
 *
 */
public interface IMini2MainAdptr {
	/**
	 * Get the pubsubsync manager to create data channels
	 * @return the manager.
	 */
	public IPubSubSyncManager getPubSubSyncManager();
	
	/**
	 * Upon exit, tell the main to clean up.
	 */
	public void removeRoom();
	
	/**
	 * @return the logger to be used to display status.
	 */
	public ILogger getLogger();
	
	/**
	 * @return
	 */
	public Set<INamedReceiver> getRoomRoster();
}
