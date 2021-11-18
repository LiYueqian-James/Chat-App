package hpn2_yl176.msg.receiverMsgImpl;

import common.receiver.messages.IReceiverMsg;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * @author James Li
 *
 */
public class TabMsg implements IReceiverMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4106457338459860895L;
	
	/**
	 * Makes an id for this message type
	 * @return the data packet id of this message type
	 */
	
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(TabMsg.class);
	}

	/**
	 * Get the data packet ID associated with an instance of this class.
	 * 
	 * @return The data packet ID.
	 */
	@Override
	public IDataPacketID getID() {
		return TabMsg.GetID();
	}

}
