/**
 * 
 */
package hpn2_yl176.msg.connectorMsgCmd;
import common.connector.AConnectorDataPacketAlgoCmd;
import common.connector.ConnectorDataPacket;
import common.connector.messages.IConnectorMsg;
import provided.datapacket.IDataPacketID;
import provided.logger.ILogger;
import provided.logger.LogLevel;

/**
 * @author James Li
 *
 */
public class DefaultConnectorMsgCmd extends AConnectorDataPacketAlgoCmd<IConnectorMsg>{

	/**
	 * Serialization purpose.
	 */
	private static final long serialVersionUID = -5925845596790725503L;
	
	/**
	 * The system logger.
	 */
	private ILogger sysLogger;
	
	/**
	 * Construct a connector msg cmd.
	 * @param sysLogger the logger
	 */
	public DefaultConnectorMsgCmd(ILogger sysLogger) {
		this.sysLogger = sysLogger;
		
	}
		
	@Override
	public Void apply(IDataPacketID index, ConnectorDataPacket<IConnectorMsg> host, Void... params) {
		sysLogger.log(LogLevel.DEBUG, "Unknown connector msg cmd received!");
		return null;
	}

}
