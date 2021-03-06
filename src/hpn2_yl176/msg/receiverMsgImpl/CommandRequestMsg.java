package hpn2_yl176.msg.receiverMsgImpl;

import common.receiver.messages.ICommandRequestMsg;
import common.receiver.messages.IReceiverMsg;
import hpn2_yl176.msg.receiverMsgCmd.CommandRequestMsgCmd;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

public class CommandRequestMsg implements ICommandRequestMsg {

	/**
	 * serialization 
	 */
	private static final long serialVersionUID = 3157160933415724123L;
	
	/**
	 * 
	 */
	private IDataPacketID cmdId;
	/**
	 *
	 * @param cmdId the id
	 */
	public CommandRequestMsg(IDataPacketID cmdId) {
		this.cmdId = cmdId;
	}
	
	@Override
	public IDataPacketID getCmdID() {
		return cmdId;
	}

}
