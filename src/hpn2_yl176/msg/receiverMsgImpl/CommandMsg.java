/**
 * 
 */
package hpn2_yl176.msg.receiverMsgImpl;

import java.util.UUID;

import common.adapter.ICmd2ModelAdapter;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.messages.ICommandMsg;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;

/**
 * @author hungnguyen
 *
 */
public class CommandMsg implements ICommandMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -958601882847635293L;
	private AReceiverDataPacketAlgoCmd<?> cmd;
	private IDataPacketID cmdId;

	/**
	 * @param roomID the room Id.
	 * @param roomName the room name.
	 */
	public CommandMsg(AReceiverDataPacketAlgoCmd<?> cmd, IDataPacketID cmdId) {
		this.cmd = cmd;
		this.cmdId = cmdId;
	}

	@Override
	public AReceiverDataPacketAlgoCmd<?> getCmd() {
		// TODO Auto-generated method stub
		return this.cmd;
	}

	@Override
	public IDataPacketID getCmdID() {
		// TODO Auto-generated method stub
		return this.cmdId;
	}
	
	/**
	 * Get the data packet ID associated with this class.
	 * 
	 * @return The data packet ID.
	 */
	public static IDataPacketID GetID() {
		return DataPacketIDFactory.Singleton.makeID(CommandMsg.class);
	}
	
	/**
	 * Get the data packet ID associated with an instance of this class.
	 * 
	 * @return The data packet ID.
	 */
	@Override
	public IDataPacketID getID() {
		return CommandMsg.GetID();
	}

}
