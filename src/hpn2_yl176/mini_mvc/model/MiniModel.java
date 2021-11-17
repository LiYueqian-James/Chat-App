package hpn2_yl176.mini_mvc.model;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import common.adapter.ICmd2ModelAdapter;
import common.adapter.IComponentFactory;
import common.connector.AConnectorDataPacketAlgoCmd;
import common.connector.INamedConnector;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.INamedReceiver;
import common.receiver.IReceiver;
import common.receiver.ReceiverDataPacket;
import common.receiver.ReceiverDataPacketAlgo;
import common.receiver.messages.ICommandMsg;
import common.receiver.messages.ICommandRequestMsg;
import common.receiver.messages.IReceiverMsg;
import common.receiver.messages.IStringMsg;
import controller.BallWorldController;
import hpn2_yl176.main_mvc.IMain2MiniAdptr;
import hpn2_yl176.main_mvc.model.ChatAppConfig;
import hpn2_yl176.mini_mvc.view.ChatRoomView;
import hpn2_yl176.msg.receiverMsgCmd.CommandRequestMsgCmd;
import hpn2_yl176.msg.receiverMsgCmd.DefaultReceiverMsgCmd;
import hpn2_yl176.msg.receiverMsgCmd.StringMsgCmd;
import hpn2_yl176.msg.receiverMsgImpl.CommandMsg;
import hpn2_yl176.msg.receiverMsgImpl.StringMsg;
import provided.datapacket.DataPacketIDFactory;
import provided.datapacket.IDataPacketID;
import provided.logger.ILogEntry;
import provided.logger.ILogEntryFormatter;
import provided.logger.ILogEntryProcessor;
import provided.logger.ILogger;
import provided.logger.ILoggerControl;
import provided.logger.LogLevel;
import provided.mixedData.MixedDataDictionary;
import provided.mixedData.MixedDataKey;
import provided.pubsubsync.IPubSubSyncChannelUpdate;
import provided.pubsubsync.IPubSubSyncConnection;
import provided.pubsubsync.IPubSubSyncManager;
import provided.pubsubsync.IPubSubSyncUpdater;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.RMIUtils;
/**
 * @author James Li
 *
 */
public class MiniModel {

	private IMini2ViewAdptr adptr;
	
	private ReceiverDataPacketAlgo receiverVisitor;
	
	private Set<INamedReceiver> roomRoster;
	
	private MixedDataDictionary mixedDictionary;
	
	private ICmd2ModelAdapter cmd2ModelAdapter = new ICmd2ModelAdapter() {
		
		@Override
		public <T extends IReceiverMsg> void send(T data, INamedReceiver recv) {
			// TODO Auto-generated method stub
		
		}
		
		@Override
		public File saveFile(String defaultName) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <T> T put(MixedDataKey<T> key, T value) {
			// TODO Auto-generated method stub
			return mixedDictionary.put(key, value);
		}
		
		@Override
		public String getRoomName() {
			// TODO Auto-generated method stub
			return getRoomName();
		}
		
		@Override
		public String getInstanceName() {
			// TODO Auto-generated method stub
			return adptr.getUserName();
		}
		
		@Override
		public File getFile() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public <T> T get(MixedDataKey<T> key) {
			// TODO Auto-generated method stub
			return mixedDictionary.get(key);
		}
		
		@Override
		public boolean containsKey(MixedDataKey<?> key) {
			// TODO Auto-generated method stub
			return mixedDictionary.containsKey(key);
		}
		
		@Override
		public void buildScrollingComponent(IComponentFactory fac, String name) {
			// TODO Auto-generated method stub
//			adptr.addScrollingComponent(fac, name);
		}
		
		@Override
		public void buildFixedComponent(IComponentFactory fac, String name) {
			// TODO Auto-generated method stub
//			adptr.addFixedComponent(fac, name);
		}
		
		@Override
		public <T extends IReceiverMsg> void broadcast(T msg) {
			// TODO Auto-generated method stub
//			ReceiverDataPacket<T> dataPacket = new ReceiverDataPacket<MiniModel>(msg, namedReceiver)
		}
	};
	
	private ILogger viewLogger;
	private ILogger sysLogger;
	
	private IReceiver myReceiver;
	private INamedReceiver myNamedReceiver;
	
//	private ChatRoom chatRoom;
	
