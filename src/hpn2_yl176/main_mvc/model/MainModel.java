/**
 * 
 */
package hpn2_yl176.main_mvc.model;

import java.awt.Component;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import common.connector.ConnectorDataPacket;
import common.connector.ConnectorDataPacketAlgo;
import common.connector.IConnector;
import common.connector.INamedConnector;
import common.connector.messages.IConnectorMsg;
import common.connector.messages.IInviteMsg;
import common.connector.messages.ISyncPeersMsg;
import common.receiver.INamedReceiver;
import common.receiver.IReceiver;
import hpn2_yl176.main_mvc.IMain2MiniAdptr;
import hpn2_yl176.msg.connectorMsgCmd.DefaultConnectMsgCmd;
import hpn2_yl176.msg.connectorMsgCmd.InviteMsgCmd;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;
import provided.logger.ILogEntry;
import provided.logger.ILogEntryFormatter;
import provided.logger.ILogEntryProcessor;
import provided.logger.ILogger;
import provided.logger.ILoggerControl;
import provided.logger.LogLevel;
import provided.pubsubsync.IPubSubSyncChannelUpdate;
import provided.pubsubsync.IPubSubSyncConnection;
import provided.pubsubsync.IPubSubSyncManager;
import provided.pubsubsync.IPubSubSyncUpdater;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.IRMI_Defs;
import provided.rmiUtils.RMIUtils;

/**
 * @author hungnguyen
 *
 */
public class MainModel {
	
	/**
	 * The IRMIUtils in use
	 */
	
	private IRMIUtils rmiUtils;
	
	/**
	 * The system logger to use.
	 */
	private ILogger sysLogger = ILoggerControl.makeLogger(LogLevel.DEBUG);
	
	/**
	 * Interaction with the main view
	 */
	private IMainModel2ViewAdpt model2ViewAdpt;
	
	/**
	 * Interaction with the mini controller
	 */
	private IMain2MiniAdptr main2miniAdptr;
	
	/**
	 * Manage the data channels.
	 */
	private IPubSubSyncManager pubSubManager; 
	
	/**
	 * A logger that logs to the view and the system logger
	 */
	private ILogger viewLogger;
	
	/**
	 * The connector representing the current chat app instance.
	 */
	private IConnector connector;
	
	/**
	 * The stub of the current chat app instance.
	 */
	private IConnector connectorStub;
	
	/**
	 * A list of chat app instance stubs/
	 */
	private List<INamedConnector> contacts;
	
	 /**
	  * The user name of the chat app instance.
	  */
	private String userName;
	
	/**
	 * The local Registry
	 */
	private Registry registry;
	
	/**
	 * The configuration of the chat app instance. 
	 */
	private ChatAppConfig chatAppConfig;
	
	private ConnectorDataPacketAlgo receiverVisitor;
	
	private Map<Component, IMain2MiniAdptr> panel2chatRoom;
	
	
	/**
	 * Constructor for the model.
	 * @param logger the logger being used by the pubsubSync manager.
	 * @param model2ViewAdpt interaction with the view.
	 * @param adptr interacting with the mini mvc.
	 */
	public MainModel(ILogger logger, IMainModel2ViewAdpt model2ViewAdpt) {
		this.sysLogger = logger;
		this.model2ViewAdpt = model2ViewAdpt;
		rmiUtils = new RMIUtils(logger);
		
		viewLogger = ILoggerControl.makeLogger(new ILogEntryProcessor() {
			ILogEntryFormatter formatter = ILogEntryFormatter.MakeFormatter("[%1s] %2s");

			@Override
			public void accept(ILogEntry logEntry) {
				MainModel.this.model2ViewAdpt.displayStatusMsg(formatter.apply(logEntry));
			}

		}, LogLevel.INFO);
		
		// Chain the system logger to the end of the view logger so that 
		// anything logged to the view also goes to the system log 
		// (default = to console).
		viewLogger.append(sysLogger);		
	}
	
	/**
	 * @return the user name.
	 */
	public String getUserName() {
		return this.userName;
	}
	
