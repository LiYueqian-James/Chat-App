/**
 * 
 */
package hpn2_yl176.msg.receiverMsgCmd;

import common.adapter.ICmd2ModelAdapter;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.ReceiverDataPacket;
import common.receiver.messages.IReceiverMsg;
import provided.datapacket.IDataPacketID;

/**
 * @author hungnguyen
 *
 */
public class DefaultReceiverMsgCmd extends AReceiverDataPacketAlgoCmd<IReceiverMsg>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2325284822657390621L;

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
