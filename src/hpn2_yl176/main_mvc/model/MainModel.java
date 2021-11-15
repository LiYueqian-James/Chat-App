/**
 * 
 */
package hpn2_yl176.main_mvc.model;

import java.awt.Component;


import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Set;
import common.connector.ConnectorDataPacket;
import common.connector.ConnectorDataPacketAlgo;
import common.connector.IConnector;
import common.connector.INamedConnector;
import common.connector.messages.IAddPeersMsg;
import common.connector.messages.IInviteMsg;
import common.connector.messages.IQuitMsg;
import common.connector.messages.ISyncPeersMsg;
import hpn2_yl176.main_mvc.IMain2MiniAdptr;
import hpn2_yl176.msg.connectorMsgCmd.AddPeersMsgCmd;
import hpn2_yl176.msg.connectorMsgCmd.DefaultConnectorMsgCmd;
import hpn2_yl176.msg.connectorMsgCmd.InviteMsgCmd;
import hpn2_yl176.msg.connectorMsgCmd.QuitMsgCmd;
import hpn2_yl176.msg.connectorMsgCmd.SyncPeersMsgCmd;
import hpn2_yl176.msg.connectorMsgImpl.QuitMsg;
import provided.logger.ILogEntry;
import provided.logger.ILogEntryFormatter;
import provided.logger.ILogEntryProcessor;
import provided.logger.ILogger;
import provided.logger.ILoggerControl;
import provided.logger.LogLevel;
import provided.pubsubsync.IPubSubSyncConnection;
import provided.pubsubsync.IPubSubSyncManager;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.IRMI_Defs;
import provided.rmiUtils.RMIUtils;

/**
 * @author James Li
 *
 */
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
	private IConnector myConnector;
	
	/**
	 * The stub of the current chat app instance.
	 */
	private IConnector myConnectorStub;
	
	/**
	 * A set of chat app instance stubs/
	 */
	private Set<INamedConnector> myContacts;
	
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
	
	/**
	 * Visitor to process messages on the connection level.
	 */
	private ConnectorDataPacketAlgo connectoMsgVisitor;
	
	/**
	 * Map a panel to the chat room, used to get information about the current chat room.
	 */
	private Map<Component, IMain2MiniAdptr> panel2chatRoom;
	
	/**
	 * A dyad containing the name and the stub of the current chat app.
	 */
	private INamedConnector myMamedConnector;
	
	
	/**
	 * Constructor for the model.
	 * @param logger the logger being used by the pubsubSync manager.
	 * @param model2ViewAdpt interaction with the view.
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
		this.setConnectorMsgVisitor();
	}
	
	/**
	 * Set the visitor to process connection level messages.
	 */
	private void setConnectorMsgVisitor() {
		connectoMsgVisitor = new ConnectorDataPacketAlgo(new DefaultConnectorMsgCmd(this.sysLogger));
		
		connectoMsgVisitor.setCmd(IInviteMsg.GetID(), new InviteMsgCmd(this.pubSubManager, this.main2miniAdptr.getNamedReceiver()));
		
		connectoMsgVisitor.setCmd(ISyncPeersMsg.GetID(), new SyncPeersMsgCmd(this.myContacts, this.getNamedConnector(), this.sysLogger));
		
		connectoMsgVisitor.setCmd(IAddPeersMsg.GetID(), new AddPeersMsgCmd(this.myContacts));
		
		connectoMsgVisitor.setCmd(IQuitMsg.GetID(), new QuitMsgCmd(this.model2ViewAdpt, this.myContacts));
		
		connectoMsgVisitor.setCmd(null, null);
	};
	
	/**
	 * @return the name and the stub in a dyad.
	 */
	public INamedConnector getNamedConnector() {
		return this.myMamedConnector;
	}
	
	/**
	 * @return the user name.
	 */
	public String getUserName() {
		return this.userName;
	}

	
	/**
	 * @return the panel to room mapping.
	 */
	public Map<Component, IMain2MiniAdptr> getPanel2RoomMap(){
		return this.panel2chatRoom;
	}
	
	// this part has been moved to InviteMsgCmd class
