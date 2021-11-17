/**
 * 
 */
package hpn2_yl176.msg.receiverMsgCmd;

import common.adapter.ICmd2ModelAdapter;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.ReceiverDataPacket;
import common.receiver.messages.IReceiverErrMsg;
import provided.datapacket.IDataPacketID;

/**
 * @author hungnguyen
 *
 */
public class ErrorMsgCmd extends AReceiverDataPacketAlgoCmd<IReceiverErrMsg>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2024369332423460763L;

	@Override
	public Void apply(IDataPacketID index, ReceiverDataPacket<IReceiverErrMsg> host, Void... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
	}
}
