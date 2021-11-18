/**
 * 
 */
package hpn2_yl176.mini_mvc.model;

import java.util.Set;
import java.awt.Component;

import common.adapter.IComponentFactory;
import common.connector.INamedConnector;
import common.receiver.INamedReceiver;
import hpn2_yl176.main_mvc.model.ChatAppConfig;
import provided.logger.ILogger;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.RMIUtils;

/**
 * @author James Li
 * @autho Hung Nguyen
 * The adapter from the mini model to view
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

	/**
	 * 
	 * @return the system's logger
	 */
	public ILogger getSysLogger();

	/**
	 * 
	 * @return the RMI Utils
	 */
	public IRMIUtils getRmiUtils();

	//	public void updateMemberList(Set<INamedReceiver> namedReceivers);

	/**
	 * 
	 * @return the username
	 */
	public String getUserName();

	/**
	 * 
	 * @return the app configuration
	 */
	public ChatAppConfig getConfig();

	/**
	 * Gets the named connector associated with this app.
	 * @return the named connector
	 */
	public INamedConnector getNamedConnector();

	/**
	 * adds a scrolling component
	 * @param fac
	 * @param name
	 */
	public void addScrollingComponent(IComponentFactory fac, String name);

	/**
	 * Adds a fixed component
	 * @param fac
	 * @param name
	 */
	public void addFixedComponent(IComponentFactory fac, String name);

	/**
	 * gets the view panel
	 * @return the view panel
	 */
	public Component getViewPanel();

	/**
	 * Gets the bound name
	 * @return the bound name
	 */
	public String getBoundName();
	
	/**
	 * Gets the room name
	 * @return the room name
	 */
	public String getRoomName();
}
