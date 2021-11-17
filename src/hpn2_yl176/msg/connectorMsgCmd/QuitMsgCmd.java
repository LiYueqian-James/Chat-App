/**
 * 
 */
package hpn2_yl176.msg.connectorMsgCmd;

import java.util.Set;

import common.connector.AConnectorDataPacketAlgoCmd;
import common.connector.ConnectorDataPacket;
import common.connector.INamedConnector;
import common.connector.messages.IQuitMsg;
import hpn2_yl176.main_mvc.model.IMainModel2ViewAdpt;
import provided.datapacket.IDataPacketID;

/**
 * @author James Li
 *
 */
public class QuitMsgCmd extends AConnectorDataPacketAlgoCmd<IQuitMsg> {

	/**
	 * Serialization purpose.
	 */
	private static final long serialVersionUID = 4719656209285663477L;

	/**
	 * the model2view adptr.
	 */
	private IMainModel2ViewAdpt adpt;

	/**
	 * the set of known stub.
	 */
	private Set<INamedConnector> contacts;

	/**
	 * Constructor.
	 * @param adpt the model2view adptr.
	 * @param contacts the set of known stub.
	 */
	public QuitMsgCmd(IMainModel2ViewAdpt adpt, Set<INamedConnector> contacts) {
		this.adpt = adpt;
		this.contacts = contacts;
	}

	@Override
	public Void apply(IDataPacketID index, ConnectorDataPacket<IQuitMsg> host, Void... params) {
		Thread t = new Thread(() -> {
			System.out.println(contacts.size());
			contacts.remove(host.getSender());
			System.out.println(contacts.size());
			// tell the rest of the world (all the chat rooms) that this stub is dead.
			adpt.updateContacts(contacts);
		});
		t.start();
		return null;
	}

}
