package hpn2_yl176.msg.receiverMsgImpl;

import common.adapter.ICmd2ModelAdapter;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.ReceiverDataPacket;
import common.receiver.messages.ICommandMsg;
import common.receiver.messages.IReceiverMsg;
import hpn2_yl176.msg.receiverMsgCmd.CommandRequestMsgCmd;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * @author James Li
 *
 */
public class HeartMessage implements IReceiverMsg{

	/**
	 * Serialization purpose.
	 */
	private static final long serialVersionUID = 8183288106602993218L;
	
	
	/**
	 * Get the data packet ID associated with this class.
	 * 
	 * @return The data packet ID.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(HeartMessage.class);
	}
	
	/**
	 * Get the data packet ID associated with an instance of this class.
	 * 
	 * @return The data packet ID.
	 */
	@Override
	public IDataPacketID getID() {
		return HeartMessage.GetID();
	}
}
