package hpn2_yl176.mini_mvc.controller;
import hpn2_yl176.mini_mvc.view.MiniView;
import hpn2_yl176.main_mvc.IMain2MiniAdptr;
import hpn2_yl176.main_mvc.model.ChatAppConfig;
import hpn2_yl176.mini_mvc.model.IMini2ViewAdptr;
import hpn2_yl176.mini_mvc.view.IView2MiniAdptr;
import hpn2_yl176.mini_mvc.model.MiniModel;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import java.awt.Component;

import common.adapter.IComponentFactory;
import common.connector.INamedConnector;
import common.receiver.INamedReceiver;
import common.receiver.ReceiverDataPacketAlgo;
import provided.logger.ILogger;
import provided.logger.LogLevel;
import provided.pubsubsync.IPubSubSyncChannelUpdate;
import provided.pubsubsync.IPubSubSyncManager;
import provided.pubsubsync.IPubSubSyncUpdater;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.RMIUtils;


/**
 * @author James Li
 *
 */
public class MiniController {
	/**
	 * The view of a chat room.
	 */
	private MiniView view;
	
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

	private IPubSubSyncChannelUpdate<HashSet<INamedReceiver>> chatRoom;
	
	private HashSet<INamedReceiver> roster = new HashSet<>();
	
	private IMain2MiniAdptr chatRoomAdptr;
	
