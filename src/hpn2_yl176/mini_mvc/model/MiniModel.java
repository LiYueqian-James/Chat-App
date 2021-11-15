/**
 * 
 */
package hpn2_yl176.mini_mvc.model;
import java.util.HashSet;

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
	
	private IMini2ViewAdptr adptr;
	
	private ReceiverDataPacketAlgo receiverVisitor;
	
	/**
	 * @param adptr the adapter
	 */
	public MiniModel(IMini2ViewAdptr adptr) {
		this.adptr = adptr;
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
