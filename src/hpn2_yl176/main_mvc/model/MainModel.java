/**
 * 
 */
package hpn2_yl176.main_mvc.model;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.HashSet;
import java.util.List;

import common.connector.ConnectorDataPacket;
import common.connector.IConnector;
import common.connector.INamedConnector;
import common.receiver.IReceiver;
import hpn2_yl176.main_mvc.IMain2MiniAdptr;
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
	 * Manage the data channels.
	 */
	private IPubSubSyncManager pubSubManager; 
	
	public IConnector otherStub;
	
	/**
	 * A logger that logs to the view and the system logger
	 */
	private ILogger viewLogger;
	
	/**
	 * The connector representing the current chat app instance.
	 */
	private IConnector connector;
	
	/**
	 * A dyad containing infos about the current chat app instance.
	 */
	private INamedConnector namedChatApp;
	
	/**
	 * A list of chat app instance stubs/
	 */
	private List<INamedConnector> contacts;
	
	
	
	
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
				MainModel.this.model2ViewAdpt.displayMsg(formatter.apply(logEntry));
			}

		}, LogLevel.INFO);
		
		// Chain the system logger to the end of the view logger so that 
		// anything logged to the view also goes to the system log 
		// (default = to console).
		viewLogger.append(sysLogger);
		
		// make the connector and named connector
		this.connector = new IConnector() {

			@Override
			public void sendMessage(ConnectorDataPacket<?> packet) throws RemoteException {
				// The message received here should be in the connector/messages package
				// We need a visitor to deal with these different types of message
				// Regular visitor pattern should be enough since the set of message types is fixed
				
			}

			@Override
			public INamedConnector makeNamedConnector() throws RemoteException {
				// TODO Auto-generated method stub
				return null;
			}};
		
		// 
		
	}
	
	/**
	 * Make a mini-contoller
	 * @return An adapter to that instance of the controller.
	 */
	public IMain2MiniAdptr makeMiniController() {
		return this.model2ViewAdpt.make();
	}
	
	public void quit(int exitCode) {
		rmiUtils.stopRMI();
		//TODO: send a IQuitMessage to the chat rooms
		System.exit(exitCode);
		
	}
	
	public String connectTo(String remoteRegistryIPAddr, String boundName) {
		//sysLogger.log(LogLevel.INFO, "HERE: " + remoteRegistryIPAddr);
		try {
			sysLogger.log(LogLevel.INFO, "Locating registry at " + remoteRegistryIPAddr + "...");
			Registry registry = rmiUtils.getRemoteRegistry(remoteRegistryIPAddr);
			sysLogger.log(LogLevel.INFO, "Found registry: " + registry);
			IConnector remoteStub = (IConnector) registry.lookup(boundName);
//			for (connection: remoteStub.getContacts()) {
//				this.addContact(String);
//			}
			
			sysLogger.log(LogLevel.INFO, "Found remote stub: ");
//			this.processStub(remoteStub);

		} catch (Exception e) {
			sysLogger.log(LogLevel.ERROR, "Exception connecting to " + remoteRegistryIPAddr + ": " + e);
			e.printStackTrace();

			return "No connection established!";
		}
		return "Connection to " + remoteRegistryIPAddr + " established!";
	}
	
	/**
	 * Add the 
	 * @param contact
	 */
	public void addContact(INamedConnector contact) {
		this.contacts.add(contact);
	}
	
	/**
	 * Make a chat room!
	 * @param roomName the name of the room.
	 */
	public void makeRoom(String roomName) {
		HashSet<IReceiver> roster = new HashSet<>();
		IPubSubSyncChannelUpdate<HashSet<IReceiver>> chatRoom = pubSubManager.createChannel(roomName, roster, null, 
				(statusMessage) -> {
					sysLogger.log(LogLevel.DEBUG, "room " + roomName +" has been made sucessfully.");
				});
		IMain2MiniAdptr miniController = this.makeMiniController();
		
		// add the current stub of the room to the data channel
		chatRoom.update(IPubSubSyncUpdater.makeRemoteSetAddFn(miniController.getNamedReceiver().getStub()));
		this.model2ViewAdpt.addComponent(miniController.getRoomPanel());
	}
	
	public void leaveRoom(String roomname) {
		viewLogger.log(LogLevel.INFO, "Left room " + roomname);
		//TODO: remove the panel from the view
	}
	
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
		
	}
	
//	public void 
	// I moved this part to the controller based on hw7
//	public void connectToDiscoveryServer(String category, Consumer<Iterable<IEndPointData>> endPointsUpdateFn) {
////		try {
////			discover
////		}
//	}
	
	
}