	/**
	 * Construct a miniController, representing a instance of the chat room!
	 * @param mini2MainAdptr the adapter towards the main model
	 * @param chatRoomID the chatRoomID.
	 */
	public MiniController(String friendlyName, IMini2MainAdptr mini2MainAdptr) {
		
		this.mini2MainAdptr = mini2MainAdptr;

		this.pubSubSyncManager = this.mini2MainAdptr.getPubSubSyncManager();
		
		this.sysLogger = this.mini2MainAdptr.getLogger();
		
		this.roomName = friendlyName;

		model = new MiniModel(chatRoomID, friendlyName, new IMini2ViewAdptr() {

			@Override
			public void displayMsg(String msg) {
				view.appendMessage(msg);
			}

			@Override
			public void displayStatus(String status) {
				view.appendStatus(status);
			}

			@Override
			public ILogger getSysLogger() {
				return mini2MainAdptr.getLogger();
			}

			@Override
			public IRMIUtils getRmiUtils() {
				return mini2MainAdptr.getRmiUtils();
			}

			@Override
			public String getUserName() {
				return mini2MainAdptr.getUserName();
			}

			@Override
			public ChatAppConfig getConfig() {
				return mini2MainAdptr.getConfig();
			}

			@Override
			public INamedConnector getNamedConnector() {
				return mini2MainAdptr.getNamedConnector();
			}

//			@Override
//			public void updateMemberList(Set<INamedReceiver> namedReceivers) {
//				Set<String> memberList = new HashSet<>();
//				for (INamedReceiver namedReceiver: namedReceivers) {
//					memberList.add(namedReceiver.getName());
//				}
//				view.setRoomRoster(memberList);
//			}

			@Override
			public void removeRoom() {
				MiniController.this.stop();
			}

			@Override
			public Set<INamedReceiver> getRoomRoster() {
				return roster;
			}

			@Override
			public void addScrollingComponent(IComponentFactory fac, String name) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void addFixedComponent(IComponentFactory fac, String name) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		view = new MiniView(new IView2MiniAdptr() {

			@Override
			public void sendMsg(String msg) {
				model.sendStringMsg(msg);
			}

			@Override
			public void leave() {
				model.leaveRoom();
			}

			@Override
			public void sendBallWorld() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public Set<String> getRoomRoster() {
				Set<INamedReceiver> roster = model.getRoomRoster();
				Set<String> stringRoster = new HashSet<>();
				for (INamedReceiver person: roster) {
					stringRoster.add(person.toString());
					}
			return stringRoster;	
			}
			
		});
		

	}
	
	/**
	 * Start the chat room by giving the room a manager.
	 * @param pubSubSyncManager data channel manager.
	 */
	public void start() {
		model.start();
		view.start();
		
		chatRoomAdptr = new IMain2MiniAdptr() {

			@Override
			public INamedReceiver getNamedReceiver() {
				return getMyNamedReceiver();
			}

			@Override
			public Component getRoomPanel() {
				return getMyRoomPanel();
			}

			@Override
			public void start() {
				start();
				
			}

//			@Override
//			public void removeParticipant(INamedReceiver person) {
//				miniController.removePerson(person);
//			}

//			@Override
//			public ReceiverDataPacketAlgo getReceiverMsgAlgo() {
//				return miniController.getReceiverMsgAlgo();
//			}
//
			@Override
			public UUID getChatRoomID() {
				return getRoomID();
			}

			@Override
			public String getRoomName() {
				return roomName;
			}

			@Override
			public void quit() {
				stop();
				
			}
			
		};
		
	}
	
	public void makeNewRoom() {
		Set<String> nameRoster = new HashSet<>();
		/*
		* The (reference to the) roster will be passed to the mini controller so that it gets updated whenever 
		* the data channel changes.
		*/
		IPubSubSyncChannelUpdate<HashSet<INamedReceiver>> chatRoom = pubSubSyncManager.createChannel(this.roomName, roster, 
			(pubSubSyncData) -> {
				roster.clear();
				nameRoster.clear();
				roster.addAll(pubSubSyncData.getData());
				for (INamedReceiver person: roster) {
					nameRoster.add(person.getName());
				}
				view.updateRoomRoster(nameRoster);
			},
			(statusMessage) -> {
				sysLogger.log(LogLevel.DEBUG, "room " + this.roomName +" has been made sucessfully.");
			});
		
		this.chatRoomID = chatRoom.getChannelID();
		chatRoom.update(IPubSubSyncUpdater.makeSetAddFn(model.getMyNamedReceiver()));
	}
	
	public void joinRoom(UUID roomID) {
		Set<String> nameRoster = new HashSet<>();
		/*
		* The (reference to the) roster will be passed to the mini controller so that it gets updated whenever 
		* the data channel changes.
		*/
		IPubSubSyncChannelUpdate<HashSet<INamedReceiver>> chatRoom = pubSubSyncManager.subscribeToUpdateChannel(roomID, 
			(pubSubSyncData) -> {
				roster.clear();
				nameRoster.clear();
				roster.addAll(pubSubSyncData.getData());
				for (INamedReceiver person: roster) {
//					System.out.println(person.getName()+"is already in the room");
					nameRoster.add(person.getName());
				}
				view.updateRoomRoster(nameRoster);
			},
			(statusMessage) -> {
				sysLogger.log(LogLevel.DEBUG, "room " + this.roomName +" has been made sucessfully.");
			});
		
		this.chatRoomID = roomID;
		chatRoom.update(IPubSubSyncUpdater.makeSetAddFn(model.getMyNamedReceiver()));
	}
	
	/**
	 * Stop the current chat room - i.e. remove myself from the room.
	 */
	public void stop() {
		chatRoom.update(IPubSubSyncUpdater.makeSetRemoveFn(model.getMyNamedReceiver()));
		chatRoom.unsubscribe();
		this.mini2MainAdptr.removePanel(view);
	}
	
	/**
	 * @return the chat room id.
	 */
	public UUID getRoomID() {
		return chatRoomID;
	}

	public Component getMyRoomPanel(){
		return this.view;
	}

	public INamedReceiver getMyNamedReceiver(){
		return this.model.getMyNamedReceiver();
	}

//	public void removePerson(INamedReceiver person){
//		this.model.removeParticipant(person);
//	}

	public ReceiverDataPacketAlgo getReceiverMsgAlgo(){
		return this.model.getReceiverMsgAlgo();
	} 
	
	public IMain2MiniAdptr getRoomAdptr() {
		return chatRoomAdptr;
	}

//	public void removeRoomHelper() {
//	HashSet<INamedReceiver> roster = new HashSet<>();
//	chatRoom.update(IPubSubSyncUpdater.makeSetRemoveFn(this.getMyNamedReceiver()));
//	chatRoom.unsubscribe();
//	this.mini2MainAdptr.removePanel(view);
//	}
	
	
}
