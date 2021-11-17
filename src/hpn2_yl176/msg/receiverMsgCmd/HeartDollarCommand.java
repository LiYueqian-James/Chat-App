/**
 * 
 */
package hpn2_yl176.msg.receiverMsgCmd;

import common.adapter.ICmd2ModelAdapter;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.ReceiverDataPacket;
import common.receiver.messages.IReceiverErrMsg;
import common.receiver.messages.IReceiverMsg;
import provided.datapacket.IDataPacketID;

/**
 * @author James Li
 *
 */
public class HeartDollarCommand extends AReceiverDataPacketAlgoCmd<IReceiverMsg>{

	@Override
	public Void apply(IDataPacketID index, ReceiverDataPacket<IReceiverMsg> host, Void... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		
	}

}
