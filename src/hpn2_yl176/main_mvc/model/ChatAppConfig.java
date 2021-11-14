/**
 * 
 */
package hpn2_yl176.main_mvc.model;

import provided.config.AppConfig;

/**
 * A configura
 * @author James Li
 *
 */
public class ChatAppConfig extends AppConfig{

	private int port;

	/**
	 * Instantiate a chatapp config;
	 * @param appName the name of the chat app
	 * @param port the port number
	 */
	public ChatAppConfig(String appName, int port) {
		super(appName);
		this.port = port;
		
	}

	/**
	 * @return the port number
	 */
	public int getPort() {
		return this.port;
	}

	/**
	 * @return the name of the chatapp
	 */
	public String getName() {
		return this.name;
	}



}