	public INamedConnector getNamedConnector() {
		try {
			return this.connector.makeNamedConnector();
		} catch (RemoteException e) {
			sysLogger.log(LogLevel.DEBUG, "Unable to make named connector!");
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Map<Component, IMain2MiniAdptr> getPanel2RoomMap(){
		return this.panel2chatRoom;
	}
	/**
	 * Join a chat room, used by the visitor.
	 * @param id the id of the chatRoom.
	 * @param roomname the name of the chat room.
	 */
	private void joinRoom(UUID id, String roomname) {
		IPubSubSyncChannelUpdate<HashSet<IReceiver>> chatRoom = pubSubManager.subscribeToUpdateChannel(id, null, null);
		chatRoom.update(IPubSubSyncUpdater.makeRemoteSetAddFn(localStub));
	}
	
	public String connectToStub(INamedConnector connectedStub) {
		connectedStub.sendMessage(new ConnectorDataPacket<ISyncPeersMsg>(new ISyncPeersMsg() {
			
			@Override
			public Set<INamedConnector> getNewPeers() {
				// TODO Auto-generated method stub
				return contacts;
		}}, namedConnector));
	}
	
	/**
	 * Set the visitor to process connection level messages.
	 */
	private void setConnectorMsgVisitor() {
		receiverVisitor = new ConnectorDataPacketAlgo(new DefaultConnectMsgCmd());
		
		receiverVisitor.setCmd(IInviteMsg.GetID(), new InviteMsgCmd(this.pubSubManager, this.main2miniAdptr.getNamedReceiver()));
		
		receiverVisitor.setCmd(ISyncPeersMsg.GetID(), new AConnectorDataPacketAlgoCmd<ISyncPeersMsg>() 
		});
		
		receiverVisitor.setCmd(DataPacketIDFactory.Singleton.makeID(IQuitMsg.class), new AConnectorDataPacketAlgoCmd<IQuitMsg>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Void apply(IDataPacketID index, ConnectorDataPacket<IQuitMsg> host, Void... params) {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		
		receiverVisitor.setCmd(null, null);
	};
	
	
	public void quit(int exitCode) {
		rmiUtils.stopRMI();
		//TODO: send a IQuitMessage to the chat rooms
		System.exit(exitCode);
		
	}

	
	/**
	 * Connect to another Chat app instance.
	 * @param remoteRegistryIPAddr the ip address to connect to.
	 * @param boundName the bound name of the chat app instance.
	 * @return a status message of the connection
	 */
	public String connectTo(String remoteRegistryIPAddr, String boundName) {
		//sysLogger.log(LogLevel.INFO, "HERE: " + remoteRegistryIPAddr);
		try {
			sysLogger.log(LogLevel.INFO, "Locating registry at " + remoteRegistryIPAddr + "...");
			Registry registry = rmiUtils.getRemoteRegistry(remoteRegistryIPAddr);
			sysLogger.log(LogLevel.INFO, "Found registry: " + registry);
			IConnector remoteStub = (IConnector) registry.lookup(boundName);
			//TODO: wrap around the stub in an INamedConnector and add to the list of contacts.
			sysLogger.log(LogLevel.INFO, "Found remote stub: ");

		} catch (Exception e) {
			sysLogger.log(LogLevel.ERROR, "Exception connecting to " + remoteRegistryIPAddr + ": " + e);
			e.printStackTrace();

			return "No connection established!";
		}
		return "Connection to " + remoteRegistryIPAddr + " established!";
	}
	
	/**
	 * Add the connected stub to the list of contacts
	 * @param contact the stub of a chat app instance.
	 */
	public void addContact(INamedConnector contact) {
		this.contacts.add(contact);
	}
	
//	/**
//	 * Make a chat room!
//	 * @param roomName the name of the room.
//	 */
//	public void makeRoom(String roomName) {
//		IMain2MiniAdptr miniController = this.model2ViewAdpt.make(roomName);
//		miniController.start();
//		this.model2ViewAdpt.addComponent(miniController.getRoomPanel());
//	}
	
	
	// I think this one is more like a cmd to process the invite message
//	public void joinRoom(UUID id, String roomname, IMini2MainAdptr mini2MainAdptr) {
//		IPubSubSyncChannelUpdate<HashSet<IReceiver>> chatRoom = pubSubManager.subscribeToUpdateChannel(id, null, null);
//		chatRoom.update(IPubSubSyncUpdater.makeRemoteSetAddFn(localStub));
//	}
	
	/**
	 * Start the RMI and create a pubsubManager.
	 */
	public void start() {
		// must start the rmi-utils first
		this.rmiUtils.startRMI(IRMI_Defs.CLASS_SERVER_PORT_CLIENT);
		// must be here rather than the constructor to ensure the RMI has already started
		try {
			pubSubManager = IPubSubSyncConnection.getPubSubSyncManager(this.sysLogger, this.rmiUtils, IRMI_Defs.CLASS_SERVER_PORT_CLIENT);
		} catch (RemoteException e) {
			this.sysLogger.log(LogLevel.DEBUG, "Failed to create a pubsubSync Manager.");
			e.printStackTrace();
		}
		
		// get the username from the view
		this.userName = this.model2ViewAdpt.getUserName();
		
		// make the Connector and the namedConnector
		this.connector = new IConnector() {

			@Override
			public void sendMessage(ConnectorDataPacket<?> packet) throws RemoteException {
				// The message received here should be in the connector/messages package
				// We need a visitor to deal with these different types of message
				// Regular visitor pattern should be enough since the set of message types is fixed
				packet.execute(receiverVisitor, null);
			}

			@Override
			public INamedConnector makeNamedConnector() throws RemoteException {
				return new INamedConnector() {

					/**
					 * The serial version UID
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public String getName() {
						return userName;
					}

					@Override
					public IConnector getStub() {
						return connectorStub;
					}
					
					public int hashcode() {
						return this.getStub().hashCode();
					}
					
					public boolean equals(Object obj) {
						if (obj instanceof INamedConnector) {
							return this.getStub().equals(obj);
						}
						return false;
					}
					
					public String toString() {
						return this.getName();
					}
					
				};
			}};
		
		// Bind the connector to the discovery server
		try {
			registry = rmiUtils.getLocalRegistry();
			viewLogger.log(LogLevel.INFO, "Local Registry = " + registry);
		} catch (Exception e) {
			viewLogger.log(LogLevel.ERROR, "Exception while intializing RMI: " + e);
			e.printStackTrace();
			quit(-1); // exit the program.
		}

		try {

			// Create a UnicastRemoteObject stub from the RMI Server implementation to be sent to the clients.
			this.connectorStub = (IConnector) UnicastRemoteObject.exportObject(this.connector,
					chatAppConfig.getPort());
			
			
			// Bind the remote object's stub in the registry at the specified
			// port use rebind instead of bind so the program can be run
			// multiple times with the same registry

			registry.rebind(this.userName, this.connectorStub);
			viewLogger.log(LogLevel.INFO, "Chat app sucessfully binded to the discovery server!");

		} catch (Exception e) {
			viewLogger.log(LogLevel.ERROR, "Server exception: " + e.toString());
			e.printStackTrace();
			quit(-1); // exit the program.
		}
		this.addContact(getNamedConnector());
		
	}
	
	
}
