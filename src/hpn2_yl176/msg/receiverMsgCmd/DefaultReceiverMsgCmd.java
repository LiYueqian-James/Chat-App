/**
 * 
 */
package hpn2_yl176.msg.receiverMsgCmd;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import common.adapter.ICmd2ModelAdapter;
import common.receiver.AReceiverDataPacketAlgoCmd;
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

	/**
	 * A cache of list of messages to data packetID
	 */
	HashMap<IDataPacketID, ArrayList<ReceiverDataPacket<IReceiverMsg>>> unexecutedMsgs;

	/**
	 * DefaultReceiverMsgCmd to process the 
	 * @param unexecutedMsgs
	 */
	public DefaultReceiverMsgCmd(HashMap<IDataPacketID, ArrayList<ReceiverDataPacket<IReceiverMsg>>> unexecutedMsgs) {
		this.unexecutedMsgs = unexecutedMsgs;
	}

	@Override
	public Void apply(IDataPacketID index, ReceiverDataPacket<IReceiverMsg> host, Void... params) {
		// TODO Auto-generated method stub
//		System.out.println("wtf?????"+unexecutedMsgs.toString());
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
				host.getSender().sendMessage(new ReceiverDataPacket<IReceiverMsg>(
						new CommandRequestMsg(host.getData().getID()), host.getSender()));
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
}
