package hpn2_yl176.controller;
import hpn2_yl176.mini_mvc.view.ChatRoomView;
import hpn2_yl176.mini_mvc.model.IMini2ViewAdptr;
import hpn2_yl176.mini_mvc.view.IView2MiniAdptr;
import hpn2_yl176.mini_mvc.model.MiniModel;

import java.util.HashSet;

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
	 * Construct a miniController.
	 * @param pubSubSyncManager the pubsubsync manager.
	 * @param roomName the name of the chat room.
	 * @param sysLogger the logger to display status.
	 */
	public MiniController(IPubSubSyncManager pubSubSyncManager, String roomName, ILogger sysLogger) {
		
		//TODO: maybe this should be done via some adapter.
		this.pubSubSyncManager = pubSubSyncManager;
		
		this.roomName = roomName;
		
		this.sysLogger = sysLogger;
		
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
			public IPubSubSyncManager getPubSubSyncManager() {
				return pubSubSyncManager;
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
	
	
}