//	/**
//	 * Join a chat room, used by the visitor.
//	 * @param id the id of the chatRoom.
//	 * @param roomname the name of the chat room.
//	 */
//	private void joinRoom(UUID id, String roomname) {
//		IPubSubSyncChannelUpdate<HashSet<IReceiver>> chatRoom = pubSubManager.subscribeToUpdateChannel(id, null, null);
//		chatRoom.update(IPubSubSyncUpdater.makeRemoteSetAddFn(localStub));
//	}
	
//	public String connectToStub(INamedConnector connectedStub) {
//		connectedStub.sendMessage(new ConnectorDataPacket<ISyncPeersMsg>(new ISyncPeersMsg() {
//			
//			@Override
//			public Set<INamedConnector> getNewPeers() {
//				// TODO Auto-generated method stub
//				return contacts;
//		}}, namedConnector));
//	}
	

	
	
	/**
	 * Quit the entire chat app.
	 * @param exitCode the exit code.
	 */
	public void quit(int exitCode) {
		rmiUtils.stopRMI();
		/*
		 * This is intentionally implemented as SYNCHROUNOUS as the app needs to finish sending the msg
		 * before it can actually quit.
		 */
		QuitMsg quitMsg = new QuitMsg();
		ConnectorDataPacket<IQuitMsg> quitData = new ConnectorDataPacket<>(quitMsg, this.myMamedConnector);
		
		// Tell everyone I know(including myself) that I have quit!
		for (INamedConnector app: this.myContacts) {
			try {
				app.sendMessage(quitData);
			} catch (RemoteException e) {
				sysLogger.log(LogLevel.DEBUG, "Failed to send quit msg.");
				e.printStackTrace();
			}
		}
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
	
//	/**
//	 * Add the connected stub to the list of contacts
//	 * @param contact the stub of a chat app instance.
//	 */
//	public void addContact(INamedConnector contact) {
//		this.contacts.add(contact);
//	}
	
//	/**
//	 * Make a chat room!
//	 * @param roomName the name of the room.
//	 */
//	public void makeRoom(String roomName) {
//		IMain2MiniAdptr miniController = this.model2ViewAdpt.make(roomName);
//		miniController.start();
//		this.model2ViewAdpt.addComponent(miniController.getRoomPanel());
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
		this.myConnector = new IConnector() {

			@Override
			public void sendMessage(ConnectorDataPacket<?> packet) throws RemoteException {
				// The message received here should be in the connector/messages package
				// We need a visitor to deal with these different types of message
				// Regular visitor pattern should be enough since the set of message types is fixed
				packet.execute(connectoMsgVisitor, null);
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
						return myConnectorStub;
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
			this.myConnectorStub = (IConnector) UnicastRemoteObject.exportObject(this.myConnector,
					chatAppConfig.getPort());
			
			
			// Bind the remote object's stub in the registry at the specified
			// port use rebind instead of bind so the program can be run
			// multiple times with the same registry
			registry.rebind(this.userName, this.myConnectorStub);
			viewLogger.log(LogLevel.INFO, "Chat app sucessfully binded to the discovery server!");

		} catch (Exception e) {
			viewLogger.log(LogLevel.ERROR, "Server exception: " + e.toString());
			e.printStackTrace();
			quit(-1); // exit the program.
		}
		
		INamedConnector namedConnector;
		
			try {
				namedConnector = myConnector.makeNamedConnector(); 
				this.myContacts.add(namedConnector);
				this.myMamedConnector = namedConnector;
			} catch (RemoteException e) {
				sysLogger.log(LogLevel.DEBUG, "Unable to make named connector!");
				e.printStackTrace();
//				return;
			}
			
			
	}
	
	
}
