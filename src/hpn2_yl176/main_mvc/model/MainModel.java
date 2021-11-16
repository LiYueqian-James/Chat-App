/**
 * 
 */
package hpn2_yl176.main_mvc.model;

import java.awt.Component;


import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
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
import hpn2_yl176.msg.connectorMsgImpl.SyncPeersMsg;
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
	private Set<INamedConnector> myContacts = new HashSet<>();
	
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
	private INamedConnector myNamedConnector;
	
	
	/**
	 * Constructor for the model.
	 * @param logger the logger being used by the pubsubSync manager.
	 * @param model2ViewAdpt interaction with the view.
	 * @param chatAppConfig the chat app configuration.
	 */
	public MainModel(ILogger logger, IMainModel2ViewAdpt model2ViewAdpt, ChatAppConfig chatAppConfig) {
		this.sysLogger = logger;
		this.model2ViewAdpt = model2ViewAdpt;
		this.chatAppConfig = chatAppConfig;
		this.connectoMsgVisitor = new ConnectorDataPacketAlgo(new DefaultConnectorMsgCmd(this.sysLogger));
		
		
		rmiUtils = new RMIUtils(logger);
		
		// The view logger will display itself through the main view.
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
	 * Set the visitor to process connection level messages.
	 */
	private void startConnectorMsgVisitor() {
		
//		connectoMsgVisitor.setCmd(IInviteMsg.GetID(), new InviteMsgCmd(this.pubSubManager, this.main2miniAdptr.getNamedReceiver()));
		
		connectoMsgVisitor.setCmd(ISyncPeersMsg.GetID(), new SyncPeersMsgCmd(this.myContacts, this.getNamedConnector(), this.sysLogger, this.model2ViewAdpt));
		
		connectoMsgVisitor.setCmd(IAddPeersMsg.GetID(), new AddPeersMsgCmd(this.myContacts, this.model2ViewAdpt));
		
		connectoMsgVisitor.setCmd(IQuitMsg.GetID(), new QuitMsgCmd(this.model2ViewAdpt, this.myContacts));
	};
	
	/**
	 * @return the name and the stub in a dyad.
	 */
	public INamedConnector getNamedConnector() {
		return this.myNamedConnector;
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
	
	/**
	 * @return my contacts.
	 */
	public Set<INamedConnector> getContacts(){
		return this.myContacts;
	}
		
	/**
	 * @param connectedStub the stub we got from the registry
	 */
	public void connectToStub(INamedConnector connectedStub) {
		Thread t = new Thread(()->{
			try {
				connectedStub.sendMessage(new ConnectorDataPacket<ISyncPeersMsg>(new SyncPeersMsg(this.myContacts), this.myNamedConnector));
			} catch (RemoteException e) {
				sysLogger.log(LogLevel.DEBUG, "Failed to send sync peers message to the remote stub.");
				e.printStackTrace();
			}
		});
		t.start();
	}
		
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
		ConnectorDataPacket<IQuitMsg> quitData = new ConnectorDataPacket<>(quitMsg, this.myNamedConnector);
		Set<INamedConnector> prevContact = new HashSet<>(this.myContacts);
		
		// Tell everyone I know(including myself) that I have quit!
		// A copy is necessary to avoid concurrent modification.
		for (INamedConnector app: prevContact) {
			try {
				app.sendMessage(quitData);
			} catch (RemoteException e) {
				sysLogger.log(LogLevel.ERROR, "Failed to send quit msg.");
				e.printStackTrace();
			}
		}
//		System.exit(exitCode);
		
	}

	
	/**
	 * Connect to another Chat app instance.
	 * @param remoteRegistryIPAddr the ip address to connect to.
	 * @param boundName the bound name of the chat app instance.
	 * @return a status message of the connection
	 */
	public String connectTo(String remoteRegistryIPAddr, String boundName) {
		try {
			sysLogger.log(LogLevel.INFO, "Locating registry at " + remoteRegistryIPAddr + "...");
			Registry registry = rmiUtils.getRemoteRegistry(remoteRegistryIPAddr);
			sysLogger.log(LogLevel.INFO, "Found registry: " + registry);
			
			// Wrap the stub inside a namedConnector and add it to our contact.
			IConnector remoteStub = (IConnector) registry.lookup(boundName);
			this.connectToStub(new NamedConnector(boundName, remoteStub));
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
		this.myContacts.add(contact);
		this.model2ViewAdpt.updateContacts(myContacts);
	}
	
	
	/**
	 * Start the RMI and create a pubsubManager.
	 */
	public void start() {
		// must start the rmi-utils first
		this.rmiUtils.startRMI(this.chatAppConfig.getClassPort());
		
		// must be here rather than the constructor to ensure the RMI has already started
		try {
			pubSubManager = IPubSubSyncConnection.getPubSubSyncManager(this.sysLogger, this.rmiUtils, this.chatAppConfig.getRMIPort());
		} catch (RemoteException e) {
			this.sysLogger.log(LogLevel.ERROR, "Failed to create a pubsubSync Manager.");
			e.printStackTrace();
			System.exit(1);
		}
		
		// get the username from the view
		this.userName = this.model2ViewAdpt.getUserName();
		
		// make the Connector and the namedConnector
		this.myConnector = new Connector(connectoMsgVisitor, chatAppConfig);
		try {
			this.myConnectorStub = (IConnector) UnicastRemoteObject.exportObject(this.myConnector,
					chatAppConfig.getRMIPort());
		} catch (RemoteException e1) {
			sysLogger.log(LogLevel.ERROR, "Unable to make connector stub!");
			e1.printStackTrace();
		}
		
		try {
			this.myNamedConnector = myConnectorStub.makeNamedConnector(); 
			this.addContact(this.myNamedConnector);
			
		} catch (RemoteException e) {
			sysLogger.log(LogLevel.ERROR, "Unable to make named connector!");
			e.printStackTrace();
			System.exit(1);
		}
		
		// Bind the connector to the discovery server
		try {
			registry = rmiUtils.getLocalRegistry();
			viewLogger.log(LogLevel.INFO, "Local Registry = " + registry);
		} catch (Exception e) {
			viewLogger.log(LogLevel.ERROR, "Exception while intializing RMI: " + e);
			System.out.println("here");
			e.printStackTrace();
			quit(-1); // exit the program.
		}

		try {
			// Create a UnicastRemoteObject stub from the RMI Server implementation to be sent to the clients.
			this.myConnectorStub = this.myNamedConnector.getStub();
			
			// Bind the remote object's stub in the registry at the specified
			// port use rebind instead of bind so the program can be run
			// multiple times with the same registry
			registry.rebind(this.chatAppConfig.getName(), this.myConnectorStub);
			viewLogger.log(LogLevel.INFO, this.chatAppConfig.getName()+" sucessfully binded to the discovery server!");

		} catch (Exception e) {
			viewLogger.log(LogLevel.ERROR, "Failed to bind to the discovery server");
			e.printStackTrace();
			quit(-1); // exit the program.
		}
		
		this.model2ViewAdpt.updateContacts(myContacts);
		
		this.startConnectorMsgVisitor();
	}

	/**
	 * @return the rmi instance.
	 */
	public IRMIUtils getRMIUtils() {
		return this.rmiUtils;
	}
	
	
}
