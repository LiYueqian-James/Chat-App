/**
 * 
 */
package hpn2_yl176.msg.receiverMsgCmd;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import common.adapter.ICmd2ModelAdapter;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.INamedReceiver;
import common.receiver.ReceiverDataPacket;
import common.receiver.messages.ICommandRequestMsg;
import common.receiver.messages.IReceiverMsg;
import hpn2_yl176.msg.receiverMsgImpl.CommandRequestMsg;
import provided.datapacket.IDataPacketID;

/**
 * @author hungnguyen
 * DefaultReceiverMsgCmd 
 */
public class DefaultReceiverMsgCmd extends AReceiverDataPacketAlgoCmd<IReceiverMsg> {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -2325284822657390621L;

	private HashMap<IDataPacketID, ArrayList<ReceiverDataPacket<IReceiverMsg>>> unexecutedMsgs;
	
	private INamedReceiver me;

	/**
	 * DefaultReceiverMsgCmd to process the 
	 * @param unexecutedMsgs
	 */
	public DefaultReceiverMsgCmd(HashMap<IDataPacketID, ArrayList<ReceiverDataPacket<IReceiverMsg>>> unexecutedMsgs) {
		this.unexecutedMsgs = unexecutedMsgs;
//		this.me = me;
	}

	@Override
	public Void apply(IDataPacketID index, ReceiverDataPacket<IReceiverMsg> host, Void... params) {
		// TODO Auto-generated method stub
		if (!unexecutedMsgs.containsKey(index)) {
			ArrayList<ReceiverDataPacket<IReceiverMsg>> list = new ArrayList<>();
			list.add(host);
			unexecutedMsgs.put(index, list);
		}
		else {
			unexecutedMsgs.get(index).add(host);
		}
		
		Thread thread = new Thread(() -> {
			try {
				host.getSender().sendMessage(new ReceiverDataPacket<ICommandRequestMsg>(
						new CommandRequestMsg(host.getData().getID()), me));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		thread.start();
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub

	}
	
	public void setNamedReceiver(INamedReceiver me) {
		this.me = me;
	}
}
