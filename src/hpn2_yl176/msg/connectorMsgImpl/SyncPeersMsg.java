/**
 * 
 */
package hpn2_yl176.msg.connectorMsgImpl;

import java.util.Set;

import common.connector.INamedConnector;
import common.connector.messages.ISyncPeersMsg;

/**
 * @author James Li
 *
 */
public class SyncPeersMsg implements ISyncPeersMsg{
	
	
	/**
	 * Serialization purpose.
	 */
	private static final long serialVersionUID = 1312550007625217376L;
	
	/**
	 * The contacts the sender has.
	 */
	private Set<INamedConnector> contacts;
	
	/**
	 * Constructor a sync peer msg.
	 * @param contacts the contacts from the sender.
	 */
	public SyncPeersMsg(Set<INamedConnector> contacts) {
		this.contacts = contacts;
	}
	
	@Override
	public Set<INamedConnector> getNewPeers() {
		return this.contacts;
	}

}
