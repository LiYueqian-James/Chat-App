package hpn2_yl176.mini_mvc.model;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import javax.swing.JFileChooser;

import java.awt.Component;

import common.adapter.ICmd2ModelAdapter;
import common.adapter.IComponentFactory;
import common.receiver.AReceiverDataPacketAlgoCmd;
import common.receiver.INamedReceiver;
import common.receiver.IReceiver;
import common.receiver.ReceiverDataPacket;
import common.receiver.ReceiverDataPacketAlgo;
import common.receiver.messages.ICommandMsg;
import common.receiver.messages.ICommandRequestMsg;
import common.receiver.messages.IReceiverMsg;
import common.receiver.messages.IStringMsg;
import hpn2_yl176.main_mvc.model.ChatAppConfig;
import hpn2_yl176.msg.receiverMsgCmd.CommandMsgCmd;
import hpn2_yl176.msg.receiverMsgCmd.CommandRequestMsgCmd;
import hpn2_yl176.msg.receiverMsgCmd.DefaultReceiverMsgCmd;
import hpn2_yl176.msg.receiverMsgCmd.HeartMessageCmd;
import hpn2_yl176.msg.receiverMsgCmd.StringMsgCmd;
import hpn2_yl176.msg.receiverMsgCmd.TabMsgCmd;
import hpn2_yl176.msg.receiverMsgImpl.CommandMsg;
import hpn2_yl176.msg.receiverMsgImpl.CommandRequestMsg;
import hpn2_yl176.msg.receiverMsgImpl.HeartMessage;
import hpn2_yl176.msg.receiverMsgImpl.StringMsg;
import hpn2_yl176.msg.receiverMsgImpl.TabMsg;
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

/**
 * @author James Li
 *
 */
public class MiniModel {

	private IMini2ViewAdptr adptr;

	private ReceiverDataPacketAlgo receiverVisitor;

	private Set<INamedReceiver> roomRoster;

	private HashMap<IDataPacketID, ArrayList<ReceiverDataPacket<IReceiverMsg>>> unexecutedMsgs = new HashMap<>();

	private MixedDataDictionary mixedDictionary = new MixedDataDictionary();

	private ICmd2ModelAdapter cmd2ModelAdapter = new ICmd2ModelAdapter() {

		@Override
		public <T extends IReceiverMsg> void send(T data, INamedReceiver recv) {
			if (roomRoster.contains(recv)) {
				try {
					recv.sendMessage(new ReceiverDataPacket<T>(data, myNamedReceiver));
				} catch (RemoteException e) {
					sysLogger.log(LogLevel.ERROR, "Request to send message failed");
					e.printStackTrace();
				}
			}

		}

		@Override
		public File saveFile(String defaultName) {
			// parent component of the dialog
			Component parentFrame = adptr.getViewPanel();

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Specify a file to save");

			int userSelection = fileChooser.showSaveDialog(parentFrame);

			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToSave = fileChooser.getSelectedFile();
				return fileToSave;
			}

			return null;

		}

		@Override
		public <T> T put(MixedDataKey<T> key, T value) {
			// TODO Auto-generated method stub
			return mixedDictionary.put(key, value);
		}

		@Override
		public String getRoomName() {
			return adptr.getRoomName();
		}

		@Override
		public String getInstanceName() {
			return adptr.getBoundName();
		}

		@Override
		public File getFile() {
			return this.saveFile("");
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
			adptr.addScrollingComponent(fac, name);
		}

		@Override
		public void buildFixedComponent(IComponentFactory fac, String name) {
			// TODO Auto-generated method stub
			adptr.addFixedComponent(fac, name);
		}

