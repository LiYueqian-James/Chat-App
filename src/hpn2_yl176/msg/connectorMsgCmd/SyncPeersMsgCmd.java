/**
 * 
 */
package hpn2_yl176.msg.connectorMsgCmd;

import java.rmi.RemoteException;
import java.util.Set;

import common.connector.AConnectorDataPacketAlgoCmd;

import common.connector.ConnectorDataPacket;
import common.connector.INamedConnector;
import common.connector.messages.IInviteMsg;
import common.connector.messages.ISyncPeersMsg;
import common.connector.messages.IAddPeersMsg;
import hpn2_yl176.main_mvc.model.IMainModel2ViewAdpt;
import hpn2_yl176.main_mvc.model.MainModel;
import hpn2_yl176.msg.connectorMsgImpl.AddPeersMsg;
import provided.datapacket.IDataPacketID;
import provided.logger.ILogger;
import provided.logger.LogLevel;

/**
 * @author James Li
 *
 */
public class SyncPeersMsgCmd extends AConnectorDataPacketAlgoCmd<ISyncPeersMsg> {

	/**
	 * Serialization purpose
	 */
	private static final long serialVersionUID = -1331341483331991334L;

	/**
	 * Contacts to be sent.
	 */
	private Set<INamedConnector> contacts;

	/**
	 * The sender of the message.
	 */
	private INamedConnector sender;

	/**
	 * System logger.
	 */
	private ILogger sysLogger;
	
	/**
	 * The adapter.
	 */
	private IMainModel2ViewAdpt adptr;

	/**
	 * @param contacts my contacts.
	 * @param sender the sender of the msg.
	 * @param sysLogger the logger to display status msg.
	 */
	public SyncPeersMsgCmd(Set<INamedConnector> contacts, INamedConnector sender, ILogger sysLogger,
			IMainModel2ViewAdpt adptr) {
		this.contacts = contacts;
		this.sender = sender;
		this.sysLogger = sysLogger;
		this.adptr = adptr;
	}

	@Override
	public Void apply(IDataPacketID index, ConnectorDataPacket<ISyncPeersMsg> host, Void... params) {
		Thread t = new Thread(() -> {
			for (INamedConnector newPeer : host.getData().getNewPeers()) {
				try {
					newPeer.sendMessage(
							new ConnectorDataPacket<IAddPeersMsg>(new AddPeersMsg(this.contacts), this.sender));
				} catch (RemoteException e) {
					sysLogger.log(LogLevel.DEBUG, "Failed to send AddPeersMsg");
					e.printStackTrace();
				}
			}

			// for each contact I previously have (Including myself)
			// tell them to add the new contacts from the sender
			for (INamedConnector myConnectedStub : this.contacts) {
				try {
					myConnectedStub.sendMessage(new ConnectorDataPacket<IAddPeersMsg>(
							new AddPeersMsg(host.getData().getNewPeers()), myConnectedStub));
				} catch (RemoteException e) {
					sysLogger.log(LogLevel.DEBUG, "Failed to send AddPeersMsg");
					e.printStackTrace();
				}
			}
		});
		t.start();
		return null;
	}

}
