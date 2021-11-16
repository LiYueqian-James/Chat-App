package hpn2_yl176.main_mvc.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.connector.ConnectorDataPacket;
import common.connector.ConnectorDataPacketAlgo;
import common.connector.IConnector;
import common.connector.INamedConnector;

/**
 * @author James Li
 *
 */
public class Connector implements IConnector{
	
	/**
	 * The visitor.
	 */
	private ConnectorDataPacketAlgo connectMsgVisitor;
	
	/**
	 * app config.
	 */
	private ChatAppConfig config;

	
	/**
	 * @param connectMsgVisitor the visitor to process the message.
	 * @param userName the user name of the chat app.
	 * @param config app config.
	 * 
	 */
	public Connector(ConnectorDataPacketAlgo connectMsgVisitor, ChatAppConfig config) {
		this.connectMsgVisitor = connectMsgVisitor;
		this.config = config;
	}

	@Override
	public void sendMessage(ConnectorDataPacket<?> packet) throws RemoteException {
		packet.execute(connectMsgVisitor, null);
		
	}

	@Override
	public INamedConnector makeNamedConnector() throws RemoteException {
		return new NamedConnector(this.config.getName(), this);
		
	}

}
