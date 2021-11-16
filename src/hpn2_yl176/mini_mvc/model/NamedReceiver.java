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
	 * 
	 */
	private static final long serialVersionUID = -6162987341184298439L;
	
	private IReceiver receiver;
	private IMini2ViewAdptr adptr;
	
	public NamedReceiver(IReceiver receiver, IMini2ViewAdptr adptr) {
		this.receiver = receiver;
		this.adptr = adptr;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return adptr.getUserName();
	}

	@Override
	public INamedConnector getConnector() {
		// TODO Auto-generated method stub
		return adptr.getNamedConnector();
	}

	@Override
	public IReceiver getStub() {
		// TODO Auto-generated method stub
		return receiver;
	}

}
