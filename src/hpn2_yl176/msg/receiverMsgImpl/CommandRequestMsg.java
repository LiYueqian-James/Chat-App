package hpn2_yl176.msg.receiverMsgImpl;

import common.receiver.messages.ICommandRequestMsg;
import provided.datapacket.IDataPacketID;

public class CommandRequestMsg implements ICommandRequestMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3157160933415724123L;
	
	/**
	 * 
	 */
	private IDataPacketID cmdId;
	/**
	 *
	 * @param cmdId
	 */
	public CommandRequestMsg(IDataPacketID cmdId) {
		this.cmdId = cmdId;
	}

	@Override
	public IDataPacketID getCmdID() {
		// TODO Auto-generated method stub
		return cmdId;
	}

}
