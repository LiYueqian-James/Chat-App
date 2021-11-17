/**
 * 
 */
package hpn2_yl176.main_mvc.view;

import common.connector.INamedConnector;

/**
 * @author hungnguyen, James Li
 * @param <Stub> the view holds the stub of other chat app instances.
 *
 */
public interface IMainViewToModelAdapter<Stub> {
	/**
	 * Allows the view to set up a connection with the remote IP
	 * @param remoteIP the IP address we are connecting to
	 * @param boundName The name of the chatApp instance
	 * @return A status message representing if the connection is successful or not.
	 */
	String connectTo(String remoteIP, String boundName);

	/**
	 * Terminate the program, clean up(send IQuitMsg).
	 */
	void quit();
	
	/**
	 * Invite the chat room app instance to the current chat room.
	 * @param app the app to be invited.
	 */
	void invite(Stub app);
	
	/**
	 * Start the model, used by the start button.
	 */
	void start();
	
	void makeNewRoom(String roomName);
	
	void removeRoom(int idx);
}
