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
	 * serialization.
	 */
	private static final long serialVersionUID = -958601882847635293L;
	/**
	 * the cmd
	 */
	private AReceiverDataPacketAlgoCmd<?> cmd;
	/**
	 * the id
	 */
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


}
