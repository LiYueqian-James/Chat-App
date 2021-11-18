/**
 * 
 */
package hpn2_yl176.mini_mvc.model;

import common.connector.INamedConnector;
import common.receiver.INamedReceiver;
import common.receiver.IReceiver;

/**
 * @author hungnguyen
 *
 */
public class NamedReceiver implements INamedReceiver {

	/**
	 * Serialization purpose.
	 */
	private static final long serialVersionUID = -6162987341184298439L;

	/**
	 * The receiver
	 */
	private IReceiver receiver;

	//	private transient IMini2ViewAdptr adptr;

	/**
	 * Username
	 */
	private String userName;

	/**
	 * The named connector of the app
	 */
	private INamedConnector app;

	/**
	 * The Named Receiver constructor
	 * @param receiver the receiver
	 * @param userName the username
	 * @param app the app connector
	 */
	public NamedReceiver(IReceiver receiver, String userName, INamedConnector app) {
		this.receiver = receiver;
		//		this.adptr = adptr;
		this.userName = userName;
		this.app = app;
	}

	/**
	 * Gets the username
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	/**
	 * Gets the connector
	 */
	@Override
	public INamedConnector getConnector() {
		// TODO Auto-generated method stub
		return this.app;
	}

	@Override
	public IReceiver getStub() {
		// TODO Auto-generated method stub
		return receiver;
	}

	@Override
	public String toString() {
		return this.getName();
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof INamedReceiver) {
			return ((INamedReceiver) obj).getStub().equals(this.receiver);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.receiver.hashCode();
	}

}
