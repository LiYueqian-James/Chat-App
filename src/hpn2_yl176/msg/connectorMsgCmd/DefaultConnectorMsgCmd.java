/**
 * 
 */
package hpn2_yl176.msg.connectorMsgCmd;
import common.connector.AConnectorDataPacketAlgoCmd;
import common.connector.ConnectorDataPacket;
import common.connector.INamedConnector;
import common.connector.messages.IConnectorMsg;
import common.connector.messages.IInviteMsg;
import provided.datapacket.IDataPacketID;

/**
 * @author James Li
 *
 */
public abstract class DefaultConnectorMsgCmd extends AConnectorDataPacketAlgoCmd<IConnectorMsg>{

	/**
	 * Serialization purpose.
	 */
	private static final long serialVersionUID = -5925845596790725503L;
	
	/**
	 * Sender of the msg.
	 */
	protected INamedConnector sender;
	
	/**
	 * Construct a connector msg cmd.
	 * @param sender the sender of the msg.
	 */
	public DefaultConnectorMsgCmd(INamedConnector sender) {
		this.sender = sender;
	}
	
	@Override
	public Void apply(IDataPacketID index, ConnectorDataPacket<IConnectorMsg> host, Void... params) {
		return null;
	}

}
