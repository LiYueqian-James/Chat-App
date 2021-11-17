/**
 * 
 */
package hpn2_yl176.msg.connectorMsgImpl;

import java.util.Set;

import common.connector.INamedConnector;
import common.connector.messages.IAddPeersMsg;

/**
 * @author James Li
 *
 */
public class AddPeersMsg implements IAddPeersMsg {

	/**
	 * Serialization purpose.
	 */
	private static final long serialVersionUID = 4996302500710282606L;

	/**
	 * Set of known contacts.
	 */
	Set<INamedConnector> contacts;

	/**
	 * Give my contacts to you.
	 * @param contacts my contacts.
	 */
	public AddPeersMsg(Set<INamedConnector> contacts) {
		this.contacts = contacts;
	}

	@Override
	public Set<INamedConnector> getNewPeers() {
		return this.contacts;
	}

}
