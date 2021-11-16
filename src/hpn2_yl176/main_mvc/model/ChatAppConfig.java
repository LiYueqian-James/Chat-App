/**
 * 
 */
package hpn2_yl176.main_mvc.model;

import provided.config.AppConfig;

/**
 * A configuration.
 * @author James Li
 *
 */
public class ChatAppConfig extends AppConfig{
	
	/**
	 * Port used by RMI stubs.
	 */
	private int RMIPort;
	
	/**
	 * Port used by the class server.
	 */
	private int classServerPort;

	/**
	 * Instantiate a chatapp config;
	 * @param boundName the name of the chat app.
	 * @param RMIport Port used by RMI stubs.
	 * @param classServerPort the class server port. 
	 */
	public ChatAppConfig(String boundName, int RMIport, int classServerPort) {
		super(boundName);
		this.RMIPort = RMIport;
		this.classServerPort = classServerPort;
	}

	/**
	 * @return the RMI port number
	 */
	public int getRMIPort() {
		return this.RMIPort;
	}
	
	public int getClassPort() {
		return this.classServerPort;
	}

	/**
	 * @return the name of the chatapp
	 */
	public String getName() {
		return this.name;
	}



}
