/**
 * 
 */
package hpn2_yl176.msg.receiverMsgCmd;

import common.adapter.ICmd2ModelAdapter;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.ReceiverDataPacket;
import common.receiver.messages.IReceiverErrMsg;
import common.receiver.messages.IReceiverMsg;
import hpn2_yl176.msg.receiverMsgImpl.StringMsg;
import provided.datapacket.IDataPacketID;

/**
 * @author James Li
 *
 */
public class HeartMessageCommand extends AReceiverDataPacketAlgoCmd<IReceiverMsg>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6428616618671948978L;

	private ICmd2ModelAdapter cmd2ModelAdapter;
	
	@Override
	public Void apply(IDataPacketID index, ReceiverDataPacket<IReceiverMsg> host, Void... params) {
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

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		cmd2ModelAdapter = cmd2ModelAdpt;
	}

}
