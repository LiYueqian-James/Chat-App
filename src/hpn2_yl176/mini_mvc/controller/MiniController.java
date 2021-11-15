package hpn2_yl176.mini_mvc.controller;
import hpn2_yl176.mini_mvc.view.ChatRoomView;
import hpn2_yl176.mini_mvc.model.IMini2ViewAdptr;
import hpn2_yl176.mini_mvc.view.IView2MiniAdptr;
import hpn2_yl176.mini_mvc.model.MiniModel;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import common.receiver.INamedReceiver;
import common.receiver.ReceiverDataPacketAlgo;
import provided.logger.ILogger;
import provided.logger.LogLevel;
import provided.pubsubsync.IPubSubSyncChannelUpdate;
import provided.pubsubsync.IPubSubSyncManager;
import provided.pubsubsync.IPubSubSyncUpdater;


/**
 * @author James Li
 *
 */
public class MiniController {
	/**
	 * The view of a chat room.
	 */
	private ChatRoomView view;
	
	/**
	 * The model behind a chat room.
	 */
	private MiniModel model;
	
	/**
	 * Mini-controller 2 Main model adapter.
	 */
	private IMini2MainAdptr mini2MainAdptr;
	
	/**
	 * The manager to create data channels, i.e. chat rooms.
	 */
	private IPubSubSyncManager pubSubSyncManager; 
	
	/**
	 * The host of the chat room.
	 */
	private INamedReceiver host;
	
	/**
	 * Name of the chat room.
	 */
	private String roomName;
	
	private ILogger sysLogger;
	
	/**
	 * The visitor to process the receiver message.
	 */
	private ReceiverDataPacketAlgo visitor;
	
	/**
	 * The chat room ID.
	 */
	private UUID chatRoomID;
	
	/**
	 * Construct a miniController, representing a instance of the chat room!
	 * @param mini2MainAdptr the adapter towards the main model
	 * @param chatRoomID the chatRoomID.
	 */
	public MiniController(IMini2MainAdptr mini2MainAdptr, UUID chatRoomID) {
		
		this.mini2MainAdptr = mini2MainAdptr;
		
		this.pubSubSyncManager = this.mini2MainAdptr.getPubSubSyncManager();
		
		this.sysLogger = this.mini2MainAdptr.getLogger();
		
		this.chatRoomID = chatRoomID;
		
		model = new MiniModel(new IMini2ViewAdptr() {

			@Override
			public void displayMsg(String msg) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void displayStatus(String status) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Set<INamedReceiver> getRoomRoster() {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		
		view = new ChatRoomView(new IView2MiniAdptr() {

			@Override
			public void sendMsg(String msg) {
				model.sendMsg(msg);
				
			}

			@Override
			public void leave() {
				
			}

			@Override
			public void sendBallWorld() {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	/**
	 * Start the chat room by giving the room a manager.
	 * @param pubSubSyncManager data channel manager.
	 */
	public void start() {
		view.start();
		model.start();
	}
	
	/**
	 * @return the chat room id.
	 */
	public UUID getRoomID() {
		return this.getRoomID();
	}
	
	
}
