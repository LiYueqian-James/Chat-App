/**
 * 
 */
package hpn2_yl176.msg.receiverMsgCmd;

import common.adapter.ICmd2ModelAdapter;
import common.connector.AConnectorDataPacketAlgoCmd;
import common.connector.ConnectorDataPacket;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.ReceiverDataPacket;
import common.receiver.ReceiverDataPacketAlgo;
import common.receiver.messages.IStringMsg;
import hpn2_yl176.mini_mvc.model.IMini2ViewAdptr;
import provided.datapacket.IDataPacketID;

/**
 * @author hungnguyen
 *
 */
public class StringMsgCmd extends AReceiverDataPacketAlgoCmd<IStringMsg> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5121721703851699016L;
	
	/**
	 * Ad
	 */
	private IMini2ViewAdptr adptr;

	public StringMsgCmd(IMini2ViewAdptr adptr) {
		this.adptr = adptr;
	}

	@Override
	public Void apply(IDataPacketID index, ReceiverDataPacket<IStringMsg> host, Void... params) {
		// TODO Auto-generated method stub
		Thread thread = new Thread(() -> {
			this.adptr.displayMsg(host.getSender().getName() + ": " + host.getData().getString() + "\n");
		});
		thread.start();
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub

	}
}
