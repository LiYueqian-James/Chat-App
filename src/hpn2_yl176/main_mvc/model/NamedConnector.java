/**
 * 
 */
package hpn2_yl176.main_mvc.model;

import common.connector.IConnector;
import common.connector.INamedConnector;

/**
 * @author James Li
 *
 */
public class NamedConnector implements INamedConnector{
	
	/**
	 * Serialization purpose.
	 */
	private static final long serialVersionUID = 8729827477340890263L;

	
	/**
	 * User name.
	 */
	private String name;
	
	
	/**
	 * Stub of the chat app instance.
	 */
	private IConnector stub;
	
	
	/**
	 * Constructor a named connector.
	 * @param name user name.
	 * @param stub the stub of the chat app.
	 */
	public NamedConnector(String name, IConnector stub) {
		this.name = name;
		this.stub = stub;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public IConnector getStub() {
		return this.stub;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