	private ChatAppConfig config;
	/**
	 * @param adptr the adapter
	 */
	public MiniModel(UUID id, String friendlyName, IMini2ViewAdptr adptr) {
		this.adptr = adptr;
		
		// Logger to log the status to the console.
		sysLogger = adptr.getSysLogger();
		viewLogger = ILoggerControl.makeLogger(new ILogEntryProcessor() {
			ILogEntryFormatter formatter = ILogEntryFormatter.MakeFormatter("[%1s] %2s");
			@Override
			public void accept(ILogEntry logEntry) {
				// TODO Auto-generated method stub
				MiniModel.this.adptr.displayMsg(formatter.apply(logEntry));
			}
		}, LogLevel.INFO);
		viewLogger.append(sysLogger);
		
		config = adptr.getConfig();
		
		// room information
		this.roomRoster = adptr.getRoomRoster();
		receiverVisitor = new ReceiverDataPacketAlgo(new DefaultReceiverMsgCmd());
		this.myReceiver = new Receiver(this.receiverVisitor);
		this.adptr = adptr;
		
		try {
			IReceiver receiverStub = (IReceiver) UnicastRemoteObject.exportObject(this.myReceiver, config.getRMIPort());
			this.myNamedReceiver = new NamedReceiver(receiverStub, this.adptr.getUserName(), this.adptr.getNamedConnector());			
		}
		catch (Exception e) {
			 sysLogger.log(LogLevel.ERROR, "Can't make receiver stub");
			e.printStackTrace();
		}
	}

	private void initVisitor() {	
		receiverVisitor.setCmd(DataPacketIDFactory.Singleton.makeID(IStringMsg.class), new StringMsgCmd(adptr));
		
		receiverVisitor.setCmd(DataPacketIDFactory.Singleton.makeID(ICommandRequestMsg.class), new CommandRequestMsgCmd(adptr, receiverVisitor, cmd2ModelAdapter));
	}
	
	public Set<INamedReceiver> getRoomRoster(){
		return this.roomRoster;
	}

//	public void removeParticipant(INamedReceiver person){
//		roomRoster.remove(person);
//		this.adptr.updateMemberList(roomRoster);
//	}

	public INamedReceiver getMyNamedReceiver(){
		return this.myNamedReceiver;
	}
	/**
	 * @return
	 */
	public ReceiverDataPacketAlgo getReceiverMsgAlgo() {
		return this.receiverVisitor;
	}
	
	/**
	 * start the chat room - create a pubsubsync manager
	 */
	public void start() {
		this.initVisitor();
	}
	
	public void sendThreadedMessage(INamedReceiver receiver, ReceiverDataPacket<? extends IReceiverMsg> message) {
		Thread thread = new Thread(() -> {
		try {
			receiver.sendMessage(message);
		}
		catch (Exception e) {
			// TODO: handle exception
		}});
		thread.start();
	}
	
	/**
	 * Send a string message to the chat room.
	 * @param msg the message to be sent
	 */
	public void sendStringMsg(String msg) {
		
		IStringMsg stringMsg = new StringMsg(msg);
		for (INamedReceiver person: this.roomRoster) {
			try {
				person.sendMessage(new ReceiverDataPacket<IStringMsg>(stringMsg, this.myNamedReceiver));
			} catch (RemoteException e) {
				adptr.displayMsg("Message \"" + msg +"\" failed to be sent!.");
				e.printStackTrace();
			}
		}
		adptr.displayStatus("Message \"" + msg + "\" successfully sent to the ChatRoom!");
	}
	
	/**
	 * Send a ballworld instance to the chat room
	 */
	public void sendBallWorld() {
		
		//toDo: determine what data is needed to send a ball world
		// just the controller, all everything(model,view, controller?)
		
		try {
			BallWorldController controller = new BallWorldController();
			adptr.displayStatus("A Ball World program has been successfully sent!");
		}
		catch (Exception e) {
			adptr.displayStatus("Exception occured: "+e.toString());
		}
	}
	
	public void leaveRoom() {	
		adptr.removeRoom();
	}
	
//	public void removeUser(INamedReceiver namedReceiver) {
//		this.roomRoster.remove(namedReceiver);
//		adptr.updateMemberList(roomRoster);
//	}
//	
//	public void addUser(INamedReceiver namedUser) {
//		this.roomRoster.add(namedUser);
//		adptr.updateMemberList(this.roomRoster);
//	}
	
	public void sendTextMsg(String text) {
		try {
			myReceiver.sendMessage(new ReceiverDataPacket<IStringMsg>(new StringMsg(text), myNamedReceiver));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendCmdMsg(AReceiverDataPacketAlgoCmd<?> cmd, IDataPacketID cmdId) {
		try {
			myReceiver.sendMessage(new ReceiverDataPacket<ICommandMsg>(new CommandMsg(cmd,cmdId), myNamedReceiver));
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
