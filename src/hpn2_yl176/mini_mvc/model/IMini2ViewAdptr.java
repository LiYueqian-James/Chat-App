/**
 * 
 */
package hpn2_yl176.mini_mvc.model;

import java.util.Set;

import common.adapter.IComponentFactory;
import common.connector.INamedConnector;
import common.receiver.INamedReceiver;
import hpn2_yl176.main_mvc.model.ChatAppConfig;
import provided.logger.ILogger;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.RMIUtils;

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
	
	public ILogger getSysLogger();

	public IRMIUtils getRmiUtils();
	
	public void updateMemberList(Set<INamedReceiver> namedReceivers);
	
	public String getUserName();
	
	public ChatAppConfig getConfig();
	
	public INamedConnector getNamedConnector();
	
	public void removeRoom();
	
	public void addScrollingComponent(IComponentFactory fac, String name);
	public void addFixedComponent(IComponentFactory fac, String name);
}
