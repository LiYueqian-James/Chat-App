/**
 * 
 */
package hpn2_yl176.mini_mvc.model;

import java.util.HashMap;
import java.util.UUID;

import common.receiver.AReceiverDataPacketAlgoCmd;
import provided.datapacket.IDataPacketID;

/**
 * @author hungnguyen
 *
 */
public class ChatRoom {
	
	
	public HashMap<IDataPacketID, AReceiverDataPacketAlgoCmd<?>> commands; 
	private UUID id;
	private String friendlyName;
	private IMini2ViewAdptr adptr;
	
	
	public ChatRoom(UUID id, String friendlyName, IMini2ViewAdptr adptr) {
		this.id = id;
		this.friendlyName = friendlyName;
		this.adptr = adptr;
	}
	
	public UUID getID() {
		return this.id;
	}
	
	public String getFriendlyName() {
		return this.friendlyName;
	}
	
	public void leave() {
		
	};
	
	
}
