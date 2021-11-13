/**
 * 
 */
package hpn2_yl176.main_mvc.view;

import common.connector.INamedConnector;

/**
 * @author hungnguyen
 *
 */
public interface IMainViewToModelAdapter<ConnectedHostname> {
	/**
	 * Allows the view to set up a connection with the remote IP
	 * @param remoteIP the IP address we are connecting to
	 */
	String connectTo(String remoteIP);

	/**
	 * Terminate the program
	 */
	void quit();
	
	void invite(INamedConnector namedConnector);
	
	void makeRoom(String roomName);
}
