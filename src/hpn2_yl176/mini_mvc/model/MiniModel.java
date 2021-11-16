/**
 * 
 */
package hpn2_yl176.mini_mvc.model;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.swing.text.StyledEditorKit.ForegroundAction;

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
	
	private ILogger viewLogger;
	private ILogger sysLogger;
	
	private IReceiver receiver = new IReceiver() {
		
		@Override
		public void sendMessage(ReceiverDataPacket<?> packet) throws RemoteException {
			// TODO Auto-generated method stub
			packet.execute(receiverVisitor);
		}
	};
	
	private INamedReceiver namedReceiver;
	
//	private ChatRoom chatRoom;
	private ChatAppConfig config;
	/**
	 * @param adptr the adapter
	 */
	public MiniModel(UUID id, String friendlyName, IMini2ViewAdptr adptr) {
		this.adptr = adptr;
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
		this.roomRoster = adptr.getRoomRoster();
	}

	private void initVisitor() {
		receiverVisitor = new ReceiverDataPacketAlgo(new DefaultReceiverMsgCmd());
		
		receiverVisitor.setCmd(DataPacketIDFactory.Singleton.makeID(IStringMsg.class), new StringMsgCmd(adptr));
		
		receiverVisitor.setCmd(DataPacketIDFactory.Singleton.makeID(ICommandRequestMsg.class), new CommandRequestMsgCmd(adptr, receiverVisitor));
	}
	
	public Set<INamedReceiver> getRoomRoster(){
		return this.roomRoster;
	}

	public void removeParticipant(INamedReceiver person){
		roomRoster.remove(person);
		this.adptr.updateMemberList(roomRoster);
	}

	public INamedReceiver getMyNamedReceiver(){
		return this.namedReceiver;
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
		try {
			IReceiver receiver = (IReceiver) UnicastRemoteObject.exportObject(this.receiver, config.getRMIPort());
			this.namedReceiver = new NamedReceiver(receiver, adptr);
			adptr.updateMemberList(roomRoster);
		}
		catch (Exception e) {
			// TODO: handle exception 
			e.printStackTrace();
		}
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
	 * Send a message to the chat room.
	 * @param msg the message to be sent
	 */
	public void sendMsg(String msg) {
		try {
			adptr.displayStatus("Message " + msg + "successfully sent!");
		}
		catch (Exception e){
			adptr.displayStatus("Exception occured: "+e.toString());
		}
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
	
	public void removeUser(INamedReceiver namedReceiver) {
		this.roomRoster.remove(namedReceiver);
		adptr.updateMemberList(roomRoster);
	}
	
	public void addUser(INamedReceiver namedUser) {
		this.roomRoster.add(namedUser);
		adptr.updateMemberList(this.roomRoster);
	}
	
	public void sendTextMsg(String text) {
		try {
			receiver.sendMessage(new ReceiverDataPacket<IStringMsg>(new StringMsg(text), namedReceiver));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendCmdMsg(AReceiverDataPacketAlgoCmd<?> cmd, IDataPacketID cmdId) {
		try {
			receiver.sendMessage(new ReceiverDataPacket<ICommandMsg>(new CommandMsg(cmd,cmdId), namedReceiver));
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
