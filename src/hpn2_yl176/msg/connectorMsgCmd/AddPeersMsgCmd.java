/**
 * 
 */
package hpn2_yl176.msg.connectorMsgCmd;

import java.util.Set;

import common.connector.AConnectorDataPacketAlgoCmd;
import common.connector.ConnectorDataPacket;
import common.connector.INamedConnector;
import common.connector.messages.IAddPeersMsg;
import hpn2_yl176.main_mvc.model.IMainModel2ViewAdpt;
import provided.datapacket.IDataPacketID;

/**
 * @author James Li
 *
 */
public class AddPeersMsgCmd extends AConnectorDataPacketAlgoCmd<IAddPeersMsg>{
	
	/**
	 * Serialization purpose.
	 */
	private static final long serialVersionUID = 5492068021261370628L;
	
	/**
	 * Set of known stubs.
	 */
	private Set<INamedConnector> contacts;
	
	/**
	 * Model2View adptr.
	 */
	private IMainModel2ViewAdpt adptr;
	
	/**
	 * @param contacts set of existing contacts.
	 */
	public AddPeersMsgCmd(Set<INamedConnector> contacts, IMainModel2ViewAdpt adptr) {
		this.contacts = contacts;	
		this.adptr = adptr;
	}
	
	@Override
	public Void apply(IDataPacketID index, ConnectorDataPacket<IAddPeersMsg> host, Void... params) {
		Thread t = new Thread(() ->{
			contacts.addAll(host.getData().getNewPeers());
			adptr.updateContacts(contacts);
		});
		t.start();
		return null;
	}

}
