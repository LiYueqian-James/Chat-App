/**
 * 
 */
package hpn2_yl176.msg.receiverMsgCmd;

import java.util.ArrayList;
import java.util.HashMap;

import common.adapter.ICmd2ModelAdapter;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.ReceiverDataPacket;
import common.receiver.ReceiverDataPacketAlgo;
import common.receiver.messages.ICommandMsg;
import common.receiver.messages.IReceiverErrMsg;
import common.receiver.messages.IReceiverMsg;
import hpn2_yl176.msg.receiverMsgImpl.HeartMessage;
import hpn2_yl176.msg.receiverMsgImpl.StringMsg;
import provided.datapacket.IDataPacketID;

/**
 * @author James Li
 *
 */
public class HeartMessageCmd extends AReceiverDataPacketAlgoCmd<ICommandMsg>{	
	
	private ICmd2ModelAdapter cmd2ModelAdapter;
	

	public HeartMessageCmd(ICmd2ModelAdapter cmd2ModelAdapter) {
		// TODO Auto-generated constructor stub
		this.cmd2ModelAdapter = cmd2ModelAdapter;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6428616618671948978L;
	
//	public Void apply(IDataPacketID index, ReceiverDataPacket<IReceiverMsg> host, Void... params) {
//		// TODO Auto-generated method stub
//		String heartMessage = "_____$$$$_________$$$$\n"
//				+ "___$$$$$$$$_____$$$$$$$$\n"
//				+ "_$$$$$$$$$$$$_$$$$$$$$$$$$\n"
//				+ "$$$$$$$$$$$$$$$$$$$$$$$$$$$\n"
//				+ "$$$$$$$$$$$$$$$$$$$$$$$$$$$\n"
//				+ "_$$$$$$$$$$$$$$$$$$$$$$$$$\n"
//				+ "__$$$$$$$$$$$$$$$$$$$$$$$\n"
//				+ "____$$$$$$$$$$$$$$$$$$$\n"
//				+ "_______$$$$$$$$$$$$$\n"
//				+ "__________$$$$$$$\n"
//				+ "____________$$$\n"
//				+ "_____________$";
//		IReceiverMsg receiverMsg = new StringMsg(heartMessage);
//		cmd2ModelAdapter.broadcast(receiverMsg);
//		return null;
//	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		 cmd2ModelAdapter = cmd2ModelAdpt;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Void apply(IDataPacketID index, ReceiverDataPacket<ICommandMsg> host, Void... params) {
		// TODO Auto-generated method stub
		String heartMessage = "_____$$$$_________$$$$\n"
		+ "___$$$$$$$$_____$$$$$$$$\n"
		+ "_$$$$$$$$$$$$_$$$$$$$$$$$$\n"
		+ "$$$$$$$$$$$$$$$$$$$$$$$$$$$\n"
		+ "$$$$$$$$$$$$$$$$$$$$$$$$$$$\n"
		+ "_$$$$$$$$$$$$$$$$$$$$$$$$$\n"
		+ "__$$$$$$$$$$$$$$$$$$$$$$$\n"
		+ "____$$$$$$$$$$$$$$$$$$$\n"
		+ "_______$$$$$$$$$$$$$\n"
		+ "__________$$$$$$$\n"
		+ "____________$$$\n"
		+ "_____________$";
		IReceiverMsg receiverMsg = new StringMsg(heartMessage);
		cmd2ModelAdapter.broadcast(receiverMsg);
return null;
	}
}
