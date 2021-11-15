/**
 * 
 */
package hpn2_yl176.msg.connectorMsgCmd;

import common.connector.AConnectorDataPacketAlgoCmd;
import common.connector.ConnectorDataPacket;
import common.connector.messages.IInviteMsg;
import provided.datapacket.IDataPacketID;

/**
 * @author James Li
 *
 */
public class DefaultConnectMsgCmd extends AConnectorDataPacketAlgoCmd<IInviteMsg>{

	/**
	 * serialization purpose.
	 */
	private static final long serialVersionUID = 2866083490358496941L;

	@Override
	public Void apply(IDataPacketID index, ConnectorDataPacket<IInviteMsg> host, Void... params) {
		return null;
	}

}
