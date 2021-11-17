package hpn2_yl176.msg.receiverMsgImpl;

import common.receiver.messages.ICommandMsg;
import hpn2_yl176.msg.receiverMsgCmd.HeartMessageCommand;

public class HeartMessage extends CommandMsg{

	public HeartMessage() {
		
		super(new HeartMessageCommand(), ICommandMsg.GetID());
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9187981649706816326L;
	
}