		@Override
		public <T extends IReceiverMsg> void broadcast(T msg) {
			// TODO Auto-generated method stub
			for (INamedReceiver person : roomRoster) {
				this.send(msg, person);
			}

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
		DefaultReceiverMsgCmd dCmd = new DefaultReceiverMsgCmd(unexecutedMsgs);
		receiverVisitor = new ReceiverDataPacketAlgo(dCmd);
		this.myReceiver = new Receiver(this.receiverVisitor);

		try {
			IReceiver receiverStub = (IReceiver) UnicastRemoteObject.exportObject(this.myReceiver, config.getRMIPort());
			this.myNamedReceiver = new NamedReceiver(receiverStub, this.adptr.getUserName(),
					this.adptr.getNamedConnector());
		} catch (Exception e) {
			sysLogger.log(LogLevel.ERROR, "Can't make receiver stub");
			e.printStackTrace();
		}
		dCmd.setNamedReceiver(myNamedReceiver);
	}

	private void initVisitor() {
		receiverVisitor.setCmd(DataPacketIDFactory.Singleton.makeID(IStringMsg.class), new StringMsgCmd(adptr));
		receiverVisitor.setCmd(CommandRequestMsg.GetID(), new CommandRequestMsgCmd(this.myNamedReceiver, receiverVisitor));
//		receiverVisitor.setCmd(HeartMessage.GetID(), new HeartMessageCmd(cmd2ModelAdapter));
//		receiverVisitor.setCmd(TabMsg.GetID(), new TabMsgCmd(cmd2ModelAdapter));
		CommandMsgCmd cmd = new CommandMsgCmd(receiverVisitor, unexecutedMsgs);
		cmd.setCmd2ModelAdpt(cmd2ModelAdapter);
		receiverVisitor.setCmd(CommandMsg.GetID(), cmd);
	}

	public Set<INamedReceiver> getRoomRoster() {
		return this.roomRoster;
	}

	//	public void removeParticipant(INamedReceiver person){
	//		roomRoster.remove(person);
	//		this.adptr.updateMemberList(roomRoster);
	//	}

	public INamedReceiver getMyNamedReceiver() {
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
			} catch (Exception e) {
				// TODO: handle exception
			}
		});
		thread.start();
	}

	/**
	 * Send a string message to the chat room.
	 * @param msg the message to be sent
	 */
	public void sendStringMsg(String msg) {

		IStringMsg stringMsg = new StringMsg(msg);
		this.sendMsg(stringMsg);
	}

	/**
	 * Send a heart message to the chat room
	 */
	public void sendHeartMsg() {

		//toDo: determine what data is needed to send a ball world
		// just the controller, all everything(model,view, controller?)

		HeartMessage msg = new HeartMessage();
		this.sendMsg(msg);
	}
	
	/**
	 * Send a heart message to the chat room
	 */
	public void sendTabMsg() {

		//toDo: determine what data is needed to send a ball world
		// just the controller, all everything(model,view, controller?)

		TabMsg msg = new TabMsg();
		this.sendMsg(msg);
	}

	/**
	 * @param msg the message to be sent; can be any IRecevierMsg.
	 */
	private void sendMsg(IReceiverMsg msg) {
		for (INamedReceiver person : this.roomRoster) {
			try {
				person.sendMessage(new ReceiverDataPacket<IReceiverMsg>(msg, this.myNamedReceiver));
			} catch (RemoteException e) {
				adptr.displayMsg("Message \"" + msg.toString() + "\" failed to be sent!.");
				e.printStackTrace();
			}
		}
		adptr.displayStatus("Message \"" + msg.toString() + "\" successfully sent to the ChatRoom!");
	}

	//	public void leaveRoom() {
	//		adptr.removeRoom();
	//	}

	//	public void removeUser(INamedReceiver namedReceiver) {
	//		this.roomRoster.remove(namedReceiver);
	//		adptr.updateMemberList(roomRoster);
	//	}
	//	
	//	public void addUser(INamedReceiver namedUser) {
	//		this.roomRoster.add(namedUser);
	//		adptr.updateMemberList(this.roomRoster);
	//	}

	//	public void sendCmdMsg(CommandMsg msg) {
	//		for (INamedReceiver person: this.roomRoster) {
	//			try {
	//				person.sendMessage(new ReceiverDataPacket<IReceiverMsg>(msg, myNamedReceiver));
	//			}
	//			catch (Exception e) {
	//				// TODO: handle exception
	//				e.printStackTrace();
	//			}
	//		}
	//	}

}
