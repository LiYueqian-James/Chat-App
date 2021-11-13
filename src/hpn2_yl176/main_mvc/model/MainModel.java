/**
 * 
 */
package hpn2_yl176.main_mvc.model;

import java.rmi.registry.Registry;
import java.rmi.server.RemoteStub;
import java.util.HashMap;
import java.util.HashSet;

import common.connector.IConnector;
import common.receiver.IReceiver;
import hpn2_yl176.MiniFactory;
import hpn2_yl176.mini_mvc.IMini2MainAdptr;
import hpn2_yl176.mini_mvc.controller.MiniController;
import provided.logger.ILogEntry;
import provided.logger.ILogEntryFormatter;
import provided.logger.ILogEntryProcessor;
import provided.logger.ILogger;
import provided.logger.ILoggerControl;
import provided.logger.LogLevel;
import provided.logger.demo.model.IModel2ViewAdapter;
import provided.pubsubsync.IPubSubSyncChannelUpdate;
import provided.pubsubsync.IPubSubSyncManager;
import provided.rmiUtils.IRMIUtils;
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
	
	private ILogger sysLogger;
	
	public IMainModel2ViewAdpt model2ViewAdpt;
	
	public IPubSubSyncManager manager; 
	
	/**
	 * A logger that logs to the view and the system logger
	 */
	private ILogger viewLogger;
	
	
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
		viewLogger.append(sysLogger);
	}
	
	public MiniController makeController() {
		return MiniFactory.Singleton.make();
	}
	
	public void quit(int exitCode) {
		rmiUtils.stopRMI();
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
	
	public void addContact(String name) {
		
	}
	
	public void makeRoom(String roomName, IMini2MainAdptr mini2MainAdptr) {
		HashSet<IReceiver> roster = new HashSet<>();
		IPubSubSyncChannelUpdate<HashSet<IReceiver>> chatRoom = manager.createChannel(roomName, roster, null, 
				(statusMessage) -> {
					sendStatusMsg(other)
				});
	
	}
	
	public void leaveRoom(String roomname) {
		viewLogger.log(LogLevel.INFO, "Left room " + roomname);
		
	}
	
	public void joinRoom(String roomname) {
		
	}
}
