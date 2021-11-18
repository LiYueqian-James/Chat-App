/**
 * 
 */
package hpn2_yl176.msg.receiverMsgCmd;

import java.util.ArrayList;
import java.util.HashMap;

import common.adapter.ICmd2ModelAdapter;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.IReceiver;
import common.receiver.ReceiverDataPacket;
import common.receiver.ReceiverDataPacketAlgo;
import common.receiver.messages.ICommandMsg;
import common.receiver.messages.IReceiverMsg;
import provided.datapacket.IDataPacketID;
import provided.mixedData.MixedDataDictionary;
import provided.mixedData.MixedDataKey;

/**
 * @author hungnguyen
 *
 */
public class CommandMsgCmd extends AReceiverDataPacketAlgoCmd<ICommandMsg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3540863009064871106L;

	/**
	 * a receiver 
	 */
	private ReceiverDataPacketAlgo receiverVisitor;

	/**
	 * A cache that maps each data packet id to a list of receiver data packets 
	 */
	private HashMap<IDataPacketID, ArrayList<ReceiverDataPacket<IReceiverMsg>>> unexecutedMsgs;

	/**
	 * The command 2 model adapter
	 */
	private ICmd2ModelAdapter cmd2ModelAdapter;

	/**
	 * Constructor 
	 * @param receiverVisitor the visitor
	 * @param unexecutedMsgs the messages
	 */
	public CommandMsgCmd(ReceiverDataPacketAlgo receiverVisitor, HashMap<IDataPacketID, ArrayList<ReceiverDataPacket<IReceiverMsg>>> unexecutedMsgs) {
		this.receiverVisitor = receiverVisitor;
		this.unexecutedMsgs = unexecutedMsgs;
	}

	@Override
	public Void apply(IDataPacketID index, ReceiverDataPacket<ICommandMsg> host, Void... params) {
		AReceiverDataPacketAlgoCmd<?> receivedCmd = ((ICommandMsg) host.getData()).getCmd();
		IDataPacketID id = host.getData().getCmdID();
		receiverVisitor.setCmd(id, receivedCmd);
		receivedCmd.setCmd2ModelAdpt(cmd2ModelAdapter);
//		unexecutedMsgs.get(id)
		for (ReceiverDataPacket<IReceiverMsg> message : unexecutedMsgs.get(id)) {
			message.execute(receiverVisitor);
		}
		unexecutedMsgs.put(id, new ArrayList<ReceiverDataPacket<IReceiverMsg>>());
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		this.cmd2ModelAdapter = cmd2ModelAdpt;
	}

	public HashMap<IDataPacketID, ArrayList<ReceiverDataPacket<IReceiverMsg>>> getUnexecutedMsgs() {
		return this.unexecutedMsgs;
	}

}
