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
import hpn2_yl176.main_mvc.IMain2MiniAdptr;
import hpn2_yl176.main_mvc.model.IMainModel2ViewAdpt;
import model.IModel2ViewAdapter;
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

	
	/**
	 * Constructor for the invite msg cmd.
	 * @param pubSubSyncManager the manager.
	 * @param receiier the receiver in this room.
	 */
	public InviteMsgCmd(IPubSubSyncManager pubSubSyncManager, INamedReceiver receiver, IMainModel2ViewAdpt adptr) {
		this.pubSubSyncManager = pubSubSyncManager;
		this.receiver = receiver;
		this.adapter = adptr;
	}
	
	@Override
	public Void apply(IDataPacketID index, ConnectorDataPacket<IInviteMsg> host, Void... params) {
		//TODO: instantiate the mini controller!
		Thread t = new Thread(() -> {
			Set<INamedReceiver> initialRoster = new HashSet<>();
			UUID id = host.getData().getUUID();
			IPubSubSyncChannelUpdate<HashSet<INamedReceiver>> chatRoom = this.pubSubSyncManager.subscribeToUpdateChannel(id, 
					(data)->{
						initialRoster.addAll(data.getData());
					}, null);
			chatRoom.update(IPubSubSyncUpdater.makeSetAddFn(this.receiver));
			this.adapter.join(id, chatRoom.getFriendlyName(), pubSubSyncManager);
		});
		t.start();
		return null;
	}

}
