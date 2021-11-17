/**
 * 
 */
package hpn2_yl176.mini_mvc.controller;

import java.util.Set;
import java.awt.Component;

import common.connector.INamedConnector;
import common.receiver.INamedReceiver;
import hpn2_yl176.main_mvc.model.ChatAppConfig;
import provided.logger.ILogger;
import provided.pubsubsync.IPubSubSyncManager;
import provided.rmiUtils.IRMIUtils;

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
	

	// public void removeRoom();
	
	/**
	 * Upon exit, tell the main to clean up.
	 */
	public void removeRoom();
	
	/**
	 * @return the logger to be used to display status.
	 */
	public ILogger getLogger();
	
	public ChatAppConfig getConfig();
	
	public INamedConnector getNamedConnector();
	
	public String getUserName();
	
	public IRMIUtils getRmiUtils();
}
