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
public class NamedReceiver implements INamedReceiver{

	/**
	 * Serialization purpose.
	 */
	private static final long serialVersionUID = -6162987341184298439L;
	
	private IReceiver receiver;
	
//	private transient IMini2ViewAdptr adptr;
	
	private String userName;
	
	private INamedConnector app;
	
	public NamedReceiver(IReceiver receiver, String userName, INamedConnector app) {
		this.receiver = receiver;
//		this.adptr = adptr;
		this.userName = userName;
		this.app = app;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.userName;
	}

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
