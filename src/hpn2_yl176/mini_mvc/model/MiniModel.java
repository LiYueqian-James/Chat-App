/**
 * 
 */
package hpn2_yl176.mini_mvc.model;
import java.util.HashSet;
import java.util.Set;

import common.receiver.INamedReceiver;
import common.receiver.ReceiverDataPacketAlgo;
import controller.BallWorldController;
import hpn2_yl176.main_mvc.IMain2MiniAdptr;
import provided.logger.LogLevel;
import provided.pubsubsync.IPubSubSyncChannelUpdate;
import provided.pubsubsync.IPubSubSyncUpdater;
/**
 * @author James Li
 *
 */
public class MiniModel {

	private IReceiver receiver;

	private ReceiverDataPacketAlgo receiverVisitor;
	
	private IMini2ViewAdptr adptr;
	
	private ReceiverDataPacketAlgo receiverVisitor;
	
	private Set<INamedReceiver> roomRoster;
	
	/**
	 * @param adptr the adapter
	 */
	public MiniModel(IMini2ViewAdptr adptr) {
		this.adptr = adptr;
		this.roomRoster = adptr.getRoomRoster();
	}

	private void initVisitor() {
		receiverVistor = new ReceiverDataPacketAlgo(new AReceiverDataPacketAlgoCmd<IReceiverMsg>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7464317584958038535L;

			@Override
			public Void apply(IDataPacketID index, ReceiverDataPacket<IReceiverMsg> host, Void... params) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
				
			}
		});
		
		receiverVistor.setCmd(DataPacketIDFactory.Singleton.makeID(IStringMsg.class), new AReceiverDataPacketAlgoCmd<IStringMsg>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 6286359836516888655L;

			@Override
			public Void apply(IDataPacketID id, ReceiverDataPacket<IStringMsg> host, Void... params) {
				// TODO Auto-generated method stub
				adptr.displayMsg(host.getSender().getName() + ": " + host.getData().toString());
				return null;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
				
			}
		} );
		
		receiverVistor.setCmd(DataPacketIDFactory.Singleton.makeID(ICommandMsg.class), new AReceiverDataPacketAlgoCmd<ICommandMsg>() {

			@Override
			public Void apply(IDataPacketID index, ReceiverDataPacket<ICommandMsg> host, Void... params) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				// TODO Auto-generated method stub
				
			}
		});
		this.roomRoster = adptr.getRoomRoster();
	}
	
	public Set<INamedReceiver> getRoomRoster(){
		return this.roomRoster;
		
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
		try {
			IReceiver receiver = (IReceiver) UnicastRemoteObject.exportObject(receiver, config.getPort());
		}
		catch (Exception e) {
			// TODO: handle exception
		}
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
	

}
