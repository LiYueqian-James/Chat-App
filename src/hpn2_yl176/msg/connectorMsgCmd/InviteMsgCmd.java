/**
 * 
 */
package hpn2_yl176.msg.connectorMsgCmd;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import common.connector.AConnectorDataPacketAlgoCmd;
import common.connector.ConnectorDataPacket;
import common.connector.messages.IInviteMsg;
import common.receiver.INamedReceiver;
import common.receiver.IReceiver;
import common.receiver.ReceiverDataPacket;
import hpn2_yl176.main_mvc.model.IMainModel2ViewAdpt;
import hpn2_yl176.main_mvc.model.MainModel;
import provided.datapacket.IDataPacketID;
import provided.pubsubsync.IPubSubSyncChannelUpdate;
import provided.pubsubsync.IPubSubSyncUpdater;
import provided.pubsubsync.IPubSubSyncManager;

/**
 * @author James Li
 *
 */
public class InviteMsgCmd extends AConnectorDataPacketAlgoCmd<IInviteMsg>{

	/**
	 * for serialization purpose
	 */
	private static final long serialVersionUID = 3344719687626090467L;
	
	/**
	 * The manager of the pubsubsync system.
	 */
	private IPubSubSyncManager pubSubSyncManager;
	
	private INamedReceiver receiver;

	private IMainModel2ViewAdpt adapter;

	
	private MainModel model;
	
	/**
	 * Constructor for the invite msg cmd.
	 * @param pubSubSyncManager the manager.
	 * @param receiier the receiver in this room.
	 */
	public InviteMsgCmd(INamedReceiver receiver, IMainModel2ViewAdpt adptr) {
//		this.pubSubSyncManager = pubSubSyncManager;
		this.receiver = receiver;
		this.adapter = adptr;
	}
	
	@Override
	public Void apply(IDataPacketID index, ConnectorDataPacket<IInviteMsg> host, Void... params) {
		//TODO: instantiate the mini controller!
		Thread t = new Thread(() -> {
//			model.makeRoom(host.getData().getFriendlyName());
		});
		t.start();
		return null;
	}

}
