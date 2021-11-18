/**
 * 
 */
package hpn2_yl176.msg.connectorMsgImpl;

import java.util.UUID;

import common.connector.messages.IInviteMsg;

/**
 * @author James Li
 *
 */
public class InviteMsg implements IInviteMsg {

	/**
	 * Serialization purpose.
	 */
	private static final long serialVersionUID = -6919276183053373640L;

	/**
	 * The room ID; 
	 */
	private UUID roomID;

	/**
	 * The room name. 
	 */
	private String roomName;

	/**
	 * @param roomID the room Id.
	 * @param roomName the room name.
	 */
	public InviteMsg(UUID roomID, String roomName) {
		this.roomID = roomID;
		this.roomName = roomName;
	}

	@Override
	public UUID getUUID() {
		return this.roomID;
	}

	@Override
	public String getFriendlyName() {
		return this.roomName;
	}

}
